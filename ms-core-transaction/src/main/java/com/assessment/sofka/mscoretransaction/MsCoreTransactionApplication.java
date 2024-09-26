package com.assessment.sofka.mscoretransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dependency.mscore",
        "com.assessment.sofka"})
public class MsCoreTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCoreTransactionApplication.class, args);
    }

}
