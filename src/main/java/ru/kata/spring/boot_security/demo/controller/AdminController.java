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
        List<User> users = userService.getAllUsers();

        // Добавим каждому пользователю строку ролей
        users.forEach(user -> {
            String roleNames = user.getRoles().stream()
                    .map(r -> r.getName().replace("ROLE_", ""))
                    .collect(Collectors.joining(", "));
            user.setRoleString(roleNames);
        });

        model.addAttribute("admin", admin);
        model.addAttribute("users", users);
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

        User user = userService.getUserById(id);

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);


        Set<Role> roleSet = roles.stream()
                .map(r -> "ROLE_" + r.replace("ROLE_", "").toUpperCase())
                .map(roleRepository::findByName)
                .filter(role -> role != null)
                .collect(Collectors.toSet());

        user.setRoles(roleSet);

        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";

    }
}

