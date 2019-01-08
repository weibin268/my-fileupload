package com.zhuang.fileupload.impl.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

import com.zhuang.fileupload.util.FileUtils;

public class FtpManager {

	private String ip;

	private String userName;

	private String password;

	private FTPClient ftpClient;

	private String basePath;

	public FtpManager(String ip, String userName, String password, String basePath) {

		this.ip = ip;
		this.userName = userName;
		this.password = password;
		this.basePath = basePath;

		ftpClient = new FTPClient();

	}

	public FtpManager(String ip, String userName, String password) {

		this(ip, userName, password, null);

	}

	public void uploadFile(InputStream inputStream, String fileFullPath) {

		try {

			ftpClient.connect(ip);
			ftpClient.user(userName);
			ftpClient.pass(password);
			ensureDirectoryExists(ftpClient, basePath);

			String dirPath = FileUtils.getDirPath(fileFullPath);
			String fileName = FileUtils.getFileName(fileFullPath);

			ensureDirectoryExists(ftpClient, dirPath);

			ftpClient.storeFile(fileName, inputStream);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public InputStream downloadFile(String fileName) {

		try {

			ftpClient.connect(ip);
			ftpClient.user(userName);
			ftpClient.pass(password);
			ensureDirectoryExists(ftpClient, basePath);

			return ftpClient.retrieveFileStream(fileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;

	}

	public void deleteFile(String fileName) {

		try {

			ftpClient.connect(ip);
			ftpClient.user(userName);
			ftpClient.pass(password);
			ensureDirectoryExists(ftpClient, basePath);

			ftpClient.deleteFile(fileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void ensureDirectoryExists(FTPClient ftpClient, String path) throws IOException {

		String[] pathItems = path.split("\\/");

		for (String item : pathItems) {
			boolean exists = ftpClient.changeWorkingDirectory(item);
			if (!exists) {
				ftpClient.mkd(item);
				ftpClient.changeWorkingDirectory(item);
			}
		}
	}

}
