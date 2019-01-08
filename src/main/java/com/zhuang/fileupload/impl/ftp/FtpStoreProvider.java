package com.zhuang.fileupload.impl.ftp;

import java.io.InputStream;
import com.zhuang.fileupload.StoreProvider;
import com.zhuang.fileupload.config.MyFileUploadProperties;

public class FtpStoreProvider implements StoreProvider {

	private FtpManager ftpManager;

	public FtpStoreProvider() {

		MyFileUploadProperties fileUploadProperties = new MyFileUploadProperties();
		ftpManager = new FtpManager(fileUploadProperties.getFtpIp(), fileUploadProperties.getFtpUserName(),
				fileUploadProperties.getFtpPassword(),fileUploadProperties.getFtpBasePath());

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
