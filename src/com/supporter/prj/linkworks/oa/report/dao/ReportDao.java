package com.supporter.prj.linkworks.oa.report.dao;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.meeting.entity.MeetingEq;
import com.supporter.prj.linkworks.oa.report.constant.AuthConstant;
import com.supporter.prj.linkworks.oa.report.constant.LogConstant;
import com.supporter.prj.linkworks.oa.report.constant.ReportConstant;
import com.supporter.prj.linkworks.oa.report.entity.Report;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class ReportDao extends MainDaoSupport < Report, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<Report> findPage(UserProfile user, JqGrid jqGrid, Report report) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+Report.class.getName()+" where 1=1 ");
		String authHql = "";
		if(report != null){
			//查询
			String reportTitle = report.getReportTitle();
			if(StringUtils.isNotBlank(reportTitle)){
				jqGrid.addHqlFilter(
						"reportTitle like ? or empName like ? ", 
						"%" + reportTitle + "%","%" + reportTitle + "%");
				hql.append(" and reportTitle like ? or empName like ? ");
				params = ArrayUtils.add(params, "%" + reportTitle + "%");
				params = ArrayUtils.add(params, "%" + reportTitle + "%");
			}
			 //状态过滤
	        if(report.getReportStatus() > -1){
	        	jqGrid.addHqlFilter("reportStatus = ?" ,report.getReportStatus());
	        	hql.append(" and reportStatus = ? ");
				params = ArrayUtils.add(params, report.getReportStatus());
	        }
	        //授权
	        authHql = EIPService.getAuthorityService().getHqlFilter(user, ReportConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME_PAGESHOW);
			
	        jqGrid.addHqlFilter(authHql);
			
			jqGrid.addSortPropertyDesc("reportId");
		}
		//日志
		EIPService.getLogService(LogConstant.INFO_TYPE_DEBUG).info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return this.retrievePage(jqGrid);
	}
	/**
	 * 批量提前文件
	 * @param reportId
	 */
	public String batchExtractFiles(UserProfile userProfile){
		List <Report> reportList= this.getReportList();
		for(Report report:reportList){
			String ls_UploadFileName = report.getFileName();
	    	String[] lst_Report = ls_UploadFileName.split(",");
	    	int i = 0;
	    	for (String ls_SubUploadFileName:lst_Report){
					StringTokenizer lst_SubReport = new StringTokenizer(ls_SubUploadFileName,".");
					String ls_BeforSubUploadFileName = "";
					String ls_SubUploadFileNameType = "";
					while (lst_SubReport.hasMoreElements()){ 
						ls_BeforSubUploadFileName = report.getReportId()+ "_" + i;
						ls_SubUploadFileNameType = (String)lst_SubReport.nextElement();
					}
					String fileName = ls_BeforSubUploadFileName+"." + ls_SubUploadFileNameType;
					String path=EIPService.getContextService().getRootDirPath()+"/oa/report/attachment/" + fileName;
					File file = new File(path);
					EIPService.getFileUploadService().saveFile(file, "OAREPORT","OAREPORT", ls_SubUploadFileName, i, userProfile, report.getReportId());
					i++;
	    	}
		}
    	return "success";
	}
	
	/**
	 * 
	 * @param reportId
	 */
	public String extractFiles(String reportId,UserProfile userProfile){
		Report report =  this.get(reportId);
		String ls_UploadFileName = report.getFileName();
    	String[] lst_Report = ls_UploadFileName.split(",");
    	int i = 0;
    	for (String ls_SubUploadFileName:lst_Report){
				StringTokenizer lst_SubReport = new StringTokenizer(ls_SubUploadFileName,".");
				String ls_BeforSubUploadFileName = "";
				String ls_SubUploadFileNameType = "";
				while (lst_SubReport.hasMoreElements()){ 
					ls_BeforSubUploadFileName = report.getReportId()+ "_" + i;
					ls_SubUploadFileNameType = (String)lst_SubReport.nextElement();
				}
				String fileName = ls_BeforSubUploadFileName+"." + ls_SubUploadFileNameType;
				String path=EIPService.getContextService().getRootDirPath()+"/oa/report/attachment/" + fileName;
				File file = new File(path);
				EIPService.getFileUploadService().saveFile(file, "OAREPORT","OAREPORT", ls_SubUploadFileName, i, userProfile, report.getReportId());
				i++;
    	}
    	return "success";
	}
	/**
	 * 获取所有的报告记录.
	 * @param user
	 * @return
	 */
	public List<Report> getReportList() {
		StringBuffer hql = new StringBuffer("from "+Report.class.getName()+" where 1=1 ");
		List<Report> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	/**
	 * 判断名字是否重复
	 * 
	 * @param contractId
	 * @param contractName
	 * @return
	 */
//	public boolean checkNameIsValid(String contractId, String contractName) {
//		String hql = null;
//		List retList = null;
//		if (StringUtils.isBlank(contractId)) {// 新建时
//			hql = "from " + Report.class.getName() + " where contractName = ?";
//			retList = this.retrieve(hql, contractName);
//		} else {// 编辑时
//			hql = "from " + Report.class.getName() + " where contractId != ? and contractName = ?";
//			retList = this.retrieve(hql, contractId, contractName);
//		}
//		if (CollectionUtils.isEmpty(retList)) {
//			return true;
//		}
//		return false;
//	}
	
}
