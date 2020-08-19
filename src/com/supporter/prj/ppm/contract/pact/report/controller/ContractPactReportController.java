package com.supporter.prj.ppm.contract.pact.report.controller;

import java.util.List;
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
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/contract/pact/report")
public class ContractPactReportController extends AbstractController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ContractPactReportService service;

	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractPactReport report) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		service.getGrid(jqGrid, report, user);
		return jqGrid;
	}

	/**
	 *  进入新建或编辑或查看页面需要加载的信息.
	 * @param reportId 主键
	 * @param user 当前登录用户
	 * @param prjId 项目信息栏中选中项目的id
	 * @return 实体对象
	 */
	@RequestMapping("/initEditOrViewPage")
	@ResponseBody
	public ContractPactReport initEditOrViewPage(String reportId, String prjId) {
		UserProfile user = this.getUserProfile();
		ContractPactReport entity = service.initEditOrViewPage(reportId, user, prjId);
		return entity;
	}

	/**
	 * 保存或更新
	 * @param report 实体对象
	 * @return 操作结果
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public OperResult<ContractPactReport> saveOrUpdate(ContractPactReport report) {
		UserProfile user = this.getUserProfile();
		ContractPactReport entity = service.saveOrUpdate(report, user);
		return OperResult.succeed("保存成功", "保存成功", entity);
	}

	/**
	 * 设置提交人等信息
	 * @param reportId 报审主键
	 */
	@RequestMapping("/valid")
	@ResponseBody
	public void vaild(String reportId) {
		UserProfile user = this.getUserProfile();
		service.valid(reportId, user);
	}

	/**
	 * 删除
	 * @param reportId 主键
	 * @return	操作结果
	 */
	@RequestMapping("/batchDel")
	@ResponseBody
	public OperResult<?> batchDel(String reportId) {
		this.service.delete(reportId);
		return OperResult.succeed("操作成功", "操作成功", null);
	}

	/**
	 * 获取所有审批完成报审的报审名称
	 * @return Map<报审id, 报审编号>
	 */
	@RequestMapping("/getAllReport")
	@ResponseBody
	public Map<String, String> getAllCompleteReport(String prjId) {
		Map<String, String> map = service.getAllCompleteReport(prjId);
		return map;
	}

	/**
	 * 根据主键获取报审对象
	 * @param reportId 报审主键
	 * @return 实体对象
	 */
	@RequestMapping("/getReport")
	@ResponseBody
	public ContractPactReport getReport(String reportId) {
		return service.getReport(reportId);
	}

	/**
	 * 获取某项目下所有通过/有条件通过的报审
	 * @return Map<报审id, 报审编号>
	 */
	@RequestMapping("/getAllPassReport")
	@ResponseBody
	public Map<String, String> getAllPassReport(String prjId) {
		return service.getAllPassReport(prjId);
	}

	/**
	 * 检查所选项目是否可以启动合同及协议
	 * @param prjId 项目id
	 * @return 检查结果
	 */
	@RequestMapping("/checkPrjStatus")
	@ResponseBody
	public boolean checkPrjStatus(String prjId) {
		return service.checkPrjStatus(prjId);
	}

	/**
	 * 初始化合同及协议类型下拉选选项组数据
	 */
	@RequestMapping("/getContractPactType")
	@ResponseBody
	public List<Object> getContractPactType() {
		List<Object> list = service.getContractPactType();
		return list;
	}

	/**
	 * 获取某项目下已备案完成的报审列表,且报审的合同及协议类型为项目开发合作协议下子项
	 */
	@RequestMapping("/getBeianPassRepGrid")
	@ResponseBody
	public JqGrid getBeianPassRepGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractPactReport report, String keyword) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		service.getBeianPassRepGrid(jqGrid, report, user, keyword);
		return jqGrid;
	}

	/**
	 * 获取某项目下已备案完成的报审列表
	 * @param keyword 模糊查询关键字
	 */
	@RequestMapping("/getBeianPassRepGridSimp")
	@ResponseBody
	public JqGrid getBeianPassRepGridSimp(HttpServletRequest request, JqGridReq jqGridReq, ContractPactReport report, String keyword)
			throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		service.getBeianPassRepGridSimp(jqGrid, report, user, keyword);
		return jqGrid;
	}

}


