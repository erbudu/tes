package com.supporter.prj.cneec.tpc.util;

import java.io.Serializable;

/**
 * @Title: CheckBoxVO
 * @Description: TPC复选框基础类
 * @author: yanweichao
 * @date: 2017-8-31
 * @version: V1.0
 */
public class CheckBoxVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id = "";// 复选框ID
	private String name = "";// 复选框NAME
	private String value = "";// 复选框值
	private String describe = "";// 复选框显示信息
	private boolean checked = false;// 是否默认选中

	public CheckBoxVO() {
	}

	public CheckBoxVO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public CheckBoxVO(String id, String name, String value, String describe) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.describe = describe;
	}

	public CheckBoxVO(String id, String name, String value, String describe, boolean checked) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.describe = describe;
		this.checked = checked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}