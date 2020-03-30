package home.lflt.utils;

import home.lflt.model.Lot;
import home.lflt.model.Portfolio;
import home.lflt.repo.LotRepo;
import home.lflt.repo.PortfolioRepo;
import home.lflt.repo.QuoteRepo;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static home.lflt.utils.Utils.checkPortfolio;

@Slf4j
@Service
public class PortfolioUpdater {
    private PortfolioRepo portfolioRepo;
    private StockRepo stockRepo;
    private LotRepo lotRepo;

    public PortfolioUpdater(PortfolioRepo portfolioRepo, StockRepo stockRepo, LotRepo lotRepo) {
        this.portfolioRepo = portfolioRepo;
        this.stockRepo = stockRepo;
        this.lotRepo = lotRepo;
    }

//    second, minute, hour, day of month, month, day(s) of week
    @Transactional
//    @Scheduled(cron = "1 2 19 * * MON-FRI")
    @Scheduled(cron = "1 * * * * ?")
    public void update() {
        log.info("start updater");
        Iterable<Portfolio> portfolios = portfolioRepo.getByTypeNot("USER");

        for(Portfolio pp : portfolios) {
            log.info("pp found: " + pp);
            boolean update = checkPortfolio(pp.getUstamp(), pp.getEpochs());

            if(update)
                switch (pp.getType()) {
                    case "DROP":
                        log.info("drop case");
                        break;
                    case "POSITIVE":
                        log.info("positive case");
                        break;
                    default:
                        log.info("random (default) case");
                        Lot newLot = new BuyingAlgorithm(stockRepo, pp.getFunds()).buyStockRandomly();
                        newLot.setPortfolio(pp);
                        log.info("bought lot=" + newLot);

                        Lot alreadyExists = lotRepo.getByPortfolioIdAndSymbol(pp.getId(), newLot.getSymbol());
                        if(alreadyExists == null) {
                            pp.getLots().add(newLot);
                            log.info("alreadyExists == null");
                        } else {
                            alreadyExists.setUnits(alreadyExists.getUnits() + newLot.getUnits());
                            alreadyExists.setIpt(alreadyExists.getIpt() + newLot.getIpt());
                            alreadyExists.setIp(alreadyExists.getIpt() / alreadyExists.getUnits());
                            log.info("alreadyExists exists");
                        }
                        pp.setUstamp(LocalDateTime.now());
                        log.info("pp updated: " + pp);
                        break;
                }
        }
    }
}
