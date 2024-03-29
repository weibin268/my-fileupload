package com.zhuang.fileupload.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.zhuang.fileupload.exception.LoadConfigException;

public class MyFileUploadProperties {

    private Properties properties;

    private final static String STORE_PROVIDER = "my.fileupload.storeProvider";
    private final static String FTP_IP = "my.fileupload.ftp.ip";
    private final static String FTP_USERNAME = "my.fileupload.ftp.username";
    private final static String FTP_PASSWORD = "my.fileupload.ftp.password";
    private final static String FTP_BASE_PATH = "my.fileupload.ftp.basePath";
    private final static String FTP_CONNECTION_MODE = "my.fileupload.ftp.connectionMode";
    private final static String LOCAL_BASE_PATH = "my.fileupload.local.basePath";
    private final static String WEB_DAV_USERNAME = "my.fileupload.webDav.username";
    private final static String WEB_DAV_PASSWORD = "my.fileupload.webDav.password";
    private final static String WEB_DAV_BASE_URL = "my.fileupload.webDav.baseUrl";

    public MyFileUploadProperties() {
        this("config/my-fileupload.properties");
    }

    public MyFileUploadProperties(String configFile) {
        if (configFile == null) return;
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(configFile);
            properties = new Properties();
            properties.load(inputStream);
            this.storeProvider = properties.getProperty(STORE_PROVIDER);
            this.ftp.setIp(properties.getProperty(FTP_IP));
            this.ftp.setUserName(properties.getProperty(FTP_USERNAME));
            this.ftp.setPassword(properties.getProperty(FTP_PASSWORD));
            this.ftp.setBasePath(properties.getProperty(FTP_BASE_PATH));
            this.ftp.setConnectionMode(properties.getProperty(FTP_CONNECTION_MODE));
            this.local.setBasePath(properties.getProperty(LOCAL_BASE_PATH));
            this.webDav.setUsername(properties.getProperty(WEB_DAV_USERNAME));
            this.webDav.setPassword(properties.getProperty(WEB_DAV_PASSWORD));
            this.webDav.setBaseUrl(properties.getProperty(WEB_DAV_BASE_URL));
        } catch (Exception e) {
            throw new LoadConfigException("加载“my-fileupload.properties”配置文件出错！");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private String storeProvider = "local";
    private final Ftp ftp = new Ftp();
    private final Local local = new Local();
    private final WebDav webDav = new WebDav();

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
}
