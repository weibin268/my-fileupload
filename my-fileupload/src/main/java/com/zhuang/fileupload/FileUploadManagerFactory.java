package com.zhuang.fileupload;

import com.zhuang.fileupload.config.MyFileUploadProperties;
import com.zhuang.fileupload.enums.StoreProviderType;
import com.zhuang.fileupload.impl.ftp.FtpStoreProvider;
import com.zhuang.fileupload.impl.local.LocalStoreProvider;
import com.zhuang.fileupload.service.impl.DefaultFileUploadService;

public class FileUploadManagerFactory {

	private static FileUploadManager fileUploadManager;

	public static synchronized FileUploadManager getDefaultFileUploadManager() {
		if (fileUploadManager == null) {
			MyFileUploadProperties myFileUploadProperties = new MyFileUploadProperties();
			if (myFileUploadProperties.getStoreProvider().equalsIgnoreCase(StoreProviderType.FTP.getValue())) {
				fileUploadManager = new FileUploadManager(new FtpStoreProvider(), new DefaultFileUploadService());
			} else if (myFileUploadProperties.getStoreProvider().equalsIgnoreCase(StoreProviderType.LOCAL.getValue())) {
				fileUploadManager = new FileUploadManager(new LocalStoreProvider(), new DefaultFileUploadService());
			}
		}
		return fileUploadManager;
	}

}
