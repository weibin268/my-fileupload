package com.zhuang.fileupload;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.zhuang.fileupload.model.FileUpload;
import com.zhuang.fileupload.service.FileUploadService;
import com.zhuang.fileupload.util.FileUtils;

public class FileUploadManager {

    private StoreProvider storeProvider;
    private FileUploadService fileUploadService;

    public FileUploadService getFileUploadService() {
        return fileUploadService;
    }

    public void setFileUploadService(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    public FileUploadManager(StoreProvider storeProvider, FileUploadService fileUploadService) {
        this.storeProvider = storeProvider;
        this.fileUploadService = fileUploadService;
    }

    public FileUpload upload(InputStream inputStream, String bizTable, String bizField, String bizId, String fileName) {
        String path = bizTable + "/" + bizField + "/" + UUID.randomUUID().toString() + FileUtils.getExtension(fileName);
        storeProvider.save(inputStream, path);
        FileUpload fileUpload = new FileUpload();
        fileUpload.setBizTable(bizTable);
        fileUpload.setBizField(bizField);
        fileUpload.setBizId(bizId);
        fileUpload.setFilePath(path);
        fileUpload.setFileName(fileName);
        fileUploadService.add(fileUpload);
        return fileUpload;
    }

    public InputStream download(FileUpload fileUpload) {
        InputStream inputStream = storeProvider.get(fileUpload.getFilePath());
        return inputStream;
    }

    public InputStream download(String id) {
        FileUpload fileUpload = getSysFileUpload(id);
        return download(fileUpload);
    }

    public void delete(String id) {
        FileUpload fileUpload = getSysFileUpload(id);
        delete(fileUpload);
    }

    public void delete(FileUpload fileUpload) {
        storeProvider.delete(fileUpload.getFilePath());
        fileUploadService.delete(fileUpload.getId());
    }

    public void submit(String ids, String bizTable, String bizField, String bizId) {
        submit(Arrays.asList(ids.split(",")), bizTable, bizField, bizId);
    }

    public void submit(List<String> idList, String bizTable, String bizField, String bizId) {
        idList.forEach(id -> {
            fileUploadService.submit(id, bizTable, bizField, bizId);
        });
    }

    public FileUpload getSysFileUpload(String id) {
        return fileUploadService.get(id);
    }

    public List<FileUpload> getSysFileUploadList(String bizTable,String bizField) {
        return fileUploadService.getListByBizTableAndBizField(bizTable,bizField);
    }

    public FileUpload getFirstSysFileUpload(String bizTable, String bizField) {
        List<FileUpload> fileUploadList = getSysFileUploadList(bizTable, bizField);
        if (fileUploadList.size() > 0) {
            return fileUploadList.get(0);
        }
        return null;
    }
}
