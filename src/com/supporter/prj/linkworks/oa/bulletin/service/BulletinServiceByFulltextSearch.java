package com.supporter.prj.linkworks.oa.bulletin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.entity.DeptResource;
import com.supporter.prj.dept_resource.service.DeptResourceService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.bulletin.dao.AcccntAccessCountDao;
import com.supporter.prj.linkworks.oa.bulletin.dao.BulletinContentDao;
import com.supporter.prj.linkworks.oa.bulletin.dao.BulletinDao;
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;
import com.supporter.prj.linkworks.oa.bulletin.entity.BulletinContent;
import com.supporter.prj.linkworks.oa.consignment.entity.Consignment;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * @version V1.0   
 *
 */
@Service
public class BulletinServiceByFulltextSearch {

	@Autowired
	private BulletinDao bulletinDAO;
	
	@Autowired
	private BulletinContentDao bulletinContentDao;
	
	
	
	/**
	 * 封装详情页附件下载部分
	 * @param bulletinId
	 * @return
	 */
	public String getFiles(Bulletin bulletin){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OABULLETIN", "filesName", bulletin.getBulletinId());
		for(IFile file:list){
			sb.append("<a href=\"javaScript:javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	
	
	
	private Bulletin getBulletin(String bulletinId) {
		// TODO Auto-generated method stub
		return bulletinDAO.get(bulletinId);
	}
	public BulletinContent getBulletinContent(Bulletin bulletin) {
		// TODO Auto-generated method stub
		return bulletinContentDao.get(bulletin.getBulletinId());
	}
	/**
	 * 获取公告列表
	 * @param BulletinStatus
	 * @return
	 */
	public <Bulletin> List getBulletinList(int bulletinStatus){
		return bulletinDAO.getBulletinList(bulletinStatus);
	}
}
