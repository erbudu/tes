package com.supporter.prj.cneec.tpc.purchase_demand.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class PurchaseDemandConstant {

	/** 对外报价评审类别 **/
	public static final String REVIEW_AGENT_NO = "REVIEW_AGENT_NO";// 代理不评审
	public static final String REVIEW_AGENT_YES = "REVIEW_AGENT_YES";// 代理评审
	public static final String REVIEW_SELF_SUPPORT_XIAO_NO = "REVIEW_SELF_SUPPORT_XIAO_NO";// 自营小额不评审
	public static final String REVIEW_SELF_SUPPORT_XIAO_YES = "REVIEW_SELF_SUPPORT_XIAO_YES";// 自营小额评审（仅事业部）
	public static final String REVIEW_SELF_SUPPORT_DA = "REVIEW_SELF_SUPPORT_DA";// 自营大额评审（事业部及公司）
	public static final String REVIEW_SELF_SUPPORT_SUPER = "REVIEW_SELF_SUPPORT_SUPER";// 自营超大额会审

	public static Map<String, String> getReviewClassificationMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(REVIEW_AGENT_NO, "代理贸易-不评审");
		map.put(REVIEW_AGENT_YES, "代理贸易-评审");
		map.put(REVIEW_SELF_SUPPORT_XIAO_NO, "自营贸易-小额-不评审");
		map.put(REVIEW_SELF_SUPPORT_XIAO_YES, "自营贸易-小额-事业部评审");
		map.put(REVIEW_SELF_SUPPORT_DA, "自营贸易-大额-事业部及公司评审");
		map.put(REVIEW_SELF_SUPPORT_SUPER, "自营贸易-超大额-会审");
		return map;
	}

	/** 合同签约前评审类别 **/
	public static final String CONTRACT_SIGN_REVIEW_DEPT = "CONTRACT_SIGN_REVIEW_DEPT";
	public static final String CONTRACT_SIGN_REVIEW = "CONTRACT_SIGN_REVIEW";

	public static Map<String, String> getContractSignReviewMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(CONTRACT_SIGN_REVIEW_DEPT, "合同签约前事业部评审");
		map.put(CONTRACT_SIGN_REVIEW, "合同签约前公司评审");
		return map;
	}

}
