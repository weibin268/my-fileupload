package com.zhuang.fileupload.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.zhuang.data.DbAccessor;
import com.zhuang.fileupload.model.SysFileUpload;
import com.zhuang.fileupload.model.SysFileUploadTemplate;
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

		dbAccessor.delete(id, SysFileUpload.class);
		
	}

	public SysFileUpload get(String id) {

		return dbAccessor.select(id, SysFileUpload.class);
	}

	public List<SysFileUpload> getListByBizId(String bizId) {
		
		return dbAccessor.queryEntities("com.zhuang.fileupload.mapper.SysFileUpload.getListByBizId", bizId, SysFileUpload.class);
	
	}
	
	public String getBizIdById(String id) {
	
		return dbAccessor.queryEntity("com.zhuang.fileupload.mapper.SysFileUpload.getBizIdById", id, String.class);
	
	}

	public String save(SysFileUpload model)
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
		
		dbAccessor.executeNonQuery("com.zhuang.fileupload.mapper.SysFileUpload.updateBizId",params);
	
	}

	public List<SysFileUploadTemplate> getAllTemplates() {
		
		return dbAccessor.queryEntities("com.zhuang.fileupload.mapper.SysFileUpload.getAllTemplates",null,SysFileUploadTemplate.class);
	
	}

	public void submit(String[] ids) {
		
		for (String id : ids) {
			
			dbAccessor.executeNonQuery("com.zhuang.fileupload.mapper.SysFileUpload.submitById",id);
				
		}
		
	}



	
}
