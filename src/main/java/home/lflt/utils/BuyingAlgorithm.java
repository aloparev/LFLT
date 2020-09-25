package home.lflt.utils;

import home.lflt.model.*;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

import static home.lflt.utils.Utils.getQuoteMi;
import static home.lflt.utils.Utils.getRandomIntBetween;

@Slf4j
public class BuyingAlgorithm {
//    @Autowired
    private final StockRepo stockRepo;
    private double funds = -1;
    private int stockCounter = -1;
    private boolean picked = false;
    private Portfolio portfolio;


    public BuyingAlgorithm(StockRepo stockRepo, double funds){
        this.stockRepo = stockRepo;
        this.funds = funds;
        this.stockCounter = stockRepo.getCount();
//        this.stockCounter = (int) stockRepo.count();
    }

    public BuyingAlgorithm(Portfolio pp, StockRepo stockRepo, double funds){
        this(stockRepo, funds);
        this.portfolio = pp;
    }

    /**
     * buy new stock for portfolio and update balance/funds
     * @param symbol stock ticker for purchase
     * @return new lot
     */
    public Lot buyStock(String symbol) {
        Quote quote = getQuoteMi(symbol);
        Stock stock = stockRepo.getBySymbol(symbol);
        int units = (int) (funds / quote.getPrice());

        if(units > 0) {
            portfolio.setBalance(portfolio.getBalance() - units * quote.getPrice());
            return new Lot(portfolio, symbol, stock.getName(), units, quote.getPrice());
        } else {
            return null;
        }
    }

    public Lot buyStockRandomly() {
        Stock stock = null;
        Quote quote = null;

        String symbol = "UNDEFINED";
        int units = -1;
        int price = -1;

        if(stockRepo.count() != 0) {
            while (!picked) {
                int randomIndex = getRandomIntBetween(0, stockCounter);
                stock = stockRepo.getByIndex(randomIndex);
                quote = getQuoteMi(stock.getSymbol());
                if (quote.getPrice() <= funds) picked = true;
            }

            symbol = stock.getSymbol();
            units = (int) (funds / quote.getPrice());
            return new Lot(symbol, stock.getName(), units, quote.getPrice());
        }

        return new Lot(symbol, symbol, units, price);
    }
}
