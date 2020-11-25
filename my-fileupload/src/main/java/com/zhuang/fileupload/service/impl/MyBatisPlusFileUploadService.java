package com.zhuang.fileupload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuang.fileupload.enums.CommonStatus;
import com.zhuang.fileupload.mapper.FileUploadMapper;
import com.zhuang.fileupload.model.FileUpload;
import com.zhuang.fileupload.service.FileUploadService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MyBatisPlusFileUploadService extends ServiceImpl<FileUploadMapper, FileUpload> implements FileUploadService {

    @Override
    public void delete(String id) {
        removeById(id);
    }

    @Override
    public FileUpload get(String id) {
        return getById(id);
    }

    @Override
    public void add(FileUpload model) {
        model.setId(UUID.randomUUID().toString());
        model.setCreateTime(new Date());
        model.setStatus(CommonStatus.DISABLE.getValue());
        save(model);
    }

    @Override
    public void disable(String bizTable, String bizField, String bizId) {
        update(new LambdaUpdateWrapper<FileUpload>()
                .eq(FileUpload::getBizTable, bizTable)
                .eq(FileUpload::getBizField, bizField)
                .eq(FileUpload::getBizId, bizId)
                .set(FileUpload::getStatus, CommonStatus.DISABLE.getValue())
                .set(FileUpload::getModifyTime, new Date())
        );
    }

    @Override
    public List<FileUpload> getList(String bizTable, String bizField, String bizId) {
        return list(new LambdaQueryWrapper<FileUpload>()
                .eq(FileUpload::getBizTable, bizTable)
                .eq(FileUpload::getBizField, bizField)
                .eq(FileUpload::getBizId, bizId)
                .eq(FileUpload::getStatus, CommonStatus.ENABLE.getValue())
        );
    }

    @Override
    public void submit(String id, String bizTable, String bizField, String bizId) {
        FileUpload fileUpload = getById(id);
        if (fileUpload.getBizTable() != null) {
            if (!fileUpload.getBizTable().equals(bizTable)) {
                throw new RuntimeException("跟新的bizTable与原有的bizTable的值不一致！");
            }
        }
        update(new LambdaUpdateWrapper<FileUpload>()
                .eq(FileUpload::getId, id)
                .set(FileUpload::getBizTable, bizTable)
                .set(FileUpload::getBizField, bizField)
                .set(FileUpload::getBizId, bizId)
                .set(FileUpload::getStatus, CommonStatus.ENABLE.getValue())
                .set(FileUpload::getModifyTime, new Date())
        );
    }
}
