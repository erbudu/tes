package com.supporter.prj.cneec.tpc.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.util.CommonUtil;

/**
 * 
 * @Title: TpcConstant
 * @Description: 贸易项目通用常量
 * @author: yanweichao
 * @date: 2017-11-9
 * @version: V1.0
 */
public class TpcConstant {

	/** 项目唯一类别 **/
	public static final String SINGLE_CUSTOMER = "SINGLE_CUSTOMER";
	public static final String SINGLE_SUPPLIER = "SINGLE_SUPPLIER";

	public static Map<String, String> getSingleCategoryMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(SINGLE_CUSTOMER, "单一客户");
		map.put(SINGLE_SUPPLIER, "单一供方");
		return map;
	}

	/** 币别 **/
	public static final String CURRENCY_CATEGORY = "CURRENCY_CATEGORY";

	// 币别编码+币别名称
	public static Map<String, String> getCurrencyMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“币别”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 币别编码+币别名称
				map.put(item.getExtField3(), item.getDisplayName());
			}
		}
		return map;
	}

	// 币别编码+币别汇率
	public static Map<String, String> getCurrencyRateMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“币别”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 币别编码+币别汇率
				map.put(item.getExtField3(), item.getExtField2());
			}
		}
		return map;
	}

	/** 常用币别 **/
	public static final String STANDARD_CURRENCY = "CNY";
	public static final String CURRENCY1 = "CNY";
	public static final String CURRENCY2 = "USD";
	public static final String CURRENCY3 = "EUR";
	public static final String CURRENCY4 = "GBP";
	public static final String CURRENCY5 = "JPY";

	public static Map<String, String> getCommonCurrencyMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(CURRENCY1, "人民币");
		map.put(CURRENCY2, "美元");
		map.put(CURRENCY3, "欧元");
		map.put(CURRENCY4, "英镑");
		map.put(CURRENCY5, "日元");
		return map;
	}

	/** 评审结论 **/
	public static final String REVIEW_CONCLUSION_AGREE = "REVIEW_CONCLUSION_AGREE";
	public static final String REVIEW_CONCLUSION_CONTINUE = "REVIEW_CONCLUSION_CONTINUE";
	public static final String REVIEW_CONCLUSION_DISAGREE = "REVIEW_CONCLUSION_DISAGREE";

	public static Map<String, String> getReviewConclusionMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(REVIEW_CONCLUSION_AGREE, "同意签约");
		map.put(REVIEW_CONCLUSION_CONTINUE, "继续谈判");
		map.put(REVIEW_CONCLUSION_DISAGREE, "不同意签约");
		return map;
	}

	/** 基于模块（如用印、备案基于框架协议评审/合同签约期评审） **/
	public static final String RELY_BY_PROTOCOL_REVIEW = "PROTOCOL_REVIEW";// 框架协议评审
	public static final String RELY_BY_CONTRACT_SIGN_REVIEW = "CONTRACT_SIGN_REVIEW";// 合同签约前评审
	public static final String RELY_BY_DERIVATIVE_CONTRACT = "DERIVATIVE_CONTRACT";// 衍生合同

	/**
	 * 用印、备案业务类型
	 */
	public static final String OUTLINE_AGREEMENT = "OUTLINE_AGREEMENT";// 框架协议
	public static final String ORDER_CONTRACT = "ORDER_CONTRACT";// 销售合同
	public static final String PURCHASE_CONTRACT = "PURCHASE_CONTRACT";// 采购合同
	public static final String DERIVATION_CONTRACT = "DERIVATION_CONTRACT";// 衍生合同
	public static final String ORDER_PROTOCOL_CHANGER = "ORDER_PROTOCOL_CHANGER";// 销售合同变更协议
	public static final String PURCHASE_PROTOCOL_CHANGER = "PURCHASE_PROTOCOL_CHANGER";// 采购合同变更协议
	public static final String DERIVATION_CONTRACT_CHANGE = "DERIVATION_CONTRACT_CHANGE";// 衍生合同变更

	/**
	 * 用印下拉
	 */
	public static Map<String, String> getUseSealTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(ORDER_CONTRACT, "销售合同用印");
		map.put(PURCHASE_CONTRACT, "采购合同用印");
		map.put(DERIVATION_CONTRACT, "衍生合同用印");
		map.put(OUTLINE_AGREEMENT, "框架协议用印");
		map.put(ORDER_PROTOCOL_CHANGER, "销售合同变更协议用印");
		map.put(PURCHASE_PROTOCOL_CHANGER, "采购合同变更协议用印");
		map.put(DERIVATION_CONTRACT_CHANGE, "衍生合同变更用印");
		return map;
	}

	/**
	 * 销售/采购合同用印下拉
	 */
	public static Map<String, String> getUseSealTypeOfContractMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(ORDER_CONTRACT, "销售合同用印");
		map.put(PURCHASE_CONTRACT, "采购合同用印");
		return map;
	}

	/**
	 * 备案下拉
	 */
	public static Map<String, String> getRecordFilingTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(ORDER_CONTRACT, "销售合同备案");
		map.put(PURCHASE_CONTRACT, "采购合同备案");
		map.put(DERIVATION_CONTRACT, "衍生合同备案");
		map.put(OUTLINE_AGREEMENT, "框架协议备案");
		map.put(ORDER_PROTOCOL_CHANGER, "销售合同变更协议备案");
		map.put(PURCHASE_PROTOCOL_CHANGER, "采购合同变更协议备案");
		map.put(DERIVATION_CONTRACT_CHANGE, "衍生合同变更备案");
		return map;
	}

	/**
	 * 销售/采购合同备案下拉
	 */
	public static Map<String, String> getRecordFilingTypeOfContractMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(ORDER_CONTRACT, "销售合同备案");
		map.put(PURCHASE_CONTRACT, "采购合同备案");
		return map;
	}

	/** 开户行省市 **/
	public static final String AREA = "AREA";
	public static final String ProvinceAndCity = "ProvinceAndCity";

	// 开户行所在省
	public static Map<String, String> getRemitProvinceMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取所属区域下省市中的省份
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(AREA, ProvinceAndCity);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}

	// 开户行所在市
	public static Map<String, String> getRemitCityMap(String remitProvinceId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (remitProvinceId.length() > 0) {
			// 取所属区域下省市中的地级市
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(AREA, remitProvinceId);
			if (list != null && list.size() > 0) {
				for (IComCodeTableItem item : list) {
					map.put(item.getItemId(), item.getDisplayName());
				}
			}
		}
		return map;
	}

	/** 项目所属业务部门（六个事业部） **/
	public static final String TPC_BUSINESS_DEPTMENT = "TPC_BUSINESS_DEPTMENT";

	public static Map<String, String> getProjectDeptMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“贸易项目注册项目部门”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(TPC_BUSINESS_DEPTMENT);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 部门ID+部门名称
				map.put(CommonUtil.trim(item.getExtField1()), CommonUtil.trim(item.getDisplayName()));
			}
		}
		return map;
	}

	/** 注册客户/供应商等所属区域 **/
	public static final String INTERNAL = "INTERNAL";// 国内
	public static final String FOREIGN = "FOREIGN";// 国外

	// 所属地区
	public static Map<String, String> getAreaMap(boolean isForeign) {
		String areaCode = isForeign ? FOREIGN : INTERNAL;
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取国内/国外下地区
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(AREA, areaCode);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}

	// 国家/省份
	public static Map<String, String> getAreaItemMap(String areaId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (CommonUtil.trim(areaId).length() > 0) {
			// 取地区下国家/省份
			List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(AREA, areaId);
			if (list != null && list.size() > 0) {
				for (IComCodeTableItem item : list) {
					map.put(item.getItemId(), item.getDisplayName());
				}
			}
		}
		return map;
	}

	/** 预算项 **/
	/**
	 * 已经作废
	 * @author 田森
	 */
	public static final String TPC_BUDGET_ITEM = "TPC_BUDGET_ITEM";

	public static Map<String, String> getBudgetItemMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(TPC_BUDGET_ITEM);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 预算项id+预算项名称
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	
	/**
	 * 获取预算项目
	 * @author 田森
	 */
	//销售合同合计(用于销售合同),数据来源码表。
	public static final String TPC_BUDGET_SaleContract_ITEM = "TPC_BENEFIT_SUMMARY_SALE_TOTAL";
	//采购合同合计(用于采购合同),数据来源码表。
	public static final String TPC_BUDGET_SubContract_ITEM = "TPC_BENEFIT_SUMMARY_PURCHASE_TOTAL";
	//费用合计(用于费用支付和衍生合同),数据来源码表。
	public static final String TPC_BUDGET_COST_ITEM = "TPC_BENEFIT_SUMMARY_EXPENSE_TOTAL";

	/**
	 * 获取销售合同合计/采购合同合计/费用合计下级预算项
	 * @param budgetCodeTableName
	 * @return
	 */
	public static Map<String, String> getBudgetItems(String budgetCodeTableName) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(budgetCodeTableName);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 预算项id+预算项名称
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	
	/** 采购类别 **/
	public static final String EQUIPMENT_AND_MATERIALS = "EQUIPMENT_AND_MATERIALS";
	public static final String CONSTRUCTION_SUBCONTRACT = "CONSTRUCTION_SUBCONTRACT";
	public static final String SERVICE_CONTRACT = "SERVICE_CONTRACT";
	public static final String EQUIPMENT_AND_MATERIALS_DESC = "设备/材料";
	public static final String CONSTRUCTION_SUBCONTRACT_DESC = "施工分包";
	public static final String SERVICE_CONTRACT_DESC = "服务";
	
	/**
	 * 获取采购类别Map
	 * @return
	 */
	public static Map<String, String> getPurchaseTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(EQUIPMENT_AND_MATERIALS, EQUIPMENT_AND_MATERIALS_DESC);
		map.put(CONSTRUCTION_SUBCONTRACT, CONSTRUCTION_SUBCONTRACT_DESC);
		map.put(SERVICE_CONTRACT, SERVICE_CONTRACT_DESC);
		return map;
	}

}
