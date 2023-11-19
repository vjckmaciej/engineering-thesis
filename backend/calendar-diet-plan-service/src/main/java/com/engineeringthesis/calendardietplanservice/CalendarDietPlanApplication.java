package com.engineeringthesis.calendardietplanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CalendarDietPlanApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalendarDietPlanApplication.class, args);
    }

}
