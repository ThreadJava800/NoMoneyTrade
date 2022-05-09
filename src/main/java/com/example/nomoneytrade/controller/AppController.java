package com.example.nomoneytrade.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/")
    public String index() {
        return "<h1>Try to register</h1>";
    }

    @GetMapping("/admin")
    public String admin() {
        return "<h1>Hey, admin</h1>";
    }

    @GetMapping("/user")
    public String user() {
        return "<h1>Hey, user!</h1>";
    }
}
