package com.fixflow.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }

    @GetMapping("/healthz")
    public String health() {
        return "OK";
    }

    @GetMapping({
            "/login",
            "/register",
            "/dashboard",
            "/issues/create",
            "/issues/{id}"
    })
    public String frontendRoutes() {
        return "forward:/index.html";
    }
}