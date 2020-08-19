package com.supporter.prj.cneec.tpc.trade_homepage.constant;

/**
 * @Title: LowerStyleEnum
 * @Description: 下级菜单展示样式
 * @author: yanweichao
 * @date: 2018-1-26
 * @version: V1.0
 */
public enum LowerStyleEnum {

	GROUP(1, "分组显示"), TILE(2, "平铺显示"), ONE_ROW(3, "仅显示直接下级一列"), TWO_ROW(4, "仅显示直接下级两列"), GROUP_ONE_ROW(5, "显示下两级一列"), GROUP_TWO_ROW(6, "显示两级两列");

	private int attrInnerName;
	private String attrDisplayName;

	private LowerStyleEnum(int attrInnerName, String attrDisplayName) {
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
