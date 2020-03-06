package home.lflt.controller;

import home.lflt.model.Stock;
import home.lflt.model.fmpQuote;
import home.lflt.repo.LotRepo;
import home.lflt.repo.QuoteRepo;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static home.lflt.utils.Utils.getQuote;

@Slf4j
@Controller
@RequestMapping("/lot")
public class PortfolioController {
    private LotRepo lotRepo;
    private StockRepo stockRepo;
    private QuoteRepo quoteRepo;

    public PortfolioController(LotRepo lotRepo, StockRepo stockRepo, QuoteRepo quoteRepo) {
        this.lotRepo = lotRepo;
        this.stockRepo = stockRepo;
        this.quoteRepo = quoteRepo;
    }
//
//    @GetMapping
//    public String test(Model model) {
//        Stock stock = stockRepo.getBySymbol("GOOG");
//        log.info("get stock: " + stock);
//
//        fmpQuote quote = getQuote(stock.getSymbol());
//        log.info("get quote: " + quote.toString());
//
//        int units = 10;
//        Lot lot = new Lot(stock.getSymbol(), units, quote.getPrice());
//        log.info("lot created: " + lot.toString());
//
//        lotRepo.save(lot);
//        return "stock";
//    }

    @GetMapping
    public String test2(Model model) {
        Iterable<Stock> stocks = stockRepo.findAll();
        log.info("stocks retrieved: " + stockRepo.count());

        for(Stock ss : stocks) {
            fmpQuote qq = getQuote(ss.getSymbol());
            log.info("quote retrieved: " + qq.toString());
            quoteRepo.save(qq);
        }

        return "stock";
    }



}
