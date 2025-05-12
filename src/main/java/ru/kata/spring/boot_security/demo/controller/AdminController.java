package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String adminPage(@AuthenticationPrincipal User admin, Model model) {
        model.addAttribute("admin", admin);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add_user_form";
    }

    @PostMapping
    public String addUser(@ModelAttribute("newUser") User user,
                          @RequestParam("roles") List<Long> roleIds) {

        Set<Role> roles = roleIds.stream()
                .map(roleRepository::getById)
                .collect(Collectors.toSet());

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit_user_form";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam Integer age,
                             @RequestParam String email,
                             @RequestParam List<String> roles) {

        Set<Role> roleSet = roles.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());

        User user = new User(id, username, passwordEncoder.encode(password), firstName, lastName, age, email, roleSet);
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}