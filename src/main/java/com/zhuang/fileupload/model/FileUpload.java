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
    private Date createTime;
    private Date modifyTime;
    private String createBy;
    private String modifyBy;


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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Override
	public String toString() {
		return "FileUpload [id=" + id + ", templateId=" + templateId + ", bizId=" + bizId + ", saveFullPath="
				+ saveFullPath + ", originFileName=" + originFileName + ", status=" + status + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + ", createBy=" + createBy + ", modifyBy="
				+ modifyBy + "]";
	}

}
