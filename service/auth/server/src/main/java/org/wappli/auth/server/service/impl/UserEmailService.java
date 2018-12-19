package org.wappli.auth.server.service.impl;

import org.wappli.auth.server.config.ApplicationConfig;
import org.wappli.auth.server.domain.Uzer;
import org.wappli.auth.server.service.EmailService;
import org.wappli.auth.server.util.EmailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.wappli.auth.server.util.EmailConstants.*;

@Service
@Transactional
public class UserEmailService {
    private static final Logger LOG = LoggerFactory.getLogger(UserEmailService.class);

    private final ApplicationConfig applicationConfig;
    private final EmailService emailService;

    public UserEmailService(ApplicationConfig applicationConfig, EmailService emailService) {
        this.applicationConfig = applicationConfig;
        this.emailService = emailService;
    }

    public void sendRegistrationConfirmEmail(Uzer user, Locale locale) {
        final String confirmUrlPattern = applicationConfig.getRegistration().getConfirmUrlPattern();
        String confirmUrl = String.format(confirmUrlPattern, user.getActivationHash());

        Map<String, Object> variables = createUserVariables(user);

        variables.put(EmailConstants.VARIABLE_CONFIRM_URL, confirmUrl);

        send(user.getEmail(), SUBJECT_REGISTRATION_CONFIRM, HTML_TEMPLATE_REGISTRATION_CONFIRM, variables, locale);

    }

    public void sendForgottenPasswordEmail(Uzer user, Locale locale) {
        final String forgottenPasswordUrlPattern = applicationConfig.getRegistration().getForgottenPasswordUrlPattern();
        String forgottenPasswordUrl = String.format(forgottenPasswordUrlPattern, user.getActivationHash());

        Map<String, Object> variables = createUserVariables(user);

        variables.put(EmailConstants.VARIABLE_FORGOTTEN_PASSWORD_URL, forgottenPasswordUrl);

        send(user.getEmail(), SUBJECT_FORGOTTEN_PASSWORD, HTML_TEMPLATE_FORGOTTEN_PASSWORD, variables, locale);

    }

    protected Map<String, Object> createUserVariables(Uzer user) {
        HashMap<String, Object> variables = new HashMap<>();

        String name = "<username not found>";

        if (user.getUsername() != null) {
            name = String.format("%s", user.getUsername());
        }

        variables.put("user", Collections.singletonMap("name", name));

        return variables;
    }

    //TODO: pass the langCode once the branch 'feature-email-lang' merged
    protected void send(String recipient, String subjectKey, String htmlBodyKey, Map<String, Object> variables,
                        Locale locale) {
        String langCode = locale.getLanguage();

        emailService.sendEmail(recipient, subjectKey, htmlBodyKey, null, variables, langCode);
    }
}
