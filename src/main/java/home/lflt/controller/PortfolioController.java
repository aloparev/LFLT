package home.lflt.controller;

import home.lflt.model.Lot;
import home.lflt.model.Portfolio;
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

import java.util.List;

import static home.lflt.utils.Utils.getQuote;

@Slf4j
@Controller
@RequestMapping("/pf")
public class PortfolioController {
    private LotRepo lotRepo;
    private StockRepo stockRepo;
    private QuoteRepo quoteRepo;
    private PortfolioRepo portfolioRepo;

    public PortfolioController(LotRepo lotRepo, StockRepo stockRepo, QuoteRepo quoteRepo, PortfolioRepo pr) {
        this.lotRepo = lotRepo;
        this.stockRepo = stockRepo;
        this.quoteRepo = quoteRepo;
        this.portfolioRepo = pr;
    }

    @GetMapping
    public String show(Model model) {
        Portfolio pf = portfolioRepo.findAll().iterator().next();
        log.info(pf.toString());

        List<Lot> lots = pf.getLots();
        log.info("lots retrieved: " + lots.size());

        for(Lot lot : lots) {
            log.info("lot retrieved: " + lot.toString());
        }

        return "home";
    }



}
