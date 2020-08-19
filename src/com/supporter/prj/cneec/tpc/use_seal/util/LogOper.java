package com.supporter.prj.cneec.tpc.use_seal.util;

/**
 * 
 * @Title: LogOper
 * @Description: 日志操作类型
 * @author: yanweichao
 * @date: 2017-10-28
 * @version: V1.0
 */
public enum LogOper {

	MODULE_ADD("新建贸易用印"), MODULE_EDIT("编辑贸易用印"), MODULE_DEL("删除贸易用印"), MODULE_CHANGE_DISPLAY_ORDER("调整贸易用印显示顺序");

	private String operName;

	LogOper(String operName) {
		this.operName = operName;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

}
