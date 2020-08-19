package com.supporter.prj.cneec.tpc.trade_homepage.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: PagePathTypeEnum
 * @Description: 贸易导航常量
 * @author: yanweichao
 * @date: 2018-1-26
 * @version: V1.0
 */
public class TradeHomepageConstant {

	// 初始化菜单路径
	public static final String TRADE_HOMEPAGE_PAGE_PATH = "/tpc/trade_homepage/data/DefaultTradeHomepage.json";

	// 下级菜单展示样式（1：分组显示，2：平铺显示，3：仅显示直接下级一列，4：仅显示直接下级两列，5：显示下两级一列，6：显示两级两列）
	public static final int GROUP = 1;
	public static final int TILE = 2;
	public static final int ONE_ROW = 3;
	public static final int TWO_ROW = 4;
	public static final int GROUP_ONE_ROW = 5;
	public static final int GROUP_TWO_ROW = 6;

	// 菜单级别
	public static final int MENU_LEVEL_ONE = 1;
	public static final int MENU_LEVEL_TWO = 2;
	public static final int MENU_LEVEL_THREE = 3;

	// 菜单打开方式（0：当前窗口iframe内嵌打开，1：新tab标签页打开）
	public static final int OPEN_MODE_CURRENT = 0;
	public static final int OPEN_MODE_TAB = 1;

	public static final String DEFAULT_ICON_PATH = "/tpc/trade_homepage/img/menu_icon.png";
	public static final int DEFAULT_DISPLAY_ORDER = 10;

	// 一级菜单
	public static final String oneLevelId1 = "PROTOCOL";// 框架协议审批
	public static final String oneLevelId2 = "PURCHASE_INTENTION";// 客户采购意向
	public static final String oneLevelId3 = "EXTERNAL_REVIEW";// 对外报价评审
	public static final String oneLevelId4 = "REVIEW_AND_SIGN";// 合同评审及签约
	public static final String oneLevelId5 = "CONTRACT_CHANGE";// 合同变更调整
	public static final String oneLevelId6 = "CONTRACT_PERFORM";// 合同履行

	public static Map<String, String> getOneLevelMenuMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(oneLevelId1, "框架协议审批");
		map.put(oneLevelId2, "客户采购意向");
		map.put(oneLevelId3, "对外报价评审");
		map.put(oneLevelId4, "合同评审及签约");
		map.put(oneLevelId5, "合同变更调整");
		map.put(oneLevelId6, "合同履行");
		return map;
	}

	// 二级菜单（事业部评审）
	public static final String twoLevelId_EXTERNAL = "EXTERNAL_QUOTATION_REVIEW_DEPT";// 对外报价事业部评审
	public static final String twoLevelId_CONTRACT = "CONTRACT_SIGN_REVIEW_DEPT";// 合同签约前事业部评审

	public static Map<String, String> getTwoLevelMenuOfReviewMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(twoLevelId_EXTERNAL, "事业部评审");
		map.put(twoLevelId_CONTRACT, "事业部评审");
		return map;
	}

}
