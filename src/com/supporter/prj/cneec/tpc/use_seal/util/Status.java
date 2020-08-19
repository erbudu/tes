package com.supporter.prj.cneec.tpc.use_seal.util;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Title: Status
 * @Description: 流程操作类型
 * @author: yanweichao
 * @date: 2017-10-28
 * @version: V1.0
 */
public enum Status {

	draft(0, "草稿"), auditing(10, "审核中"), completed(20, "审核完成"), rejected(30, "驳回");

	private int key;
	private String value;

	Status(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public static int getKeyByValue(String value) {
		Status[] types = Status.values();
		for (Status type : types) {
			if (StringUtils.equals(value, type.getValue())) {
				return type.getKey();
			}
		}
		return 0;
	}

	public static String getValueByKey(int key) {
		Status[] types = Status.values();
		for (Status type : types) {
			if (key == type.getKey()) {
				return type.getValue();
			}
		}
		return null;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
