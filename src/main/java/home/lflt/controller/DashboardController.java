package home.lflt.controller;

import home.lflt.model.Lot;
import home.lflt.model.MarketsInsiderHead;
import home.lflt.model.Portfolio;
import home.lflt.model.fmpQuote;
import home.lflt.repo.PortfolioRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

import static home.lflt.utils.Utils.fmpGetQuote;
import static home.lflt.utils.Utils.miGetQuote;

@Slf4j
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private PortfolioRepo portfolioRepo;

    public DashboardController(PortfolioRepo portfolioRepo) {
        this.portfolioRepo = portfolioRepo;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public String showPortfolios(Model model) {
        log.info("showPortfolios");

        Iterable<Portfolio> portfolios = portfolioRepo.findAll();
        model.addAttribute("portfolios", portfolios);

        return "portfolioOverview";
    }

    /**
     * generate portfolio stats on the fly by aggregating all lots
     * depends on utils.getQuote
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public String showPortfolioById(@PathVariable("id") Long id, Model model) {
        log.info("showPortfolioById");

        Portfolio pf = portfolioRepo.getById(id);
//        log.info(">> portfolioRepo.getById: " + id);
        pf.setCptSum(pf.getBalance());
        pf.setChangePct(0);
        pf.setPlTotalSum(0);
//        log.info(">> pf after set = " + pf.toString());

        Set<Lot> lots = pf.getLots();
//        log.info(">> pf.getLots();");
        for(Lot lot : lots) {
            MarketsInsiderHead quote = miGetQuote(lot.getSymbol());

            lot.setCp(quote.getPrice());
            lot.setCpt(lot.getUnits() * lot.getCp());
            lot.setChangePct(quote.getChangePct());
            if (quote.getPrice() == 0) {
                lot.setPlt(0);
            } else {
                lot.setPlt(lot.getCpt() - lot.getIpt());
            }
            
            pf.setCptSum(pf.getCptSum() + lot.getCpt());
            pf.setChangePct(pf.getChangePct() + lot.getChangePct());
            pf.setPlTotalSum(pf.getPlTotalSum() + lot.getPlt());
        }
//        log.info("pf after transient update=" + pf.toString());

        model.addAttribute("pf", pf);
        return "dashboard";
    }

    /**
     * extended "showPortfolioById"
     * under construction
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}/details")
    public String showPortfolioByIdDetails(@PathVariable("id") Long id, Model model) {
        log.info("showPortfolioByIdDetails");

        Portfolio pf = portfolioRepo.getById(id);
//        log.info(">> portfolioRepo.getById: " + id);
        pf.setCptSum(pf.getBalance());
        pf.setChangePct(0);
        pf.setPlDailySum(0);
        pf.setPlTotalSum(0);
//        log.info(">> pf after set = " + pf.toString());

        Set<Lot> lots = pf.getLots();
//        log.info(">> pf.getLots();");
        for(Lot lot : lots) {
            fmpQuote quote = fmpGetQuote(lot.getSymbol());

            lot.setCp(quote.getPrice());
            lot.setCpt(lot.getUnits() * lot.getCp());
            lot.setChangePct(quote.getChangesPercentage());
            lot.setYc(quote.getPreviousClose());
            lot.setPld(lot.getCpt() - (lot.getUnits() * lot.getYc()));
            lot.setPlt(lot.getCpt() - lot.getIpt());

            pf.setCptSum(pf.getCptSum() + lot.getCpt());
            pf.setChangePct(pf.getChangePct() + lot.getChangePct());
            pf.setPlDailySum(pf.getPlDailySum() + lot.getPld());
            pf.setPlTotalSum(pf.getPlTotalSum() + lot.getPlt());
        }
//        log.info("pf after transient update=" + pf.toString());

        model.addAttribute("pf", pf);
        return "dashboardDetails";
    }
}
