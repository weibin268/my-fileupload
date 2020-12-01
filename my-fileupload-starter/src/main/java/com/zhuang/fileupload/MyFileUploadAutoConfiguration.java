package com.zhuang.fileupload;

import com.zhuang.fileupload.enums.StoreProviderType;
import com.zhuang.fileupload.impl.ftp.FtpStoreProvider;
import com.zhuang.fileupload.impl.local.LocalStoreProvider;
import com.zhuang.fileupload.service.FileUploadService;
import com.zhuang.fileupload.service.impl.MyBatisPlusFileUploadService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyFileUploadProperties.class)
@MapperScan("com.zhuang.fileupload.mapper")
@ComponentScan
public class MyFileUploadAutoConfiguration {

    @Autowired
    private MyFileUploadProperties myFileuploadProperties;

    @Bean
    public FileUploadManager fileUploadManager() {
        FileUploadManager fileUploadManager = null;
        if (myFileuploadProperties.getStoreProvider().equalsIgnoreCase(StoreProviderType.FTP.getValue())) {
            fileUploadManager = new FileUploadManager(ftpStoreProvider(), fileUploadService());
        } else if (myFileuploadProperties.getStoreProvider().equalsIgnoreCase(StoreProviderType.LOCAL.getValue())) {
            fileUploadManager = new FileUploadManager(localStoreProvider(), fileUploadService());
        }
        return fileUploadManager;
    }

    @Bean
    public FileUploadService fileUploadService() {
        return new MyBatisPlusFileUploadService();
    }

    @Bean
    public LocalStoreProvider localStoreProvider() {
        return new LocalStoreProvider(getMyFileUploadProperties(myFileuploadProperties));
    }

    private com.zhuang.fileupload.config.MyFileUploadProperties getMyFileUploadProperties(MyFileUploadProperties myFileuploadProperties) {
        com.zhuang.fileupload.config.MyFileUploadProperties result = new com.zhuang.fileupload.config.MyFileUploadProperties(null);
        BeanUtils.copyProperties(myFileuploadProperties, result);
        BeanUtils.copyProperties(myFileuploadProperties.getFtp(), result.getFtp());
        BeanUtils.copyProperties(myFileuploadProperties.getLocal(), result.getLocal());
        return result;
    }

    @Bean
    public FtpStoreProvider ftpStoreProvider() {
        return new FtpStoreProvider(getMyFileUploadProperties(myFileuploadProperties));
    }

}
