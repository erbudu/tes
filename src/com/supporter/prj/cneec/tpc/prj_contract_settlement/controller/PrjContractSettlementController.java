package com.supporter.prj.cneec.tpc.prj_contract_settlement.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlement;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlementRec;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.service.PrjContractSettlementService;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

@Controller
@RequestMapping("tpc/prjContractSettlement")
public class PrjContractSettlementController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PrjContractSettlementService contractSettlementService;
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,String attr,String prjId,String settlementStatus,String controlStatus) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSettlementService.getGrid(user, jqGrid, attr, prjId, settlementStatus, controlStatus);
		return jqGrid;
	}
	
	/**
	 * 高级查询获取字典数据-付款状态的下拉列表
	 * 
	 * @param key
	 */
	@ResponseBody
	@RequestMapping("getSettlementStatus")
	public Map<String, String> getSettlementStatus() {
		Map<String, String> map = PrjContractSettlement.SettlementStatus.getCodeTable();
		return map;
	}

	/**
	 * 高级查询获取字典数据-合同控制状态的下拉列表
	 * 
	 * @param key
	 */
	@ResponseBody
	@RequestMapping("getControlStatus")
	public Map<String, String> getControlStatus() {
		Map<String, String> map = PrjContractSettlement.ControlStatus.getCodeTable();
		return map;
	}

	/**
	 * 新建页面获取字典数据-选择合同的下拉列表
	 * 
	 * @param key
	 */
	@ResponseBody
	@RequestMapping("getContractCodeTable")
	public Map<String, String> getContractCodeTable(String projectId) {
		Map<String, String> map = contractSettlementService.getContractCodeTable(projectId, this.getUserProfile());
		return map;
	}
	
	/**
	 * 新建获取字典数据-银行账户的下拉列表
	 * 
	 * @param key
	 */
	@ResponseBody
	@RequestMapping("getBankAccountCodeTable")
	public Map<String, String> getBankAccountCodeTable(String supplierId, String gatheringUnit, String bank) {
		Map<String, String> map = contractSettlementService.getBankAccountCodeTable(supplierId, gatheringUnit, bank);
		return map;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	PrjContractSettlement initEditOrViewPage() {
		Map<String, Object> paramMap = getRequestParameters();
		PrjContractSettlement entity = contractSettlementService.initEditOrViewPage(paramMap, this.getUserProfile());
		return entity;
	}

	/**
	 * 打印预览
	 * @return
	 */
	@RequestMapping("viewPrint")
	public @ResponseBody
	PrjContractSettlement viewPrint(String settlementId) {
		PrjContractSettlement entity = contractSettlementService.viewPrint(settlementId, this.getUserProfile());
		return entity;
	}

	/**
	 * 设置打印份数
	 * @param response
	 */
	@RequestMapping("setPrintCount")
	public void setPrintCount(HttpServletResponse response, String settlementId) {
		String json = this.contractSettlementService.setPrintCount(settlementId, this.getUserProfile());
		HttpUtil.write(response, json);
	}

	/**
	 * 银行出纳审核
	 * @param response
	 */
	@RequestMapping("viewByBankTeller")
	public @ResponseBody
	PrjContractSettlement viewByBankTeller(HttpServletResponse response, String settlementId) {
		PrjContractSettlement entity = this.contractSettlementService.viewByBankTeller(settlementId, this.getUserProfile());
		return entity;
	}

	/**
	 * 新建获取字典数据-银行的下拉列表
	 * 
	 * @param key
	 */
	@ResponseBody
	@RequestMapping("getBankCodeTable")
	public Map<String, String> getBankCodeTable(String supplierId, String gatheringUnit) {
		Map<String, String> map = contractSettlementService.getBankCodeTable(supplierId, gatheringUnit);
		return map;
	}
	
	/**
	 * 根据客户获取银行或银行账户
	 * 
	 * @param key
	 */
	@ResponseBody
	@RequestMapping("selectBankOrAccount")
	public Map<String, String> selectBankOrAccount(String supplierId, String gatheringUnit, String bank) {
		Map<String, String> map = contractSettlementService.selectBankOrAccount(supplierId, gatheringUnit, bank);
		return map;
	}

	@ResponseBody
	@RequestMapping("selectBankAccount")
	public List<?> selectBankAccount(String supplierId, String gatheringUnit, String bank) {
		List<?> list = contractSettlementService.selectBankAccount(supplierId, gatheringUnit, bank);
		return list;
	}
	
	/**
	 * 分页表格展示明细数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRecGrid")
	public @ResponseBody
	JqGrid getRecGrid(HttpServletRequest request, JqGridReq jqGridReq, String settlementId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.contractSettlementService.getRecGrid(jqGrid, settlementId);
		return jqGrid;
	}

	/**
	 * 获取收款明细详细信息
	 * @param payaccountId
	 * @return
	 */
	@RequestMapping(value = "/getDetail")
	public @ResponseBody
	PrjContractSettlementRec getDetail(String recordId) {
		return contractSettlementService.getRec(recordId);
	}

	/**
	 * 高级查询获取字典数据-预算项的下拉列表
	 * 
	 */
	@ResponseBody
	@RequestMapping("getItemTable")
	public Map<String, String> getItemTable() {
		Map<String, String> map = TpcConstant.getBudgetItems(TpcConstant.TPC_BUDGET_SubContract_ITEM);
		return map;
	}

	/**
	 * 采购合同付款条款
	 * @return
	 */
	@RequestMapping("selectPaymentTermsData")
	public @ResponseBody
	Map<String, String> selectPaymentTermsData(String contractId) {
		return this.contractSettlementService.getPaymentTermsMap(contractId);
	}

	/**
	 * 付款条款
	 * 
	 * @param key
	 */
	@RequestMapping("getPrjContractPaymentTerms")
	public @ResponseBody
	PrjContractCollectionTerms getPrjContractPaymentTerms(String paymentTermsId) {
		PrjContractCollectionTerms entity = contractSettlementService.getPrjContractPaymentTerms(paymentTermsId);
		return entity;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param news 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<PrjContractSettlement> saveOrUpdate(PrjContractSettlement contractSettlement ,String filesName) {
		UserProfile user = this.getUserProfile();
		PrjContractSettlement entity = this.contractSettlementService.saveOrUpdate(user, contractSettlement);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 提交
	 * @param prjSettlement
	 * @param response
	 */
	@RequestMapping("commit")
	public void commit(PrjContractSettlement contractSettlement, HttpServletResponse response) {
		UserProfile user = this.getUserProfile();
		String json = this.contractSettlementService.commit(user, contractSettlement);
		HttpUtil.write(response, json);
	}

	/**
	 * 编辑时获取合计的数据
	 * 
	 * @param key
	 */
	@ResponseBody
	@RequestMapping("getTotalAmount")
	public List<Map<String, String>> getTotalAmount(String contractId, String settlementId) {
		List<Map<String, String>> list = this.contractSettlementService.getTotal(contractId, settlementId);
		return list;
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult<?> batchDel(String settlementIds) {
		UserProfile user = this.getUserProfile();
		this.contractSettlementService.delete(user, settlementIds);
		return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS);
	}

	/**
	 * 汇款用途
	 * @return
	 */
	@RequestMapping("getRemittancePurposeData")
	@ResponseBody
	public Map<String, String> getRemittancePurposeData() {
		Map<String, String> map = this.contractSettlementService.getRemittancePurposeData();
		return map;
	}

}
