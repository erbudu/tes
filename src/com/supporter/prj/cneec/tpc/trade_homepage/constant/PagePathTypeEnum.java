package com.supporter.prj.cneec.tpc.trade_homepage.constant;

/**
 * @Title: PagePathTypeEnum
 * @Description: 页面路径来源类
 * @author: yanweichao
 * @date: 2018-1-26
 * @version: V1.0
 */
public enum PagePathTypeEnum {

	GROUP_TITLE(0, "无（仅作为分组标题显示）"), FROM_MODULE(1, "来自功能模块的导航项"), NAVIGATION_PAGE(2, "根据下级自动生成导航页"), URL(3, "URL");

	private int attrInnerName;
	private String attrDisplayName;

	private PagePathTypeEnum(int attrInnerName, String attrDisplayName) {
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
