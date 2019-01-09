package com.zhuang.fileupload.model;

import java.util.Date;

import com.zhuang.data.orm.annotation.Id;
import com.zhuang.data.orm.annotation.Table;
import com.zhuang.data.orm.annotation.UnderscoreNaming;

@UnderscoreNaming
@Table(name="sys_fileupload")
public class FileUpload {
	
	@Id
    private String id;
 
    private String templateId;
 
    private String bizId;
    
    private String saveFullPath;
    
    private String originFileName;
    
    private Integer status;

    private Date createdTime;

    private Date modifiedTime;

    private String createdBy;

    private String modifiedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	public String getSaveFullPath() {
		return saveFullPath;
	}

	public void setSaveFullPath(String saveFullPath) {
		this.saveFullPath = saveFullPath;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return "FileUpload [id=" + id + ", templateId=" + templateId + ", bizId=" + bizId + ", saveFullPath="
				+ saveFullPath + ", originFileName=" + originFileName + ", status=" + status + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + "]";
	}

}
