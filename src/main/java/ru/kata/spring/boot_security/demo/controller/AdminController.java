package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam("roles") List<Long> roleIds) {

        Set<Role> roles = roleIds.stream()
                .map(id -> new Role(id, null)) // предположим, что роль уже существует
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam("roles") List<Long> roleIds) {

        Set<Role> roles = roleIds.stream()
                .map(id -> new Role(id, null))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }
}