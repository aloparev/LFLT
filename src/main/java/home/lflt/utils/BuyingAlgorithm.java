package home.lflt.utils;

import home.lflt.controller.LotController;
import home.lflt.model.Lot;
import home.lflt.model.Stock;
import home.lflt.model.fmpQuote;
import home.lflt.repo.LotRepo;
import home.lflt.repo.StockRepo;

import java.util.Random;

import static home.lflt.utils.Utils.getQuote;

public class BuyingAlgorithm {
    private StockRepo stockRepo;
    private int funds = -1;
    private int stockCounter = -1;
    Lot lot;
    boolean picked = false;

    public BuyingAlgorithm(StockRepo stockRepo, int funds){
        this.stockRepo = stockRepo;
        this.funds = funds;
        this.stockCounter = (int) stockRepo.count();
    }

    public Lot buyStockRandomly() {
        Stock stock = null;
        fmpQuote quote = null;
        int units = -1;
        String symbol = "UNDEFINED";

        while(!picked) {
            int randomIndex = getRandomIntBordersInclusive(0, stockCounter - 1);
            stock = stockRepo.getByIndex(randomIndex);
            quote = getQuote(stock.getSymbol());

            if(quote.getPrice() <= funds) picked = true;
        }

        symbol = stock.getSymbol();
        units = funds / (int) quote.getPrice();
        return new Lot(symbol, units, quote.getPrice());
    }

    public int getRandomIntBordersInclusive(int min, int max) {
        boolean loop = true;
        Random rand = new Random();
        int num = 0;
        while(loop) {
            num = rand.nextInt(max + 1); //including border values
            if(min <= num && num <= max) loop = false;
        }
        return num;
    }

}
