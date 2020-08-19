package com.supporter.prj.linkworks.oa.maintenance.util;

public interface LogConstant {
	// 日志类型-业务
	String INFO_TYPE_BUSINESS = "OA_MAINTENAN";
	// 日志类型-调试
	String INFO_TYPE_DEBUG = "OA_MAINTENAN_DEBUG";

	// 新增的日志
	String ADD_MAINTENANCE_LOG_ACTION = "新增信息系统维护申请";
	String ADD_MAINTENANCE_LOG_MESSAGE = "新增信息系统维护申请，标题：{0}";
	// 编辑的日志
	String EDIT_MAINTENANCE_LOG_ACTION = "编辑信息系统维护申请";
	String EDIT_MAINTENANCE_LOG_MESSAGE = "编辑信息系统维护申请，标题：{0}";
	// 删除的日志
	String DELETE_MAINTENANCE_LOG_ACTION = "删除信息系统维护申请";
	String DELETE_MAINTENANCE_LOG_MESSAGE = "删除信息系统维护申请，详情：{0}";

}
