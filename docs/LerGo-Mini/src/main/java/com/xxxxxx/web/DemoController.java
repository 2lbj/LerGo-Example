package com.xxxxxx.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DemoController {

    @Value("${spring.application.description}")
    String description;

    @RequestMapping(value = "/")
    public String test() {
        return "Minimal Spring Boot Application ["+ description +"]";
    }

}

