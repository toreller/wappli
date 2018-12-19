package org.wappli.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients({"org.wappli.auth.api.client"})
public class AuthServerApp {

    public static void main(final String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
    }
}
