package com.supporter.prj.cneec.tpc.order_change.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: OrderChangeConstant
 * @Description: 销售合同变更常量类
 */
public class OrderChangeConstant {

	/** 调整性质  **/
	public static final int ADJUSTMENT_NATURE01 = 101;
	public static final int ADJUSTMENT_NATURE02 = 102;
	public static final int ADJUSTMENT_NATURE03 = 103;
	public static final int ADJUSTMENT_NATURE04 = 104;
	public static final int ADJUSTMENT_NATURE05 = 105;
	public static final int ADJUSTMENT_NATURE06 = 106;
	public static final int ADJUSTMENT_NATURE07 = 107;
	public static final int ADJUSTMENT_NATURE08 = 108;
	public static final int ADJUSTMENT_NATURE09 = 109;
	public static final int ADJUSTMENT_NATURE10 = 110;
	public static final int ADJUSTMENT_NATURE11 = 111;
	public static final int ADJUSTMENT_NATURE12 = 112;
	

	public static final int ADJUSTMENT_NATURE13 = 113;
	public static final int ADJUSTMENT_NATURE14 = 114;

	public static Map<Integer, String> getAdjustmentNatureMap(int chType) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(chType==1) {
			map.put(ADJUSTMENT_NATURE01, "单价变更");
			map.put(ADJUSTMENT_NATURE02, "总价变更");
			map.put(ADJUSTMENT_NATURE03, "合同暂估总金额变更");
			map.put(ADJUSTMENT_NATURE04, "期中款项结算方式变更");
			map.put(ADJUSTMENT_NATURE05, "合同范围变更");
			map.put(ADJUSTMENT_NATURE06, "交付时间变更");
			map.put(ADJUSTMENT_NATURE07, "责任条款变更");
			map.put(ADJUSTMENT_NATURE08, "服务期限延长或缩短");
			map.put(ADJUSTMENT_NATURE09, "供货内容增减");
			map.put(ADJUSTMENT_NATURE10, "合同终止");
			map.put(ADJUSTMENT_NATURE11, "其它变更");
	}else if(chType==2) {
		map.put(ADJUSTMENT_NATURE12, "合同暂估总金额调整");
		map.put(ADJUSTMENT_NATURE13, "供货内容少量增减");
		map.put(ADJUSTMENT_NATURE14, "其它");
	 }else {
		 map.put(ADJUSTMENT_NATURE01, "单价变更");
			map.put(ADJUSTMENT_NATURE02, "总价变更");
			map.put(ADJUSTMENT_NATURE03, "合同暂估总金额变更");
			map.put(ADJUSTMENT_NATURE04, "期中款项结算方式变更");
			map.put(ADJUSTMENT_NATURE05, "合同范围变更");
			map.put(ADJUSTMENT_NATURE06, "交付时间变更");
			map.put(ADJUSTMENT_NATURE07, "责任条款变更");
			map.put(ADJUSTMENT_NATURE08, "服务期限延长或缩短");
			map.put(ADJUSTMENT_NATURE09, "供货内容增减");
			map.put(ADJUSTMENT_NATURE10, "合同终止");
			map.put(ADJUSTMENT_NATURE11, "其它变更");
			
			map.put(ADJUSTMENT_NATURE12, "合同暂估总金额调整");
			map.put(ADJUSTMENT_NATURE13, "供货内容少量增减");
			map.put(ADJUSTMENT_NATURE14, "其它");
	 }
		return map;
	}

	/** 变更方式 **/
	public static final String CHANGE_MODE1 = "CHANGE_AGREEMENT";
	public static final String CHANGE_MODE2 = "SUPPLEMENTARY_AGREEMENT";
	public static final String CHANGE_MODE3 = "CONFIRMATION_LETTER";
	public static final String CHANGE_MODE4 = "OTHER";
	public static final String CHANGE_MODE5 = "OTHERS";
	
	public static Map<String, String> getChangeModeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(CHANGE_MODE1, "变更协议");
		map.put(CHANGE_MODE2, "补充协议");
		map.put(CHANGE_MODE3, "确认函");
		map.put(CHANGE_MODE4, "其他电文");
		map.put(CHANGE_MODE5, "其他");
		return map;
	}

	/** 增减类型 **/
	public static final String CHANGE_TYPE1 = "UNCHANGE";
	public static final String CHANGE_TYPE2 = "ADD";
	public static final String CHANGE_TYPE3 = "CUT";

	public static Map<String, String> getChangeTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(CHANGE_TYPE1, "不变");
		map.put(CHANGE_TYPE2, "增加");
		map.put(CHANGE_TYPE3, "减少");
		return map;
	}

	/** 是否有协议 **/
	public static final String HAS_PROTOCOL_YES = "true";
	public static final String HAS_PROTOCOL_NO = "false";

	public static Map<String, String> getHasProtocolMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(HAS_PROTOCOL_YES, "有");
		map.put(HAS_PROTOCOL_NO, "无");
		return map;
	}

}
