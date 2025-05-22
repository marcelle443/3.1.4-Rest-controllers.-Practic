package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.model.User;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @GetMapping("/user")
    public UserDTO getAuthenticatedUser(@AuthenticationPrincipal User user) {
        return UserMapper.userDTO(user);
    }
}