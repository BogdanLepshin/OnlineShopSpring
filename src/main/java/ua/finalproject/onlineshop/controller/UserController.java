package ua.finalproject.onlineshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.finalproject.onlineshop.entity.User;
import ua.finalproject.onlineshop.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String pagination(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Page<User> users = getPage(page);

        if (users == null) {
            return "redirect:/users";
        }

        model.addAttribute("users", users);
        model.addAttribute("pageNumbers", getPageNumbersList(users));

        return "users/users";
    }

    private Page<User> getPage(int page) {
        if (page < 1) {
            return null;
        }

        Page<User> users = userService.getAllUsers(PageRequest.of(page - 1, 2));

        if (page > users.getTotalPages()) {
            return null;
        }
        return users;
    }

    private List<Integer> getPageNumbersList(Page<User> page) {
        return IntStream.rangeClosed(1, page.getTotalPages())
                .boxed()
                .collect(Collectors.toList());
    }
}
