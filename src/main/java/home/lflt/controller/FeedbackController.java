package home.lflt.controller;

import home.lflt.model.Feedback;
import home.lflt.repo.FeedbackRepo;
import home.lflt.security.GetUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static home.lflt.utils.Constants.LIMIT_99;

@Slf4j
@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackRepo feedbackRepo;
    private final GetUser getUser;

    public FeedbackController(FeedbackRepo feedbackRepo, GetUser getUser) {
        this.feedbackRepo = feedbackRepo;
        this.getUser = getUser;
    }

    @ModelAttribute
    public void addFeedbackTypes(Model model) {
        model.addAttribute("feedbackTypes", Stream
                .of("Suggestion", "Content", "GUI", "Bug", "Other")
                .collect(Collectors.toList()));
    }

    @GetMapping
    public String feedbackForm(Model model) {
        long counter = feedbackRepo.count();
        log.info("Feedback counter: " + counter);

        if (counter > LIMIT_99) model.addAttribute("feedbackJam", true);
        else model.addAttribute("feedbackJam", false);

        model.addAttribute("feedbackPojo", new Feedback());
        return "feedback";
    }

    @Transactional
    @PostMapping
    public String processFeedback(@Valid @ModelAttribute("feedbackPojo") Feedback feedback, Errors errors) {
        log.info("processFeedback: " + feedback);
        if (errors.hasErrors()) {
            return "feedback";
        }

        feedback.setUser(getUser.currentUser());
        log.info("feedback submitted: " + feedback);

        feedbackRepo.save(feedback);
        return "redirect:/";
    }
}
