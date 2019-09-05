package com.midea.epm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class EpmGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpmGatewayApplication.class, args);
    }

}
