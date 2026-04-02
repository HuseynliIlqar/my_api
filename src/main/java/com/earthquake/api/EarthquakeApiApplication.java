package com.earthquake.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EarthquakeApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EarthquakeApiApplication.class, args);
    }
}
