package com.supporter.prj.cneec.tpc.prj_settlement.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlement;
import com.supporter.prj.cneec.tpc.prj_settlement.service.PrjSettlementService;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

@Controller
@RequestMapping("tpc/prjSettlement")
public class PrjSettlementController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PrjSettlementService prjSettlementService;
	
	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,String attr,String prjId,String settlementStatus) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjSettlementService.getGrid( jqGrid, attr, prjId, settlementStatus);
		return jqGrid;
	}

	/**
	 * 高级查询获取字典数据-付款状态的下拉列表
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getSettlementStatus")
	public Map<String, String> getSettlementStatus()throws IOException {
		Map<String, String> map = PrjSettlement.getCodeSettlementStatus();
		return map;
	}

	/**
	 * 分页表格展示明细数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRecGrid")
	public @ResponseBody JqGrid getRecGrid(HttpServletRequest request, JqGridReq jqGridReq, String settlementId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjSettlementService.getRecGrid(jqGrid, settlementId);
		return jqGrid;
	}
	
	/**
	 * 新建页面获取字典数据-选择币别的下拉列表
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getCurrencyMap")
	public Map<String, String> getCurrencyMap()throws IOException {
		Map<String, String> map = TpcConstant.getCommonCurrencyMap();
		return map;
	}
	
	/**
	 * 新建页面获取字典数据-选择费用类型的下拉列表
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getCostgType")
	public Map<String, String> getCostType()throws IOException {
		Map<String, String> map = PrjSettlement.getCostType();
		return map;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param reportId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody PrjSettlement initEditOrViewPage() {
		Map<String, Object> paramMap = getRequestParameters();
		PrjSettlement entity = prjSettlementService.initEditOrViewPage(paramMap,this.getUserProfile());
		return entity;
	}

	/**
	 * 打印预览
	 * @return
	 */
	@RequestMapping("viewPrint")
	public @ResponseBody
	PrjSettlement viewPrint(String settlementId) {
		PrjSettlement entity = prjSettlementService.viewPrint(settlementId, this.getUserProfile());
		return entity;
	}

	/**
	 * 设置打印份数
	 * @param response
	 */
	@RequestMapping("setPrintCount")
	public void setPrintCount(HttpServletResponse response, String settlementId) {
		String json = this.prjSettlementService.setPrintCount(settlementId, this.getUserProfile());
		HttpUtil.write(response, json);
	}

	/**
	 * 银行出纳审核
	 * @param response
	 */
	@RequestMapping("viewByBankTeller")
	public @ResponseBody
	PrjSettlement viewByBankTeller(HttpServletResponse response, String settlementId) {
		PrjSettlement entity = this.prjSettlementService.viewByBankTeller(settlementId, this.getUserProfile());
		return entity;
	}

	/**
	 * 新建页面获取字典数据-选择预算项的下拉列表
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getAvailableAmount")
	public Map<String, String> getAvailableAmount()throws IOException {
		Map<String, String> map = TpcConstant.getBudgetItemMap();
		return map;
	}
	
	/**
	 * 新建页面获取字典数据-选择预算项的下拉列表
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getBudgetItem")
	public Map<String, String> getBudgetItem()throws IOException {
		Map<String, String> map = TpcConstant.getBudgetItems(TpcConstant.TPC_BUDGET_COST_ITEM);
		return map;
	}
	
	/**
	 * 新建页面获取预算项数据
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getBudgetById")
	public List<Double> getBudgetById(String prjId,String itemId)throws IOException {
		List<Double> list = TpcBudgetUtil.getBudgetById(prjId, itemId);
		return list;
	}
	
	/**
	 * 新建页面获取预算项数据
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("getTotalSettlementAmountAct")
	public double getTotalSettlementAmountAct(String prjId) throws IOException {
		double act = prjSettlementService.getTotalSettlementAmountAct(prjId);
		return act;
	}

	/**
	 * 批量删除数据.
	 * 
	 * @param settlementIds 页面传递参数ids
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult<PrjSettlement> batchDel(String settlementIds) {
		UserProfile user = this.getUserProfile();
		this.prjSettlementService.delete(user, settlementIds);
		return OperResult.succeed("deleteSuccess", null, null);
		
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param news 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<PrjSettlement> saveOrUpdate(PrjSettlement prjSettlement ) {
		UserProfile user = this.getUserProfile();
		PrjSettlement entity = this.prjSettlementService.saveOrUpdate(user, prjSettlement);
		return OperResult.succeed("saveSuccess", null, entity);
		
	}

	/**
	 * 提交
	 * @param prjSettlement
	 * @return
	 */
	@RequestMapping("commit")
	public void commit(PrjSettlement prjSettlement, HttpServletResponse response) {
		UserProfile user = this.getUserProfile();
		String json = this.prjSettlementService.commit(user, prjSettlement);
		HttpUtil.write(response, json);
	}

	/**
	 * 保存或更新特殊审批.
	 * 
	 * @param news 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveIsFullSwf")
	public @ResponseBody OperResult<PrjSettlement> saveIsFullSwf(String settlementId, String isFullSwf) {
		PrjSettlement entity = this.prjSettlementService.saveIsFullSwf(settlementId, isFullSwf);
		return OperResult.succeed("saveSuccess", null, entity);
		
	}

	/**
	 * 汇款用途
	 * @return
	 */
	@RequestMapping("getRemittancePurposeData")
	@ResponseBody
	public Map<String, String> getRemittancePurposeData() {
		Map<String, String> map = this.prjSettlementService.getRemittancePurposeData();
		return map;
	}

}
