package ua.finalproject.onlineshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String mainPage(){
        return "home";
    }

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/all_users")
    public String userPage(){
        return "/users/users";
    }


}
