package home.lflt.controller;

import home.lflt.model.*;
import home.lflt.repo.*;
import home.lflt.security.AuthenticationFacade;
import home.lflt.security.GetUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Set;

import static home.lflt.utils.Constants.LIMIT_99;

@Slf4j
@Controller
@RequestMapping("/mine")
public class MineController {
    private final PortfolioRepo portfolioRepo;
    private final GameRepo gameRepo;
    private final UserRepo userRepo;
    private final GetUser getUser;

    public MineController(PortfolioRepo pr, GameRepo gr, GetUser gu, UserRepo ur) {
        portfolioRepo = pr;
        gameRepo = gr;
        userRepo = ur;
        getUser = gu;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public String showMineObjects(Model model) {
        log.info("showMineObjects");
        String username = getUser.currentUserNameSimple();
//        log.info(username);
        User user = userRepo.findByUsername(username);
//        System.out.println(user);
//        long userId = user.getId();
//        System.out.println(userId);
        int limit = 0;

        model.addAttribute("mine", true);

        Set<Portfolio> portfolios = portfolioRepo.getByUserId(user.getId());
        model.addAttribute("portfolios", portfolios);
        limit += portfolios.size() * 2;

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<Game> games = gameRepo.getByUserId(user.getId());
        model.addAttribute("games", games);
        limit += games.size();

        if(limit > 4)
            model.addAttribute("limitReached", true);
        else
            model.addAttribute("limitReached", false);

        return "mineOverview";
    }

    @GetMapping("/new_portfolio")
    public String showNewPortfolioForm(Model model) {
//        log.info("showMineObjects");
        model.addAttribute("pojo", new fPortfolio());
//        model.addAttribute("user", userRepo.findByUsername(getUser.currentUserNameSimple()));
        return "newPortfolio";
    }

    @PostMapping(path = "/new_portfolio_submit")
    public String processNewUserPortfolio(@Valid @ModelAttribute("pojo") fPortfolio form, Errors errors) {
        log.info("processNewUserPortfolio");
        //necessary for error messages in view
        if (errors.hasErrors()) {
            return "mineOverview";
        }

        form.setUser(getUser.currentUser());
//        form.setUser(userRepo.findByUsername(getUser.currentUserNameSimple()));
        log.info("received form: " + form);
        portfolioRepo.save(form.toPortfolio());
        return "redirect:/mine";
    }

}
