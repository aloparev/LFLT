package home.lflt.controller;

import home.lflt.model.fRegistration;
import home.lflt.repo.UserRepo;
import home.lflt.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static home.lflt.utils.Constants.LIMIT_99;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
//    private final BasicTextEncryptor textEncryptor;

    public RegistrationController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
//
//    public RegistrationController(UserRepo userRepo, PasswordEncoder passwordEncoder, BasicTextEncryptor textEncryptor) {
//        this.userRepo = userRepo;
//        this.passwordEncoder = passwordEncoder;
//        this.textEncryptor = textEncryptor;
//    }

    @GetMapping
    public String registrationForm(Model model) {
        long counter = userRepo.count();
        log.info("User counter: " + counter);

        if (counter > LIMIT_99) model.addAttribute("feedbackJam", true);
        else model.addAttribute("feedbackJam", false);

        model.addAttribute("pojo", new fRegistration());
        return "register";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("pojo") fRegistration form, Errors errors) {
        //necessary for error messages in view
        if (errors.hasErrors()) {
            return "register";
        }

        log.info("received form: " + form);
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/";
    }
}
