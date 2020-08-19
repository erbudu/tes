package com.supporter.prj.pm.reserve_fund.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.reserve_fund.entity.NotReimbursed;
import com.supporter.prj.pm.reserve_fund.entity.NotReimbursedRec;
import com.supporter.prj.pm.reserve_fund.service.NotReimbursedService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("pm/reserve_fund/notReimbursed")
public class NotReimbursedController extends AbstractController{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private NotReimbursedService reimbursedService;
	
	/**
	 * 分页表格展示数据.--主表
	 * 用于系统登录首页展示
	 * @param isTrue 
	 * @return
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, NotReimbursed notReimbursed) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.reimbursedService.getGrid( jqGrid, notReimbursed,user);
		return jqGrid;
	}	
	
	/**
	 * 分页表格展示数据.--明细表
	 * 用于系统登录首页展示
	 * @param isTrue 
	 * @return
	 */
	@RequestMapping("getRecGrid")
	public @ResponseBody JqGrid getRecGrid(HttpServletRequest request, JqGridReq jqGridReq, NotReimbursedRec notReimbursedRec) throws Exception  {
		UserProfile user = this.getUserProfile();
		String reimbursedId = this.getRequestPara("reimbursedId");
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.reimbursedService.getRecGrid( jqGrid, notReimbursedRec,user,reimbursedId);
		return jqGrid;
	}
	
}
