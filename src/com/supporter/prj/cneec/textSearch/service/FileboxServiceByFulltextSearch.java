package com.supporter.prj.cneec.textSearch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.textSearch.dao.DataFileDao;
import com.supporter.prj.cneec.textSearch.entity.DataFile;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * @version V1.0   
 *
 */
@Service
public class FileboxServiceByFulltextSearch {

	@Autowired
	private DataFileDao fileboxDAO;
	
	
	
	
	/**
	 * 封装详情页附件下载部分
	 * @param fileboxId
	 * @return
	 */
	public String getFiles(DataFile filebox){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"javaScript:javascript:downloadFileBox('"+ filebox.getId() +"');\">" + filebox.getFileName() +"</a><br/>");
		return sb.toString();
	}
	
	
	/**
	 * 获取公告列表
	 * @param FileboxStatus
	 * @return
	 */
	public <DataFile> List getFileboxList(int fileboxStatus){
		return fileboxDAO.getDataFileList();
	}
}
