package com.supporter.prj.cneec.tpc.benefit_budget_change.util;

import java.math.BigDecimal;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;

/**
 * @Title: BenefitAmountBean
 * @Description: 项目效益测算/预算表金额类
 * @author: yanweichao
 * @date: 2018-6-7
 * @version: V1.0
 */
public class BenefitAmountBean {

	private String processBudgetId;
	private double currency1Amount;
	private double currency2Amount;
	private double currency3Amount;
	private double currency4Amount;
	private double currency5Amount;
	private double currency6Amount;
	private double currency7Amount;
	private double currency8Amount;
	private double currency9Amount;
	private double totalRmbAmount;
	private double proportion;

	public BenefitAmountBean() {
		super();
	}

	

	public String getProcessBudgetId() {
		return processBudgetId;
	}



	public void setProcessBudgetId(String processBudgetId) {
		this.processBudgetId = processBudgetId;
	}



	public double getCurrency1Amount() {
		return this.currency1Amount;
	}

	public void setCurrency1Amount(double currency1Amount) {
		this.currency1Amount = currency1Amount;
	}

	public double getCurrency2Amount() {
		return this.currency2Amount;
	}

	public void setCurrency2Amount(double currency2Amount) {
		this.currency2Amount = currency2Amount;
	}

	public double getCurrency3Amount() {
		return this.currency3Amount;
	}

	public void setCurrency3Amount(double currency3Amount) {
		this.currency3Amount = currency3Amount;
	}

	public double getCurrency4Amount() {
		return this.currency4Amount;
	}

	public void setCurrency4Amount(double currency4Amount) {
		this.currency4Amount = currency4Amount;
	}

	public double getCurrency5Amount() {
		return this.currency5Amount;
	}

	public void setCurrency5Amount(double currency5Amount) {
		this.currency5Amount = currency5Amount;
	}

	public double getCurrency6Amount() {
		return this.currency6Amount;
	}

	public void setCurrency6Amount(double currency6Amount) {
		this.currency6Amount = currency6Amount;
	}

	public double getCurrency7Amount() {
		return this.currency7Amount;
	}

	public void setCurrency7Amount(double currency7Amount) {
		this.currency7Amount = currency7Amount;
	}

	public double getCurrency8Amount() {
		return this.currency8Amount;
	}

	public void setCurrency8Amount(double currency8Amount) {
		this.currency8Amount = currency8Amount;
	}

	public double getCurrency9Amount() {
		return this.currency9Amount;
	}

	public void setCurrency9Amount(double currency9Amount) {
		this.currency9Amount = currency9Amount;
	}

	public double getTotalRmbAmount() {
		return this.totalRmbAmount;
	}

	public void setTotalRmbAmount(double totalRmbAmount) {
		this.totalRmbAmount = totalRmbAmount;
	}
	
	public double getProportion() {
		return proportion;
	}

	public void setProportion(double proportion) {
		this.proportion = proportion;
	}


	/**
	 * 根据预算项及币别汇率重置币别为默认值
	 * @param budgetName 预算项
	 * @param defaultRate 默认汇率值
	 * @param currencyRate 各币别汇率值
	 */
	public double putAllCurrencyAmount(String budgetName, double defaultRate, double... currencyRate) {
		// 判断是否特殊预算
		boolean isSpecialBudget = BenefitBudgetUtil.isSpecialBudget(budgetName);
		// 特殊预算置币别金额为默认值，其他预算若无币别列置币别金额为0
		double defaultAmount = isSpecialBudget ? BenefitBudgetConstant.DEFAULT_VALUE : 0;
		if (isSpecialBudget || currencyRate[0] == defaultRate) this.currency1Amount = defaultAmount;
		if (isSpecialBudget || currencyRate[1] == defaultRate) this.currency2Amount = defaultAmount;
		if (isSpecialBudget || currencyRate[2] == defaultRate) this.currency3Amount = defaultAmount;
		if (isSpecialBudget || currencyRate[3] == defaultRate) this.currency4Amount = defaultAmount;
		if (isSpecialBudget || currencyRate[4] == defaultRate) this.currency5Amount = defaultAmount;
		if (isSpecialBudget || currencyRate[5] == defaultRate) this.currency6Amount = defaultAmount;
		if (isSpecialBudget || currencyRate[6] == defaultRate) this.currency7Amount = defaultAmount;
		if (isSpecialBudget || currencyRate[7] == defaultRate) this.currency8Amount = defaultAmount;
		if (isSpecialBudget || currencyRate[8] == defaultRate) this.currency9Amount = defaultAmount;
		// 非特殊预算计算折合人民币合计(特殊预算折人民币合计随后根据其他预算计算得出，非特殊预算折人民币合计为所有币别折人民币之和)
		if (!isSpecialBudget) {
			this.totalRmbAmount = BigDecimal.valueOf(this.getCurrency1Amount() * currencyRate[0])
					.add(BigDecimal.valueOf(this.getCurrency2Amount() * currencyRate[1]))
					.add(BigDecimal.valueOf(this.getCurrency3Amount() * currencyRate[2]))
					.add(BigDecimal.valueOf(this.getCurrency4Amount() * currencyRate[3]))
					.add(BigDecimal.valueOf(this.getCurrency5Amount() * currencyRate[4]))
					.add(BigDecimal.valueOf(this.getCurrency6Amount() * currencyRate[5]))
					.add(BigDecimal.valueOf(this.getCurrency7Amount() * currencyRate[6]))
					.add(BigDecimal.valueOf(this.getCurrency8Amount() * currencyRate[7]))
					.add(BigDecimal.valueOf(this.getCurrency9Amount() * currencyRate[8])).doubleValue();
		}
		return totalRmbAmount;
	}

}
