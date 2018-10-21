package org.wappli.transfer.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private Rest rest = new Rest();

    public Rest getRest() {
        return rest;
    }

    public void setRest(Rest rest) {
        this.rest = rest;
    }

    public static class Rest {
        private String admin;
        private String transfer;

        public String getTransfer() {
            return transfer;
        }

        public void setTransfer(String admin) {
            this.transfer = admin;
        }

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }
    }
}
