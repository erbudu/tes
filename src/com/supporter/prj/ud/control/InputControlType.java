package com.supporter.prj.ud.control;

import java.util.LinkedHashMap;
import java.util.Map;

import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;

public class InputControlType {
	public static String TEXTINPUT = "TEXTINPUT";
	public static final String NUMBERINPUT = "NUMBERINPUT";
	public static String COMBOBOX = "COMBOBOX";
	public static String SELECT_DATE = "SELECTDATE";
	public static String SELECT_CODETABLE = "SELECTCODETABLE";
	public static String TEXTAREA = "TEXTAREA";
	public static String RADIO = "RADIO";
	public static String CHECKBOX = "CHECKBOX";
	public static String SELECT_EMP = "SELECTEMP";
	public static String SELECT_ORG = "SELECTORG";

	public static CodeTable getCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();

		lcdtbl_Return.insertItem(TEXTINPUT, "普通输入", 0);
		lcdtbl_Return.insertItem(NUMBERINPUT, "数字输入", 0);
		lcdtbl_Return.insertItem(RADIO, "单选", 0);
		lcdtbl_Return.insertItem(CHECKBOX, "多选", 0);
		lcdtbl_Return.insertItem(SELECT_DATE, "日期选择", 0);
		lcdtbl_Return.insertItem(TEXTAREA, "文本框", 0);
		lcdtbl_Return.insertItem(SELECT_CODETABLE, "下拉列表", 0);
		lcdtbl_Return.insertItem(SELECT_EMP, "选择员工", 0);
		lcdtbl_Return.insertItem(SELECT_ORG, "选择部门", 0);

		return lcdtbl_Return;
	}

	public static Map<String, String> getMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(TEXTINPUT, "普通输入");
		map.put(NUMBERINPUT, "数字输入");
		map.put(RADIO, "单选");
		map.put(CHECKBOX, "多选");
		map.put(SELECT_DATE, "日期选择");
		map.put(TEXTAREA, "文本框");
		map.put(SELECT_CODETABLE, "下拉列表");
		map.put(SELECT_EMP, "选择员工");
		map.put(SELECT_ORG, "选择部门");
		return map;
	}

	public static String getDataType(String as_InputMode, String as_LabelName) {

		String ls_DataType = "";

		as_InputMode = CommonUtil.trim(as_InputMode);
		if ((as_InputMode.equals(TEXTINPUT)) || (as_InputMode.equals(RADIO))
				|| (as_InputMode.equals(CHECKBOX))
				|| (as_InputMode.equals(SELECT_CODETABLE))
				|| (as_InputMode.equals(COMBOBOX))) {
			ls_DataType = "varchar2(255)";
		}
		if (as_InputMode.equals(TEXTAREA)) {
			ls_DataType = "varchar2(1000)";
		}
		if (as_InputMode.equals(SELECT_DATE)) {
			ls_DataType = "varchar2(27)";
		}
		if ((as_InputMode.equals(SELECT_EMP))
				|| (as_InputMode.equals(SELECT_ORG))) {
			if (as_LabelName.indexOf("ID") != -1) {
				ls_DataType = "varchar2(128)";
			} else {
				ls_DataType = "varchar2(128)";
			}
		}
		if (as_InputMode.equals("NUMBERINPUT")) {
			ls_DataType = "varchar2(128)";
		}
		return ls_DataType;
	}
}
