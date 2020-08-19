package com.supporter.prj.linkworks.oa.maintenance.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.prj.linkworks.oa.maintenance.dao.MaintenanceContentDao;
import com.supporter.prj.linkworks.oa.maintenance.dao.MaintenanceDao;
import com.supporter.prj.linkworks.oa.maintenance.entity.Maintenance;
import com.supporter.prj.linkworks.oa.maintenance.entity.MaintenanceContent;
import com.supporter.prj.linkworks.oa.maintenance.util.AuthConstant;
import com.supporter.prj.linkworks.oa.maintenance.util.AuthUtil;
import com.supporter.prj.linkworks.oa.maintenance.util.LogConstant;
import com.supporter.util.CommonUtil;


@Service
public class MaintenanceService {
	@Autowired
	private MaintenanceDao maintenanceDao;
	@Autowired
	private MaintenanceContentDao maintenanceContentDao;
	@Autowired
	private VCneecExamService cneecExamService;
	
	@Autowired
	private ExtractFiles extractFiles;


	public Maintenance get(String moduleId) {
		return maintenanceDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public Maintenance initEditOrViewPage(JqGrid jqGrid,String maintenanceId, UserProfile user) {
		if (StringUtils.isBlank(maintenanceId)) {// 新建
			Maintenance maintenance = newMaintenance(user);
			maintenance.setAdd(true);
			return maintenance;
		} else {// 编辑
			//获得主表
			Maintenance maintenance =  maintenanceDao.get(maintenanceId);
			
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(maintenance);
			if (oldProcId > 0)maintenance.setOldProcId(oldProcId);
			
			
			//获得从表MaintenanceContent
			MaintenanceContent maintenanceContent=maintenanceContentDao.get(maintenanceId);
			if(maintenanceContent!=null){
				//问题描述
				maintenance.setProblemDescriptionDesc(maintenanceContent.getProblemDescription());	
			}			
			//查看时显示部门名称
	    	if(user.getDept()==null){
	    		maintenance.setDeptName("无所属部门");
		   	}else{
		   		maintenance.setDeptName(user.getDept().getName());
		   	}
		    
			//添加详情页的附件下载
			maintenance.setFiles(getFiles(maintenance));
			
			maintenance.setAdd(false);		
			return maintenance;
		}
	}
	
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public Maintenance viewPage(String maintenanceId, UserProfile user) {
			Maintenance maintenance =  maintenanceDao.get(maintenanceId);
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(maintenance);
			if (oldProcId > 0)maintenance.setOldProcId(oldProcId);
//			maintenance.setMaintenanceContent(maintenanceContentDao.get(maintenanceId));
			maintenance.setFiles(getFiles(maintenance));
			return maintenance;
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public Maintenance newMaintenance(UserProfile auserprf_U){
    	Maintenance lmaintenance_N = new Maintenance();
    	lmaintenance_N.setMaintenanceId(com.supporter.util.UUIDHex.newId());
    	lmaintenance_N.setCreatedById(auserprf_U.getPersonId());
    	lmaintenance_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lmaintenance_N.setCreatedDate(date);
    	lmaintenance_N.setDeptId(auserprf_U.getDeptId());
    	if(auserprf_U.getDept()==null){
    		lmaintenance_N.setDeptName("无所属部门");
	   	}else{
	   		lmaintenance_N.setDeptName(auserprf_U.getDept().getName());
	   	}
    	//单号
    	lmaintenance_N.setMaintenanceNo(getCurrentNo());
    	lmaintenance_N.setSwfStatus(Long.valueOf(Maintenance.DRAFT+""));
        return lmaintenance_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<Maintenance> getGrid(UserProfile user, JqGrid jqGrid, Maintenance maintenance) {
		List<Maintenance> list1=this.maintenanceDao.findPage(user,jqGrid, maintenance);//根据输入的查询条件查询列表
		return list1;	
		
	}
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public Maintenance saveOrUpdate(UserProfile user, Maintenance maintenance, Map< String, Object > valueMap) {
		Maintenance ret = null;
		if (maintenance.getAdd()) {// 新建

			//保存主表
			this.maintenanceDao.save(maintenance);
			//保存从表MaintenanceContent
			MaintenanceContent maintenanceContent=new MaintenanceContent();
			maintenanceContent.setMaintenanceId(maintenance.getMaintenanceId());
			maintenanceContent.setProblemDescription(maintenance.getProblemDescriptionDesc());
			this.maintenanceContentDao.save(maintenanceContent);			
			ret = maintenance;
			// 记录日志
			/*MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
					MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);*/
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_MAINTENANCE_LOG_MESSAGE, maintenance.getTitle());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.ADD_MAINTENANCE_LOG_ACTION, logMessage,
					maintenance, null);
		} else {// 编辑
			
			//权限验证(判断是不是有编辑信息系统维护申请的权限)
			AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, maintenance.getMaintenanceId(), maintenance);									
			maintenance.setModifiedBy(user.getName());
			maintenance.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			maintenance.setModifiedDate(date);
			//编辑之后保存主表
			this.maintenanceDao.update(maintenance);
			//保存从表MaintenanceContent
			MaintenanceContent maintenanceContent=this.maintenanceContentDao.get(maintenance.getMaintenanceId());						
			maintenanceContent.setProblemDescription(maintenance.getProblemDescriptionDesc());
			this.maintenanceContentDao.update(maintenanceContent);
			ret = maintenance;			
			// 记录日志
/*			MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
					MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);*/	
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_MAINTENANCE_LOG_MESSAGE, maintenance.getTitle());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.EDIT_MAINTENANCE_LOG_ACTION, logMessage,
					maintenance, null);

		}
		return ret;

	}

	
	 public  String getCurrentNo(){
		 		String no=	maintenanceDao.getMaintenanceNo();
			    SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
				String year=sdf.format(new Date());
	    		if(year.equals(no.substring(0,4))){
	    			//2017-002
	    			String nm = no.substring(5);
	    			String number = String.valueOf((Integer.parseInt(nm)+1));
	    			if(number.length()<nm.length()){
	    				no = "";
	    				for (int i = 0; i < nm.length()-number.length(); i++) {
							no += "0";
						}
	    				no += number;
	    			}else{
	    				no = number;
	    			}
	    			no = year+"-"+no;
	    		}else{
	    			no = year+"-001";
	    		}
	    	return no;
	    }
	
	
	
    /**
	 * 保存提交
	 * 
	 * @param user 用户信息
	 * @param apply 实体类
	 * @return
	 */
	public Maintenance commit(UserProfile user, Maintenance maintenance, Map< String, Object > valueMap) {
		Maintenance ret = null;
		boolean isNew=maintenance.getAdd();
		if(isNew){

			//状态（审批中）
			maintenance.setSwfStatus(Long.valueOf(Maintenance.PROCESSING+""));
			//保存主表
			this.maintenanceDao.save(maintenance);
			//保存从表MaintenanceContent
			MaintenanceContent maintenanceContent=new MaintenanceContent();
			maintenanceContent.setMaintenanceId(maintenance.getMaintenanceId());
			this.maintenanceContentDao.save(maintenanceContent);			
			//保存从表MaintenancePerson			
			ret = maintenance;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{AddApplication : " + apply + "}", null, null);
		} else {// 编辑
			maintenance.setModifiedBy(user.getName());
			maintenance.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			maintenance.setModifiedDate(date);
			//状态（审批中）
			maintenance.setSwfStatus(Long.valueOf(Maintenance.PROCESSING+""));
			//编辑之后保存主表
			this.maintenanceDao.update(maintenance);
			//保存从表MaintenanceContent
			MaintenanceContent maintenanceContent=this.maintenanceContentDao.get(maintenance.getMaintenanceId());						
			this.maintenanceContentDao.update(maintenanceContent);
			ret = maintenance;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + apply + "}", null, null);
		}
		return ret;
	}
	

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String maintenanceIds) {
		if (StringUtils.isNotBlank(maintenanceIds)) {
			for (String maintenanceId : maintenanceIds.split(",")) {
				Maintenance maintenance=maintenanceDao.get(maintenanceId);
				if(maintenance==null){
					continue;
				}
				//权限验证(判断是不是有编辑信息系统维护申请的权限)
				AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, maintenance.getMaintenanceId(), maintenance);	
				//删除主表
				this.maintenanceDao.delete(maintenance);
				
				//删除content
				MaintenanceContent maintenanceContent=maintenanceContentDao.get(maintenanceId);
				if(maintenanceContent!=null){
				    this.maintenanceContentDao.delete(maintenanceContent);
				}
				

			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_MAINTENANCE_LOG_MESSAGE, "删除的主键id为："+maintenanceIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.DELETE_MAINTENANCE_LOG_ACTION, logMessage,
					null, null);

		}
	}
	
	
	/**
	 * 附件下载部分
	 * @param maintenance
	 * @return
	 */
	public String getFiles(Maintenance maintenance){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAMAINTENAN", "OAMAINTENAN2", maintenance.getMaintenanceId());
		for(IFile file:list){
			sb.append("<a onclick=\"downloads('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 修改（流程处理类调用的方法，用于修改状态和保存流程id）
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public void update(Maintenance maintenance) {	
		this.maintenanceDao.update(maintenance);
	}
	
	


	/**
	 * 单一提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		Maintenance entity =  this.get(id);
		return extractFiles.extractFiles(entity.getMaintenanceId(), entity.getFileName(),
				"/oa/maintenance/docs/", "OAMAINTENAN", "OAMAINTENAN2", userProfile);
	}
	
	/**
	 * 批量提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <Maintenance> list= maintenanceDao.getMaintenanceList();
		for(Maintenance entity:list){
			returnStr = extractFiles.extractFiles(entity.getMaintenanceId(), entity.getFileName(),
					"/oa/maintenance/docs/", "OAMAINTENAN", "OAMAINTENAN2", userProfile);
			
			// 记录日志
/*			String logMessage = MessageFormat.format(
					LogConstant.EXTRACT_FILES_LOG_MESSAGE+":"+returnStr, CommonUtil.trim(report.getReportTitle()));
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					userProfile, LogConstant.EXTRACT_FILES_LOG_ACTION, logMessage,
					report, null);*/
		}
		return returnStr;
		//return reportDao.batchExtractFiles(userProfile);
	}
	
	
	
}
