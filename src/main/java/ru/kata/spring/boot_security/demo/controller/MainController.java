package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String homeRedirect() {
        return  "index"; // или return "redirect:/login"; если не хочешь index.html
    }
}

