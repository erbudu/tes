package com.supporter.prj.cneec.tpc.register_project.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: RegisterProjectConstant
 * @Description: 注册项目常量类
 * @author: yanweichao
 * @date: 2017-8-31
 * @version: V1.0
 */
public class RegisterProjectConstant {

	/** 项目类别 **/
	public static final int PROJECT_CATEGORY1 = 101;
	public static final int PROJECT_CATEGORY2 = 102;
	public static final int PROJECT_CATEGORY3 = 103;

	public static Map<Integer, String> getProjectCategoryMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(PROJECT_CATEGORY1, "小额（0~300万美元）");
		map.put(PROJECT_CATEGORY2, "大额（300(含)~1000万美元）");
		map.put(PROJECT_CATEGORY3, "超大额（1000(含)万美元以上）");
		return map;
	}

	/** 采购类型 **/
	public static final int PURCHASE_TYPE1 = 101;
	public static final int PURCHASE_TYPE2 = 102;
	public static final int PURCHASE_TYPE3 = 103;

	public static Map<Integer, String> getPurchaseTypeMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(PURCHASE_TYPE1, "货物");
		map.put(PURCHASE_TYPE2, "服务");
		map.put(PURCHASE_TYPE3, "货物+服务");
		return map;
	}

	/** 项目分类 **/
	public static final String SELF_SUPPORT = "SELF-SUPPORT";
	public static final String AGENT = "AGENT";

	public static Map<String, String> getProjectClassificationMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(SELF_SUPPORT, "自营贸易");
		map.put(AGENT, "代理贸易");
		return map;
	}

	/** 是否有框架协议 **/
	public static final int FRAMEWORK_AGREEMENT_YES = 1;
	public static final int FRAMEWORK_AGREEMENT_NO = 0;

	public static Map<Integer, String> getFrameworkAgreementMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(FRAMEWORK_AGREEMENT_YES, "有");
		map.put(FRAMEWORK_AGREEMENT_NO, "无");
		return map;
	}

	/** 项目性质 **/
	//国内自营、国外自营、进口自营、出口自营、国内代理、国外代理、进口代理、出口代理
	public static final String PROJECT_NATURE1 = "DOMESTIC_SELF-SUPPORT";
	public static final String PROJECT_NATURE2 = "ABROAD_SELF-SUPPORT";
	public static final String PROJECT_NATURE3 = "IMPORTED_SELF-SUPPORT";
	public static final String PROJECT_NATURE4 = "EXIT_SELF-SUPPORT";
	public static final String PROJECT_NATURE5 = "DOMESTIC_AGENT";
	public static final String PROJECT_NATURE6 = "ABROAD_AGENT";
	public static final String PROJECT_NATURE7 = "IMPORTED_AGENT";
	public static final String PROJECT_NATURE8 = "EXIT_AGENT";

	public static Map<String, String> getProjectNatureMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(PROJECT_NATURE1, "国内自营");
		map.put(PROJECT_NATURE2, "国外自营");
		map.put(PROJECT_NATURE3, "进口自营");
		map.put(PROJECT_NATURE4, "出口自营");
		map.put(PROJECT_NATURE5, "国内代理");
		map.put(PROJECT_NATURE6, "国外代理");
		map.put(PROJECT_NATURE7, "进口代理");
		map.put(PROJECT_NATURE8, "出口代理");
		return map;
	}

}
