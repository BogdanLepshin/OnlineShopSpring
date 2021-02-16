package ua.finalproject.onlineshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.finalproject.onlineshop.dto.UserDTO;
import java.security.Principal;


@Slf4j
@Controller
public class LoginFormController {

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("user", new UserDTO());
    }

    @GetMapping("login")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/users";
        }
        return "login";
    }
}
