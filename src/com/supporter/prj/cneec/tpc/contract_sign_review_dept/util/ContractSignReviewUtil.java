package com.supporter.prj.cneec.tpc.contract_sign_review_dept.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.supporter.prj.cneec.tpc.register_project.util.RegisterProjectConstant;

/**
 * @Title: ContractSignDeptReviewConstant
 * @Description: 评审意见常量
 * @author: yanweichao
 * @date: 2017-10-9
 * @version: V1.0
 */
public class ContractSignReviewUtil {

	/** 评审类型 **/
	public static final int REVIEW_TYPE_ORDER = 10;
	public static final int REVIEW_TYPE_CONTRACT = 20;
	public static final int REVIEW_TYPE_SUBCONTRACT = 30;

	public static Map<Integer, String> getReviewTypeMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(REVIEW_TYPE_ORDER, "销售合同签约前评审");
		map.put(REVIEW_TYPE_CONTRACT, "采购合同签约前评审");
		map.put(REVIEW_TYPE_SUBCONTRACT, "销售+采购合同签约前评审");
		return map;
	}

	/** 评审从表信息类型 **/
	public static final int INFOR_TYPE_ORDER = 10;
	public static final int INFOR_TYPE_CONTRACT = 20;

	public static Map<Integer, String> getInforTypeMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(INFOR_TYPE_ORDER, "销售合同");
		map.put(INFOR_TYPE_CONTRACT, "采购合同");
		return map;
	}

	/** 是否有框架协议 **/
	public static final String FRAMEWORK_AGREEMENT_YES = "true";
	public static final String FRAMEWORK_AGREEMENT_NO = "false";

	public static Map<String, String> getFrameworkAgreementMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(FRAMEWORK_AGREEMENT_YES, "是");
		map.put(FRAMEWORK_AGREEMENT_NO, "否");
		return map;
	}

	/** 评审类别 **/
	public static final String REVIEW_AGENT_NET = "REVIEW_AGENT_NET";// 代理网审(仅事业部)
	public static final String REVIEW_AGENT_MEET = "REVIEW_AGENT_MEET";// 代理会审(仅事业部)
	public static final String REVIEW_SELF_SUPPORT_XIAO_NET = "REVIEW_SELF_SUPPORT_XIAO_NET";// 自营小额网审(仅事业部)
	public static final String REVIEW_SELF_SUPPORT_XIAO_MEET = "REVIEW_SELF_SUPPORT_XIAO_MEET";// 自营小额会审(仅事业部)
	public static final String REVIEW_SELF_SUPPORT_DA = "REVIEW_SELF_SUPPORT_DA";// 自营大额网审(事业部及公司)
	public static final String REVIEW_SELF_SUPPORT_SUPER = "REVIEW_SELF_SUPPORT_SUPER";// 自营超大额会审(事业部及公司)

	public static Map<String, String> getReviewClassificationMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(REVIEW_AGENT_NET, "代理网审(仅事业部)");
		map.put(REVIEW_AGENT_MEET, "代理会审(仅事业部)");
		map.put(REVIEW_SELF_SUPPORT_XIAO_NET, "自营小额网审(仅事业部)");
		map.put(REVIEW_SELF_SUPPORT_XIAO_MEET, "自营小额会审(仅事业部)");
		map.put(REVIEW_SELF_SUPPORT_DA, "自营大额网审(事业部及公司)");
		map.put(REVIEW_SELF_SUPPORT_SUPER, "自营超大额会审(事业部及公司)");
		return map;
	}

	public static Map<String, String> getReviewClassMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(REVIEW_AGENT_NET, "事业部评审(代理网审)");
		map.put(REVIEW_AGENT_MEET, "事业部评审(代理会审)");
		map.put(REVIEW_SELF_SUPPORT_XIAO_NET, "事业部评审(自营小额网审)");
		map.put(REVIEW_SELF_SUPPORT_XIAO_MEET, "事业部评审(自营小额会审)");
		map.put(REVIEW_SELF_SUPPORT_DA, "事业部+公司评审(自营大额网审)");
		map.put(REVIEW_SELF_SUPPORT_SUPER, "事业部+公司评审(自营超大额会审)");
		return map;
	}

	//事业部总经理、法律事务部、资产财务部、经营管理部、储运部、公司主管总经理、公司总经理
	/** 评审意见人员 **/
	public static final int OPINION_MAN1 = 101;
	public static final int OPINION_MAN2 = 102;
	public static final int OPINION_MAN3 = 103;
	public static final int OPINION_MAN4 = 104;
	public static final int OPINION_MAN5 = 105;
	public static final int OPINION_MAN6 = 106;
	public static final int OPINION_MAN7 = 107;

	public static Map<Integer, String> getOpinionManMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(OPINION_MAN1, "事业部总经理");
		map.put(OPINION_MAN2, "法律事务部");
		map.put(OPINION_MAN3, "资产财务部");
		map.put(OPINION_MAN4, "经营管理部");
		map.put(OPINION_MAN5, "储运部");
		map.put(OPINION_MAN6, "公司主管总经理");
		map.put(OPINION_MAN7, "公司总经理");
		return map;
	}

	/**
	 * 
	 * @param classificationId
	 *            项目类型
	 * @param usdAmount
	 *            折合美元金额
	 * @return
	 */
	public static String getReviewClassification(String classificationId, double usdAmount) {
		if (classificationId.length() > 0 && usdAmount > 0) {
			if (classificationId.equals(RegisterProjectConstant.SELF_SUPPORT)) {// 自营贸易
				// A自营
				if (usdAmount < 3000000) {
					// 1.自营小于300万美元：小额、分情况网审或会审
					if (usdAmount < 2000000) {
						// a).小于200万美元：网审
						return ContractSignReviewUtil.REVIEW_SELF_SUPPORT_XIAO_NET;
					} else {
						// b).不小于200万美元：会审
						return ContractSignReviewUtil.REVIEW_SELF_SUPPORT_XIAO_MEET;
					}
				} else if (usdAmount >= 10000000) {
					// 2.自营不小于1000万美元：超大额、会审
					return ContractSignReviewUtil.REVIEW_SELF_SUPPORT_SUPER;
				} else {
					// 3.自营不小于300万美元小于1000万美元：大额、网审
					return ContractSignReviewUtil.REVIEW_SELF_SUPPORT_DA;
				}
			} else if (classificationId.equals(RegisterProjectConstant.AGENT)) {// 代理贸易
				// B代理
				if (usdAmount < 20000000) {
					// 1.?<2000万美元：网审
					return ContractSignReviewUtil.REVIEW_AGENT_NET;
				} else {
					// 2.?>=2000万美元：会审
					return ContractSignReviewUtil.REVIEW_AGENT_MEET;
				}
			}
		}
		return "";
	}

}
