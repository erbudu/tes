package com.supporter.prj.pm.reserve_fund.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * PmReserveFund entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseNotReimbursed implements java.io.Serializable {

	// Fields

	private String reimbursedId;
	private String currencyId;
	private String currency;
	private double reserveAmount;
	private boolean isModified = true; // 是否发生了更改

	// Constructors

	/** default constructor */
	public BaseNotReimbursed() {
	}

	/** minimal constructor */
	public BaseNotReimbursed(String reimbursedId) {
		this.reimbursedId = reimbursedId;
	}

	/** full constructor */
	public BaseNotReimbursed(String reimbursedId, String currency, double reserveAmount, boolean isModified) {
		this.reimbursedId = reimbursedId;
		this.currency = currency;
		this.reserveAmount = reserveAmount;
		this.isModified = isModified;
	}

	// Property accessors
	@Id
	@Column(name = "reimbursed_id", unique = true, nullable = false, length = 32)
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

	@Column(name = "reserve_amount", precision = 18, scale = 3)
	public double getReserveAmount() {
		return this.reserveAmount;
	}

	public void setReserveAmount(double reserveAmount) {
		this.reserveAmount = reserveAmount;
	}

	@Column(name = "is_modified", length = 1)
	@Type(type = "true_false")
	public boolean getIsModified() {
		return isModified;
	}

	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
	}

}