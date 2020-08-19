package com.supporter.prj.ppm.prj_org.dev_org.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgDept;
import com.supporter.prj.ppm.prj_org.dev_org.service.DevOrgService;
import com.supporter.prj.ppm.prj_org.util.PrjConstant;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/prj_org/dev_org")//开发工作组织
public class DevOrgController extends AbstractController{
	private static final long serialVersionUID = 1L;
	@Autowired
	private DevOrgService devOrgService;
	
	@RequestMapping("deleteOrgCombo")
	public @ResponseBody String deleteOrgCombo(String id) {
		if(id == null || id == "") {
			return null;
		}
		return devOrgService.deleteOrgCombo(id);
	}
	
	@RequestMapping("deleteOrgDept")
	public @ResponseBody String deleteOrgDept(String id) {//删除组织部门
		if(id == null || id == "") {
			return null;
		}
		return devOrgService.deleteOrgDept(id);
	}
	
	@RequestMapping("deleteOrgEmp")
	public @ResponseBody String deleteOrgEmp(String id) {//删除组织人员
		if(id == null || id == "") {return null;}
		return devOrgService.deleteOrgEmp(id);
	}
	
	@RequestMapping("deleteOrgCoop")
	public @ResponseBody String deleteOrgCoop(String id) {//删除合作方
		if(id == null || id == "") {return null;}
		return devOrgService.deleteOrgCoop(id);
	}
	
	@RequestMapping("deleteOrgAgent")
	public @ResponseBody String deleteOrgAgent(String id) {//删除代理商
		if(id == null || id == "") {return null;}
		return devOrgService.deleteOrgAgent(id);
	}
	
	@RequestMapping("deleteOrgequipment")
	public @ResponseBody String deleteOrgequipment(String id) {//删除核心供应商
		if(id == null || id == "") {return null;}
		return devOrgService.deleteOrgequipment(id);
	}
	
//	/**
//	 * 初始化方法
//	 */
//	@RequestMapping("initEditPayPage")
//	
//	public @ResponseBody ContractPay initEditPayPage(String payId, String contractId) {
//		UserProfile user = this.getUserProfile();
//		ContractPay entity = contractPayService.initEditPayPage(payId,contractId,user);
//		return entity;
//	}
//	
	/**
	 * 工作组织部门列表.
	 */
	@RequestMapping("getDevOrgDeptGrid")
	public @ResponseBody JqGrid getDevOrgDeptGrid(HttpServletRequest request, 
			JqGridReq jqGridReq, PrjDeOrgDept prjDeorgDept,String prjId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.devOrgService.getDevOrgDeptGrid(user,jqGrid,prjId);
		return jqGrid;
	}
	
	/**
	 * 工作组织人员列表
	 * getDevOrgDeptGrid
	 */
	
	@RequestMapping("getDevOrgEmpGrid")
	public @ResponseBody JqGrid getDevOrgEmpGrid(HttpServletRequest request, 
			JqGridReq jqGridReq,String prjId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.devOrgService.getDevOrgEmpGrid(user,jqGrid,prjId);
		return jqGrid;
	}
	
	/**
	 * 工作组织合作方列表
	 * getDevOrgCoopGrid
	 */
	@RequestMapping("getDevOrgCoopGrid")
	public @ResponseBody JqGrid getDevOrgCoopGrid(HttpServletRequest request, 
			JqGridReq jqGridReq,String prjId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.devOrgService.getDevOrgCoopGrid(user,jqGrid,prjId);
		return jqGrid;
	}
	
	/**
	 * <pre>获取核心供应商列表</pre>
	 * @param request 请求对象
	 * @param jqGridReq 
	 * @param prjId 项目主键
	 * @return 列表
	 */
	@RequestMapping("getEquipmentGrid")
	public @ResponseBody JqGrid getEquipmentGrid(HttpServletRequest request, JqGridReq jqGridReq,String prjId) {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.devOrgService.getEquipmentGrid(user,jqGrid,prjId);
		return jqGrid;
	}
	
	/**
	 * <pre>获取联合体成员列表</pre>
	 * @param request 请求对象
	 * @param jqGridReq 
	 * @param prjId 项目主键
	 * @return 列表
	 */
	@RequestMapping("getComboGrid")
	public @ResponseBody JqGrid getComboGrid(HttpServletRequest request, JqGridReq jqGridReq,String prjId) {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.devOrgService.getComboGrid(user,jqGrid,prjId);
		return jqGrid;
	}
	/**
	 * 工作组织代理商列表
	 * getDevOrgAgentGrid
	 */
	@RequestMapping("getDevOrgAgentGrid")
	public @ResponseBody JqGrid getDevOrgAgentGrid(HttpServletRequest request, 
			JqGridReq jqGridReq,String prjId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.devOrgService.getDevOrgAgentGrid(user,jqGrid,prjId);
		return jqGrid;
	}
	
	/**
	 * 保存或更新
	 */
	@RequestMapping("saveOrUpdateDevOrg")
	public @ResponseBody OperResult saveOrUpdate(Prj prj) {
		UserProfile user = this.getUserProfile();
		this.devOrgService.saveOrUpdate(user, prj);
		return OperResult.succeed("保存成功",null,null);
	}
	
	
	/**
	 * 部门级别
	 * selectDeptLevel
	 */
	@RequestMapping("selectDeptLevel")
	public @ResponseBody Map<String, String> selectDeptLevel() {
		return PrjConstant.selectDeptLevel();
	}
	
	/**
	 * 人员角色
	 * selectEmpRole
	 */
	@RequestMapping("selectEmpRole")
	public @ResponseBody Map<String, String> selectEmpRole() {
		return PrjConstant.selectEmpRole();
	}
	
	/**
	 * 承担角色
	 * selectCoopRole
	 */
	@RequestMapping("selectCoopRole")
	public @ResponseBody Map<String, String> selectCoopRole() {
		return PrjConstant.selectCoopRole();
	}
	
}
