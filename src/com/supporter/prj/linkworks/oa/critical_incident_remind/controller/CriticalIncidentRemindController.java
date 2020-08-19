package com.supporter.prj.linkworks.oa.critical_incident_remind.controller;

import java.io.IOException;
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
import com.supporter.prj.linkworks.oa.critical_incident_remind.entity.CriticalIncidentRemind;
import com.supporter.prj.linkworks.oa.critical_incident_remind.service.CriticalIncidentRemindService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/critical_incident_remind")     
public class CriticalIncidentRemindController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private CriticalIncidentRemindService criticalIncidentRemindService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, CriticalIncidentRemind criticalIncidentRemind) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.criticalIncidentRemindService.getGrid(user, jqGrid, criticalIncidentRemind);
		return jqGrid;
	}
    

	/**
	 * 根据主键获取功能模块�?.
	 * 
	 * @param reportId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody CriticalIncidentRemind get(String incidentId) {
		CriticalIncidentRemind criticalIncidentRemind = criticalIncidentRemindService.get(incidentId);
		return criticalIncidentRemind;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param incidentId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody CriticalIncidentRemind initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String incidentId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		CriticalIncidentRemind entity = criticalIncidentRemindService.initEditOrViewPage(jqGrid,incidentId,this.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param report 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<CriticalIncidentRemind> saveOrUpdate(CriticalIncidentRemind criticalIncidentRemind) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(CriticalIncidentRemind.class);		
		CriticalIncidentRemind entity = this.criticalIncidentRemindService.saveOrUpdate(user,criticalIncidentRemind,valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 生效.
	 * 
	 * @param apply
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<CriticalIncidentRemind> commit(CriticalIncidentRemind criticalIncidentRemind) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(CriticalIncidentRemind.class);
		CriticalIncidentRemind entity = this.criticalIncidentRemindService.commit(user,
				criticalIncidentRemind, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("effectiveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param incidentIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String incidentIds) {
		UserProfile user = this.getUserProfile();
		this.criticalIncidentRemindService.delete(user, incidentIds);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	
	
	
	/**
	 * 撤销生效操作
	 * 
	 * @param incidentIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("revokeStatus")
	public @ResponseBody OperResult revokeStatus(String incidentId) {
		UserProfile user = this.getUserProfile();
		this.criticalIncidentRemindService.revokeStatus(user, incidentId);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}

	
	/**
	 *办结完代办之后更改状态
	 * 
	 * @param incidentIds 主键集合，多个以逗号分隔
	 * @return
	 */
/*	@RequestMapping("updateStatus")
	public @ResponseBody OperResult updateStatus(String incidentId,String type) {
		UserProfile user = this.getUserProfile();
		System.out.println("type="+type);
		this.criticalIncidentRemindService.updateStatus(user, incidentId,type);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}*/
	
	
	/**
	 * 获取字典数据-用于新建或者编辑页面的下拉显示（是否重复提醒）
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getIsRepeatRemindCodetable")
	public Map<String, String> getIsRepeatRemindCodetable()
			throws IOException {
		Map<String, String> map = CriticalIncidentRemind.getIsRepeatRemindMap();
		return map;
	}
	
	
	
}
