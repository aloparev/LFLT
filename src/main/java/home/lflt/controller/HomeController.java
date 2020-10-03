package home.lflt.controller;

import home.lflt.model.PagePost;
import home.lflt.repo.PostRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private PostRepo postRepo;

//    public HomeController(PostRepo postRepo) {
//        this.postRepo = postRepo;
//    }

    @GetMapping
    public String showHome(Model model) {
//        log.info("show home");
        List<PagePost> posts = postRepo.getAllByPageOrderByTstamp("home");
//        System.out.println(posts.size());
//        for(int i=0; i < posts.size(); i++)
//            System.out.println("post #" + i + ": " + posts.get(i));
        model.addAttribute("posts", posts);
        return "home";
    }
}
