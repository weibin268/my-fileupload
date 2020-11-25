package com.zhuang.fileupload;

import org.junit.Test;

import com.zhuang.fileupload.config.MyFileUploadProperties;

public class MyFileUploadPropertiesTest {

	@Test
	public void test() {
		
		MyFileUploadProperties fileUploadProperties=new MyFileUploadProperties();
		System.out.println(fileUploadProperties.getFtp().getIp());
		System.out.println(fileUploadProperties.getFtp().getUserName());
		System.out.println(fileUploadProperties.getFtp().getPassword());
		
	}
	
}
