package com.example.company.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Getter
@NoArgsConstructor
public class RequestCounter {

    private final AtomicInteger count = new AtomicInteger(0);

    public void incrementCount() {
        count.incrementAndGet();
    }
}