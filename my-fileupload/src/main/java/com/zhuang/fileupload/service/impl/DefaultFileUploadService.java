package com.zhuang.fileupload.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.util.StringUtils;
import com.zhuang.fileupload.enums.CommonStatus;
import com.zhuang.fileupload.model.FileUpload;
import com.zhuang.fileupload.service.FileUploadService;

public class DefaultFileUploadService implements FileUploadService {

    private DbAccessor dbAccessor;

    public DefaultFileUploadService() {
        dbAccessor = DbAccessor.get();
    }

    public DefaultFileUploadService(DbAccessor dbAccessor) {
        this.dbAccessor = dbAccessor;
    }

    @Override
    public void delete(String id) {
        dbAccessor.delete(id, FileUpload.class);
    }

    @Override
    public FileUpload get(String id) {
        return dbAccessor.select(id, FileUpload.class);
    }

    @Override
    public void add(FileUpload model) {
        model.setId(UUID.randomUUID().toString());
        model.setCreateTime(new Date());
        model.setStatus(CommonStatus.DISABLE.getValue());
        dbAccessor.insert(model);
    }

    @Override
    public void disable(String bizTable, String bizField, String bizId) {
        FileUpload query = new FileUpload();
        query.setBizTable(bizTable);
        query.setBizField(bizField);
        query.setBizId(bizId);
        List<FileUpload> fileUploadList = dbAccessor.selectList(query, FileUpload.class);
        fileUploadList.forEach(item -> {
            FileUpload fileUpload = new FileUpload();
            fileUpload.setId(item.getId());
            fileUpload.setStatus(CommonStatus.DISABLE.getValue());
            fileUpload.setModifyTime(new Date());
            dbAccessor.update(fileUpload, true);
        });
    }

    @Override
    public List<FileUpload> getList(String bizTable, String bizField, String bizId) {
        FileUpload query = new FileUpload();
        query.setBizTable(bizTable);
        query.setBizField(bizField);
        query.setBizId(bizId);
        query.setStatus(CommonStatus.ENABLE.getValue());
        List<FileUpload> fileUploadList = dbAccessor.selectList(query, FileUpload.class);
        return fileUploadList;
    }

    @Override
    public void submit(String id, String bizTable, String bizField, String bizId) {
        FileUpload fileUpload = get(id);
        if (fileUpload.getBizTable() != null) {
            if (!fileUpload.getBizTable().equals(bizTable)) {
                throw new RuntimeException("跟新的bizTable与原有的bizTable的值不一致！");
            }
        }
        FileUpload update = new FileUpload();
        update.setId(fileUpload.getId());
        update.setBizTable(bizTable);
        update.setBizField(bizField);
        update.setBizId(bizId);
        update.setStatus(CommonStatus.ENABLE.getValue());
        update.setModifyTime(new Date());
        dbAccessor.update(update, true);
    }
}
