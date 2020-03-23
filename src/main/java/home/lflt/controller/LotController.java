package home.lflt.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import home.lflt.model.Lot;
import home.lflt.model.Stock;
import home.lflt.model.fmpQuote;
import home.lflt.repo.LotRepo;
import home.lflt.repo.PortfolioRepo;
import home.lflt.repo.QuoteRepo;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static home.lflt.utils.Utils.*;

@Slf4j
@Controller
@RequestMapping("/lot")
public class LotController {
    private LotRepo lotRepo;
    private StockRepo stockRepo;
    private QuoteRepo quoteRepo;
    private PortfolioRepo portfolioRepo;

    public LotController(LotRepo lotRepo, StockRepo stockRepo, QuoteRepo quoteRepo, PortfolioRepo portfolioRepo) {
        this.lotRepo = lotRepo;
        this.stockRepo = stockRepo;
        this.quoteRepo = quoteRepo;
        this.portfolioRepo = portfolioRepo;
    }

    @GetMapping("/test")
    public String test(Model model) {
        log.info("\tpublic String test(Model model)");

        Lot aa = lotRepo.getByPortfolioIdAndSymbol(0, "FB");
        log.info("get FB=" + aa);

        aa.setUnits(aa.getUnits() + 10);
        aa.setIpt(aa.getIpt() + 2000);
        aa.setIp(aa.getIpt() / aa.getUnits());
        log.info("FB modified=" + aa);

        return "stock";
    }

    @GetMapping("/update-quotes")
    public String saveAllQuotes(Model model) {
        log.info("/update-quotes");
        Iterable<Stock> stocks = stockRepo.findAll();
        log.info("stocks retrieved: " + stockRepo.count());

//        for(Stock ss : stocks) {
//            fmpQuote qq = getQuote(ss.getSymbol());
//            log.info("quote retrieved: " + qq.toString());
//            quoteRepo.save(qq);
//        }

        return "stock";
    }

    @GetMapping("/past-quotes")
    public String pastQuotes(Model model) {
        log.info("/past-quotes");
        LocalDate dateTo = LocalDate.now();
//        LocalDate dateFrom = dateTo.minusDays(1);
        Stock stock = stockRepo.getBySymbol("BKNG");
        log.info("stock retrieved: " + stock);

        for(int i = 0; i < 10; i++) {
            historicalPrice(stock.getSymbol(), dateTo.minusYears(i).toString(), dateTo.minusYears(i).toString());
        }

        return "stock";
    }



}
