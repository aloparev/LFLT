package home.lflt.controller;

import home.lflt.model.Stock;
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
@RequestMapping("/each")
public class StockController implements WebMvcConfigurer {

    @GetMapping
    public String listStudent(Model model) {
        List<Stock> stocks = Arrays.asList(
                new Stock("YNDX", "Yandex", "S&P 500"),
                new Stock("TTWO", "Grand Theft Auto", "S&P 500")
        );

        model.addAttribute("stocksKey", stocks);
        return "each";
    }
}
