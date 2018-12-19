package org.wappli.auth.server.service;

import java.util.Map;

public interface EmailService {

    void sendEmail(String to, String subjectKey, String htmlBodyKey, String textBodyKey, Map<String, Object> variables,
                   String langCode);

}
