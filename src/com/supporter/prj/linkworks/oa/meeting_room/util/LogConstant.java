package com.supporter.prj.linkworks.oa.meeting_room.util;

public interface LogConstant {
	/**
	 * 会议日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS1 = "OA_MEETING";
	// 日志类型-调试
	String INFO_TYPE_DEBUG1 = "OA_MEETING_DEBUG";

	// 预定的日志
	String ADD_MEETING_LOG_ACTION = "预定会议";
	String ADD_MEETING_LOG_MESSAGE = "预订会议，标题：{0}";
	// 修改的日志
	String EDIT_MEETING_LOG_ACTION = "修改会议";
	String EDIT_MEETING_LOG_MESSAGE = "修改会议，标题：{0}";
	// 取消的日志
	String DELETE_MEETING_LOG_ACTION = "取消会议";
	String DELETE_MEETING_LOG_MESSAGE = "取消会议，详情：{0}";

	
	/**
	 * 会议室日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS2 = "OA_MEETINGROOM";
	// 日志类型-调试
	String INFO_TYPE_DEBUG2 = "OA_MEETINGROOM_DEBUG";

	// 新增的日志
	String ADD_MEETINGROOM_LOG_ACTION = "新增会议室";
	String ADD_MEETINGROOM_LOG_MESSAGE = "新增会议室，会议室名称：{0}";
	// 编辑的日志
	String EDIT_MEETINGROOM_LOG_ACTION = "编辑会议室";
	String EDIT_MEETINGROOM_LOG_MESSAGE = "编辑会议室，会议室名称：{0}";
	// 删除的日志
	String DELETE_MEETINGROOM_LOG_ACTION = "删除会议室";
	String DELETE_MEETINGROOM_LOG_MESSAGE = "删除会议室，详情：{0}";
}
