package com.supporter.prj.linkworks.oa.roll_information.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.news_alerts.entity.NewsAlerts;
import com.supporter.prj.linkworks.oa.roll_information.dao.RollInformationContentDao;
import com.supporter.prj.linkworks.oa.roll_information.dao.RollInformationDao;
import com.supporter.prj.linkworks.oa.roll_information.entity.RollInformation;
import com.supporter.prj.linkworks.oa.roll_information.entity.RollInformationContent;
import com.supporter.prj.linkworks.oa.roll_information.util.CommonUtils;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class RollInformationService {

	@Autowired
	private RollInformationDao rollInformationDao;
	@Autowired
	private RollInformationContentDao rollInformationContentDao;
	@Autowired
	private ExtractFiles extractFiles;
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<RollInformation> getGrid(UserProfile user, JqGrid jqGrid, String attr) {
		return rollInformationDao.findPage(jqGrid, attr);
	}

	/**
	 * 进入新建或编辑需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public RollInformation initEditOrViewPage(String rollInforId, UserProfile user) {
		if (StringUtils.isBlank(rollInforId)) {// 新建
			RollInformation rollInformation = newRollInformation(user);
			return rollInformation;
		} else {// 编辑
			RollInformation rollInformation =  rollInformationDao.get(rollInforId);
			rollInformation.setRollInformationContent(rollInformationContentDao.get(rollInforId));
			if(rollInformation.getPublishStatus() == RollInformation.PUBLISHED){
				rollInformation.setStatus("已发布");
			}else{
				rollInformation.setStatus("草稿");
			}
			
			//添加部门
			IEmployee emp = EIPService.getEmpService().getEmployee(rollInformation.getCreatedById());
	        if(emp != null && emp.getDept()!= null){
	        	rollInformation.setDeptName(emp.getDept().getName());
	        }
	        
			return rollInformation;
		}
	}

	
	/**
	 * 新建滚动信息
	 * @param user
	 * @return
	 */
	private RollInformation newRollInformation(UserProfile user) {
		RollInformation rollInformation  = new RollInformation();
		rollInformation.setRollInforId(com.supporter.util.UUIDHex.newId());
		rollInformation.setCreatedBy(user.getName());
		rollInformation.setCreatedById(user.getPersonId());
		rollInformation.setCreatedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		rollInformation.setModifiedBy(user.getName());
		rollInformation.setModifiedById(user.getPersonId());
		rollInformation.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		rollInformation.setCreateType(RollInformation.HANDLER);
		rollInformation.setPublishStatus(RollInformation.DRAFT);
		rollInformation.setReadCount(0);
		rollInformation.setStatus("草稿");
		
		IEmployee emp = EIPService.getEmpService().getEmployee(user.getPersonId());
        if(emp.getDept() !=null){
        	rollInformation.setDeptName(emp.getDept().getName());
        }
		return rollInformation;
	}

	/**
	 * 保存或更新数据
	 * @param user
	 * @param rollInformation
	 * @param valueMap
	 * @return
	 */
	public RollInformation saveOrUpdate(UserProfile user,RollInformation rollInformation, Map<String, Object> valueMap) {
		RollInformation information = rollInformationDao.get(rollInformation.getRollInforId());
		if(information == null){//新建
			rollInformation.setModifiedById(user.getPersonId());
			rollInformation.setModifiedBy(user.getName());
			rollInformation.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			
			RollInformationContent content = rollInformation.getRollInformationContent();
			content.setRollInforId(rollInformation.getRollInforId());
			content.setReadPersons("0");
			rollInformationDao.save(rollInformation);
			rollInformationContentDao.save(content);
		}else{//修改
			rollInformation.setModifiedById(user.getPersonId());
			rollInformation.setModifiedBy(user.getName());
			rollInformation.setModifiedDate(CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			rollInformation.setPublishStartDate(CommonUtils.dateStringSub(rollInformation.getPublishStartDate()));
			rollInformation.setPublishEndDate(CommonUtils.dateStringSub(rollInformation.getPublishEndDate()));
			RollInformationContent content = rollInformationContentDao.get(rollInformation.getRollInforId());
			content.setInformationContent(rollInformation.getRollInformationContent().getInformationContent());
			rollInformationDao.update(rollInformation);
			rollInformationContentDao.update(content);
		}
		return rollInformation;
	}

	/**
	 * 删除操作
	 * @param user
	 * @param rollInforIds
	 */
	public void delete(UserProfile user, String rollInforIds) {
		
		if (StringUtils.isNotBlank(rollInforIds)) {
			for (String rollInforId : rollInforIds.split(",")) {
				this.rollInformationContentDao.delete(rollInforId);
				this.rollInformationDao.delete(rollInforId);
			}
		}
	}

	/**
	 * 取消发布
	 * @param rollInforId
	 * @return
	 */
	public RollInformation cancelSubmit(String rollInforId) {
		RollInformation information = rollInformationDao.get(rollInforId);
		information.setPublishStatus(RollInformation.DRAFT);
		rollInformationDao.update(information);
		return information;
	}
	
	
	/**
	 * 获取可下载的文件
	 * @param newsAlertsId
	 * @return
	 */
	public String getFiles(String rollInforId){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAROLLINF", "RollFiles", rollInforId);
		for(IFile file:list){
			//可下载的附件
			sb.append("<a href=\"javaScript:javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	/**
	 * 获取图片id
	 * @param newsAlertsId
	 * @return
	 */
	public List<String> getImages(String rollInforId){
		List<String> list = new ArrayList<String>();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> files  = fileUploadService.getFileList("OAROLLINF", "RollFiles", rollInforId);
		for(IFile file:files){
			//图片附件
			if(file.getFileExt().equalsIgnoreCase("jpg") || file.getFileExt().equalsIgnoreCase("gif")){
				String fileId = file.getFileId();
				list.add(fileId);
			}
		}
		return list;
	}
	
	
	/**
	 * 预览时获取对象，增加阅读次数
	 * @param rollInforId
	 * @return
	 */
	public synchronized RollInformation getInView(String rollInforId,UserProfile user){
		RollInformation information = rollInformationDao.get(rollInforId);
		RollInformationContent content = rollInformationContentDao.get(rollInforId);
		
		String infoTitle = CommonUtil.trim(information.getInforTitle());
		if(infoTitle.indexOf("食堂")!=-1 && infoTitle.indexOf("菜谱")!=-1){
			//阅读次数
			information.setReadCount(information.getReadCount() + 1);
			//信息内容中统计多少人看了
			String persons = CommonUtil.trim(content.getReadPersons());
			if(persons.length()>0){persons += ",";}
			persons += CommonUtil.trim(user.getPersonId());
			content.setReadPersons(persons);
			//更新阅读次数和人数
			rollInformationDao.update(information);
			rollInformationContentDao.update(content);
			
		}
    	
    	//获取部门
    	IEmployee emp = EIPService.getEmpService().getEmployee(information.getCreatedById());
        if(emp != null && emp.getDept()!= null){
        	information.setDeptName(emp.getDept().getName());
        }
        information.setFilesInView(getFiles(rollInforId));
        information.setImages(getImages(rollInforId));
    	information.setRollInformationContent(content);
		return information;
	}
	
	/**
	 * 获取首页上的滚动信息
	 * @return
	 */
	public List<RollInformation> getSrollInformation(JqGrid jqGrid){
		List<RollInformation> list = rollInformationDao.getSrollInformation(jqGrid);
		return list;
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		RollInformation report =  this.get(id);
		return extractFiles.extractUtilFiles(report.getRollInforId(), "oa_roll_information",
				"/oa/news_alerts/files/", "OAROLLINF", "RollFiles", userProfile);
	}
	



	private RollInformation get(String id) {
		// TODO Auto-generated method stub
		return rollInformationDao.get(id);
	}

	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <RollInformation> reportList= rollInformationDao.getRollInformationList();
		for(RollInformation report:reportList){
			returnStr = extractFiles.extractUtilFiles(report.getRollInforId(), "oa_roll_information",
					"/oa/news_alerts/files/", "OAROLLINF", "RollFiles", userProfile);
			
			/*// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EXTRACT_FILES_LOG_MESSAGE+":"+returnStr, CommonUtil.trim(report.getReportTitle()));
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					userProfile, LogConstant.EXTRACT_FILES_LOG_ACTION, logMessage,
					report, null);*/
		}
		return returnStr;
		//return reportDao.batchExtractFiles(userProfile);
	}
}
