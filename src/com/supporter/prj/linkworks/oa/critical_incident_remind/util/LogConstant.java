package com.supporter.prj.linkworks.oa.critical_incident_remind.util;

public interface LogConstant {
	// 日志类型-业务
	String INFO_TYPE_BUSINESS = "OA_CCREMIND";
	// 日志类型-调试
	String INFO_TYPE_DEBUG = "OA_CCREMIND_DEBUG";

	// 新增的日志
	String ADD_CCREMIND_LOG_ACTION = "新增关键事件提醒";
	String ADD_CCREMIND_LOG_MESSAGE = "新增关键事件提醒，事件名称：{0}";
	// 编辑的日志
	String EDIT_CCREMIND_LOG_ACTION = "编辑关键事件提醒";
	String EDIT_CCREMIND_LOG_MESSAGE = "编辑关键事件提醒，事件名称：{0}";
	// 删除的日志
	String DELETE_CCREMIND_LOG_ACTION = "删除关键事件提醒";
	String DELETE_CCREMIND_LOG_MESSAGE = "删除关键事件提醒，详情：{0}";

}
