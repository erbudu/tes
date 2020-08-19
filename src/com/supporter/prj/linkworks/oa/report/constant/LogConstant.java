package com.supporter.prj.linkworks.oa.report.constant;

public interface LogConstant {
	// 日志类型-业务
	String INFO_TYPE_BUSINESS = "OA_REPORT";
	// 日志类型-调试
	String INFO_TYPE_DEBUG = "OA_REPORT_DEBUG";

	// 发布的日志
	String PUBLISH_REPORT_LOG_ACTION = "发布报告";
	String PUBLISH_REPORT_LOG_MESSAGE = "发布报告，标题：{0}";
	// 取消发布的日志
	String CANCEL_REPORT_LOG_ACTION = "取消发布报告";
	String CANCEL_REPORT_LOG_MESSAGE = "取消发布报告，标题：{0}";
	// 停止发布的日志
	String ENDPUBLISH_REPORT_LOG_ACTION = "停止发布报告";
	String ENDPUBLISH_REPORT_LOG_MESSAGE = "停止发布报告，标题：{0}";
	// 同步文件的日志
	String EXTRACT_FILES_LOG_ACTION = "报告同步文件";
	String EXTRACT_FILES_LOG_MESSAGE = "报告同步文件，标题：{0}";

}
