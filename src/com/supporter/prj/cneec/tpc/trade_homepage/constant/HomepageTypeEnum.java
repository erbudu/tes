package com.supporter.prj.cneec.tpc.trade_homepage.constant;

/**
 * @Title: HomepageTypeEnum
 * @Description: 菜单类型控制
 * @author: yanweichao
 * @date: 2018-1-26
 * @version: V1.0
 */
public enum HomepageTypeEnum {

	// 一级菜单
	ALL(0, "无（全部适用）"), AGREEMENT(1, "有框架协议"), NO_AGREEMENT(2, "无框架协议"),
	// 二级菜单
	REVIEW_ALL(0, "无（全部适用）"), REVIEW_BUSINESS(1, "事业部"), REVIEW_COMPANY(2, "公司");

	private int attrInnerName;
	private String attrDisplayName;

	private HomepageTypeEnum(int attrInnerName, String attrDisplayName) {
		this.attrInnerName = attrInnerName;
		this.attrDisplayName = attrDisplayName;
	}

	public int getAttrInnerName() {
		return this.attrInnerName;
	}

	public void setAttrInnerName(int attrInnerName) {
		this.attrInnerName = attrInnerName;
	}

	public String getAttrDisplayName() {
		return this.attrDisplayName;
	}

	public void setAttrDisplayName(String attrDisplayName) {
		this.attrDisplayName = attrDisplayName;
	}

}
