package org.wappli.common.server.web.exception;

import org.springframework.http.ResponseEntity;

public class DescribedResponseEntity {
    String description;
    ResponseEntity<?> responseEntity;

    public DescribedResponseEntity(String description, ResponseEntity<?> responseEntity) {
        this.description = description;
        this.responseEntity = responseEntity;
    }

    public String getDescription() {
        return description;
    }

    public ResponseEntity<?> getResponseEntity() {
        return responseEntity;
    }

    @Override
    public String toString() {
        return "{" +
                "\"description\": " + "\"" + description + "\", " +
                "\"responseEntity\": " + "\"" + responseEntity.toString() + "\"" +
                '}';
    }
}
