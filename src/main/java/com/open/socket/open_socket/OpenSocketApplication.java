package com.open.socket.open_socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OpenSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenSocketApplication.class, args);
    }
}
