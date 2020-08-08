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

    public MyFileUploadProperties() {
        this("config/my-fileupload.properties");
    }

    public MyFileUploadProperties(String configFile) {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(configFile);
            properties = new Properties();
            properties.load(inputStream);

        } catch (IOException e) {
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

    public String getFtpIp() {
        return properties.getProperty(FTP_IP);
    }

    public String getFtpUserName() {
        return properties.getProperty(FTP_USERNAME);
    }

    public String getFtpPassword() {
        return properties.getProperty(FTP_PASSWORD);
    }

    public String getFtpBasePath() {
        return properties.getProperty(FTP_BASE_PATH);
    }

    public String getFtpConnectionMode() {
        return properties.getProperty(FTP_CONNECTION_MODE);
    }

    public String getStoreProvider() {
        return properties.getProperty(STORE_PROVIDER);
    }

    public String getLocalBasePath() {
        return properties.getProperty(LOCAL_BASE_PATH);
    }

}
