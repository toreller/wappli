package org.wappli.auth.server.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "auth")
@Component
public class ApplicationConfig {

    private String applicationVersion;

    private int accountActivationPeriod;

    private Api api = new Api();

    private Mail mail = new Mail();

    @Valid
    private Registration registration = new Registration();

    @Getter
    @Setter
    public static class Api {

        private int maxSize;
    }

    @Getter
    @Setter
    public static class Mail {

        private String from;

    }

    @Getter
    @Setter
    public static class Registration {

        @NotNull
        private String confirmUrlPattern;

        @NotNull
        private String forgottenPasswordUrlPattern;

    }

}
