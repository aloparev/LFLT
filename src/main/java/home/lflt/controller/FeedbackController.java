package home.lflt.controller;

import home.lflt.model.Feedback;
import home.lflt.repo.FeedbackRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private FeedbackRepo feedbackRepo;

    public FeedbackController(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    @ModelAttribute
    public void addFeedbackTypes(Model model) {
        model.addAttribute("feedbackTypes",
                Stream.of("Suggestion", "Content", "GUI", "Bug", "Other")
                        .collect(Collectors.toList()));
    }

    @GetMapping
    public String feedbackForm(Model model) {
        long counter = feedbackRepo.count();
        log.info("Feedback counter: " + counter);

        if (counter > 99) model.addAttribute("feedbackJam", true);
        else model.addAttribute("feedbackJam", false);

        model.addAttribute("feedbackPojo", new Feedback());
        return "feedback";
    }

    @PostMapping
    public String processFeedback(@Valid @ModelAttribute("feedbackPojo") Feedback feedback, Errors errors) {
        if (errors.hasErrors()) {
            return "feedback";
        }
        log.info("Feedback submitted: " + feedback);
        feedbackRepo.save(feedback);
        return "redirect:/";
    }
}
