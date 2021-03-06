package com.zhuang.fileupload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.zhuang.fileupload.model.FileUpload;
import org.junit.Test;

public class FileUploadManagerTest {

    private FileUploadManager fileUploadManager;

    public FileUploadManagerTest() {
        fileUploadManager = FileUploadManagerFactory.getDefaultFileUploadManager();
    }

    @Test
    public void upload() throws IOException {

        InputStream inputStream = new FileInputStream(new File("/home/zhuang/myfiles/doc/test.txt"));
        FileUpload fileUpload = fileUploadManager.upload(inputStream, "sys_user/pic", "test.txt");
        fileUploadManager.submit("6ae77d11-7b5d-4086-b506-9d74ee89f41c,d5a352d1-c6c2-46e2-a90f-588f5fceace0", "", "", "");
        inputStream.close();

    }

    @Test
    public void submit() throws IOException {

        fileUploadManager.submit("c67a0a46-d8eb-4ff0-90da-2d06622e3b7c", "006", "", "");

    }

    @Test
    public void delete() {

        fileUploadManager.delete("7dbf3e0f-d429-4d3f-968c-e6490747ea10");

    }

    @Test
    public void download() throws IOException {

        FileUpload fileUpload = fileUploadManager.getFileUpload("f005d5fb-4e3c-4876-ae6c-19a8346a8a51");

        System.out.println(fileUpload);
        InputStream inputStream = fileUploadManager.download(fileUpload);

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {

            System.out.println(line);
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

    }

    @Test
    public void getSysFileUploadList() {

        List<FileUpload> fileUploads = fileUploadManager.getFileUploadList("sys_user", "pic","1");

        for (FileUpload fileUpload : fileUploads) {
            System.out.println(fileUpload);
        }
    }

}
