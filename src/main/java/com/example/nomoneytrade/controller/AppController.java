package com.example.nomoneytrade.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/")
    public String index() {
        return "<h1>No special data</h1>";
    }
}
