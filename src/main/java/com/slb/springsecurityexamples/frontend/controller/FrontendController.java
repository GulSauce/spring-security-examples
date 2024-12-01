package com.slb.springsecurityexamples.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping("/")
    public String home() {
        return "home"; // templates/main.html을 렌더링
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // templates/main.html을 렌더링
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // templates/main.html을 렌더링
    }
}