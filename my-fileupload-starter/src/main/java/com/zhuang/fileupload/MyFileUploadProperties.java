package com.zhuang.fileupload;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my.fileupload")
public class MyFileUploadProperties {

    private String storeProvider = "local";
    private final Ftp ftp = new Ftp();
    private final Local local = new Local();
    private final WebDav webDav = new WebDav();

    public static class Ftp {
        private String ip = "127.0.0.1";
        private String userName = "root";
        private String password = "123";
        private String basePath = "";
        //链接模式：主动=active；被动=passive；
        private String connectionMode = "active";

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }

        public String getConnectionMode() {
            return connectionMode;
        }

        public void setConnectionMode(String connectionMode) {
            this.connectionMode = connectionMode;
        }
    }

    public static class Local {
        private String basePath = "";

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }
    }

    public static class WebDav {

        private String baseUrl = "";

        private String username;

        private String password;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
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
    }

    public String getStoreProvider() {
        return storeProvider;
    }

    public void setStoreProvider(String storeProvider) {
        this.storeProvider = storeProvider;
    }

    public Ftp getFtp() {
        return ftp;
    }

    public Local getLocal() {
        return local;
    }

    public WebDav getWebDav() {
        return webDav;
    }
}
