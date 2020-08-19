package com.supporter.prj.linkworks.oa.salary.util;

public interface LogConstant {
	// 日志类型-业务
	String INFO_TYPE_BUSINESS = "OA_SALARY";
	// 日志类型-调试
	String INFO_TYPE_DEBUG = "OA_SALARY_DEBUG";

	// 导入的日志
	String INPUT_SALARY_LOG_ACTION = "导入工资";
	String INPUT_SALARY_LOG_MESSAGE = "导入工资，月份：{0}";
	// 删除的日志
	String DELETE_SALARY_LOG_ACTION = "删除工资";
	String DELETE_SALARY_LOG_MESSAGE = "删除工资，详情：{0}";
	// 按月删除的日志
	String DELETEBYMONTH_SALARY_LOG_ACTION = "按月删除工资";
	String DELETEBYMONTH_SALARY_LOG_MESSAGE = "按月删除工资，详情：{0}";

}
