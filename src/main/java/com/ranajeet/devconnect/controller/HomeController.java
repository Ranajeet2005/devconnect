
package com.ranajeet.devconnect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {

        return Map.of(
                "application", "DevConnect",
                "message", "Welcome to DevConnect API",
                "status", "Running"
        );
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {

        return Map.of(
                "status", "UP",
                "service", "DevConnect Backend"
        );
    }
}
