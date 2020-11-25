package com.zhuang.fileupload;

import java.io.InputStream;
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

    public FileUpload upload(InputStream inputStream, String path, String fileName) {
        String filePath = path + "/" + UUID.randomUUID().toString() + FileUtils.getExtension(fileName);
        storeProvider.save(inputStream, filePath);
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFilePath(filePath);
        fileUpload.setFileName(fileName);
        fileUploadService.add(fileUpload);
        return fileUpload;
    }

    public InputStream download(FileUpload fileUpload) {
        InputStream inputStream = storeProvider.get(fileUpload.getFilePath());
        return inputStream;
    }

    public InputStream download(String id) {
        FileUpload fileUpload = getFileUpload(id);
        return download(fileUpload);
    }

    public void delete(String id) {
        FileUpload fileUpload = getFileUpload(id);
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
        fileUploadService.disable(bizTable, bizField, bizId);
        idList.forEach(id -> {
            fileUploadService.submit(id, bizTable, bizField, bizId);
        });
    }

    public FileUpload getFileUpload(String id) {
        return fileUploadService.get(id);
    }

    public List<FileUpload> getFileUploadList(String bizTable, String bizField, String bizId) {
        return fileUploadService.getList(bizTable, bizField, bizId);
    }

    public FileUpload getFirstFileUpload(String bizTable, String bizField, String bizId) {
        List<FileUpload> fileUploadList = getFileUploadList(bizTable, bizField, bizId);
        if (fileUploadList.size() > 0) {
            return fileUploadList.get(0);
        }
        return null;
    }
}
