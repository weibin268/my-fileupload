package com.zhuang.fileupload.service;

import java.util.List;

import com.zhuang.fileupload.model.FileUpload;

public interface FileUploadService {

    void delete(String id);

    FileUpload get(String id);

    void add(FileUpload model);

    void disable(String bizTable, String bizField, String bizId);

    List<FileUpload> getList(String bizTable, String bizField, String bizId);

    void submit(String id, String bizTable, String bizField, String bizId);

}
