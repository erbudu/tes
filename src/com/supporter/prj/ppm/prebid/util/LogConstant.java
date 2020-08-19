package com.supporter.prj.ppm.prebid.util;

public interface LogConstant {

	/** 启动报审  **/
	// 日志类型-业务
	String INFO_TYPE_BUSINESS_PPMREPORTINGSTART = "PPM_PREBID_REPORT";

	// 发布的日志
	String PUBLISH_LOG_ACTION_PPMREPORTINGSTART = "发布启动报审";
	String PUBLISH_LOG_MESSAGE_PPMREPORTINGSTART = "发布启动报审，标题：{0}";

	// 取消发布的日志
	String CANCEL_LOG_ACTION_PPMREPORTINGSTART = "取消发布启动报审";
	String CANCEL_LOG_MESSAGE_PPMREPORTINGSTART = "取消发布启动报审，标题：{0}";

	// 停止发布的日志
	String ENDPUBLISH_LOG_ACTION_PPMREPORTINGSTART = "停止发布启动报审";
	String ENDPUBLISH_LOG_MESSAGE_PPMREPORTINGSTART = "停止发布启动报审，标题：{0}";

	/** 启动评审  **/
	// 日志类型-业务
	String INFO_TYPE_BUSINESS_REVIEW = "PPM_PREBID_REVIEW";
	
	// 发布的日志
	String PUBLISH_LOG_ACTION_REVIEW = "发布启动评审";
	String PUBLISH_LOG_MESSAGE_REVIEW = "发布启动评审，标题：{0}";

	// 取消发布的日志
	String CANCEL_LOG_ACTION_REVIEW = "取消发布启动评审";
	String CANCEL_LOG_MESSAGE_REVIEW = "取消发布启动评审，标题：{0}";

	// 停止发布的日志
	String ENDPUBLISH_LOG_ACTION_REVIEW = "停止发布启动评审";
	String ENDPUBLISH_LOG_MESSAGE_REVIEW = "停止发布启动评审，标题：{0}";


	/** 启动评审验证  **/
	// 日志类型-业务
	String INFO_TYPE_BUSINESS_REVIEWVER = "PPM_PREBID_REVIEWVER";
	
	// 发布的日志
	String PUBLISH_LOG_ACTION_REVIEWVER = "发布启动评审验证";
	String PUBLISH_LOG_MESSAGE_REVIEWVER = "发布启动评审验证，标题：{0}";

	// 取消发布的日志
	String CANCEL_LOG_ACTION_REVIEWVER = "取消发布启动评审验证";
	String CANCEL_LOG_MESSAGE_REVIEWVER = "取消发布启动评审验证，标题：{0}";

	// 停止发布的日志
	String ENDPUBLISH_LOG_ACTION_REVIEWVER = "停止发布启动评审验证";
	String ENDPUBLISH_LOG_MESSAGE_REVIEWVER = "停止发布启动评审验证，标题：{0}";

}
