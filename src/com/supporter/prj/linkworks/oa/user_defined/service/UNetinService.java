package com.supporter.prj.linkworks.oa.user_defined.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.prj.linkworks.oa.report.dao.ReportDao;
import com.supporter.prj.linkworks.oa.report.entity.Report;
import com.supporter.prj.linkworks.oa.user_defined.constants.NetInConstant;
import com.supporter.prj.linkworks.oa.user_defined.dao.UNetinDao;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;

/**   
 * @Title: Service
 * @Description: 报告
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0   
 *
 */
@Service
@Transactional(TransManager.APP)
public class UNetinService {
	@Autowired
	private UNetinDao uNetinDao;
	@Autowired
	private VCneecExamService cneecExamService;
	@Autowired
	private ExtractFiles extractFiles;
	@Autowired
	private ReportDao reportDao;
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public UNetin initEditOrViewPage(String uNetinId, UserProfile user) {
		if (StringUtils.isBlank(uNetinId)) {// 新建
			UNetin uNetin = newUNetin(user);
			return uNetin;
		} else {// 编辑
			UNetin uNetin =  uNetinDao.get(uNetinId);
			return uNetin;
		}
	}
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public UNetin newUNetin(UserProfile auserprf_U){
        UNetin lUNetin_N = new UNetin();
        lUNetin_N.setId(com.supporter.util.UUIDHex.newId());
        lUNetin_N.setCreatedBy(auserprf_U.getName());
        lUNetin_N.setCreatedById(auserprf_U.getPersonId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        lUNetin_N.setCreatedDate(sdf.format(new Date()));
        lUNetin_N.setApplyDate(sdf.format(new Date()));
        Dept dept = auserprf_U.getDept();
        if(dept != null){
	        lUNetin_N.setDeptId(dept.getDeptId());
	        lUNetin_N.setDeptName(dept.getName());
        }
        lUNetin_N.setStatus(UNetin.DRAFT);
        lUNetin_N.setAdd(true);
        return lUNetin_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<UNetin> getGrid(UserProfile user, JqGrid jqGrid, UNetin entity) {
		return uNetinDao.findPage(jqGrid, entity);
	}
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public UNetin viewPage(String id, UserProfile user) {
		UNetin entity =  uNetinDao.get(id);
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(entity);
			if (oldProcId > 0)entity.setOldProcId(oldProcId);
			return entity;
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public UNetin saveOrUpdate(UserProfile user, UNetin entity, Map< String, Object > valueMap) {
		if (entity.getAdd()) {//
			this.uNetinDao.save(entity);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_ADD.getOperName(), null);
		} else {// 编辑
			entity.setModifiedBy(user.getName());
			entity.setModifiedById(user.getPersonId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			entity.setModifiedDate(sdf.format(new Date()));
			this.uNetinDao.update(entity);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);
		}

		return entity;

	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String uNetinIds) {
		if (StringUtils.isNotBlank(uNetinIds)) {
			for (String UNetinId : uNetinIds.split(",")) {
				this.uNetinDao.delete(UNetinId);
			}
//			EIPService.getLogService("MPM_MCA").info(user, Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " + moduleIds + "}", null, null);
		}
	}
	public UNetin commit(UserProfile user, String netinId) {
		UNetin u = uNetinDao.get(netinId);
		if(u!=null){
			u.setStatus(1);
		}
		uNetinDao.update(u);
		return u;
	}
	public UNetin get(String id) {
		return uNetinDao.get(id);
	}
	public void update(UNetin entity) {
		this.uNetinDao.update(entity);
		
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		UNetin entity =  this.get(id);
		return extractFiles.extractFiles(entity.getId(), entity.getRelatedFiles(),
				NetInConstant.File_PATH, NetInConstant.MODULE_ID, NetInConstant.FUJIAN_ID, userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
/*	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <UNetin> entitys= uNetinDao.getAllList();
		for(UNetin entity:entitys){
			returnStr = extractFiles.extractFiles(entity.getId(), entity.getRelatedFiles(),
					NetInConstant.File_PATH, NetInConstant.MODULE_ID, NetInConstant.FUJIAN_ID, userProfile);
			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EXTRACT_FILES_LOG_MESSAGE+":"+returnStr, CommonUtil.trim(report.getReportTitle()));
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					userProfile, LogConstant.EXTRACT_FILES_LOG_ACTION, logMessage,
					report, null);
		}
		return returnStr;
		//return reportDao.batchExtractFiles(userProfile);
	}*/
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <Report> reportList= reportDao.getReportList();
		for(Report report:reportList){
			returnStr = extractFiles.extractFiles(report.getReportId(), report.getFileName(),
					"/oa/report/attachment/", "OAREPORT", "OAREPORT", userProfile);
			
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
