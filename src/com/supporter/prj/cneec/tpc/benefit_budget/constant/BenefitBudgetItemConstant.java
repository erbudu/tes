package com.supporter.prj.cneec.tpc.benefit_budget.constant;

/**
 * @Title: BenefitBudgetItem
 * @Description: 效益预算表预算常量
 * @author: yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
public abstract interface BenefitBudgetItemConstant {

	/** 预算说明码表项 **/
	public static final String BENEFIT_BUDGET = "TPC_BENEFIT_CALC_BUDGET";

	/** 测算表预算分类类型 **/
	public static final String BENEFIT_CALC_SUMMARY = "TPC_BENEFIT_CALC_SUMMARY";// 预算说明汇总

	/** 预算说明-汇总 **/
	public static final String SUMMARY_TRADE_TOTAL = "TPC_BENEFIT_SUMMARY_SALE_TOTAL"; // 销售合同总金额
	public static final String SUMMARY_PURCHASE_TOTAL = "TPC_BENEFIT_SUMMARY_PURCHASE_TOTAL"; // 采购合同总金额
	public static final String SUMMARY_EXPENSE_TOTAL = "TPC_BENEFIT_SUMMARY_EXPENSE_TOTAL"; // 费用合计
	public static final String SUMMARY_EXPORT_DRAWBACK = "TPC_BENEFIT_SUMMARY_EXPORT_DRAWBACK"; // 应收出口退税
	public static final String SUMMARY_REMITTANCE_AND_LOSS = "TPC_BENEFIT_SUMMARY_REMITTANCE_AND_LOSS";// 汇兑损益
	public static final String SUMMARY_GROSS_PROFIT = "TPC_BENEFIT_SUMMARY_GROSS_PROFIT"; // 毛利
	public static final String SUMMARY_GROSS_PROFIT_RATE = "TPC_BENEFIT_SUMMARY_GROSS_PROFIT_RATE"; // 毛利率
	public static final String SUMMARY_PROJECT_DOMESTIC_MANAGEMENT_FEE = "TPC_BENEFIT_SUMMARY_PROJECT_DOMESTIC_MANAGEMENT_FEE"; // 项目国内管理费
	public static final String SUMMARY_EXPECTED_PROFIT_AND_LOSS = "TPC_BENEFIT_SUMMARY_EXPECTED_PROFIT_AND_LOSS"; // 预计盈亏额
	public static final String SUMMARY_PROFIT_MARGIN = "TPC_BENEFIT_SUMMARY_PROFIT_MARGIN"; // 利润率
	public static final String SUMMARY_OBTAIN_THE_TOTAL_VALUE_OF_VAT_INVOICE = "TPC_BENEFIT_SUMMARY_OBTAIN_THE_TOTAL_VALUE_OF_VAT_INVOICE"; // 取得全部增值税发票金额

	/** 预算说明-汇总下级预算项 **/
	public static final String SUMMARY_PURCHASE_PAYMENT = "TPC_BENEFIT_SUMMARY_PURCHASE_PAYMENT"; // 采购合同总金额-货款（服务款）金额

	public static final String SUMMARY_IMPORT_TARIFF = "TPC_BENEFIT_SUMMARY_IMPORT_TARIFF"; // 费用合计-国内进口关税
	public static final String SUMMARY_VALUE_ADDED_TAX = "TPC_BENEFIT_SUMMARY_VALUE_ADDED_TAX"; // 费用合计-国内进口增值税

}
