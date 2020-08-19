/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.constant.OverseasBimonthlyReportConstant;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity.OverseasBimonthlyReportEntity;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.service.OverseasBimonthlyReportService;
import com.supporter.prj.linkworks.oa.report.entity.Report;
import com.supporter.prj.log.controller.AbstractController;

/**
 *<p>Title: OverseasBimonthlyreportController</p>
 *<p>Description:境外双月报审批 前端控制器</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年12月23日
 * 
 */
@Controller
@RequestMapping("oa/overseas_bimonthly_report/")
public class OverseasBimonthlyReportController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OverseasBimonthlyReportService baseService;
	
	
	/**
	 * <pre>
	 * 	Description:
	 * 		This method is used initialize edit or view page information.(controller)
	 * </pre>
	 * @param overseasBimonthlyReportEntity Entity 
	 * @return Entity
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody OverseasBimonthlyReportEntity initEditOrViewPage(String reportId){
		OverseasBimonthlyReportEntity bimonthlyReportEntity = baseService.initEditOrViewPage(this.getUserProfile(),reportId);
		return bimonthlyReportEntity;
	}
	
	/**
	 * <pre>
	 * 		Description:
	 * 			This method is save or update entity class information.(controller)
	 * </pre>
	 * @param entity Entity class information.
	 * @return Entity class.
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<OverseasBimonthlyReportEntity> saveOrUpdate(OverseasBimonthlyReportEntity entity){
		OverseasBimonthlyReportEntity backEntity = baseService.saveOrUpdate(this.getUserProfile(),entity);
		return OperResult.succeed("saveSuccess",null,backEntity);
	}
	
	/**
	 * <pre>
	 * 	Description :
	 * 		This method is used batch delete entity information.(controller)
	 * </pre>
	 * @param reportIds Primary keys.
	 * @return null
	 */
	@RequestMapping("batchDelete")
	public @ResponseBody OperResult<OverseasBimonthlyReportEntity> batchDelete(String reportIds){
		this.baseService.batchDelete(this.getUserProfile(),reportIds);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	/**
	 * <pre>
	 * 	Description :
	 * 		This method is used batch delete entity information.(controller)
	 * </pre>
	 * @param reportIds Primary keys.
	 * @return null
	 */
	@RequestMapping("singleDelete")
	public @ResponseBody OperResult<OverseasBimonthlyReportEntity> singleDelete(String reportId){
		this.baseService.singleDelete(this.getUserProfile(),reportId);
		return OperResult.succeed("删除成功", null, null);
	}
	/**
	 * <pre>
	 * 		Description:
	 * 			This method is used initialize the 'jaGrid' data.(controller)
	 * </pre>
	 * @param request
	 * @param jqGridReq
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, OverseasBimonthlyReportEntity entity) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.baseService.getGrid(user, jqGrid, entity);
		return jqGrid;
	} 
	
	/**
	 * <pre>
	 * 		Description:
	 * 			This method is used initialize the process state drop-down data.(controller)
	 * </pre>
	 * @return
	 */
	@RequestMapping(value= {"getStatusCodeTable"})
	public @ResponseBody Map<Integer,String> getStatusCodeTable(){
		return OverseasBimonthlyReportConstant.statusCode();
	}
	
	/**
	 * <pre>
	 * 		Description:
	 * 			This method is used validation number of attachments(controller)
	 * </pre>
	 * @return
	 */
	@RequestMapping(value= {"validationFile"})
	public @ResponseBody Integer validationFile(String reportId){
		return OverseasBimonthlyReportService.validationFile(reportId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
