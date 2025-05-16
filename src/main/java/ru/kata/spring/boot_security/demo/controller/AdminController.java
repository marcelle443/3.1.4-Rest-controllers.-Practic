package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String showAdminPage(Model model, @AuthenticationPrincipal User user) {
        List<User> users = userService.getAllUsersWithRoleString();

        model.addAttribute("admin", user);
        model.addAttribute("users", users);
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam("roles") List<Long> roleIds) {
        userService.addWithRoles(user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam("roles") List<Long> roleIds) {
        userService.updateWithRoles(user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
