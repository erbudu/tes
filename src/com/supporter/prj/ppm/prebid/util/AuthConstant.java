package com.supporter.prj.ppm.prebid.util;

/**
 * 
 * @ClassName: AuthConstant 
 * @Description: 权限项清单
 * @author: 王康
 * @date: 2019年12月4日
 * @最后修改人: 王康
 * @最后修改时间: 2019年12月4日
 */
public abstract interface AuthConstant {

	/** 菜单显示授权  **/
	public static final String AUTH_OPER_NAME_MENU = "MENUAUTH";
	/** 显示数据授权  **/
	public static final String AUTH_OPER_NAME_DATASHOW = "DATASHOWAUTH";
	/** 显示明细数据授权  **/
	public static final String AUTH_OPER_NAME_DATASHOW_DETAIL = "DATASHOWAUTHDETAIL";
	/** 项目编号显示数据授权  **/
	public static final String AUTH_OPER_NAME_DATASHOW_BY_NO = "DATASHOWAUTHBYNO";
	/** 数据维护授权  **/
	public static final String AUTH_OPER_NAME_SETVAL = "SETVALAUTH";
	/** 审核发布授权  **/
	public static final String AUTH_OPER_NAME_AUDITPUBLISH = "AUDITPUBLISHAUTH";
	/** 其他限定授权  **/
	public static final String AUTH_OPER_NAME_LIMIT = "LIMITAUTH";

}
