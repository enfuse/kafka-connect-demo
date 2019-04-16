package com.enfuse.kafka.connect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectController {
    @GetMapping("/")
    public String hello() {
        return "Dear world, hello! - com.enfuse.kafka.connect";
    }
}
