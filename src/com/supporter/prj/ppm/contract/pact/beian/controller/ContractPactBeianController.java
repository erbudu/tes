package com.supporter.prj.ppm.contract.pact.beian.controller;

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
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeian;
import com.supporter.prj.ppm.contract.pact.beian.service.ContractPactBeianService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/contract/pact/beian")
public class ContractPactBeianController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContractPactBeianService service;

	/**
	 * 加载列表
	 * @param request
	 * @param jqGridReq
	 * @param report
	 * @throws Exception
	 */
	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ContractPactBeian entity) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		service.getGrid(user, jqGrid, entity);
		return jqGrid;
	}

	/**
	 *  进入新建或编辑或查看页面需要加载的信息.
	 * @param recordId 主键
	 * @param user 当前登录用户
	 * @return 实体对象
	 */
	@RequestMapping("/initEditOrViewPage")
	@ResponseBody
	public ContractPactBeian initEditOrViewPage(String recordId, String prjId) {
		UserProfile user = this.getUserProfile();
		ContractPactBeian entity = service.initEditOrViewPage(recordId, prjId, user);
		return entity;
	}

	/**
	 * 保存或更新
	 * @param prjActive 实体对象
	 * @return 操作结果
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public OperResult<ContractPactBeian> saveOrUpdate(ContractPactBeian beian) {
		UserProfile user = this.getUserProfile();
		ContractPactBeian entity = service.saveOrUpdate(user, beian);
		return OperResult.succeed("保存成功", "保存成功", entity);
	}

	/**
	 * 删除
	 * @param reportId 主键
	 * @return	操作结果
	 */
	@RequestMapping("/batchDel")
	@ResponseBody
	public OperResult<?> batchDel(String recordId) {
		this.service.delete(recordId);
		return OperResult.succeed("操作成功", "操作成功", null);
	}

	/**
	 * 提交
	 * @param recordId 协议备案主键
	 * @return  操作结果
	 */
	@RequestMapping("/valid")
	@ResponseBody
	public OperResult<?> valid(String recordId) {
		UserProfile user = this.getUserProfile();
		this.service.valid(recordId, user);
		return OperResult.succeed("操作成功", "操作成功", null);
	}

	/**
	 * 获取某项目下所有通过/有条件通过并验证通过的报审，且该报审未备案过
	 * @param prjId 项目id
	 * @return Map<报审id, 报审编号>
	 */
	@RequestMapping("/getAllPassReport")
	@ResponseBody
	public Map<String, String> getAllPassReport(String prjId) {
		return service.getAllPassReport(prjId);
	}

	/**
	 * 备案编辑操作下，在获取的所有符合条件的报审单上添加该条备案当前关联的报审单
	 * @return Map<报审id, 报审编号>
	 */
	@RequestMapping("/getAllPassReportPlus")
	@ResponseBody
	public Map<String, String> getAllPassReportPlus(String reportId) {
		return service.getAllPassReportPlus(reportId);
	}

}
