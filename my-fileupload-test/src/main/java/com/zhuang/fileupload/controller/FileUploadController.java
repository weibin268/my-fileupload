package com.zhuang.fileupload.controller;

import com.zhuang.fileupload.FileUploadManager;
import com.zhuang.fileupload.model.ApiResult;
import com.zhuang.fileupload.model.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/fileupload")
public class FileUploadController {

    @Autowired
    private FileUploadManager fileUploadManager;

    @RequestMapping(value = "upload")
    @ResponseBody
    public Object upload(HttpServletRequest request, @RequestParam MultipartFile file) throws IOException {
        String path = request.getParameter("path");
        InputStream inputStream;
        inputStream = file.getInputStream();
        FileUpload fileUpload = fileUploadManager.upload(inputStream, path, file.getOriginalFilename());
        return ApiResult.success(fileUpload);
    }

    @RequestMapping(value = "uploadBatch")
    @ResponseBody
    public Object uploadBatch(HttpServletRequest request, MultipartFile[] files) throws IOException {
        String path = request.getParameter("path");
        List<FileUpload> fileUploadList = new ArrayList<>();
        StandardMultipartHttpServletRequest multipartRequest = (StandardMultipartHttpServletRequest) request;
        for (Map.Entry<String, List<MultipartFile>> entry : multipartRequest.getMultiFileMap().entrySet()) {
            for (MultipartFile file : entry.getValue()) {
                InputStream inputStream;
                inputStream = file.getInputStream();
                FileUpload fileUpload = fileUploadManager.upload(inputStream, path, file.getOriginalFilename());
                fileUploadList.add(fileUpload);
            }
        }
        return ApiResult.success(fileUploadList);
    }

    @RequestMapping(value = "download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fId = request.getParameter("fId");//文件上传Id
        FileUpload fileUpload = fileUploadManager.getFileUpload(fId);
        String fileName = fileUpload.getFileName();
        fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");//chrome,firefox
        //fileName=URLEncoder.encode(fileName,"utf-8");//IE
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        InputStream inputStream;
        OutputStream outputStream;
        inputStream = fileUploadManager.download(fileUpload);
        outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int readCount;
        while ((readCount = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readCount);
        }
        inputStream.close();
    }

}
