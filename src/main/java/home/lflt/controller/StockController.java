package home.lflt.controller;

import home.lflt.model.Stock;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/stock")
public class StockController {
    private final StockRepo stockRepo;

    public StockController(StockRepo stockRepo){
        this.stockRepo=stockRepo;
    }

    @GetMapping
    public String showStocks(Model model) {
        LocalDateTime minTstamp = stockRepo.getMinDate();
        model.addAttribute("tstamp", minTstamp);

        Iterable<Stock> stocks = stockRepo.findAll();
        model.addAttribute("stocks", stocks);

        return "stock";
    }
}
