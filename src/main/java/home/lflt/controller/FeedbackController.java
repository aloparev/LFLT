package home.lflt.controller;

import home.lflt.model.Feedback;
import home.lflt.model.Feedback.Type;
import home.lflt.shadow.Ingredient;
import home.lflt.shadow.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.Arrays;

@Slf4j
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @GetMapping
    public String feedbackForm(Model model) {
//Type[] types = Feedback.Type.values();
//        for (Type type : types) {
//            model.addAttribute(type.toString().toLowerCase(), type);
//        }
        model.addAttribute("feedbackTypes", Arrays.asList(Feedback.Type.values()));
        model.addAttribute("feedbackPojo", new Feedback());
        return "feedback";
    }

    @PostMapping
    public String processFeedback(@Valid Feedback feedback, Errors errors) {
        if (errors.hasErrors()) {
            return "feedback";
        }
        log.info("Feedback submitted: " + feedback);
        return "redirect:/";
    }
}
