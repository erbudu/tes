package com.supporter.prj.pm.fund_appropriation.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseFundBudgetExpend implements java.io.Serializable{

	// primary key
	private String expendId;//主键

	// fields
	private String fundId;
	private String budgetId;
	private String contractId;
	private String contractName;
	private String budgetName;
	private String currency;
	private String currencyId;
	private double amount;
	private double rate;
	private double zhAmount;
	private double alreadyPaid;
	private String budgetItemId;
	private String budgetItemNo;
	private String budgetItemName;
	private Integer isContract;
	
	/** default constructor */
	public BaseFundBudgetExpend() {
	}

	/** minimal constructor */
	public BaseFundBudgetExpend(String expendId) {
		this.expendId = expendId;
	}
	
	/** full constructor */
	public BaseFundBudgetExpend(String expendId, String fundId,String contractId,String contractName,String budgetId,
			String budgetName,String currency,String currencyId,double amount,double rate,double zhAmount,
			double actualAmount,double actualRate,double actualZhAmount,
			double rmbRate,double rmbAmount,double alreadyPaid,double canBudget,int isContract) {
		this.expendId = expendId;
		this.fundId = fundId;
		this.contractId = contractId;
		this.contractName = contractName;
		this.budgetId = budgetId;
		this.budgetName = budgetName;
		this.currency = currency;
		this.currencyId = currencyId;
		this.amount = amount;
		this.rate = rate;
		this.zhAmount = zhAmount;
		this.alreadyPaid = alreadyPaid;
		this.isContract = isContract;
	}
	@Id
	@Column(name = "expend_id", unique = true, nullable = false, length = 32)
	public String getExpendId() {
		return expendId;
	}

	public void setExpendId(String expendId) {
		this.expendId = expendId;
	}

	@Column(name = "fund_id", length = 32)
	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
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

	@Column(name = "budget_name", length = 32)
	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	@Column(name = "currency", length = 32)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Column(name = "currency_id", length = 32)
	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}


	@Column(name = "amount", precision = 18, scale = 3)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "rate", precision = 18, scale = 4)
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Column(name = "zh_amount", precision = 18, scale = 3)
	public double getZhAmount() {
		return zhAmount;
	}

	public void setZhAmount(double zhAmount) {
		this.zhAmount = zhAmount;
	}

	@Column(name = "already_paid", precision = 18, scale = 3)
	public double getAlreadyPaid() {
		return alreadyPaid;
	}

	public void setAlreadyPaid(double alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}
	
	@Column(name = "budget_item_id", length = 32)
	public String getBudgetItemId() {
		return budgetItemId;
	}

	public void setBudgetItemId(String budgetItemId) {
		this.budgetItemId = budgetItemId;
	}

	@Column(name = "budget_item_no", length = 64)
	public String getBudgetItemNo() {
		return budgetItemNo;
	}

	public void setBudgetItemNo(String budgetItemNo) {
		this.budgetItemNo = budgetItemNo;
	}
	
	@Column(name = "budget_item_name", length = 64)
	public String getBudgetItemName() {
		return budgetItemName;
	}

	public void setBudgetItemName(String budgetItemName) {
		this.budgetItemName = budgetItemName;
	}

	@Column(name = "is_contract", length = 11)
	public Integer getIsContract() {
		return isContract;
	}

	public void setIsContract(int isContract) {
		this.isContract = isContract;
	}

}
