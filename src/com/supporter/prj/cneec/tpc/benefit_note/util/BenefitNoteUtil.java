package com.supporter.prj.cneec.tpc.benefit_note.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.supporter.prj.cneec.tpc.benefit_note.constant.BenefitNoteConstant;

/**
 * @Title: BenefitNoteUtil
 * @Description: 贸易效益测算变更记录表工具
 * @author: yanweichao
 * @date: 2018-06-11
 * @version: V1.0
 */
public class BenefitNoteUtil {

	/**
	 * 获取版本
	 * 
	 * @return
	 */
	public static Map<String, String> getVersionsTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(BenefitNoteConstant.VERSIONS_OF_DEPT_REVIEW, "事业部版");
		map.put(BenefitNoteConstant.VERSIONS_OF_COMPANY_REVIEW, "公司版");
		return map;
	}

}
