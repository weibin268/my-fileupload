package com.zhuang.fileupload.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.zhuang.data.DbAccessor;
import com.zhuang.fileupload.model.FileUpload;
import com.zhuang.fileupload.model.FileUploadTemplate;
import com.zhuang.fileupload.service.FileUploadService;

public class DefaultFileUploadService implements FileUploadService{

	private DbAccessor dbAccessor;
	
	public DefaultFileUploadService() {

		dbAccessor=DbAccessor.get();
	}

	public DefaultFileUploadService(DbAccessor dbAccessor)
    {
        this.dbAccessor=dbAccessor;
    }
	
	public void delete(String id) {

		dbAccessor.delete(id, FileUpload.class);
		
	}

	public FileUpload get(String id) {

		return dbAccessor.select(id, FileUpload.class);
	}

	public List<FileUpload> getListByBizId(String bizId) {
		
		return dbAccessor.queryEntities("com.zhuang.fileupload.mapper.FileUpload.getListByBizId", bizId, FileUpload.class);
	
	}
	
	public String getBizIdById(String id) {
	
		return dbAccessor.queryEntity("com.zhuang.fileupload.mapper.FileUpload.getBizIdById", id, String.class);
	
	}

	public String save(FileUpload model)
	{
		if(model.getId()!=null && model.getId().length()>0)
		{
			model.setModifiedTime(new Date());
			dbAccessor.update(model);
		}else
		{
			model.setId(UUID.randomUUID().toString());
			model.setCreatedTime(new Date());
			model.setStatus(0);
			dbAccessor.insert(model);
		}
		
		return model.getId();
		
	}

	public void updateBizId(String oldBizId, String newBizId) {
	
		Map<String,String> params=new HashMap<String, String>();
		
		params.put("oldBizId", oldBizId);
		
		params.put("newBizId", newBizId);
		
		dbAccessor.executeNonQuery("com.zhuang.fileupload.mapper.FileUpload.updateBizId",params);
	
	}

	public List<FileUploadTemplate> getAllTemplates() {
		
		return dbAccessor.queryEntities("com.zhuang.fileupload.mapper.FileUpload.getAllTemplates",null, FileUploadTemplate.class);
	
	}

	public void submit(String[] ids) {
		
		for (String id : ids) {
			
			dbAccessor.executeNonQuery("com.zhuang.fileupload.mapper.FileUpload.submitById",id);
				
		}
		
	}



	
}
