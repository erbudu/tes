package com.supporter.prj.cneec.tpc.collection_confirmation.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionConfirmationConstant {

	/** 收款业务 **/
	public static final String MAIN_CONTRACT_COLLECTION = "MAIN_CONTRACT_COLLECTION";
	public static final String ORDER_COLLECTION = "ORDER_COLLECTION";

	public static Map<String, String> getCollectionBusinessMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(MAIN_CONTRACT_COLLECTION, "主合同收款");
		map.put(ORDER_COLLECTION, "销售合同收款");
		return map;
	}

}
