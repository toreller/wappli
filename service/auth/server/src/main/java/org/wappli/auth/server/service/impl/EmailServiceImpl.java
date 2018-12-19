package org.wappli.auth.server.service.impl;

import org.wappli.auth.server.config.ApplicationConfig;
import org.wappli.auth.server.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final ApplicationConfig applicationConfig;

    public EmailServiceImpl(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void sendEmail(String to, String subjectKey, String htmlBodyKey, String textBodyKey,
                          Map<String, Object> variables, String langCode) {

        // todo send email
    }

}
