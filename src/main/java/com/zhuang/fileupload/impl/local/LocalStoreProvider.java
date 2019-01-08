package com.zhuang.fileupload.impl.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.zhuang.fileupload.StoreProvider;
import com.zhuang.fileupload.config.MyFileUploadProperties;
import com.zhuang.fileupload.util.FileUtils;

public class LocalStoreProvider implements StoreProvider {

	private MyFileUploadProperties fileUploadProperties;

	public LocalStoreProvider() {

		fileUploadProperties = new MyFileUploadProperties();
	}

	public void save(InputStream inputStream, String path) {

		String fullPath = FileUtils.combinePath(fileUploadProperties.getLocalBasePath(), path);
		FileOutputStream fileOutputStream = null;
		try {

			String dirPath = FileUtils.getDirPath(fullPath);

			File fileDir = new File(dirPath);

			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			fileOutputStream = new FileOutputStream(new File(fullPath));

			byte[] buffer = new byte[1024];
			int readLenth;
			while ((readLenth = inputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, readLenth);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fileOutputStream != null)
					fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public InputStream get(String path) {

		String fullPath = FileUtils.combinePath(fileUploadProperties.getLocalBasePath(), path);

		InputStream result = null;

		try {
			result = new FileInputStream(new File(fullPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public void delete(String path) {

		String fullPath = FileUtils.combinePath(fileUploadProperties.getLocalBasePath(), path);

		File file = new File(fullPath);

		if (file.exists()) {
			file.delete();
		}

	}

}
