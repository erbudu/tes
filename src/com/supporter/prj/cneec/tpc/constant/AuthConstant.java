package com.supporter.prj.cneec.tpc.constant;

/**
 * @Title: AuthConstant
 * @Description: 权限项清单
 * @author: yanweichao
 * @date: 2017-11-25
 * @version: V1.0
 */
public abstract interface AuthConstant {

	/** 菜单显示授权  **/
	public static final String AUTH_OPER_NAME_MENU = "MENUAUTH";
	/** 显示数据授权  **/
	public static final String AUTH_OPER_NAME_DATASHOW = "DATASHOWAUTH";
	/** 数据维护授权  **/
	public static final String AUTH_OPER_NAME_SETVAL = "SETVALAUTH";
	/** 审核发布授权  **/
	public static final String AUTH_OPER_NAME_AUDITPUBLISH = "AUDITPUBLISHAUTH";
	/** 其他限定授权  **/
	public static final String AUTH_OPER_NAME_LIMIT = "LIMITAUTH";

}
