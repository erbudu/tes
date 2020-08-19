package com.supporter.prj.cneec.tpc.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: ColModelUtil
 * @Description: 前台展示JSON格式数据工具类
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
public class ColModelUtil {

	/** ColModel **/
	public static final String _label = "label";
	public static final String _name = "name";
	public static final String _align = "align";
	public static final String _width = "width";
	public static final String _sortable = "sortable";
	public static final String _key = "key";
	public static final String _hidden = "hidden";

	/** GroupTitle **/
	public static final String _startColumnName = "startColumnName";
	public static final String _numberOfColumns = "numberOfColumns";
	public static final String _titleText = "titleText";

	/**
	 * 主键
	 * @param name
	 * @return
	 */
	public static Map<String, Object> getColModelKeyData(String name) {
		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put(_label, "ID");
		jsonMap.put(_name, name);
		jsonMap.put(_key, true);
		jsonMap.put(_hidden, true);
		return jsonMap;
	}

	/**
	 * 普通字段
	 * @param label
	 * @param name
	 * @param align
	 * @param width
	 * @param sortable
	 * @return
	 */
	public static Map<String, Object> getColModelData(String label, String name, String align, Integer width, Boolean sortable) {
		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put(_label, label);
		jsonMap.put(_name, name);
		if (align != null) {
			jsonMap.put(_align, align);
		}
		if (width != null) {
			jsonMap.put(_width, width);
		}
		jsonMap.put(_sortable, sortable != null ? sortable : false);
		return jsonMap;
	}

	/**
	 * 普通字段
	 * @param label
	 * @param name
	 * @param align
	 * @param width
	 * @return
	 */
	public static Map<String, Object> getColModelData(String label, String name, String align, Integer width) {
		return getColModelData(label, name, align, width, false);
	}

	/**
	 * 分组标题
	 * @param startColumnName
	 * @param numberOfColumns
	 * @param titleText
	 * @return
	 */
	public static Map<String, Object> getGroupTitleData(String startColumnName, Integer numberOfColumns, String titleText) {
		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put(_startColumnName, startColumnName);
		jsonMap.put(_numberOfColumns, numberOfColumns);
		jsonMap.put(_titleText, titleText);
		return jsonMap;
	}

}
