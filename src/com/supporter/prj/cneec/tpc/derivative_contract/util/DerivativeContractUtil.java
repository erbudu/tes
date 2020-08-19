package com.supporter.prj.cneec.tpc.derivative_contract.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: DerivativeContractUtil
 * @Description: 衍生合同记录单工具类
 * @author: yanweichao
 * @date: 2018-5-21
 * @version: V1.0
 */
public class DerivativeContractUtil {

	/** 合同付款类型(费用合计下预算项) **/
	public static final String TRANSPORTATION = "TPC_BENEFIT_SUMMARY_TRANSPORTATION"; // 运输
	public static final String CONSULTATION = "TPC_BENEFIT_SUMMARY_CONSULTATION"; // 咨询
	public static final String SUPERVISION = "TPC_BENEFIT_SUMMARY_SUPERVISION"; // 监理费
	public static final String COMMISSION_OR_AGENCY = "TPC_BENEFIT_SUMMARY_COMMISSION_OR_AGENCY"; // 佣金或代理费

	/**
	 * 合同付款类型
	 * @return
	 */
	public static Map<String, String> getPaymentTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(TRANSPORTATION, "运输");
		map.put(CONSULTATION, "咨询");
		map.put(SUPERVISION, "监理");
		map.put(COMMISSION_OR_AGENCY, "佣金或代理");
		return map;
	}

}
