package com.zhuang.fileupload.service;

import java.util.List;

import com.zhuang.fileupload.model.FileUpload;
import com.zhuang.fileupload.model.FileUploadTemplate;

public interface FileUploadService {

	void delete(String id);
    
	FileUpload get(String id);
    
    List<FileUpload> getListByBizId(String bizId);
    
    String getBizIdById(String id);
    
    String save(FileUpload model);
    
    void submit(String[] ids);
    
    void updateBizId(String oldBizId,String newBizId);
    
    List<FileUploadTemplate> getAllTemplates();
    
}
