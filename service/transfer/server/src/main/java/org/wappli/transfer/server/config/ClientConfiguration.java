package org.wappli.transfer.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.wappli.transfer.api.rest.clients", "org.wappli.common.server.web.exception"})
public class ClientConfiguration {
}
