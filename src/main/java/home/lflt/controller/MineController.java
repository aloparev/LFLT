package home.lflt.controller;

import home.lflt.model.*;
import home.lflt.repo.*;
import home.lflt.security.GetUser;
import home.lflt.utils.PortfolioUpdater;
import home.lflt.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

import static home.lflt.utils.Constants.MAX_FUNDS;
import static home.lflt.utils.Utils.preparePortfolioRendering;
import static home.lflt.utils.Utils.stringNotNullAndNotEmpty;

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
    public String showMyOverview(Model model) {
//        log.info("showMineObjects");
        int limit = 0;
        model.addAttribute("mine", true);

        Set<Portfolio> portfolios = portfolioRepo.getByUserIdAndGameIsNull(getUser.currentUserId());
//        Set<Portfolio> portfolios = portfolioRepo.getByUserId(getUser.currentUserId());
        model.addAttribute("portfolios", portfolios);
        limit += portfolios.size();

        Set<Game> games = gameRepo.getByUserIdAndFetchPortfoliosEagerly(getUser.currentUserId());
//        Set<Game> games = gameRepo.getByUserId(getUser.currentUserId());
//        log.info("games=" + games);
        model.addAttribute("games", games);
        limit += games.size();

        if(limit >= 2)
            model.addAttribute("limit", true);
//        else
//            model.addAttribute("limitReached", false);

        return "mineOverview";
    }

    @Transactional
    @GetMapping("/game/{id}")
    public String showGameById(@PathVariable("id") long id, Model model) {
        Optional<Game> gameOptional = gameRepo.findById(id);
        if(gameOptional.isPresent()) {
            Game game = gameOptional.get();
            game.getPortfolios().forEach(Utils::preparePortfolioRendering);
            model.addAttribute("portfolios", game.getPortfolios());
//            model.addAttribute("mine", true);
//            model.addAttribute("game", true);

//            Portfolio mypf = preparePortfolioRendering(game.getPortfolios().get(0));
//            Portfolio oppopf = preparePortfolioRendering(game.getPortfolios().get(1));
        }
        return "portfolioDashboard";
    }

    @PostMapping(path = "/game_rm/{id}")
    public String removeGameById(@PathVariable(name = "id") long id) {
        log.info("removeGameById=" + id);
        gameRepo.deleteById(id);
        return "redirect:/mine";
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
//        System.out.println(portfolio);
        portfolioRepo.save(portfolio);
        return "redirect:/mine";
    }

    @PostMapping(path = "/buy_ticker/{pf}")
    public String processBuyTickerForPortfolio(@PathVariable(name = "pf") long portfolioId, @RequestParam(name = "symb") String symbol, @RequestParam(name = "sum") String sumStr) {
        log.info("processBuyTicker: pf=" + portfolioId + ", symbol=" + symbol + ", sumStr=" + sumStr);
        if(stringNotNullAndNotEmpty(symbol) && stringNotNullAndNotEmpty(sumStr)) {
            try {
                int sum = Integer.parseInt(sumStr);
                portfolioUpdater.updatePortfolioBuyLot(portfolioId, symbol.toUpperCase(), sum);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }

        // here we address the same pf_id as on top
        return "redirect:/dashboard/{pf}";
    }

    @PostMapping(path = "/sell_ticker/{pf}")
    public String processSellTickerForPortfolio(@PathVariable(name = "pf") long portfolioId, @RequestParam(name = "symb") String symbol) {
//        log.info("processBuyTicker: pf=" + portfolioId + " symbol=" + symbol);
        if(stringNotNullAndNotEmpty(symbol))
            try {
                portfolioUpdater.updatePortfolioSellLot(portfolioId, symbol.toUpperCase());
            } catch (IllegalArgumentException iae) {
                iae.printStackTrace();
            }

        // here we address the same pf_id as on top
        return "redirect:/dashboard/{pf}";
    }

    @GetMapping("/new_game")
    public String showNewGameForm(Model model) {
//        log.info("showMineObjects");
        String newGame = "New Game";
        model.addAttribute("pojo", fPortfolio.builder().build());
        model.addAttribute("header", newGame);
        model.addAttribute("button", "Create " + newGame);
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
        Game game = new Game(getUser.currentUser());
        form.setGame(game);
//        form.setUser(userRepo.findByUsername(getUser.currentUserNameSimple()));
        log.info("received form: " + form);
        portfolioRepo.save(form.toUserManagedPortfolio());
        portfolioRepo.save(form.toRandomPortfolio());
        return "redirect:/mine";
    }

    @Transactional
    @PostMapping(path = "/new_preconfigured_game_submit")
    public String processNewUserGame() {
        User user = getUser.currentUser();
        Game game = new Game(getUser.currentUser());

        fPortfolio form = fPortfolio.builder()
                .name(" Game Portfolio")
                .funds(2000)
                .delay(1)
                .epochs(5)
                .user(getUser.currentUser())
                .game(game)
                .build();
        log.info("received form: " + form);

//        portfolioRepo.save(form.toUserManagedPortfolio());
//        portfolioRepo.save(form.toRandomPortfolio());
        game.getPortfolios().add(form.toUserManagedPortfolio());
        game.getPortfolios().add(form.toRandomPortfolio());
//        gameRepo.save(game);

        user.getGames().add(game);
        userRepo.save(user);

        return "redirect:/mine";
    }
}
