package com.zhuang.fileupload;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.zhuang.fileupload.model.SysFileUpload;
import com.zhuang.fileupload.model.SysFileUploadTemplate;
import com.zhuang.fileupload.service.FileUploadService;
import com.zhuang.fileupload.util.FileUtils;

public class FileUploadManager {

	private StoreProvider storeProvider;

	private FileUploadService fileUploadService;

	private List<SysFileUploadTemplate> templates;

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

	public SysFileUpload upload(InputStream inputStream, String templateId, String fileName, String bizId) {

		SysFileUploadTemplate sysFileUploadTemplate = getTemplateById(templateId);
		return upload(inputStream, sysFileUploadTemplate, fileName, bizId);

	}

	public SysFileUpload upload(InputStream inputStream, SysFileUploadTemplate template, String fileName,
			String bizId) {

		String path = template.getSaveDir() + "/" + UUID.randomUUID().toString() + FileUtils.getExtension(fileName);

		storeProvider.save(inputStream, path);

		SysFileUpload sysFileUpload = new SysFileUpload();
		sysFileUpload.setTemplateId(template.getId());
		sysFileUpload.setBizId(bizId);
		sysFileUpload.setSaveFullPath(path);
		sysFileUpload.setOrginFileName(fileName);
		fileUploadService.save(sysFileUpload);

		return sysFileUpload;
	}

	public InputStream download(SysFileUpload sysFileUpload) {

		InputStream inputStream = storeProvider.get(sysFileUpload.getSaveFullPath());

		return inputStream;
	}

	public InputStream download(String id) {

		SysFileUpload sysFileUpload = getSysFileUpload(id);

		return download(sysFileUpload);
	}

	public void delete(String id) {

		SysFileUpload sysFileUpload = getSysFileUpload(id);

		delete(sysFileUpload);

	}

	public void delete(SysFileUpload sysFileUpload) {

		storeProvider.delete(sysFileUpload.getSaveFullPath());

		fileUploadService.delete(sysFileUpload.getId());

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

		List<SysFileUpload> sysFileUploads = fileUploadService.getListByBizId(bizId);

		List<String> lsIds = Arrays.asList(ids);

		for (SysFileUpload sysFileUpload : sysFileUploads) {
			if (!lsIds.contains(sysFileUpload.getId())) {
				delete(sysFileUpload.getId());
			}
		}

		fileUploadService.submit(ids);

		if (newBizId != null && newBizId.length() > 0) {
			fileUploadService.updateBizId(bizId, newBizId);
		}
	}

	public SysFileUpload getSysFileUpload(String id) {

		return fileUploadService.get(id);

	}

	public List<SysFileUpload> getSysFileUploadList(String bizId, boolean onlySubmitted) {

		List<SysFileUpload> result = new ArrayList<SysFileUpload>();

		for (SysFileUpload sysFileUpload : fileUploadService.getListByBizId(bizId)) {

			if (onlySubmitted && sysFileUpload.getStatus() == 0) {
				continue;
			}

			result.add(sysFileUpload);

		}

		return result;
	}

	public List<SysFileUpload> getSysFileUploadList(String bizId) {

		return getSysFileUploadList(bizId, true);
	}

	public SysFileUpload getSysFileUploadFirst(String bizId) {
		
		List<SysFileUpload> sysFileUploads = getSysFileUploadList(bizId);

		if(sysFileUploads.size()>0)
		{
			return sysFileUploads.get(0);
		}
		
		return null;
	}

	private SysFileUploadTemplate getTemplateById(String templateId) {
		SysFileUploadTemplate result = null;

		for (SysFileUploadTemplate item : templates) {
			if (item.getId().equals(templateId)) {
				result = item;
				break;
			}
		}

		return result;
	}

}
