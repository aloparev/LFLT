package home.lflt.controller;

import home.lflt.model.*;
import home.lflt.repo.PortfolioRepo;
import home.lflt.security.GetUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

import static home.lflt.utils.Utils.*;

@Slf4j
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final PortfolioRepo portfolioRepo;
    private final GetUser getUser;

    public DashboardController(PortfolioRepo portfolioRepo, GetUser getUser) {
        this.portfolioRepo = portfolioRepo;
        this.getUser = getUser;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public String showPortfolios(Model model) {
//        log.info("showPortfolios");

        Iterable<Portfolio> portfolios = portfolioRepo.getByUserIsNull();
//        Iterable<Portfolio> portfolios = portfolioRepo.findAll();
        model.addAttribute("portfolios", portfolios);
        model.addAttribute("mine", false);

        return "portfolioOverview";
    }

    /**
     * generate portfolio stats on the fly by aggregating all lots
     * depends on utils.getQuote
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public String showPortfolioById(@PathVariable("id") Long id, Model model) {
//        log.info("showPortfolioById=" + id);

        Portfolio pf = preparePortfolioRendering(portfolioRepo.getById(id));
//        log.info("pf.getOwnerName()=" + pf.getOwnerName() + ", getUser.currentUsername()=" + getUser.currentUsername());
        model.addAttribute("pf", pf);

        if(Objects.equals(pf.getOwnerName(), getUser.currentUsername()) && Objects.equals(pf.getType(), "USER_SM")) {
//            log.info("mine=true");
            model.addAttribute("mine", true);
        }

        if(pf.getEpochs() < 1)
            model.addAttribute("limit", true);

        if(pf.getGame() != null)
            model.addAttribute("game", true);

        return "portfolioDashboard";
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
        pf.setChange(0);
        pf.setPlDailySum(0);
        pf.setPlTotalSum(0);
//        log.info(">> pf after set = " + pf.toString());

        Set<Lot> lots = pf.getLots();
//        log.info(">> pf.getLots();");
        for(Lot lot : lots) {
            fmpQuote quote = fmpGetQuote(lot.getSymbol());

            lot.setCp(quote.getPrice());
            lot.setCpt(lot.getUnits() * lot.getCp());
            lot.setChange(quote.getChangesPercentage());
            lot.setYesterdayClose(quote.getPreviousClose());
            lot.setPld(lot.getCpt() - (lot.getUnits() * lot.getYesterdayClose()));
            lot.setPlt(lot.getCpt() - lot.getIpt());

            pf.setCptSum(pf.getCptSum() + lot.getCpt());
            pf.setChange(pf.getChange() + lot.getChange());
            pf.setPlDailySum(pf.getPlDailySum() + lot.getPld());
            pf.setPlTotalSum(pf.getPlTotalSum() + lot.getPlt());
        }
//        log.info("pf after transient update=" + pf.toString());

        model.addAttribute("pf", pf);
        return "portfolioDashboardDetailed";
    }

    @PostMapping(path = "/rm_pf/{id}")
    public String removePortfolioById(@PathVariable(name = "id") long id) {
        log.info("removePortfolioById=" + id);
        portfolioRepo.deleteById(id);
        return "redirect:/mine";
    }
}
