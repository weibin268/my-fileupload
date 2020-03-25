package com.zhuang.fileupload.service;

import java.util.List;

import com.zhuang.fileupload.model.FileUpload;

public interface FileUploadService {

    void delete(String id);

    FileUpload get(String id);

    void add(FileUpload model);

    void disableByBizTableAndBizField(String bizTable, String bizField);

    List<FileUpload> getListByBizTableAndBizField(String bizTable, String bizField);

    void submit(String id, String bizTable, String bizField, String bizId);

}
