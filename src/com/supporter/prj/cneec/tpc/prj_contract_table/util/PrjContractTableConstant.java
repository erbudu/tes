package com.supporter.prj.cneec.tpc.prj_contract_table.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class PrjContractTableConstant {

	public static final String SALE = "SALE"; // 销售合同
	public static final String PURCHASE = "PURCHASE";// 采购合同
	public static final String DERIVATIVE = "DERIVATIVE";// 衍生合同

	/**
	 * 获取合同类别码表.
	 * @return
	 */
	public static Map<String, String> getContractTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(SALE, "销售合同");
		map.put(PURCHASE, "采购合同");
		map.put(DERIVATIVE, "衍生合同");
		return map;
	}

	public static final String EFFECT = "EFFECT"; // 有效
	public static final String INVALID = "INVALID";// 无效
	public static final String CHANGE = "CHANGE";// 无效

	/**
	 * 获取合同类别码表.
	 * @return
	 */
	public static Map<String, String> getContractStatusMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(EFFECT, "有效");
		map.put(INVALID, "无效");
		map.put(CHANGE, "变更中");
		return map;
	}

	/** 过滤类型 **/
	public static final String FILTER_BENEFIT_CALCULATION = "BENEFIT_CALCULATION";
	public static final String FILTER_SALE_CONTRACT_COLLECTION = "SALE_CONTRACT_COLLECTION";
	public static final String FILTER_PURCHASE_CONTRACT_PAYMENT = "PURCHASE_CONTRACT_PAYMENT";


}
