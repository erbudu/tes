package com.supporter.prj.cneec.e2b.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.supporter.prj.cneec.e2b.constant.E2bConstant;

/**
 * @Title: E2bUtil
 * @Description: E2B工具类
 * @author Yanweichao
 * @date 2019年1月24日
 * @version: V1.0
 */
public class E2bUtil {

	/**
	 * 获取银企直连-资金用途Map
	 * @return
	 */
	public static Map<String, String> getCapticalpurposeCodeTable() {
		Map<String, String> ct = new LinkedHashMap<String, String>();
		ct.put(E2bConstant.CapticalpurposeCode.CODE1, E2bConstant.CapticalpurposeCode.CODE1_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE2, E2bConstant.CapticalpurposeCode.CODE2_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE3, E2bConstant.CapticalpurposeCode.CODE3_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE4, E2bConstant.CapticalpurposeCode.CODE4_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE5, E2bConstant.CapticalpurposeCode.CODE5_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE6, E2bConstant.CapticalpurposeCode.CODE6_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE7, E2bConstant.CapticalpurposeCode.CODE7_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE8, E2bConstant.CapticalpurposeCode.CODE8_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE9, E2bConstant.CapticalpurposeCode.CODE9_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE10, E2bConstant.CapticalpurposeCode.CODE10_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE11, E2bConstant.CapticalpurposeCode.CODE11_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE12, E2bConstant.CapticalpurposeCode.CODE12_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE13, E2bConstant.CapticalpurposeCode.CODE13_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE14, E2bConstant.CapticalpurposeCode.CODE14_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE15, E2bConstant.CapticalpurposeCode.CODE15_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE16, E2bConstant.CapticalpurposeCode.CODE16_DESC);
		ct.put(E2bConstant.CapticalpurposeCode.CODE17, E2bConstant.CapticalpurposeCode.CODE17_DESC);
		return ct;
	}

}
