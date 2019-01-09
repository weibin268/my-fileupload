package com.zhuang.fileupload;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.zhuang.fileupload.model.FileUpload;
import com.zhuang.fileupload.model.FileUploadTemplate;
import com.zhuang.fileupload.service.FileUploadService;
import com.zhuang.fileupload.util.FileUtils;

public class FileUploadManager {

	private StoreProvider storeProvider;
	private FileUploadService fileUploadService;
	private List<FileUploadTemplate> templates;


	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public FileUploadManager(StoreProvider storeProvider, FileUploadService fileUploadService) {

		this.storeProvider = storeProvider;
		this.fileUploadService = fileUploadService;
		templates = fileUploadService.getAllTemplates();

	}

	public FileUpload upload(InputStream inputStream, String templateId, String fileName, String bizId) {

		FileUploadTemplate fileUploadTemplate = getTemplateById(templateId);
		return upload(inputStream, fileUploadTemplate, fileName, bizId);

	}

	public FileUpload upload(InputStream inputStream, FileUploadTemplate template, String fileName,
							 String bizId) {

		String path = template.getSaveDir() + "/" + UUID.randomUUID().toString() + FileUtils.getExtension(fileName);

		storeProvider.save(inputStream, path);

		FileUpload fileUpload = new FileUpload();
		fileUpload.setTemplateId(template.getId());
		fileUpload.setBizId(bizId);
		fileUpload.setSaveFullPath(path);
		fileUpload.setOriginFileName(fileName);
		fileUploadService.save(fileUpload);

		return fileUpload;
	}

	public InputStream download(FileUpload fileUpload) {

		InputStream inputStream = storeProvider.get(fileUpload.getSaveFullPath());

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

		storeProvider.delete(fileUpload.getSaveFullPath());

		fileUploadService.delete(fileUpload.getId());

	}

	public void submit(String ids) {

		submit(ids, null);
	}

	public void submit(String ids, String newBizId) {

		String[] arrIds = ids.split(",");
		submit(arrIds, newBizId);

	}

	public void submit(String[] ids) {
		submit(ids, null);
	}

	public void submit(String[] ids, String newBizId) {

		String bizId = fileUploadService.getBizIdById(ids[0]);

		List<FileUpload> fileUploads = fileUploadService.getListByBizId(bizId);

		List<String> lsIds = Arrays.asList(ids);

		for (FileUpload fileUpload : fileUploads) {
			if (!lsIds.contains(fileUpload.getId())) {
				delete(fileUpload.getId());
			}
		}

		fileUploadService.submit(ids);

		if (newBizId != null && newBizId.length() > 0) {
			fileUploadService.updateBizId(bizId, newBizId);
		}
	}

	public FileUpload getSysFileUpload(String id) {

		return fileUploadService.get(id);

	}

	public List<FileUpload> getSysFileUploadList(String bizId, boolean onlySubmitted) {

		List<FileUpload> result = new ArrayList<FileUpload>();

		for (FileUpload fileUpload : fileUploadService.getListByBizId(bizId)) {

			if (onlySubmitted && fileUpload.getStatus() == 0) {
				continue;
			}

			result.add(fileUpload);

		}

		return result;
	}

	public List<FileUpload> getSysFileUploadList(String bizId) {

		return getSysFileUploadList(bizId, true);
	}

	public FileUpload getSysFileUploadFirst(String bizId) {
		
		List<FileUpload> fileUploads = getSysFileUploadList(bizId);

		if(fileUploads.size()>0)
		{
			return fileUploads.get(0);
		}
		
		return null;
	}

	private FileUploadTemplate getTemplateById(String templateId) {
		FileUploadTemplate result = null;

		for (FileUploadTemplate item : templates) {
			if (item.getId().equals(templateId)) {
				result = item;
				break;
			}
		}

		return result;
	}

}
