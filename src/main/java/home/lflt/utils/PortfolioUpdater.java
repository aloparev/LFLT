package home.lflt.utils;

import home.lflt.model.Portfolio;
import home.lflt.repo.LotRepo;
import home.lflt.repo.PortfolioRepo;
import home.lflt.repo.QuoteRepo;
import home.lflt.repo.StockRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Component
public class PortfolioUpdater {
    private PortfolioRepo portfolioRepo;
    private LotRepo lotRepo;
    private QuoteRepo quoteRepo;
    private StockRepo stockRepo;

    public PortfolioUpdater(PortfolioRepo portfolioRepo, LotRepo lotRepo, QuoteRepo quoteRepo, StockRepo stockRepo) {
        this.portfolioRepo = portfolioRepo;
        this.lotRepo = lotRepo;
        this.quoteRepo = quoteRepo;
        this.stockRepo = stockRepo;
    }

//    second, minute, hour, day of month, month, day(s) of week
    @Scheduled(cron = "1 0 1 * * ?")
    public void update() {
        Iterable<Portfolio> portfolios = portfolioRepo.getByTypeNot("USER");
        LocalDateTime dateNow = LocalDateTime.now();

        for(Portfolio pp : portfolios) {
            if(pp.getUstamp().plusDays(pp.getEpoch()).isAfter(dateNow))
                switch (pp.getType()) {
                    case "DROP":
                        break;
                    case "POSITIVE":
                        break;
                    default:
                        pp.getLots().add(new BuyingAlgorithm(stockRepo, pp.getFunds()).buyStockRandomly());
                };
        }
    }
}
