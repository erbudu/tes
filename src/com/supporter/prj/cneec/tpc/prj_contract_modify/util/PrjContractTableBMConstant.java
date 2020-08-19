package com.supporter.prj.cneec.tpc.prj_contract_modify.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class PrjContractTableBMConstant {

	public static final String SALE = "SALE"; // 销售合同
	public static final String PURCHASE = "PURCHASE";// 采购合同

	/**
	 * 获取合同类别码表.
	 * @return
	 */
	public static Map<String, String> getContractTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(SALE, "销售合同");
		map.put(PURCHASE, "采购合同");
		return map;
	}

	public static final String EFFECT = "EFFECT"; // 有效
	public static final String INVALID = "INVALID";// 无效

	/**
	 * 获取合同类别码表.
	 * @return
	 */
	public static Map<String, String> getContractStatusMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(EFFECT, "有效");
		map.put(INVALID, "无效");
		return map;
	}

}
