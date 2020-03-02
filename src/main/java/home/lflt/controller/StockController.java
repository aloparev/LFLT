package home.lflt.controller;

import home.lflt.model.Stock;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/stock")
public class StockController {
    private StockRepo stockRepo;

    public StockController(StockRepo stockRepo){
        this.stockRepo=stockRepo;
    }

    @GetMapping
    public String listStocks(Model model) {
        Iterable<Stock> stocks = stockRepo.findAll();

        model.addAttribute("stocks", stocks);
        return "stock";
    }
}
