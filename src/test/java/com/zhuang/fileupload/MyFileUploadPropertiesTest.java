package com.zhuang.fileupload;

import org.junit.Test;

import com.zhuang.fileupload.config.MyFileUploadProperties;

public class MyFileUploadPropertiesTest {

	@Test
	public void test() {
		
		MyFileUploadProperties fileUploadProperties=new MyFileUploadProperties();
		System.out.println(fileUploadProperties.getFtpIp());
		System.out.println(fileUploadProperties.getFtpUserName());
		System.out.println(fileUploadProperties.getFtpPassword());
		
	}
	
}
