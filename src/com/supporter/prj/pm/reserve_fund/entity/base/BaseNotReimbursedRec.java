package com.supporter.prj.pm.reserve_fund.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PmReserveFund entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseNotReimbursedRec implements java.io.Serializable {

	// Fields

	private String id;
	private String reimbursedId;
	private String currencyId;
	private String currency;
	private Date reimbursedDate;//报销日期
	private double borrowAmount;//预借款
	private double reimburseAmount;//已报销
	private double repaymentAmount;//已还款
	private double arrearsAmount;//欠款额
	private String happenPersonId;//借款人id
	private String happenPerson;//借款人

	// Constructors

	/** default constructor */
	public BaseNotReimbursedRec() {
	}

	/** minimal constructor */
	public BaseNotReimbursedRec(String id) {
		this.id = id;
	}

	/** full constructor */
	public BaseNotReimbursedRec(String id,String reimbursedId, String currency,
			Date reimbursedDate, double borrowAmount,double reimburseAmount,double repaymentAmount,
			double arrearsAmount,String happenPersonId,String happenPerson) {
		this.id = id;
		this.reimbursedId = reimbursedId;
		this.currency = currency;
		this.reimbursedDate = reimbursedDate;
		this.borrowAmount = borrowAmount;
		this.reimburseAmount = reimburseAmount;
		this.repaymentAmount = repaymentAmount;
		this.arrearsAmount = arrearsAmount;
		this.happenPersonId = happenPersonId;
		this.happenPerson = happenPerson;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "reimbursed_id", length = 32)
	public String getReimbursedId() {
		return this.reimbursedId;
	}

	public void setReimbursedId(String reimbursedId) {
		this.reimbursedId = reimbursedId;
	}

	@Column(name = "currency_id", length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name = "currency", length = 32)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reimbursed_date", length = 19)
	public Date getReimbursedDate() {
		return this.reimbursedDate;
	}

	public void setReimbursedDate(Date reimbursedDate) {
		this.reimbursedDate = reimbursedDate;
	}

	@Column(name = "borrow_amount", precision = 18, scale = 3)
	public double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	@Column(name = "reimburse_amount", precision = 18, scale = 3)
	public double getReimburseAmount() {
		return reimburseAmount;
	}

	public void setReimburseAmount(double reimburseAmount) {
		this.reimburseAmount = reimburseAmount;
	}

	@Column(name = "repayment_amount", precision = 18, scale = 3)
	public double getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(double repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	@Column(name = "arrears_amount", precision = 18, scale = 3)
	public double getArrearsAmount() {
		return arrearsAmount;
	}

	public void setArrearsAmount(double arrearsAmount) {
		this.arrearsAmount = arrearsAmount;
	}

	@Column(name = "happen_person_id", length = 32)
	public String getHappenPersonId() {
		return this.happenPersonId;
	}

	public void setHappenPersonId(String happenPersonId) {
		this.happenPersonId = happenPersonId;
	}

	@Column(name = "happen_person", length = 32)
	public String getHappenPerson() {
		return this.happenPerson;
	}

	public void setHappenPerson(String happenPerson) {
		this.happenPerson = happenPerson;
	}

}