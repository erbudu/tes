package com.supporter.prj.pm.bank_manage.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseAccountDetail implements java.io.Serializable{

	// primary key
	private java.lang.String id;//主键

	// fields
	private java.lang.String accountId;
	private java.lang.String currencyId;
	private java.lang.String currency;
	private java.util.Date createdDate;
	private java.lang.String nature;
	private double amount;
	private double bankAmount;
	private String voucherNo;
	private String applyReason;
	private String payProject;
	private String contractNo;
	private String budgetName;
	
	/** default constructor */
	public BaseAccountDetail() {
	}

	/** minimal constructor */
	public BaseAccountDetail(String id) {
		this.id = id;
	}
	
	/** full constructor */
	public BaseAccountDetail(String id, String accountId,String currency,
			Date createdDate, String nature, double amount) {
		this.id = id;
		this.accountId = accountId;
		this.currency = currency;
		this.createdDate = createdDate;
		this.nature = nature;
		this.amount = amount;
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	@Column(name = "currency_id", length = 32)
	public java.lang.String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(java.lang.String currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name = "currency", length = 32)
	public java.lang.String getCurrency() {
		return currency;
	}

	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}

	@Column(name = "account_id", length = 32)
	public java.lang.String getAccountId() {
		return accountId;
	}

	public void setAccountId(java.lang.String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "created_date", length = 7)
	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "nature", length = 32)
	public java.lang.String getNature() {
		return nature;
	}

	public void setNature(java.lang.String nature) {
		this.nature = nature;
	}

	@Column(name = "amount", precision = 18, scale = 3)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Column(name = "bank_amount", precision = 18, scale = 3)
	public double getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(double bankAmount) {
		this.bankAmount = bankAmount;
	}
	
	@Column(name = "voucher_no", length = 64)
	public String getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	@Column(name = "apply_reason", length = 256)
	public String getApplyReason() {
		return this.applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}
	
	@Column(name = "pay_project", length = 64)
	public String getPayProject() {
		return this.payProject;
	}

	public void setPayProject(String payProject) {
		this.payProject = payProject;
	}
	
	@Column(name = "contract_no", length = 32)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Column(name = "budget_name", length = 128)
	public String getBudgetName() {
		return this.budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}
	
}
