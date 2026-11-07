package com.ranajeet.devconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevConnectApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                DevConnectApplication.class,
                args
        );

        System.out.println("DevConnect Started Successfully!");

    }
}