package home.lflt.utils;

import home.lflt.model.Lot;
import home.lflt.model.Stock;
import home.lflt.model.fmpQuote;
import home.lflt.repo.StockRepo;

import java.util.Random;

import static home.lflt.utils.Utils.getQuote;

public class BuyingAlgorithm {
    private StockRepo stockRepo;
    private double funds = -1;
    private int stockCounter = -1;
    Lot lot;
    boolean picked = false;

    public BuyingAlgorithm(StockRepo stockRepo, double funds){
        this.stockRepo = stockRepo;
        this.funds = funds;
        this.stockCounter = (int) stockRepo.count();
    }

    public Lot buyStockRandomly() {
        Stock stock = null;
        fmpQuote quote = null;

        String symbol = "UNDEFINED";
        int units = -1;
        int price = -1;

        if(stockRepo.count() != 0) {
            while (!picked) {
                int randomIndex = getRandomMargins(0, stockCounter);
                stock = stockRepo.getByIndex(randomIndex);
                quote = getQuote(stock.getSymbol());
                if (quote.getPrice() <= funds) picked = true;
            }

            symbol = stock.getSymbol();
            units = (int) (funds / quote.getPrice());
            return new Lot(symbol, units, quote.getPrice());
        }

        return new Lot(symbol, units, price);
    }

    public int getRandomMargins(int min, int max) {
        boolean loop = true;
        Random rand = new Random();
        int num = 0;

        while(loop) {
            num = rand.nextInt(max); // border not included
            if(min <= num && num <= max) loop = false;
        }

        return num;
    }

}
