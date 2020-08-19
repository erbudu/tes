package com.supporter.prj.pm.procure_contract.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.PmConstant;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractSwfDao;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractPay;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSwf;
import com.supporter.prj.pm.procure_contract.service.ProcureContractService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: ProcureContractController
 * @Description: 控制器类
 * @author: liyinfeng
 * @date: 2018-6-14
 * @version: V1.0
 */
@Controller
@RequestMapping("pm/procure_contract/contract")
public class ProcureContractController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProcureContractService procureContractService;
	@Autowired
	private ProcureContractSwfDao swfDao;
	
	/**
	 * 根据主键获取功能模块
	 * @param id
	 */
	@RequestMapping("get")
	public @ResponseBody ProcureContract get(String id){
		ProcureContract procureContract = procureContractService.get(id);
		return procureContract;		
	}
	
	//获取报表服务器地址
	@RequestMapping("getReportUrl")
	public @ResponseBody String getReportUrl() {
		return PmConstant.getReportUrl();
	}
	
	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request 请求对象
	 * @param jqGridReq 页面表格参数
	 * @param procureContract 现场采购
	 * @return JqGrid 表格
	 * @throws Exception 异常
	 */
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			ProcureContract procureContract) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		
		String contractTypes = this.getRequestPara("contractTypes");
		if (StringUtils.isNotBlank(contractTypes)) {
			String[] typeArray = contractTypes.split(",");
			String hqlFilter = "";
			for (int i = 0; i < typeArray.length; i++) {
				int type = CommonUtil.parseInt(typeArray[i], -1);
				if (type >= 0) {
					if (hqlFilter.length() > 0) {
						hqlFilter += " or ";
					}
					hqlFilter += "contractType=" + type;
				}
			}
			if (hqlFilter.length() > 0) {
				jqGrid.addHqlFilter(hqlFilter);
			}
		}
		this.procureContractService.getGrid(user, jqGrid, procureContract,getRequestParameters());
		
		List<ProcureContract> applyRows = jqGrid.getRows();
		List<ProcureContract> applys = new ArrayList<ProcureContract>();
		DecimalFormat df = new DecimalFormat("#");  
		if (applyRows.size() > 0) {
			for (ProcureContract contract : applyRows) {
				ProcureContract pContract = procureContractService.get(contract.getContractId());
				if(pContract.getIsDoubleCoin()==0){//单币别
					contract.setCurreyStr(pContract.getSignCurrency());
					contract.setAmountStr(df.format(pContract.getSignAmount()));
				}else{
					contract.setCurreyStr(pContract.getSignCurrency()+"，"+pContract.getForeignCurrency());//将签约币别拼接起来
					contract.setAmountStr(df.format(pContract.getSignAmount())+"，"+df.format(pContract.getSignAmountTwo()));//将签约金额拼接起来
				}
				applys.add(contract);
			}
		}
		jqGrid.setRows(applys);
		
		return jqGrid;
	}

	/**
	 * 获取涉及工程部位树.
	 * @param request 请求对象
	 * @param jqGridReq 表格请求对象
	 * @param contractId 采购合同ID
	 * @return JqGrid
	 * @throws Exception 异常
	 */
	@RequestMapping({"getProjectSiteGrid"})
	@ResponseBody
	public JqGrid getTreeGrid(HttpServletRequest request, JqGridReq jqGridReq, String contractId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.procureContractService.getTreeGrid(jqGrid, contractId);
		return jqGrid;
	}

	/**
	 * 获取货物及服务明细
	 * @param request 请求对象
	 * @param jqGridReq 表格请求对象
	 * @param contractId 采购合同ID
	 * @return JqGrid
	 * @throws Exception 异常
	 */
	@RequestMapping("getGoodsGrid")
	@ResponseBody
	public JqGrid getGoodsGrid(HttpServletRequest request, JqGridReq jqGridReq,
			String contractId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		String buyItem = this.getRequestPara("buyItem");
		this.procureContractService.getGoodsGrid(user, jqGrid, contractId,buyItem);
		return jqGrid;
	}

	/**
	 * 获取付款条款列表
	 * @param request 页面请求对象
	 * @param jqGridReq 表格参数
	 * @param contractId 采购合同ID
	 * @return JqGrid
	 * @throws Exception 异常
	 */
	@RequestMapping("getPayGrid")
	@ResponseBody
	public JqGrid getPayGrid(HttpServletRequest request,
			JqGridReq jqGridReq, String contractId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.procureContractService.getPayGrid(jqGrid, contractId);
		return jqGrid;
	}
	
	/**
	 * 根据合同id获取审批完成的付款记录
	 * @param request
	 * @param jqGridReq
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getPaymentGrid")
	@ResponseBody
	public JqGrid getPaymentGrid(HttpServletRequest request,
			JqGridReq jqGridReq, ProcureContractPay contractPay,String contractId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.procureContractService.getPaymentGrid(jqGrid, contractPay,contractId);
		return jqGrid;
	}

	/**
	 * 编辑或查看页面加载对象。
	 * @param contractId 现场采购合同ID
	 * @param contractType 采购类型
	 * @return ProcureContract
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public Map<String, Object> initEditOrViewPage(String contractId, int contractType) {
		ProcureContract entity = procureContractService.initEditOrViewPage(contractId, contractType, this.getUserProfile());
		ProcureContractSwf swf = swfDao.get(entity.getContractId());
		swf.setProcureContract(entity);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("swf", swf);

		return map;
	}

	/**
	 * 获取币别码表.
	 * @return Map<String, String>
	 */
	@RequestMapping(value = "/getCurrencyTable")
	@ResponseBody
	public Map<String, String> getCurrencyTable() {
		return PmConstant.getCurrencyTable();
	}
	
	/**
	 * 获取采购类型码表.
	 * @return Map<Integer, String>
	 */
	@RequestMapping("getContractType")
	@ResponseBody
	public Map<Integer, String> getContractType() {
		return ProcureContract.ContractType.getMap();
	}

	
	/**
	 * 获取采购级别码表.
	 * @return Map<String, String>
	 */
	@RequestMapping("getContractLevel")
	@ResponseBody
	public Map<String, String> getContractLevel() {
		return ProcureContract.ContractLevel.getMap();
	}
	
	/**
	 * 付款条款码表.
	 * @return Map<String, String>
	 */
	@RequestMapping("getPayIteme")
	@ResponseBody
	public Map<String, String> getPayIteme() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("定金", "定金");
		map.put("预付款", "预付款");
		map.put("发货款", "发货款");
		map.put("到货款", "到货款");
		return map;
	}
	/**
	 * 付款条款码表.
	 * @return Map<String, String>
	 */
	@RequestMapping("getPayItems")
	@ResponseBody
	public Map<String, String> getPayItems() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("服务费", "服务费");
		map.put("培训费", "培训费");
		map.put("其他", "其他");
		return map;
	}
	
	/**
	 * 付款条款码表.
	 * @return Map<String, String>
	 */
	@RequestMapping("getPayItem")
	@ResponseBody
	public Map<String, String> getPayItem() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("预付款", "预付款");
		map.put("进度款", "进度款");
		map.put("尾款", "尾款");
		return map;
	}
	/**
	 * 根据合同id和付款项id获取
	 */
	@RequestMapping("getByPayItemId")
	 public @ResponseBody ProcureContractPay getByPayItemId(String contractId,String payItemId) {
		ProcureContractPay contractPay = this.procureContractService.getByPayItemId(contractId,payItemId);
		 return contractPay;
	 }
	
	/**
	 * 预付款开始扣减期数
	 * @param contractId 合同ID
	 * @return int
	 */
	@RequestMapping("getFirstPeriod")
	@ResponseBody
	public String getFirstPeriod(String contractId) {
		ProcureContractPay contractPay = this.procureContractService.getByPayItemId(contractId, "预付款");
		if (contractPay == null) {
			return "0";
		}
		return contractPay.getDeductions();
	}

	/**
	 * 获取合同类型
	 * @param contractId 合同ID
	 * @return int
	 */
	@RequestMapping("getContractCategory")
	@ResponseBody
	public int getContractCategory(String contractId) {
		ProcureContract contract = procureContractService.get(contractId);
		return contract.getContractCategory();
	}

}