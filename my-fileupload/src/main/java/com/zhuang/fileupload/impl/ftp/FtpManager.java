package com.zhuang.fileupload.impl.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;
import com.zhuang.fileupload.util.FileUtils;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpManager {

    private Logger logger = LoggerFactory.getLogger(FtpManager.class);

    private String ip;
    private String userName;
    private String password;
    private String basePath;
    private ConnectionMode connectionMode;

    public FtpManager(String ip, String userName, String password, String basePath, ConnectionMode connectionMode) {
        this.ip = ip;
        this.userName = userName;
        this.password = password;
        this.basePath = basePath;
        this.connectionMode = connectionMode;
    }

    public FtpManager(String ip, String userName, String password) {
        this(ip, userName, password, null, ConnectionMode.active);
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
            handleReplyCode(ftpClient);
        } catch (Exception e) {
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
            gotoWorkingDirectory(ftpClient, basePath);
            InputStream inputStream = ftpClient.retrieveFileStream(fileName);
            handleReplyCode(ftpClient);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, count);
            }
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
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
            gotoWorkingDirectory(ftpClient, basePath);
            ftpClient.deleteFile(fileName);
            handleReplyCode(ftpClient);
        } catch (Exception e) {
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
        if (connectionMode == ConnectionMode.passive) {
            ftpClient.enterLocalPassiveMode();
        }
        ftpClient.setControlEncoding("UTF-8");// 中文支持
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        handleReplyCode(ftpClient);
        return ftpClient;
    }

    public void closeFtpClient(FTPClient ftpClient) {
        if (ftpClient != null & ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void gotoWorkingDirectory(FTPClient ftpClient, String path) throws IOException {
        if (path == null || path.length() == 0) return;
        ftpClient.changeWorkingDirectory(path);
    }

    public void handleReplyCode(FTPClient ftpClient) {
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            logger.warn("FTPClient get fail reply coded:" + reply);
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

    public enum ConnectionMode {

        active("active", "主动"),
        passive("passive", "被动");

        ConnectionMode(String value, String name) {
            this.value = value;
            this.name = name;
        }

        private String value;
        private String name;

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static ConnectionMode getByValue(String value) {
            return Arrays.stream(ConnectionMode.values()).filter(c -> c.getValue().equals(value)).findFirst().orElse(null);
        }
    }

}
