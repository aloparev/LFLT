package home.lflt.controller;

import home.lflt.model.Portfolio;
import home.lflt.model.Stock;
import home.lflt.repo.PortfolioRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
//@Configuration
@RequestMapping("/dashboard")
public class DashboardController {
    private PortfolioRepo portfolioRepo;

    public DashboardController(PortfolioRepo portfolioRepo) {
        this.portfolioRepo = portfolioRepo;
    }

    @GetMapping
    public String showDashboard(Model model) {
        log.info("showDashboard");
        Portfolio pf = portfolioRepo.getById(0);
        log.info(pf.toString());
        //        List<Stock> stocks = Arrays.asList(
//                new Stock("YNDX", "Yandex", "S&P 500"),
//                new Stock("TTWO", "Grand Theft Auto", "S&P 500")
//        );
//
//        model.addAttribute("stocksKey", stocks);
        return "dashboard";
    }
}
