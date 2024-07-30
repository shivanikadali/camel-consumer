package com.example.consumerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.example.consumerapp")
public class ConsumerappApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerappApplication.class, args);
    }
}