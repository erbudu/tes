package com.supporter.prj.linkworks.oa.bank_account.util;

public interface LogConstant {
	/**
	 * 开立申请日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS1 = "BANKOPEN";
	// 日志类型-调试
	String INFO_TYPE_DEBUG1 = "BANKOPEN_DEBUG";

	// 新增的日志
	String ADD_BANKOPEN_LOG_ACTION = "新增开立申请";
	String ADD_BANKOPEN_LOG_MESSAGE = "新增开立申请，开户银行：{0}";
	// 修改的日志
	String EDIT_BANKOPEN_LOG_ACTION = "修改开立申请";
	String EDIT_BANKOPEN_LOG_MESSAGE = "修改开立申请，开户银行：{0}";
	// 删除的日志
	String DELETE_BANKOPEN_LOG_ACTION = "删除开立申请";
	String DELETE_BANKOPEN_LOG_MESSAGE = "删除开立申请，开户银行：{0}";

	
	/**
	 * 开立申请备案日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS2 = "BANKOPEN_RECORD";
	// 日志类型-调试
	String INFO_TYPE_DEBUG2 = "BANKOPEN_RECORD_DEBUG";

	// 新增的日志
	String ADD_BANKOPEN_RECORD_LOG_ACTION = "新增开立申请备案";
	String ADD_BANKOPEN_RECORD_LOG_MESSAGE = "新增开立申请备案，备案的银行账户名称：{0}";
	// 编辑的日志
	String EDIT_BANKOPEN_RECORD_LOG_ACTION = "编辑开立申请备案";
	String EDIT_BANKOPEN_RECORD_LOG_MESSAGE = "编辑开立申请备案，备案的银行账户名称：{0}";
	// 删除的日志
	String DELETE_BANKOPEN_RECORD_LOG_ACTION = "删除开立申请备案";
	String DELETE_BANKOPEN_RECORD_LOG_MESSAGE = "删除开立申请备案，详情：{0}";
	
	
	/**
	 * 变更申请日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS3 = "BANKCHAN";
	// 日志类型-调试
	String INFO_TYPE_DEBUG3 = "BANKCHAN_DEBUG";

	// 新增的日志
	String ADD_BANKCHAN_LOG_ACTION = "新增变更申请";
	String ADD_BANKCHAN_LOG_MESSAGE = "新增变更申请，银行账户名称：{0}";
	// 编辑的日志
	String EDIT_BANKCHAN_LOG_ACTION = "编辑变更申请";
	String EDIT_BANKCHAN_LOG_MESSAGE = "编辑变更申请，银行账户名称：{0}";
	// 删除的日志
	String DELETE_BANKCHAN_LOG_ACTION = "删除变更申请";
	String DELETE_BANKCHAN_LOG_MESSAGE = "删除变更申请，详情：{0}";
	
	
	
	/**
	 * 变更申请备案日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS4 = "BANKCHAN_RECORD";
	// 日志类型-调试
	String INFO_TYPE_DEBUG4 = "BANKCHAN_RECORD_DEBUG";

	// 新增的日志
	String ADD_BANKCHAN_RECORD_LOG_ACTION = "新增变更申请备案";
	String ADD_BANKCHAN_RECORD_LOG_MESSAGE = "新增变更申请备案，备案的银行账户名称：{0}";
	// 编辑的日志
	String EDIT_BANKCHAN_RECORD_LOG_ACTION = "编辑变更申请备案";
	String EDIT_BANKCHAN_RECORD_LOG_MESSAGE = "编辑变更申请备案，备案的银行账户名称：{0}";
	// 删除的日志
	String DELETE_BANKCHAN_RECORD_LOG_ACTION = "删除变更申请备案";
	String DELETE_BANKCHAN_RECORD_LOG_MESSAGE = "删除变更申请备案，详情：{0}";
	
	
	
	
	
	/**
	 * 撤销日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS5 = "BANKREVOKE";
	// 日志类型-调试
	String INFO_TYPE_DEBUG5 = "BANKREVOKE_DEBUG";

	// 新增的日志
	String ADD_BANKREVOKE_LOG_ACTION = "新增撤销";
	String ADD_BANKREVOKE_LOG_MESSAGE = "新增撤销，撤销的银行账户名称：{0}";
	// 编辑的日志
	String EDIT_BANKREVOKE_LOG_ACTION = "编辑撤销";
	String EDIT_BANKREVOKE_LOG_MESSAGE = "编辑撤销，撤销的银行账户名称：{0}";
	// 删除的日志
	String DELETE_BANKREVOKE_LOG_ACTION = "删除撤销";
	String DELETE_BANKREVOKE_LOG_MESSAGE = "删除撤销，详情：{0}";
	
	
	
	/**
	 * 合作银行维护日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS7 = "BANKCOOPERRATIVE";
	// 日志类型-调试
	String INFO_TYPE_DEBUG7 = "BANKCOOPERRATIVE_DEBUG";

	// 新增的日志
	String ADD_BANKCOOPERRATIVE_LOG_ACTION = "新增合作银行";
	String ADD_BANKCOOPERRATIVE_LOG_MESSAGE = "新增合作银行，合作银行名称：{0}";
	// 编辑的日志
	String EDIT_BANKCOOPERRATIVE_LOG_ACTION = "编辑合作银行";
	String EDIT_BANKCOOPERRATIVE_LOG_MESSAGE = "编辑合作银行，合作银行名称：{0}";
	// 删除的日志
	String DELETE_BANKCOOPERRATIVE_LOG_ACTION = "删除合作银行";
	String DELETE_BANKCOOPERRATIVE_LOG_MESSAGE = "删除合作银行，详情：{0}";
	
	
	/**
	 * 账户内部维护日志
	 */
	// 日志类型-业务
	String INFO_TYPE_BUSINESS6 = "BANKMAINTENANCE";
	// 日志类型-调试
	String INFO_TYPE_DEBUG6 = "BANKMAINTENANCE_DEBUG";

	// 新增的日志
	String ADD_BANKMAINTENANCE_LOG_ACTION = "新增银行账户信息";
	String ADD_BANKMAINTENANCE_LOG_MESSAGE = "新增人信息：新增人Id、新增人姓名、新增时间、新增数据的主键：{0}{1}{2}{3}";
	// 编辑的日志
	String EDIT_BANKMAINTENANCE_LOG_ACTION = "编辑银行账户信息";
	String EDIT_BANKMAINTENANCE_LOG_MESSAGE = "编辑人信息：编辑人Id、编辑人姓名、编辑时间、编辑数据的主键：{0}{1}{2}{3}";
	// 删除的日志
	String DELETE_BANKMAINTENANCE_LOG_ACTION = "删除银行账户信息";
	String DELETE_BANKMAINTENANCE_LOG_MESSAGE = "删除人信息：删除人Id、删除人姓名、删除时间、删除数据的开户行、银行账号：{0}{1}{2}{3}{4}";
	
	
	
}
