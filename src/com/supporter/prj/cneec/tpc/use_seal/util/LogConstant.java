package com.supporter.prj.cneec.tpc.use_seal.util;

public interface LogConstant {

	// 日志类型-业务
	String INFO_TYPE_BUSINESS = "TPC_USE_SEAL";
	// 日志类型-调试
	String INFO_TYPE_DEBUG = "TPC_USE_SEAL_DEBUG";

	// 发布的日志
	String PUBLISH_USE_SEAL_LOG_ACTION = "发布贸易用印";
	String PUBLISH_USE_SEAL_LOG_MESSAGE = "发布贸易用印，标题：{0}";

	// 取消发布的日志
	String CANCEL_USE_SEAL_LOG_ACTION = "取消发布贸易用印";
	String CANCEL_USE_SEAL_LOG_MESSAGE = "取消发布贸易用印，标题：{0}";

	// 停止发布的日志
	String ENDPUBLISH_USE_SEAL_LOG_ACTION = "停止发布贸易用印";
	String ENDPUBLISH_USE_SEAL_LOG_MESSAGE = "停止发布贸易用印，标题：{0}";

}
