package com.supporter.prj.cneec.tpc.prj_contract_table.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.prj_contract_table.util.PrjContractTableConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: PrjContractTableController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-03-15
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/prjContractTable")
public class PrjContractTableController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PrjContractTableService prjContractTableService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param prjContractTable
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PrjContractTable prjContractTable) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjContractTableService.getGrid(user, jqGrid, prjContractTable);
		return jqGrid;
	}

	/**
	 * 获取合同额
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getContractAmountGrid")
	public @ResponseBody
	JqGrid getContractAmountGrid(HttpServletRequest request, JqGridReq jqGridReq, String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjContractTableService.getContractAmountGrid(user, jqGrid, contractId);
		return jqGrid;
	}
	
	@RequestMapping({ "getDerivateGrid" })
	@ResponseBody
	public JqGrid getDerivateGrid(HttpServletRequest request, JqGridReq jqGridReq, PrjContractTable prjContractTable,
			String projectId, String contractTypeCode) throws Exception {
		UserProfile user = getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjContractTableService.getDerivateGrid(user, jqGrid, prjContractTable, projectId, contractTypeCode);
		return jqGrid;
	}

	@RequestMapping({ "getContractGrid" })
	@ResponseBody
	public JqGrid getContractGrid(HttpServletRequest request, JqGridReq jqGridReq, PrjContractTable prjContractTable,
			String projectId, String contractTypeCode) throws Exception {
		UserProfile user = getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjContractTableService.getContractGrid(user, jqGrid, prjContractTable, projectId, contractTypeCode);
		return jqGrid;
	}

	/**
	 * 获取货物及服务明细
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGoodsGrid")
	public @ResponseBody
	JqGrid getGoodsGrid(HttpServletRequest request, JqGridReq jqGridReq, String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjContractTableService.getGoodsGrid(user, jqGrid, contractId);
		return jqGrid;
	}
	
	@RequestMapping({ "getSaleGrid" })
	@ResponseBody
	public JqGrid getSaleGrid(HttpServletRequest request, JqGridReq jqGridReq, PrjContractTable prjContractTable,
			String projectId, String contractTypeCode) throws Exception {
		UserProfile user = getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjContractTableService.getSaleGrid(user, jqGrid, prjContractTable, projectId, contractTypeCode);
		return jqGrid;
	}

	/**
	 * 获取收款条件
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCollectionTermsGrid")
	public @ResponseBody
	JqGrid getCollectionTermsGrid(HttpServletRequest request, JqGridReq jqGridReq, String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.prjContractTableService.getCollectionTermsGrid(user, jqGrid, contractId);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param contractId
	 * @param map
	 */
	@ModelAttribute
	public void getPrjContractTable(String contractId, Map<String, Object> map) {
		if (!StringUtils.isBlank(contractId)) {
			PrjContractTable prjContractTable = this.prjContractTableService.get(contractId);
			if (prjContractTable != null) {
				map.put("prjContractTable", prjContractTable);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param contractId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	PrjContractTable get(String contractId) {
		PrjContractTable prjContractTable = this.prjContractTableService.get(contractId);
		return prjContractTable;
	}

	/**
	 * 合同金额币别汇率下拉框
	 * @return
	 */
	@RequestMapping(value = "/selectContractAmountData")
	public @ResponseBody
	Map<String, Double> selectContractAmountData(String contractId) {
		return this.prjContractTableService.getPrjContractAmountMap(contractId);
	}

	/**
	 * 收/付款条款下拉框
	 * @return
	 */
	@RequestMapping(value = "/selectCollectionTermsData")
	public @ResponseBody
	Map<String, String> selectCollectionTermsData(String contractId) {
		return this.prjContractTableService.getPrjContractCollectionTermsMap(contractId);
	}

	/**
	 * 合同收/付款条款
	 * @param contractId
	 * @return
	 */
	@RequestMapping("getCollectionTerms")
	public @ResponseBody
	PrjContractCollectionTerms getCollectionTerms(String termsId) {
		PrjContractCollectionTerms collectionTerms = this.prjContractTableService.getPrjContractCollectionTerms(termsId);
		return collectionTerms;
	}

	/**
	 * 获取合同类型字典数据
	 */
	@RequestMapping(value = "/selectContractTypeData")
	public @ResponseBody
	Map<String, String> selectContractTypeData() {
		return PrjContractTableConstant.getContractTypeMap();
	}
	
	/**
	 * 获取合同状态字典数据
	 */
	@RequestMapping(value = "/selectContractStatusData")
	public @ResponseBody
	Map<String, String> selectContractStatusData() {
		return PrjContractTableConstant.getContractStatusMap();
	}

}