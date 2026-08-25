package com.fixflow.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "FixFlow backend is running";
    }

    @GetMapping("/healthz")
    public String health() {
        return "OK";
    }
}