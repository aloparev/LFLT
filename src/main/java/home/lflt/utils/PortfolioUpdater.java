package home.lflt.utils;

import home.lflt.model.Lot;
import home.lflt.model.Portfolio;
import home.lflt.repo.LotRepo;
import home.lflt.repo.PortfolioRepo;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static home.lflt.utils.Utils.checkPortfolioUpdate;

@Transactional
@Slf4j
@Service
public class PortfolioUpdater {
    private final PortfolioRepo portfolioRepo;
    private final StockRepo stockRepo;
    private final LotRepo lotRepo;

    public PortfolioUpdater(PortfolioRepo portfolioRepo, StockRepo stockRepo, LotRepo lotRepo) {
        this.portfolioRepo = portfolioRepo;
        this.stockRepo = stockRepo;
        this.lotRepo = lotRepo;
    }

//    sec, min, hour, day of month, month, weekday
//    here: 13h 2min 1sec
//    @Transactional
//    @Scheduled(cron = "1 2 13 * * MON-FRI", zone = "GMT")
    @Scheduled(cron = "1 * * * * ?")
    public void update() {
        log.info("start updater 1+2");
        updateRandomPortfolios();
        fundUserPortfolios();
    }

    /**
     * balance here is just left overs
     */
    public void updateRandomPortfolios() {
        Iterable<Portfolio> portfolios = portfolioRepo.getByType("RANDOM");

        for(Portfolio pp : portfolios) {
            log.info("pp before update: " + pp);
            boolean update = checkPortfolioUpdate(pp.getUstamp(), pp.getCron(), pp.getDelay());
            log.info("updateRandomPortfolios update flag: " + update);

            if(update)
                switch (pp.getType()) {
                    case "DROP":
                        log.info("drop case");
                        break;
                    case "POSITIVE":
                        log.info("positive case");
                        break;
                    default:
                        decrementEpochs(pp);
                        double cash = pp.getBalance() + pp.getFunds();
                        Lot newLot = new BuyingAlgorithm(stockRepo, cash).buyStockRandomly();
                        newLot.setPortfolio(pp);
                        pp.setBalance(cash - newLot.getIpt());
                        log.info("balance left = " + pp.getBalance() + "; bought lot=" + newLot);

                        Lot alreadyExists = lotRepo.getByPortfolioIdAndSymbol(pp.getId(), newLot.getSymbol());
                        if(alreadyExists == null) {
                            pp.getLots().add(newLot);
//                            log.info("alreadyExists == null");
                        } else {
                            alreadyExists.setUnits(alreadyExists.getUnits() + newLot.getUnits());
                            alreadyExists.setIpt(alreadyExists.getIpt() + newLot.getIpt());
                            alreadyExists.setIp(alreadyExists.getIpt() / alreadyExists.getUnits());
//                            log.info("alreadyExists exists");
                        }
                        pp.updateUstamp();
                        log.info("updateRandomPortfolios pp updated: " + pp);
                        break;
                }
        }
    }

    /**
     * user balance gets funded
     */
    public void fundUserPortfolios() {
        Iterable<Portfolio> portfolios = portfolioRepo.getByType("USER");

        for(Portfolio pp : portfolios) {
            log.info("fundUserPortfolios pp found: " + pp);
            boolean update = checkPortfolioUpdate(pp.getUstamp(), pp.getCron(), pp.getDelay());
            log.info("update = " + update);

            if(update) {
                decrementEpochs(pp);
                pp.setBalance(pp.getBalance() + pp.getFunds());
                pp.updateUstamp();
                log.info("fundUserPortfolios pp after: " + pp);
            }
        }
    }

    public void decrementEpochs(Portfolio pf) {
        if(pf.getEpochs() > 0)
            pf.setEpochs(pf.getEpochs() - 1);

        if(pf.getEpochs() < 1)
            pf.setType("OVER");
    }

    public void updatePortfolioBuyLot(long pid, String symbol) {
        Portfolio pp = portfolioRepo.getById(pid);

        Lot newLot = new BuyingAlgorithm(pp, stockRepo, pp.getBalance()).buyStock(symbol);
        log.info("balance=" + pp.getBalance() + "; bought lot=" + newLot);

        Lot alreadyExists = lotRepo.getByPortfolioIdAndSymbol(pp.getId(), newLot.getSymbol());
        if(alreadyExists == null) {
            pp.getLots().add(newLot);
//                            log.info("alreadyExists == null");
        } else {
            alreadyExists.setUnits(alreadyExists.getUnits() + newLot.getUnits());
            alreadyExists.setIpt(alreadyExists.getIpt() + newLot.getIpt());
            alreadyExists.setIp(alreadyExists.getIpt() / alreadyExists.getUnits());
//                            log.info("alreadyExists exists");
        }
        pp.setUstamp(LocalDateTime.now());
    }

    public void updatePortfolioBuyLot(long pid, String symb, int sum) {
        Portfolio pp = portfolioRepo.getById(pid);

        if(pp.getBalance() >= sum) {
            Lot newLot = new BuyingAlgorithm(pp, stockRepo, sum).buyStock(symb);
            if (newLot != null) {
                pp.setBalance(pp.getBalance() - newLot.getCpt());
                log.info("balance=" + pp.getBalance() + "; bought lot=" + newLot);
                Lot alreadyExists = lotRepo.getByPortfolioIdAndSymbol(pp.getId(), newLot.getSymbol());
                if (alreadyExists == null) {
                    pp.getLots().add(newLot);
//                            log.info("alreadyExists == null");
                } else {
                    alreadyExists.setUnits(alreadyExists.getUnits() + newLot.getUnits());
                    alreadyExists.setIpt(alreadyExists.getIpt() + newLot.getIpt());
                    alreadyExists.setIp(alreadyExists.getIpt() / alreadyExists.getUnits());
//                            log.info("alreadyExists exists");
                }
                pp.setUstamp(LocalDateTime.now());
            }
        }
    }

    public void updatePortfolioSellLot(long pid, String symb) {
        Portfolio pp = portfolioRepo.getById(pid);

        Lot lot = lotRepo.getByPortfolioIdAndSymbol(pid, symb);
        lotRepo.delete(lot);
        pp.setBalance(pp.getBalance() + lot.getUnits() * lot.getCp());
        log.info("balance=" + pp.getBalance() + "; sold=" + lot);
        pp.setUstamp(LocalDateTime.now());
    }
}
