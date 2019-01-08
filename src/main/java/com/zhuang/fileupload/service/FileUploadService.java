package com.zhuang.fileupload.service;

import java.util.List;

import com.zhuang.fileupload.model.SysFileUpload;
import com.zhuang.fileupload.model.SysFileUploadTemplate;

public interface FileUploadService {

	void delete(String id);
    
	SysFileUpload get(String id);
    
    List<SysFileUpload> getListByBizId(String bizId);
    
    String getBizIdById(String id);
    
    String save(SysFileUpload model);
    
    void submit(String[] ids);
    
    void updateBizId(String oldBizId,String newBizId);
    
    List<SysFileUploadTemplate> getAllTemplates();
    
}
