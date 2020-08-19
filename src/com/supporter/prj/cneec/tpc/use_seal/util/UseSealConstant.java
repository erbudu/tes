package com.supporter.prj.cneec.tpc.use_seal.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class UseSealConstant {

	/** 是否已备案 **/
	public static final String FILING_YES = "true";
	public static final String FILING_NO = "false";

	public static Map<String, String> getFilingMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(FILING_YES, "是");
		map.put(FILING_NO, "否");
		return map;
	}

}
