package home.lflt.controller;

import home.lflt.model.Lot;
import home.lflt.model.Portfolio;
import home.lflt.model.fmpQuote;
import home.lflt.repo.PortfolioRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.List;

import static home.lflt.utils.Utils.getQuote;

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
        pf.setCptSum(0);
        pf.setChangeSum(0);
        pf.setPlDailySum(0);
        pf.setPlTotalSum(0);
        log.info("got pf=" + pf.toString());

        List<Lot> lots = pf.getLots();
        for(Lot lot : lots) {
            fmpQuote quote = getQuote(lot.getSymbol());

            lot.setCp(quote.getPrice());
            lot.setCpt(lot.getUnits() * lot.getCp());
            lot.setChange(quote.getChangesPercentage());
            lot.setYc(quote.getPreviousClose());
            lot.setPld(lot.getCpt() - (lot.getUnits() * lot.getYc()));
            lot.setPlt(lot.getCpt() - lot.getIpt());

            pf.setCptSum(pf.getCptSum() + lot.getCpt());
            pf.setChangeSum(pf.getChangeSum() + lot.getChange());
            pf.setPlDailySum(pf.getPlDailySum() + lot.getPld());
            pf.setPlTotalSum(pf.getPlTotalSum() + lot.getPlt());
        }
        log.info("pf after transient update=" + pf.toString());

        log.info(new DecimalFormat("#.##").format(pf.getCptSum()));

        model.addAttribute("pf", pf);
        return "dashboard";
    }
}
