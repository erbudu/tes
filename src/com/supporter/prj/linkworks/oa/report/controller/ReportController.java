package com.supporter.prj.linkworks.oa.report.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.linkworks.oa.doc_in.util.AuthUtil;
import com.supporter.prj.linkworks.oa.report.constant.AuthConstant;
import com.supporter.prj.linkworks.oa.report.constant.ReportConstant;
import com.supporter.prj.linkworks.oa.report.entity.Report;
import com.supporter.prj.linkworks.oa.report.service.ReportService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.exception.UnauthorizedAccessException;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.module.entity.IOper;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 报告.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/report")
public class ReportController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ReportService reportService;

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Report report) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.reportService.getGrid(user, jqGrid, report);
		return jqGrid;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public Report initEditOrViewPage(String reportId) {
		Report entity = reportService.initEditOrViewPage(reportId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public Report viewPage(String reportId) {
		Report entity = reportService.viewPage(reportId,this.getUserProfile());
		return entity;
	}
	
	
	/**
	 * 根据业务对象获取流程实例ID.
	 * @param report
	 * @return
	 */
	@RequestMapping("/getProcId")
	@ResponseBody
	public String getProcId(Report report){
		return reportService.getProcId(report);
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String reportId){
		return reportService.extractFiles(reportId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return reportService.batchExtractFiles(this.getUserProfile());
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param report 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
//	@AuthCheck(module = ReportConstant.MODULE_ID, 
//     oper = AuthConstant.AUTH_OPER_NAME_SETVALREPORT, 
//     failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody OperResult<Report> saveOrUpdate(Report report) {
		UserProfile user = this.getUserProfile();
		//权限验证
		canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALREPORT, report.getReportId(), report);
		Map< String, Object > valueMap = this.getPropValues(Report.class);
		return this.reportService.saveOrUpdate(user, report, valueMap);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String reportIds) {
		UserProfile user = this.getUserProfile();
		
		this.reportService.delete(user, reportIds);
		return OperResult.succeed("deleteSuccess");
	}
	private void canExecute(UserProfile user, String operInnerName, String entityId, Object entityObj) {
	    IModule module = EIPService.getModuleService().getModule(ReportConstant.MODULE_ID);
	    IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
	    boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
	    if (!canAccess) {
	      throw new UnauthorizedAccessException("AUTH-002");
	    }
	  }
	/**
	 * 获取字典数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getReportStatusCodetable")
	public Map<Integer, String> getRelatedPartiesTypeTable() throws IOException {
		Map<Integer, String> map = Report.getStatusCodeTable();
		return map;
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param reportId
	 * @param reportName
	 * @return
	 */
//	@RequestMapping("checkNameIsValid")
//	public @ResponseBody Boolean checkNameIsValid(String reportId, String reportName) {
//		return this.reportService.checkNameIsValid(reportId, reportName);
//	}

	
}
