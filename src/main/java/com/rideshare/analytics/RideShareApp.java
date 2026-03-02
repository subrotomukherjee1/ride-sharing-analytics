package com.rideshare.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rideshare.analytics")
public class RideShareApp {
    public static void main(String[] args) {
        SpringApplication.run(RideShareApp.class, args);
    }
}
