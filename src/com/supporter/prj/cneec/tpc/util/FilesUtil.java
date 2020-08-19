package com.supporter.prj.cneec.tpc.util;

import java.io.File;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

public class FilesUtil {

	public static final String FILE_PATH = "file_upload" + File.separator;

	private static FileUploadService fileUploadService;

	public static FileUploadService getFileUploadService() {
		if (fileUploadService == null) {
			fileUploadService = SpringContextHolder.getBean(FileUploadService.class);
		}
		return fileUploadService;
	}

	/**
	 * 文件保存路径
	 * @param fileUpload
	 * @return
	 */
	public static String getFileStoragePath(FileUpload fileUpload) {
		String storagePath = "";
		String createDateString = "";
		String moduleNamePath = "";
		String busiTypePath = "";
		if (CommonUtil.trim(fileUpload.getModuleName()).length() > 0) {
			moduleNamePath = fileUpload.getModuleName() + File.separator;
		}
		if (CommonUtil.trim(fileUpload.getBusiType()).length() > 0) {
			busiTypePath = fileUpload.getBusiType() + File.separator;
		}
		if (fileUpload.getCreatedDate() != null) {
			createDateString = CommonUtil.format(fileUpload.getCreatedDate(), "yyyyMM");
		}
		storagePath = fileUpload.getTenantNo() + File.separator + FILE_PATH + moduleNamePath + busiTypePath + createDateString;
		return storagePath;
	}

	/**
	 * 拷贝文件至目标模块中（按附件上传时间）
	 * @param source 源文件参数 moduleId, busiType, oneLevelId, twoLevelId
	 * @param target 目标模块参数 moduleId, busiType, oneLevelId, twoLevelId
	 * @return
	 */
	public static boolean copySourceFilesToTargetModule(String[] sourceModule, String[] targetModule) {
		String sModuleId = sourceModule[0], sBusiType = sourceModule[1], sOneLevelId = sourceModule[2], sTwoLevelId = sourceModule[3];
		String tModuleId = targetModule[0], tBusiType = targetModule[1], tOneLevelId = targetModule[2], tTwoLevelId = targetModule[3];
		fileUploadService = getFileUploadService();
		List<FileUpload> filesList = fileUploadService.getList(sModuleId, sBusiType, sOneLevelId, sTwoLevelId);
		if (CollectionUtils.isNotEmpty(filesList) && filesList.size() > 0) {
			String storagePath = EIPService.getFileUploadService().getStoragePath();
			for (FileUpload upload : filesList) {
				File ifile = new File(storagePath + getFileStoragePath(upload) + File.separator + upload.getFileId());
				try {
					FileUpload fileUpload = new FileUpload();
					// copy对象
					BeanUtils.copyProperties(fileUpload, upload);
					// 重新设置目标对象字段属性
					fileUpload.setFileId(null);
					fileUpload.setModuleName(tModuleId);
					fileUpload.setBusiType(tBusiType);
					fileUpload.setOneLevelId(tOneLevelId);
					fileUpload.setTwoLevelId(tTwoLevelId);
					fileUpload.setFileSize(new Long(ifile.length()).intValue());//getFileSize异常，重新赋值
					// 保存fileUpload
					fileUploadService.update(fileUpload);
					// copy文件
					File toFile = new File(storagePath + getFileStoragePath(fileUpload) + File.separator + fileUpload.getFileId());
					FileUtils.copyFile(ifile, toFile);
				} catch (Exception e) {
					System.out.println("copySourceFilesToTargetModule error: " + e.getMessage());
				}
			}
		}
		return true;
	}

	/**
	 * 删除附件
	 * @param sourceModule 要删除的附件应用信息
	 */
	public static void deleteFiles(String[] sourceModule) {
		String sModuleId = sourceModule[0], sBusiType = sourceModule[1], sOneLevelId = sourceModule[2], sTwoLevelId = sourceModule[3];
		fileUploadService = getFileUploadService();
		List<FileUpload> filesList = fileUploadService.getList(sModuleId, sBusiType, sOneLevelId, sTwoLevelId);
		for (FileUpload fileUpload : filesList) {
			fileUploadService.deleteFile(fileUpload.getFileId());
		}
	}

	/**
	 * 获取附件名称
	 * @param moduleId
	 * @param busiType
	 * @param oneLevelId
	 * @param twoLevelId
	 * @return
	 */
	public static String getFileNames(String moduleId, String busiType, String oneLevelId, String twoLevelId) {
		// 只需要获取附件名称使用IFileUploadService即可
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> fileList = fileUploadService.getFileList(moduleId, busiType, CommonUtil.trim(oneLevelId), CommonUtil.trim(twoLevelId));
		StringBuffer sb = new StringBuffer();
		for (IFile file : fileList) {
			sb.append(file.getFileName()).append(",");
		}
		if (fileList != null && fileList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 拷贝附件（按当前执行方法时间）
	 * @param sourceModule
	 * @param targetModule
	 * @param user 必须有用户
	 */
	public static void copyFiles(String[] sourceModule, String[] targetModule, UserProfile user) {
		String sModuleId = sourceModule[0], sBusiType = sourceModule[1], sOneLevelId = sourceModule[2], sTwoLevelId = sourceModule[3];
		String tModuleId = targetModule[0], tBusiType = targetModule[1], tOneLevelId = targetModule[2], tTwoLevelId = targetModule[3];
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> fileList = fileUploadService.getFileList(sModuleId, sBusiType, sOneLevelId, sTwoLevelId);
		for (int i = 0; i < fileList.size(); i++) {
			File file = fileUploadService.getFile(fileList.get(i).getFileId());
			String fileName = fileList.get(i).getFileName();
			fileUploadService.saveFile(file, tModuleId, tBusiType, fileName, i, user, tOneLevelId, tTwoLevelId);
		}
	}
}
