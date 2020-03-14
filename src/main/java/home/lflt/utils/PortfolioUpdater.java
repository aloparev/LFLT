package home.lflt.utils;

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

@Slf4j
@Service
public class PortfolioUpdater {

    @Autowired
    private PortfolioRepo portfolioRepo;

    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private LotRepo lotRepo;

    private QuoteRepo quoteRepo;

//    public PortfolioUpdater(PortfolioRepo portfolioRepo, LotRepo lotRepo, QuoteRepo quoteRepo, StockRepo stockRepo) {
//        this.portfolioRepo = portfolioRepo;
//        this.lotRepo = lotRepo;
//        this.quoteRepo = quoteRepo;
//        this.stockRepo = stockRepo;
//    }

//    second, minute, hour,
//      day of month, month, day(s) of week
    @Transactional
    @Scheduled(cron = "1 * * * * ?")
    public void update() {
        log.info("start updater");
        Iterable<Portfolio> portfolios = portfolioRepo.getByTypeNot("USER");
        LocalDateTime dateNow = LocalDateTime.now();

        for(Portfolio pp : portfolios) {
            log.info("pp found: " + pp);
            if(pp.getUstamp().plusHours(pp.getEpoch()).isAfter(dateNow))
            log.info("if(pp.getUstamp().plusHours(pp.getEpoch()).isAfter(dateNow))");
//            if(pp.getUstamp().plusDays(pp.getEpoch()).isAfter(dateNow))
                switch (pp.getType()) {
                    case "DROP":
                        log.info("drop case");
                        break;
                    case "POSITIVE":
                        log.info("positive case");
                        break;
                    default:
                        log.info("random (default) case");
                        pp.getLots().add(new BuyingAlgorithm(stockRepo, pp.getFunds()).buyStockRandomly());
                        log.info("pp updated: " + pp);
                        break;
                };
        }
    }
}
