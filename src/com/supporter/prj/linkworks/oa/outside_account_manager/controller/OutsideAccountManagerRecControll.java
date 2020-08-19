package com.supporter.prj.linkworks.oa.outside_account_manager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManagerRec;
import com.supporter.prj.linkworks.oa.outside_account_manager.service.OutsideAccountManagerRecService;
import com.supporter.prj.log.controller.AbstractController;

@Controller
@RequestMapping("oa/outsideAccountManagerRec")
public class OutsideAccountManagerRecControll extends AbstractController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OutsideAccountManagerRecService outsideAccountManagerRecService;
	
	/**
	 * 获取人员列表
	 * @param request
	 * @param jqGridReq
	 * @param managerId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String managerId ) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.outsideAccountManagerRecService.getGrid(jqGrid, managerId, user);
		return jqGrid;
	}
	
	/**
	 * 审批中获取人员列表
	 * @param request
	 * @param jqGridReq
	 * @param managerId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getExamGrid")
	public @ResponseBody JqGrid getExamGrid(HttpServletRequest request, JqGridReq jqGridReq, String managerId ) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.outsideAccountManagerRecService.getExamGrid(jqGrid, managerId, user);
		return jqGrid;
	}
	
	/**
	 * 进入新建、编辑或查看页面时加载的信息
	 * @param netinId
	 * @return
	 */
	@RequestMapping("getInitGrid")
	public @ResponseBody JqGrid getInitGrid(HttpServletRequest request, JqGridReq jqGridReq, String managerId ) throws Exception{
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.outsideAccountManagerRecService.getInitGrid(jqGrid, managerId);
		return jqGrid;
	}
	
	/**
	 * 审批过程中更新状态
	 * @param id
	 * @param Status
	 * @return
	 */
	@RequestMapping("updateStatus")
	public @ResponseBody OperResult<OutsideAccountManagerRec> updateStatus(String id, String status){
		OutsideAccountManagerRec entity = this.outsideAccountManagerRecService.updateStatus(id, status);
		return OperResult.succeed("saveSuccess", null, entity);
	}
}
