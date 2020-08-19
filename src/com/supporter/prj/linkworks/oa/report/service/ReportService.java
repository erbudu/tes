package com.supporter.prj.linkworks.oa.report.service;

import java.text.MessageFormat;
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
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.prj.linkworks.oa.report.constant.AuthConstant;
import com.supporter.prj.linkworks.oa.report.constant.LogConstant;
import com.supporter.prj.linkworks.oa.report.dao.ReportContentDao;
import com.supporter.prj.linkworks.oa.report.dao.ReportDao;
import com.supporter.prj.linkworks.oa.report.entity.Report;
import com.supporter.prj.linkworks.oa.report.entity.ReportContent;
import com.supporter.prj.linkworks.oa.report.util.ReportAuthUtil;
import com.supporter.util.CommonUtil;

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
public class ReportService {
	@Autowired
	private VCneecExamService cneecExamService;
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private ExtractFiles extractFiles;
	@Autowired
	private ReportContentDao reportContentDao;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public Report get(String reportId) {
		return reportDao.get(reportId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public Report initEditOrViewPage(String reportId, UserProfile user) {
		if (StringUtils.isBlank(reportId)) {// 新建
			Report report = newReport(user);
			return report;
		} else {// 编辑
			Report report =  reportDao.get(reportId);
			
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(report);
			if (oldProcId > 0)report.setOldProcId(oldProcId);
			
			report.setReportContent(reportContentDao.get(reportId));
			return report;
		}
	}
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public Report viewPage(String reportId, UserProfile user) {
			Report report =  reportDao.get(reportId);
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(report);
			if (oldProcId > 0)report.setOldProcId(oldProcId);
			report.setReportContent(reportContentDao.get(reportId));
			report.setFiles(getFiles(report));
			return report;
	}
	
	/**
	 * 封装详情页附件下载部分
	 * @param bulletinId
	 * @return
	 */
	public String getFiles(Report report){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAREPORT", "OAREPORT", report.getReportId());
		for(IFile file:list){
			sb.append("<a href=\"javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	/**
	 * 根据业务对象获取流程实例ID.
	 * @param report
	 * @return
	 */
	public String getProcId(Report report){
		if (report == null)return "";
		return EIPService.getWfService().getProcIdByRecord(report);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String reportId, UserProfile userProfile){
		Report report =  this.get(reportId);
		return extractFiles.extractFiles(report.getReportId(), report.getFileName(),
				"/oa/report/attachment/", "OAREPORT", "OAREPORT", userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
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
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public Report newReport(UserProfile auserprf_U){
        Report lreport_N = new Report();
        lreport_N.setEmpId(auserprf_U.getPersonId());
        lreport_N.setEmpName(auserprf_U.getName());
        
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );  
        String datestr = sdf.format( new  Date());  
        
        lreport_N.setCreatedBy(auserprf_U.getPersonId());
        lreport_N.setCreatorName(auserprf_U.getName());
        lreport_N.setCreatedDate(datestr);
        
        lreport_N.setModifiedBy(auserprf_U.getPersonId());
        //lreport_N.setModifiedName(auserprf_U.getName());
        lreport_N.setModifiedDate(datestr);
        
        Dept dept = auserprf_U.getDept();
        if(dept != null){
	        lreport_N.setDeptId(dept.getDeptId());
	        lreport_N.setDeptName(dept.getName());
        }
        lreport_N.setReportStatus(Report.DRAFT);
        lreport_N.setReportId(com.supporter.util.UUIDHex.newId());
        return lreport_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<Report> getGrid(UserProfile user, JqGrid jqGrid, Report report) {
		return reportDao.findPage(user, jqGrid, report);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<Report> saveOrUpdate(UserProfile user, Report report, Map< String, Object > valueMap) {
		Report ret = reportDao.get(report.getReportId());
		if(report.getSequentialExam() == null) report.setSequentialExam("0");
		if (ret == null) {// 新建
			this.reportDao.save(report);
			ReportContent con = report.getReportContent();
			if(con != null){
				report.getReportContent().setReportId(report.getReportId());
				this.reportContentDao.save(con);
			}
		} else {// 编辑
			this.reportDao.clear();
			this.reportDao.update(report);
			ReportContent con = report.getReportContent();
			if(con != null){
				this.reportContentDao.update(con);
			}
		}
		// 记录日志
		String logMessage = MessageFormat.format(
				LogConstant.PUBLISH_REPORT_LOG_MESSAGE, report.getReportTitle());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
				user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
				report, null);
		return OperResult.succeed("saveSuccess", null,
				report);

	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public Report update(Report report) {
			this.reportDao.update(report);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return report;

	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param moduleId
	 * @param moduleName
	 * @return
	 */
//	public boolean checkNameIsValid(String moduleId, String moduleName) {
//		return this.reportDao.checkNameIsValid(moduleId, moduleName);
//	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String reportIds) {
		if (StringUtils.isNotBlank(reportIds)) {
			for (String reportId : reportIds.split(",")) {
				Report report = get(reportId);
				ReportAuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, reportId, report);
				this.reportContentDao.delete(reportId);
				this.reportDao.delete(report);
			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, reportIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
					null, null);
		}
	}

}
