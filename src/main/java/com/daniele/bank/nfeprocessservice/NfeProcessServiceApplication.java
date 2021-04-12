package com.daniele.bank.nfeprocessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NfeProcessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfeProcessServiceApplication.class, args);
    }

}
