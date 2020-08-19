package com.supporter.prj.cneec.tpc.contract_refund.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: ContractRefundConstant
 * @Description: 销售合同退款常量
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
public class ContractRefundConstant {

	/** 境内外退款 **/
	public static final String NOT_Abroad = "false";
	public static final String IS_Abroad = "true";

	public static Map<String, String> getIsAbroadMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(NOT_Abroad, "境内");
		map.put(IS_Abroad, "境外");
		return map;
	}

}
