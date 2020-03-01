package home.lflt.controller;

import home.lflt.model.Stock;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/stocks")
public class StockController {
    private StockRepo stockRepo;

    public StockController(StockRepo stockRepo){
        this.stockRepo=stockRepo;
    }

    @GetMapping
    public String listStudent(Model model) {
        Iterable<Stock> stocks = stockRepo.findAll();

        model.addAttribute("stocks", stocks);
        return "stocks";
    }
}
