package com.example.company.controller;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@NoArgsConstructor
@RestController
@RequestMapping("/req")

public class RequestCounter {

    private final AtomicInteger count = new AtomicInteger(0);

    public void incrementCount() {
        count.incrementAndGet();
    }

    @GetMapping()
    public String getCount() {
        return "Number of requests made: " + this.count.get();
    }
}