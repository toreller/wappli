package org.wappli.admin.server;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.wappli.admin.server.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableFeignClients(basePackages = {"org.wappli.admin.api.rest.clients"})
@EnableEurekaClient
@EnableConfigurationProperties({ApplicationProperties.class})
public class WappliAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(WappliAdminApplication.class, args);
	}
}
