/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.reportBack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.attendance.reportBack.entity.ReportBackEntity;
import com.supporter.prj.linkworks.oa.attendance.reportBack.service.ReportBackService;
import com.supporter.spring_mvc.AbstractController;

/**
 * @title 销假单 控制层
 * @author YUEYUN
 * @date 2019年7月21日
 * 
 */
@Controller
@RequestMapping("attendance/report_back")
public class ReportBackController extends AbstractController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ReportBackService reportBackService;
	
	/**
	 * 根据请假单主键获取销假单的流程id和流程状态 用于判断产看销假单打开页面
	 * @param leaveId
	 * @return
	 */
	@RequestMapping("getProcAndStatus")
	@ResponseBody
	public Map<String,String> getProcAndStatus(String leaveId){
		Map<String, String> map = reportBackService.getProcAndStatus(leaveId);
		return map;
		
	}
	
	/**
	 * 销假单数据是在请假审批完成后保存的 这里只做更新操作
	 * @param reportBackEntity
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public OperResult<ReportBackEntity> saveOrUpdate(ReportBackEntity reportBackEntity){
		UserProfile user = getUserProfile();
		
		reportBackEntity = reportBackService.saveOrUpdate(user,reportBackEntity);
		if (reportBackEntity != null) {
			return OperResult.succeed("保存成功", null, reportBackEntity);
		}
		return OperResult.fail("保存失败", null);
		
	}

	/**
	 * 根据请假单主键填充销假单 销假单编辑页面用
	 * @param leaveId
	 * @return
	 */
	@RequestMapping("initReportBackForm")
	@ResponseBody
	public Map<String,String> findEntityByLeaveId(String leaveId){
		 List<ReportBackEntity> list = reportBackService.findEntityByLeaveId(leaveId);
		 if(list != null) {
			 Map<String,String> map = new HashMap<String,String>();
			 for(ReportBackEntity l : list) {
				 map.put("reportBackId",l.getReportBackId());
				 map.put("reportBackTime",l.getReportBackTime());
				 map.put("procId", l.getProcId());
				 map.put("createById",l.getCreateById());
				 map.put("createBy",l.getCreateBy()); 
				 map.put("createDate",l.getCreateDate());
				 map.put("modifiedById",l.getModifiedById());
				 map.put("modifiedBy",l.getModifiedBy());
				 map.put("modifiedDate",l.getModifiedDate());
				 map.put("deptId",l.getDeptId());
				 map.put("deptName",l.getDeptName());
				 map.put("empLevel",l.getEmpLevel());
				 map.put("status",String.valueOf(l.getStatus()));
				 map.put("remark",l.getRemark());
			 }
			 return map;
		 }
		return null;
		
	}
	
	/**
	 * 填充销假单表单数据 审批等其他页面用
	 * @param reportBackId
	 * @return
	 */
	@RequestMapping("initAuditForm")
	@ResponseBody
	public ReportBackEntity initAuditForm(String reportBackId) {
		ReportBackEntity reportBackEntity = reportBackService.get(reportBackId);
		return reportBackEntity;
		
	}
	
}
