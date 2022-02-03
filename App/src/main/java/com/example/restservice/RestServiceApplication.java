package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example.Controllers")
@SpringBootApplication
public class RestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
        System.out.println("Running on http://localhost:8080");
    }
}
