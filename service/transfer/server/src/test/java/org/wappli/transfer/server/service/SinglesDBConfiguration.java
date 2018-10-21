package org.wappli.transfer.server.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SinglesDBConfiguration {
    @Bean
    SinglesDB createSinglesDB() {
        return new SinglesDB();
    }
}
