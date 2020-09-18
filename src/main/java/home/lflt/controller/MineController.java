package home.lflt.controller;

import home.lflt.model.Game;
import home.lflt.model.Lot;
import home.lflt.model.Portfolio;
import home.lflt.repo.*;
import home.lflt.security.AuthenticationFacade;
import home.lflt.security.GetUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

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

        Iterable<Portfolio> portfolios = portfolioRepo.getByUserIsNotNull();
        model.addAttribute("portfolios", portfolios);

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long userId = userRepo.findByUsername(getUser.currentUserNameSimple()).getId();
        Iterable<Game> games = gameRepo.getByUserId(userId);
        model.addAttribute("games", games);

        return "mineOverview";
    }



}
