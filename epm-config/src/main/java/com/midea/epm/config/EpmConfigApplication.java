package com.midea.epm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class EpmConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpmConfigApplication.class, args);
    }

}
