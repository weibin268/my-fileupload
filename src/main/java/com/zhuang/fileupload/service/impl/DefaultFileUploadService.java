package com.zhuang.fileupload.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.zhuang.data.DbAccessor;
import com.zhuang.fileupload.enums.CommonStatus;
import com.zhuang.fileupload.model.FileUpload;
import com.zhuang.fileupload.model.FileUploadTemplate;
import com.zhuang.fileupload.service.FileUploadService;

public class DefaultFileUploadService implements FileUploadService {

    private DbAccessor dbAccessor;

    public DefaultFileUploadService() {
        dbAccessor = DbAccessor.get();
    }

    public DefaultFileUploadService(DbAccessor dbAccessor) {
        this.dbAccessor = dbAccessor;
    }

    public void delete(String id) {
        dbAccessor.delete(id, FileUpload.class);
    }

    public FileUpload get(String id) {
        return dbAccessor.select(id, FileUpload.class);
    }

    public List<FileUpload> getListByBizId(String bizId) {
        FileUpload fileUpload = new FileUpload();
        fileUpload.setBizId(bizId);
        return dbAccessor.selectList(fileUpload, FileUpload.class);
    }

    public String getBizIdById(String id) {
        FileUpload fileUpload = dbAccessor.select(id, FileUpload.class);
        return fileUpload == null ? null : fileUpload.getBizId();
    }

    public String save(FileUpload model) {
        if (model.getId() != null && model.getId().length() > 0) {
            model.setModifyTime(new Date());
            dbAccessor.update(model);
        } else {
            model.setId(UUID.randomUUID().toString());
            model.setCreateTime(new Date());
            model.setStatus(0);
            dbAccessor.insert(model);
        }
        return model.getId();
    }

    public void updateBizId(String oldBizId, String newBizId) {
        List<FileUpload> fileUploadList = getListByBizId(oldBizId);
        fileUploadList.forEach(item -> {
            FileUpload fileUpload = new FileUpload();
            fileUpload.setId(item.getId());
            fileUpload.setBizId(newBizId);
            fileUpload.setModifyTime(new Date());
            dbAccessor.update(fileUpload, true);
        });
    }

    public List<FileUploadTemplate> getAllTemplates() {
        FileUploadTemplate fileUploadTemplate = new FileUploadTemplate();
        fileUploadTemplate.setStatus(CommonStatus.ENABLE.getValue());
        return dbAccessor.selectList(fileUploadTemplate, FileUploadTemplate.class);
    }

    public void submit(String[] ids) {
        for (String id : ids) {
            FileUpload fileUpload = new FileUpload();
            fileUpload.setId(id);
            fileUpload.setStatus(CommonStatus.ENABLE.getValue());
            fileUpload.setModifyTime(new Date());
            dbAccessor.update(fileUpload, true);
        }
    }
}
