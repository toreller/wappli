package org.wappli.auth.server.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("org.wappli.auth.server.repository")
@EnableTransactionManagement
@EntityScan("org.wappli.auth.domain")
public class JpaConfig {

}
