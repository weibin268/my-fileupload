package com.zhuang.fileupload.impl.ftp;

import java.io.InputStream;

import com.zhuang.fileupload.StoreProvider;
import com.zhuang.fileupload.config.MyFileUploadProperties;

public class FtpStoreProvider implements StoreProvider {

    private FtpManager ftpManager;

    public FtpStoreProvider() {
      this(new MyFileUploadProperties());
    }

    public FtpStoreProvider(MyFileUploadProperties fileUploadProperties){
		FtpManager.ConnectionMode connectionMode = FtpManager.ConnectionMode.active;
		if (fileUploadProperties.getFtp().getConnectionMode() != null && fileUploadProperties.getFtp().getConnectionMode() != "") {
			connectionMode = FtpManager.ConnectionMode.getByValue(fileUploadProperties.getFtp().getConnectionMode());
		}
		ftpManager = new FtpManager(fileUploadProperties.getFtp().getIp(), fileUploadProperties.getFtp().getUserName(),
				fileUploadProperties.getFtp().getPassword(), fileUploadProperties.getFtp().getBasePath(),
				connectionMode);
	}

    public void save(InputStream inputStream, String path) {
        ftpManager.uploadFile(inputStream, path);
    }

    public InputStream get(String path) {
        return ftpManager.downloadFile(path);
    }

    public void delete(String path) {
        ftpManager.deleteFile(path);
    }

}
