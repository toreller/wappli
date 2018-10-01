package org.wappli.transfer.server;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.wappli.transfer.server.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableFeignClients(basePackages = {"org.wappli.transfer.api.rest.clients"})
@EnableEurekaClient
@EnableConfigurationProperties({ApplicationProperties.class})
public class WappliTransferApplication {
	public static void main(String[] args) {
		SpringApplication.run(WappliTransferApplication.class, args);
	}
}
