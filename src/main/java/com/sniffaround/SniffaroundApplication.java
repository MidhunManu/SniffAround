package com.sniffaround;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SniffaroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(SniffaroundApplication.class, args);
    }

}
