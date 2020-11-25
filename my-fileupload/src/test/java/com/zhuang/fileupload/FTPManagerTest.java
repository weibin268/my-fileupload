package com.zhuang.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.zhuang.fileupload.impl.ftp.FtpManager;

public class FTPManagerTest {

    @Test
    public void uploadFile() throws FileNotFoundException {
        FtpManager ftpManager = new FtpManager("127.0.0.1", "zwb", "1231");
        InputStream inputStream = new FileInputStream(new File("e:/test.mp4"));
        ftpManager.uploadFile(inputStream, "test.mp4");
    }

    @Test
    public void downloadFile() throws IOException {
        FtpManager ftpManager = new FtpManager("127.0.0.1", "zwb", "123");
        InputStream inputStream = ftpManager.downloadFile("test.mp4");
        File file = new File("e:/test01.mp4");
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count;
        while ((count = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, count);
        }
        inputStream.close();
        outputStream.close();
    }

    @Test
    public void deleteFile() throws IOException {
        FtpManager ftpManager = new FtpManager("127.0.0.1", "zhuang", "zwb");
        ftpManager.deleteFile("test.sql");
    }

}
