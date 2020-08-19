package com.supporter.prj.pm.fund_appropriation.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseFundBudgetActual implements java.io.Serializable{

	// primary key
	private String actualId;//主键

	// fields
	private String fundId;
	private String budgetItemId;
	private String budgetItem;
	private String contractId;
	private String contractName;
	private String budgetId;
	private String budgetNo;
	private String budgetName;
	private double amountUsd;
	private double amountLkr;
	private double usdRate;
	private double usdAmount;
	
	
	/** default constructor */
	public BaseFundBudgetActual() {
	}

	/** minimal constructor */
	public BaseFundBudgetActual(String actualId) {
		this.actualId = actualId;
	}
	
	@Id
	@Column(name = "actual_id", unique = true, nullable = false, length = 32)
	public String getActualId() {
		return actualId;
	}

	public void setActualId(String actualId) {
		this.actualId = actualId;
	}

	@Column(name = "fund_id", length = 32)
	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	@Column(name = "budget_item_id", length = 32)
	public String getBudgetItemId() {
		return budgetItemId;
	}

	public void setBudgetItemId(String budgetItemId) {
		this.budgetItemId = budgetItemId;
	}

	@Column(name = "budget_item", length = 64)
	public String getBudgetItem() {
		return budgetItem;
	}

	public void setBudgetItem(String budgetItem) {
		this.budgetItem = budgetItem;
	}
	
	@Column(name = "contract_id", length = 32)
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	@Column(name = "contract_name", length = 128)
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	@Column(name = "budget_id", length = 32)
	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	@Column(name = "budget_no", length = 64)
	public String getBudgetNo() {
		return budgetNo;
	}

	public void setBudgetNo(String budgetNo) {
		this.budgetNo = budgetNo;
	}
	
	@Column(name = "budget_name", length = 64)
	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}
	
	@Column(name = "amount_usd", precision = 18, scale = 3)
	public double getAmountUsd() {
		return amountUsd;
	}

	public void setAmountUsd(double amountUsd) {
		this.amountUsd = amountUsd;
	}
	
	@Column(name = "amount_lkr", precision = 18, scale = 3)
	public double getAmountLkr() {
		return amountLkr;
	}

	public void setAmountLkr(double amountLkr) {
		this.amountLkr = amountLkr;
	}

	@Column(name = "usd_rate", precision = 18, scale = 4)
	public double getUsdRate() {
		return usdRate;
	}

	public void setUsdRate(double usdRate) {
		this.usdRate = usdRate;
	}

	@Column(name = "usd_amount", precision = 18, scale = 3)
	public double getUsdAmount() {
		return usdAmount;
	}

	public void setUsdAmount(double usdAmount) {
		this.usdAmount = usdAmount;
	}
	
}
