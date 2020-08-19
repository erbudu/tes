package com.supporter.prj.pm.constant;

/**
 * @Title: PmSwfConstant
 * @Description: 现场项目流程定义常量
 * @author Yanweichao
 * @date 2018年12月12日
 * @version: V1.0
 */
public interface PmSwfConstant {

	/** 流程 **/
	public static final String PM_SWF_PROC_INNER_NAME = "CNEEC_SPOT_EXAM_PROC"; // 现场通用流程

	public static final String PM_SWF_PROC_INNER_NAME_PAYMENT_1 = "CNEEC_SPOT_PAY_1"; // 付款流程-业务项下付款
	public static final String PM_SWF_PROC_INNER_NAME_PAYMENT_2 = "CNEEC_SPOT_PAY_2"; // 付款流程-费用支出、非银行货币兑换

	public static final String PM_SWF_PROC_INNER_NAME_FUND = "CNEEC_SPOT_FUND"; // 资金拨付
	public static final String PM_SWF_PROC_INNER_NAME_VISA = "CNEEC_SPOT_VISA"; // 签证
	public static final String PM_SWF_PROC_INNER_NAME_VISA_1 = "CNEEC_SPOT_VISA_1"; // 签证_重大
	public static final String PM_SWF_PROC_INNER_NAME_VISA_2 = "CNEEC_SPOT_VISA_2"; // 签证_较大
	public static final String PM_SWF_PROC_INNER_NAME_BALANCE = "CNEEC_SPOT_BALANCE"; // 结算
	
	/** 角色 **/
	public static final String PM_SWF_ROLE_CREATOR = "CNEEC_SPOT_CREATOR"; // 现场流程默认提交人角色
	public static final String PM_SWF_ROLE_CREATOR_PRJ = "CNEEC_SPOT_PRJ"; // 资金拨付提交人角色

}
