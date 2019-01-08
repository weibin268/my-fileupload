package com.zhuang.fileupload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;

import com.zhuang.fileupload.model.SysFileUpload;
import com.zhuang.fileupload.model.SysFileUploadTemplate;

public class FileUploadManagerTest {

    private FileUploadManager fileUploadManager;
	
    public FileUploadManagerTest()
    {
    	fileUploadManager=FileUploadManagerFactory.getDefaultFileUploadManager();
    }
    
	@Test
	public void upload() throws IOException {
		
	
		InputStream inputStream = new FileInputStream(new File("/home/zhuang/myfiles/doc/test.txt"));
		
		SysFileUploadTemplate sysFileUploadTemplate=new SysFileUploadTemplate();
		sysFileUploadTemplate.setId("t1");
		sysFileUploadTemplate.setSaveDir("test/01");
		SysFileUpload sysFileUpload = fileUploadManager.upload(inputStream, sysFileUploadTemplate, "test.txt", "123");
		fileUploadManager.submit("6ae77d11-7b5d-4086-b506-9d74ee89f41c,d5a352d1-c6c2-46e2-a90f-588f5fceace0");;
		
		inputStream.close();

	}
	
	@Test
	public void submit() throws IOException {
		
		fileUploadManager.submit("c67a0a46-d8eb-4ff0-90da-2d06622e3b7c","006");;

	}
	
	@Test
	public void delete() {

		fileUploadManager.delete("7dbf3e0f-d429-4d3f-968c-e6490747ea10");
		
	}

	@Test
	public void download() throws IOException {

		SysFileUpload sysFileUpload = fileUploadManager.getSysFileUpload("f005d5fb-4e3c-4876-ae6c-19a8346a8a51");
		
		System.out.println(sysFileUpload);
		InputStream inputStream = fileUploadManager.download(sysFileUpload);
		
		InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

		BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
		String line="";
		
		while ((line=bufferedReader.readLine())!=null) {

			System.out.println(line);
		}
		
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		
	}
	
	@Test
	public void getSysFileUploadList(){

		List<SysFileUpload> sysFileUploads = fileUploadManager.getSysFileUploadList("123");
		
		for (SysFileUpload sysFileUpload : sysFileUploads) {
			System.out.println(sysFileUpload);
		}
	}

}
