package com.zhuang.fileupload.util;

import com.zhuang.fileupload.FileUploadManager;
import com.zhuang.fileupload.enums.BizInfo;
import com.zhuang.fileupload.model.FileInfo;
import com.zhuang.fileupload.model.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传工具类
 */
@Component
public class FileUploadUtils {

    private static FileUploadUtils _this;

    @Autowired
    private FileUploadManager fileUploadManager;

    @PostConstruct
    public void init() {
        _this = this;
    }

    public static void save(BizInfo bizInfo, String bizId, List<FileInfo> fileInfoList) {
        if (fileInfoList == null) return;
        List<String> fileIdList = fileInfoList.stream().map(c -> c.getFileId()).collect(Collectors.toList());
        _this.fileUploadManager.submit(fileIdList, bizInfo.getTable(), bizInfo.getField(), bizId);
    }

    public static FileInfo get(BizInfo bizInfo, String bizId) {
        List<FileInfo> list = getList(bizInfo, bizId);
        if (CollectionUtils.isEmpty(list)) return null;
        return list.get(0);
    }

    public static List<FileInfo> getList(BizInfo bizInfo, String bizId) {
        List<FileUpload> fileUploadList = _this.fileUploadManager.getFileUploadList(bizInfo.getTable(), bizInfo.getField(), bizId);
        List<FileInfo> fileInfoList = fileUploadList.stream().map(item -> FileInfo.parse(item)).collect(Collectors.toList());
        return fileInfoList;
    }

}
