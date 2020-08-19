package com.supporter.prj.cneec.tpc.benefit_budget.constant;

/**
 * @Title: BenefitBudgetTemp
 * @Description: 效益预算表模板常量
 * @author: yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
public abstract interface BenefitBudgetConstant {

	/** 效益测算表预算缺省默认值 **/
	public static final int DEFAULT_VALUE = -1;
	public static final String DEFAULT_VALUE_STR = "--";

	/** 测算表预算级别 **/
	public static final int LEVEL_ONE = 0;
	public static final int LEVEL_TWO = 1;
	public static final int LEVEL_THREE = 2;

	/** 测算表预算排序 **/
	public static final int ORDER_DISPLAY_ONE = 1000;
	public static final int ORDER_DISPLAY_TWO = 10;
	public static final int ORDER_DISPLAY_THREE = 1;

	/** 效益预算表表头内容 **/
	public static final String HEAD_TITLE_XH = "序号";
	public static final String HEAD_TITLE_YS = "预算说明";
	public static final String HEAD_TITLE_ZH = "折人民币合计";
	public static final String HEAD_TITLE_ZB = "占人民币总收入/总支出百分比";
	public static final String HEAD_TITLE_HJ = "合  计";
	public static final String HEAD_TITLE_XMHJ = "项 目 合 计";
	public static final String HEAD_TITLE_XMLSHJ = "项目累计(除本次)";

	public static final String HEAD_BUDGET_TITLE_ZYS = "总预算";
	public static final String HEAD_BUDGET_TITLE_KY = "可用预算";
	public static final String HEAD_BUDGET_TITLE_ZT = "在途金额";
	public static final String HEAD_BUDGET_TITLE_ZX = "执行金额";

	/** 效益预算表表头宽度 **/
	public static final int TITLE_WIDTH = 100;// 默认
	public static final int TITLE_WIDTH_XH = 60;// 序号
	public static final int TITLE_WIDTH_YS = 250;// 预算说明
	public static final int TITLE_WIDTH_QT1 = 90;
	public static final int TITLE_WIDTH_QT2 = 110;

	/** 效益预算表模板 **/
	public static final String BENEFIT_BUDGET_EXCEL_PATH = "/tpc/benefit_budget/template_excel/";
	public static final String BENEFIT_BUDGET_TEMP0 = "benefit_budget_0";
	public static final String BENEFIT_PROCESS_TEMP0 = "benefit_process_0";
	public static final String BENEFIT_CONTRACT_TEMP0 = "benefit_contract_0";

}
