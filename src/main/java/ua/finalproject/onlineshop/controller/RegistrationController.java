package ua.finalproject.onlineshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.finalproject.onlineshop.dto.RegisterForm;
import ua.finalproject.onlineshop.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
    }

    @GetMapping("/register")
    public String registration(Principal principal) {
        if (principal != null) {
            return "redirect:/users";
        }
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.saveNewUser(registerForm);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            model.addAttribute("error_user_exists", true);
            return "register";
        }

        return "redirect:/login";
    }


}
