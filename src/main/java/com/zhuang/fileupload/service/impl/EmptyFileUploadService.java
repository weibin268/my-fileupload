package com.zhuang.fileupload.service.impl;

import java.util.List;

import com.zhuang.fileupload.model.FileUpload;
import com.zhuang.fileupload.service.FileUploadService;

public class EmptyFileUploadService implements FileUploadService{


	@Override
	public void delete(String id) {

	}

	@Override
	public FileUpload get(String id) {
		return null;
	}

	@Override
	public void add(FileUpload model) {

	}

	@Override
	public void disable(String bizTable, String bizField, String bizId) {

	}

	@Override
	public List<FileUpload> getList(String bizTable, String bizField, String bizId) {
		return null;
	}

	@Override
	public void submit(String id, String bizTable, String bizField, String bizId) {

	}
}
