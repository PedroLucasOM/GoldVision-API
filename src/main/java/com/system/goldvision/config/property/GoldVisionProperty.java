package com.system.goldvision.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("goldvision")
public class GoldVisionProperty {

    public final Seguranca seguranca = new Seguranca();

    private final Mail mail = new Mail();

    private String originPermitida = "http://localhost:8000";

    public Seguranca getSeguranca() {
        return seguranca;
    }

    public Mail getMail() {
        return mail;
    }

    public String getOriginPermitida() {
        return originPermitida;
    }

    public void setOriginPermitida(String originPermitida) {
        this.originPermitida = originPermitida;
    }

    public static class Seguranca {

        private boolean enableHttps;

        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }

    public static class Mail {

        private String host;

        private Integer port;

        private String username;

        private String password;

        private String email;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
