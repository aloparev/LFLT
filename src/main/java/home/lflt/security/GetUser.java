package home.lflt.security;

import home.lflt.model.User;
import home.lflt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetUser {
    @Autowired
    private iAuthenticationFacade authenticationFacade;
    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUsername() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User currentUser() {
        return userRepo.findByUsername(currentUsername());
    }

    @RequestMapping(value = "/userid", method = RequestMethod.GET)
    @ResponseBody
    public long currentUserId() {
        return currentUser().getId();
    }
}
