package com.supporter.prj.cneec.tpc.benefit_budget_change.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.VBenefitProject;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCh;
import com.supporter.prj.cneec.tpc.benefit_budget_change.service.BenefitBudgetChangeService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

@Controller
@RequestMapping("tpc/benefitBudgetChange")
public class BenefitBudgetChangeController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private BenefitBudgetChangeService benefitBudgetChangeService;

	/**
	 * 项目Grid
	 * 
	 * @param request
	 * @param jqGridReq
	 * @param benefitBudget
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, VBenefitProject benefitProject) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitBudgetChangeService.getGrid(jqGrid, benefitProject);
		return jqGrid;
	}
	
	//根据选择的合同id来获取过程合同预算表：TPC_BENEFIT_CONTRACT（主表）、_BUDGET（预算项）、_CURRENCY（币别）合同预算信息，并同时保存到数据库中
	@RequestMapping("createBenefitContractChange")
	public @ResponseBody BenefitContractCh copyContract(String contractId) {
		UserProfile user = this.getUserProfile();
		return benefitBudgetChangeService.createBenefitContractChange(contractId,user);
	}
	
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	BenefitContractCh initEditOrViewPage(String changeId) {
		UserProfile user = this.getUserProfile();
		BenefitContractCh benefitContractCh = this.benefitBudgetChangeService.initEditOrViewPage(changeId,user);
		return benefitContractCh;
	}
	/**
	 * 根据contractId查询变更表有无数据
	 * @param contractId
	 */
	@RequestMapping("checkBenefitChange")
	public @ResponseBody
	String checkBenefitChange(String contractId) {
		return this.benefitBudgetChangeService.checkBenefitChange(contractId);
	}
	
	/**
	 * 根据返回的contractId查询状态
	 * @param changeId
	 */
	@RequestMapping("checkBenefitChOk")
	public @ResponseBody
	boolean checkBenefitChOk(String changeId) {
		return this.benefitBudgetChangeService.checkBenefitChOk(changeId);
	}
	/**
	 * 过程合同Grid
	 * @param request
	 * @param jqGridReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractGrid")
	public @ResponseBody
	JqGrid getContractGrid(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitBudgetChangeService.getBenefitContractGrid(jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	/**
	 * 获取效益预算集合表头
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitBudgetAssembleTitleData")
	public void getBenefitBudgetAssembleTitleData(HttpServletResponse response, String projectId) throws Exception {
		boolean allBudget = Boolean.valueOf(this.getRequestPara("allBudget"));
		String json = this.benefitBudgetChangeService.getBenefitBudgetAssembleTitleData(projectId, allBudget);
		HttpUtil.write(response, json);
	}

	/**
	 * 获取效益预算集合数据
	 * 
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitBudgetAssembleGrid")
	public void getBenefitBudgetAssembleGrid(HttpServletResponse response, String projectId) throws Exception {
		boolean allBudget = Boolean.valueOf(this.getRequestPara("allBudget"));
		String json = this.benefitBudgetChangeService.getBenefitBudgetAssembleGrid(projectId, allBudget);
		HttpUtil.write(response, json);
	}
	
	/**
	 * 获取效益预算集合（项目过程）表头
	 * 
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitContractAssembleTitleData")
	public void getBenefitContractAssembleTitleData(HttpServletResponse response, String projectId) throws Exception {
		String json = this.benefitBudgetChangeService.getBenefitContractAssembleTitleData(projectId);
		HttpUtil.write(response, json);
	}
	
	/**
	 * 获取效益预算集合（项目过程）数据
	 * 
	 * @param response
	 * @param projectId
	 * @throws Exception
	 */
	@RequestMapping("getBenefitContractAssembleGrid")
	public void getBenefitContractAssembleGrid(HttpServletResponse response, String projectId) throws Exception {
		String json = this.benefitBudgetChangeService.getBenefitContractAssembleGrid(projectId);
		HttpUtil.write(response, json);
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * 
	 * @param projectId
	 * @param map
	 */
	@ModelAttribute
	public void getBenefitProject(String projectId, Map<String, Object> map) {
		if (!StringUtils.isBlank(projectId)) {
			VBenefitProject benefitProject = this.benefitBudgetChangeService.get(projectId);
			if (benefitProject != null) {
				map.put("benefitProject", benefitProject);
			}
		}
	}

	/**
	 * 根据ID获取项目对象
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	VBenefitProject get(String projectId) {
		VBenefitProject benefitProject = this.benefitBudgetChangeService.get(projectId);
		return benefitProject;
	}

	/**
	 * 根据ID获取合同对象
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("getContract")
	public @ResponseBody
	BenefitContractCh getContract(String changeId) {
		BenefitContractCh benefitContract = this.benefitBudgetChangeService.getBenefitContract(changeId);
		return benefitContract;
	}
	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return BenefitContractCh.getSwfStatusMap();
	}
	/**
	 * 获取合同预算Grid
	 *
	 * @param request
	 * @param jqGridReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractBudgetGrid")
	public @ResponseBody
	JqGrid getContractBudgetGrid(HttpServletRequest request, JqGridReq jqGridReq) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitBudgetChangeService.getBenefitContractBudgetGrid(jqGrid, this.getRequestParameters());
		return jqGrid;
	}

	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BenefitContractCh> saveOrUpdate(BenefitContractCh benefitContractCh) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		BenefitContractCh entity = this.benefitBudgetChangeService.saveOrUpdate(user, benefitContractCh,valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}
	
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String changeIds) {
		UserProfile user = this.getUserProfile();
		this.benefitBudgetChangeService.delete(user, changeIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}
	
	/**
	 * 获取币别表
	 */
	@RequestMapping("getCurrencyGrid")
	public @ResponseBody
	JqGrid getCurrencyGrid(HttpServletRequest request, JqGridReq jqGridReq, String changeId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.benefitBudgetChangeService.getCurrencyGrid(jqGrid, changeId, this.getRequestParameters());
		return jqGrid;
	}

}