package com.zhuang.fileupload.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "my.fileupload")
public class MyFileUploadProperties {

    private String storeProvider = "local";
    private final Ftp ftp = new Ftp();
    private final Local local = new Local();

    @Data
    public static class Ftp {
        private String ip = "127.0.0.1";
        private String userName = "root";
        private String password = "123";
        private String basePath = "";
        //链接模式：主动=active；被动=passive；
        private String connectionMode = "active";
    }

    @Data
    public static class Local {
        private String basePath = "";
    }

}
