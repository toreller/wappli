package org.wappli.admin.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wappli.common.server.web.resolver.PageableDTOResolver;

import java.util.List;

@Configuration
@ComponentScan({"org.wappli.common.server.web.resolver", "org.wappli.common.server.logging"})
public class WappliAdminWebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private PageableDTOResolver pageableDTOResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageableDTOResolver);
    }
}
