package home.lflt.controller;

import home.lflt.model.*;
import home.lflt.repo.*;
import home.lflt.security.GetUser;
import home.lflt.utils.PortfolioUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static home.lflt.utils.Constants.MAX_FUNDS;

@Slf4j
@Controller
@RequestMapping("/mine")
public class MineController {
    private final PortfolioRepo portfolioRepo;
    private final GameRepo gameRepo;
    private final UserRepo userRepo;
    private final GetUser getUser;
    private final PortfolioUpdater portfolioUpdater;

    public MineController(PortfolioRepo pr, GameRepo gr, GetUser gu, UserRepo ur, PortfolioUpdater pfu) {
        portfolioRepo = pr;
        gameRepo = gr;
        userRepo = ur;
        getUser = gu;
        portfolioUpdater = pfu;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public String showMineObjects(Model model) {
        log.info("showMineObjects");
        String username = getUser.currentUsername();
//        log.info(username);
        User user = userRepo.findByUsername(username);
//        System.out.println(user);
//        long userId = user.getId();
//        System.out.println(userId);
        int limit = 0;

        model.addAttribute("mine", true);

        Set<Portfolio> portfolios = portfolioRepo.getByUserId(user.getId());
        model.addAttribute("portfolios", portfolios);
        limit += portfolios.size();

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<Game> games = gameRepo.getByUserId(user.getId());
        model.addAttribute("games", games);
        limit += games.size();

        if(limit >= 2)
            model.addAttribute("limit", true);
//        else
//            model.addAttribute("limitReached", false);

        return "mineOverview";
    }

//    @GetMapping("/new_portfolio")
//    public String showNewPortfolioForm(Model model) {
////        log.info("showMineObjects");
//        String header = "New Portfolio";
//        model.addAttribute("pojo", new fPortfolio());
//        model.addAttribute("header", header);
//        model.addAttribute("button", "Create " + header);
//        model.addAttribute("action_link", "/mine/new_portfolio_submit");
////        model.addAttribute("user", userRepo.findByUsername(getUser.currentUserNameSimple()));
//        return "newPortfolio";
//    }

    /**
     * creates self managed portfolio with 10k start capital
     */
    @Transactional
    @PostMapping(path = "/new_preconfigured_portfolio_submit")
    public String processNewUserPortfolio() {
        Portfolio portfolio = new Portfolio("USER_SM", MAX_FUNDS, getUser.currentUser());
        System.out.println(portfolio);
        portfolioRepo.save(portfolio);
        return "redirect:/mine";
    }

    @PostMapping(path = "/buy_ticker/{pf}")
    public String processBuyTickerForPortfolio(@PathVariable(name = "pf") long portfolioId, @RequestParam(name = "symb") String symbol) {
//        log.info("processBuyTicker: pf=" + portfolioId + " symbol=" + symbol);
        if(symbol != null && !symbol.trim().isEmpty())
            portfolioUpdater.updatePortfolioBuyStock(portfolioId, symbol);

        // here we address the same pf_id as on top
        return "redirect:/dashboard/{pf}";
    }

    @GetMapping("/new_game")
    public String showNewGameForm(Model model) {
//        log.info("showMineObjects");
        String header = "New Game";
        model.addAttribute("pojo", new fPortfolio());
        model.addAttribute("header", header);
        model.addAttribute("button", "Create " + header);
        model.addAttribute("action_link", "/mine/new_game_submit");
//        model.addAttribute("user", userRepo.findByUsername(getUser.currentUserNameSimple()));
        return "newPortfolio";
    }

    @PostMapping(path = "/new_game_submit")
    public String processNewUserGame(@Valid @ModelAttribute("pojo") fPortfolio form, Errors errors) {
        log.info("processNewUserGame");
        //necessary for error messages in view
        if (errors.hasErrors()) {
            return "mineOverview";
        }

        form.setUser(getUser.currentUser());
//        form.setUser(userRepo.findByUsername(getUser.currentUserNameSimple()));
        log.info("received form: " + form);
        portfolioRepo.save(form.toUserManagedPortfolio());
        portfolioRepo.save(form.toRandomPortfolio());
        return "redirect:/mine";
    }
}
