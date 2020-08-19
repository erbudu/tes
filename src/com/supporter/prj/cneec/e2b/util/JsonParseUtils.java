package com.supporter.prj.cneec.e2b.util;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.util.CodeTable;
import com.supporter.util.DateJsonValueProcessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.lang.StringUtils;

public class JsonParseUtils {

	@SuppressWarnings("rawtypes")
	public static String getJsonFromMap(Map map) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.setIgnoreDefaultExcludes(true);

		JSONArray jsonArray = JSONArray.fromObject(map, jsonConfig);

		String jsonData = jsonArray.toString();
		jsonData = StringUtils.remove(jsonData, "[");
		jsonData = StringUtils.remove(jsonData, "]");

		return jsonData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getJsonFromCodeTable(CodeTable codeTable) {
		HashMap<String, Object> jsonMap = new HashMap();
		List list = new ArrayList();
		if ((codeTable != null) && (codeTable.size() > 0)) {
			Iterator iterator = codeTable.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				HashMap<String, String> categoryMap = new HashMap();

				categoryMap.put("value", key);
				categoryMap.put("name", codeTable.get(key).toString());

				list.add(categoryMap);
			}
			jsonMap.put("records", Integer.valueOf(codeTable.size()));
			jsonMap.put("page", Integer.valueOf(1));
			jsonMap.put("total", Integer.valueOf(codeTable.size()));
			jsonMap.put("rows", list);
		} else {
			jsonMap.put("records", Integer.valueOf(0));
			jsonMap.put("page", Integer.valueOf(1));
			jsonMap.put("total", Integer.valueOf(0));
			jsonMap.put("rows", list);
		}
		String jsonData = JSONArray.fromObject(jsonMap).toString();
		jsonData = jsonData.substring(1, jsonData.length() - 1);

		return jsonData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getJsonFromComCodeTableItem(List<IComCodeTableItem> itemList, String prjStageId) {
		HashMap<String, Object> jsonMap = new HashMap();
		List list = new ArrayList();
		if ((itemList != null) && (itemList.size() > 0)) {
			for (IComCodeTableItem item : itemList) {
				HashMap<String, Object> itemMap = new HashMap();
				itemMap.put("id", item.getItemId());
				itemMap.put("value", item.getItemId());
				itemMap.put("name", item.getItemValue());
				itemMap.put("describe", item.getItemValue());
				itemMap.put("checked", Boolean.valueOf(false));
				list.add(itemMap);
			}
			jsonMap.put("records", Integer.valueOf(itemList.size()));
			jsonMap.put("page", Integer.valueOf(1));
			jsonMap.put("total", Integer.valueOf(itemList.size()));
			jsonMap.put("rows", list);
		} else {
			jsonMap.put("records", Integer.valueOf(0));
			jsonMap.put("page", Integer.valueOf(1));
			jsonMap.put("total", Integer.valueOf(0));
			jsonMap.put("rows", list);
		}
		String jsonData = JSONArray.fromObject(jsonMap).toString();
		jsonData = jsonData.substring(1, jsonData.length() - 1);

		return jsonData;
	}

	@SuppressWarnings("rawtypes")
	public static String getJsonFromListPage(ListPage listPage) {
		List recs = null;
		long count = 0L;
		long pageNo = 0L;
		long pageSize = 0L;
		if (listPage != null) {
			recs = listPage.list();
			count = listPage.getRowCount();
			pageNo = listPage.getPageNo();
			pageSize = listPage.getPageCount();
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(recs, jsonConfig);
		String jsonString = "{\"records\":\"" + count + "\", \"page\":" + pageNo + ", \"total\":" + pageSize + ", \"rows\":" + jsonArray + "}";
		return jsonString;
	}

}
