package com.supporter.prj.linkworks.oa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.util.file_upload.dao.FupUploadDao;
import com.supporter.prj.linkworks.oa.util.file_upload.entity.FupUpload;
import com.supporter.util.CommonUtil;

@Repository
public class ExtractFiles {
	@Autowired
	public FupUploadDao fileDao;

	/**
	 * 
	 * @param entityId
	 * @param entityFileName
	 * @param url
	 * @param moduleId
	 * @param busiType
	 * @param userProfile
	 * @return
	 */
	public String extractFiles(String entityId, String entityFileName,
			String url, String moduleId, String busiType,
			UserProfile userProfile) {
		String ls_UploadFileName = CommonUtil.trim(entityFileName);
		String returnStr = "";
		String returnStr2 = "";
		if (!"".equals(ls_UploadFileName)) {
			// System.out.println("ls_UploadFileName==="+ls_UploadFileName);
			String[] lst_Report = ls_UploadFileName.split(",");
			int i = 0;
			for (String ls_SubUploadFileName : lst_Report) {
				if (!"".equals(ls_SubUploadFileName)) {
					StringTokenizer lst_SubReport = new StringTokenizer(
							ls_SubUploadFileName, ".");
					String ls_BeforSubUploadFileName = "";
					String ls_SubUploadFileNameType = "";
					while (lst_SubReport.hasMoreElements()) {
						ls_BeforSubUploadFileName = entityId + "_" + i;
						ls_SubUploadFileNameType = (String) lst_SubReport
								.nextElement();
					}
					String fileName = ls_BeforSubUploadFileName + "."
							+ ls_SubUploadFileNameType;
					String path = EIPService.getContextService()
							.getRootDirPath()
							+ url + fileName;
					File file = new File(path);
					try {
						FileInputStream in = new FileInputStream(file);
					} catch (FileNotFoundException e) {
						// System.out.println("e="+e.getMessage());
						// e.printStackTrace();
						returnStr += fileName + ":" + e.getMessage();
					}
					if ("".equals(returnStr)) {
						String a = EIPService.getFileUploadService().saveFile(
								file, moduleId, busiType, ls_SubUploadFileName,
								i, userProfile, entityId);
						returnStr2 += fileName + ":同步成功；";
					}
					i++;
				}
			}
		} else {
			returnStr2 = "无附件。";
		}
		if (StringUtils.isBlank(returnStr2)) {
			returnStr2 = returnStr;
		}
		return returnStr2;
	}
	
	/**
	 * 代标记的提取文件（如签报的扫描件）
	 * @param entityId
	 * @param entityFileName
	 * @param url
	 * @param moduleId
	 * @param busiType
	 * @param sign 标记（如签报扫描件的id + 标记 + _ + 索引：1000001136A_0.pdf）
	 * @param userProfile
	 * @return
	 */
	public String extractFilesForSign(String entityId, String entityFileName, String url, String moduleId, String busiType, String sign, UserProfile userProfile) {
		String ls_UploadFileName = CommonUtil.trim(entityFileName);
		String returnStr = "";
		String returnStr2 = "";
		if (!"".equals(ls_UploadFileName)) {
			// System.out.println("ls_UploadFileName==="+ls_UploadFileName);
			String[] lst_Report = ls_UploadFileName.split(",");
			int i = 0;
			for (String ls_SubUploadFileName : lst_Report) {
				if (!"".equals(ls_SubUploadFileName)) {
					StringTokenizer lst_SubReport = new StringTokenizer(
							ls_SubUploadFileName, ".");
					String ls_BeforSubUploadFileName = "";
					String ls_SubUploadFileNameType = "";
					while (lst_SubReport.hasMoreElements()) {
						ls_BeforSubUploadFileName = entityId + sign + "_" + i;
						ls_SubUploadFileNameType = (String) lst_SubReport
								.nextElement();
					}
					String fileName = ls_BeforSubUploadFileName + "."
							+ ls_SubUploadFileNameType;
					String path = EIPService.getContextService()
							.getRootDirPath()
							+ url + fileName;
					File file = new File(path);
					try {
						FileInputStream in = new FileInputStream(file);
					} catch (FileNotFoundException e) {
						// System.out.println("e="+e.getMessage());
						// e.printStackTrace();
						returnStr += fileName + ":" + e.getMessage();
					}
					if ("".equals(returnStr)) {
						String a = EIPService.getFileUploadService().saveFile(
								file, moduleId, busiType, ls_SubUploadFileName,
								i, userProfile, entityId);
						returnStr2 += fileName + ":同步成功；";
					}
					i++;
				}
			}
		} else {
			returnStr2 = "无附件。";
		}
		if (StringUtils.isBlank(returnStr2)) {
			returnStr2 = returnStr;
		}
		return returnStr2;
	}

	public String extractUtilFiles(String entityId, String entityFileName,
			String url, String moduleId, String busiType,
			UserProfile userProfile) {
		String ls_UploadFileName = CommonUtil.trim(entityFileName);
		String returnStr = "";
		String returnStr2 = "";
		if (!"".equals(ls_UploadFileName)) {
			// System.out.println("ls_UploadFileName==="+ls_UploadFileName);
			List<FupUpload> files = fileDao
					.getAllList(entityFileName, entityId);
			int i = 0;
			if (files != null) {
				for (FupUpload fupUpload : files) {
					String ls_SubUploadFileName = CommonUtil.trim(fupUpload.getRealName());
					String fileName = CommonUtil.trim(fupUpload.getFileName());
					if (!"".equals(ls_SubUploadFileName)) {
						String path = EIPService.getContextService()
								.getRootDirPath()
								+ url + fileName;
						// System.out.println("path==="+path);
						File file = new File(path);
						try {
							FileInputStream in = new FileInputStream(file);
						} catch (FileNotFoundException e) {
							// System.out.println("e="+e.getMessage());
							// e.printStackTrace();
							returnStr += fileName + ":" + e.getMessage();
						}
						if ("".equals(returnStr)) {
							String a = EIPService.getFileUploadService()
									.saveFile(file, moduleId, busiType,
											ls_SubUploadFileName, i,
											userProfile, entityId);
							returnStr2 += fileName + ":同步成功；";
						}
						i++;
					}
				}

			} else {
				returnStr2 = "无附件。";
			}
		}
		if (StringUtils.isBlank(returnStr2)) {
			returnStr2 = returnStr;
		}
		return returnStr2;
	}
	
	public String extractDianZiQianMingFiles(String entityId, String entityFileName,
			String url, String moduleId, String busiType,
			UserProfile userProfile) {
		String ls_UploadFileName = CommonUtil.trim(entityFileName);
		String returnStr = "";
		String returnStr2 = "";
		String a="";
		if (!"".equals(ls_UploadFileName)) {
			
			String[] lst_Report = ls_UploadFileName.split(",");
			int i = 0;
			for (String ls_SubUploadFileName : lst_Report) {
				if (!"".equals(ls_SubUploadFileName)) {
					StringTokenizer lst_SubReport = new StringTokenizer(
							ls_SubUploadFileName, ".");
					String ls_BeforSubUploadFileName = "";
					String ls_SubUploadFileNameType = "";
					while (lst_SubReport.hasMoreElements()) {
						ls_BeforSubUploadFileName = entityId;
						ls_SubUploadFileNameType = (String) lst_SubReport
								.nextElement();
					}
					String fileName = ls_BeforSubUploadFileName + "."
							+ ls_SubUploadFileNameType;
					String path = EIPService.getContextService()
							.getRootDirPath()
							+ url + fileName;
					File file = new File(path);
					try {
						FileInputStream in = new FileInputStream(file);
					} catch (FileNotFoundException e) {
						 
						// e.printStackTrace();
						returnStr += fileName + ":" + e.getMessage();
					}
					if ("".equals(returnStr)) {
						a = EIPService.getFileUploadService().saveFile(
								file, moduleId, busiType, ls_SubUploadFileName,
								i, userProfile, entityId);
						returnStr2 += fileName + ":同步成功；";
					}
					i++;
				}
			}
		} else {
			returnStr2 = "无附件。";
		}
		if (StringUtils.isBlank(returnStr2)) {
			returnStr2 = returnStr;
		}
		return a;
	}
}
