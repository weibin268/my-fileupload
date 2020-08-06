package com.zhuang.fileupload.impl.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

import com.zhuang.fileupload.util.FileUtils;

public class FtpManager {

    private String ip;
    private String userName;
    private String password;
    private String basePath;

    public FtpManager(String ip, String userName, String password, String basePath) {
        this.ip = ip;
        this.userName = userName;
        this.password = password;
        this.basePath = basePath;

    }

    public FtpManager(String ip, String userName, String password) {
        this(ip, userName, password, null);
    }

    public void uploadFile(InputStream inputStream, String fileFullPath) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFtpClient();
            ensureDirectoryExists(ftpClient, basePath);
            String dirPath = FileUtils.getDirPath(fileFullPath);
            String fileName = FileUtils.getFileName(fileFullPath);
            ensureDirectoryExists(ftpClient, dirPath);
            ftpClient.storeFile(fileName, inputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeFtpClient(ftpClient);
        }
    }

    public InputStream downloadFile(String fileName) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFtpClient();
            ensureDirectoryExists(ftpClient, basePath);
            InputStream inputStream = ftpClient.retrieveFileStream(fileName);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, count);
            }
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeFtpClient(ftpClient);
        }
        return null;
    }

    public void deleteFile(String fileName) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFtpClient();
            ensureDirectoryExists(ftpClient, basePath);
            ftpClient.deleteFile(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeFtpClient(ftpClient);
        }
    }

    public FTPClient getFtpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        if (ip.contains(":")) {
            String[] arrIp = ip.split(":");
            ftpClient.connect(arrIp[0], Integer.parseInt(arrIp[1]));
        } else {
            ftpClient.connect(ip);
        }
        ftpClient.login(userName, password);
        ftpClient.setControlEncoding("UTF-8");// 中文支持
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        return ftpClient;
    }

    public void closeFtpClient(FTPClient ftpClient) {
        if (ftpClient != null & ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void ensureDirectoryExists(FTPClient ftpClient, String path) throws IOException {
        if (path == null || path.length() == 0) return;
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
