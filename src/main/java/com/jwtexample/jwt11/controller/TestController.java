package com.jwtexample.jwt11.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public String securedHello() {
        return "JWT ile korunan bu endpoint'e başarıyla eriştiniz!";
    }
}
