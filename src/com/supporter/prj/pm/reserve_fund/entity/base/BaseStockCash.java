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
public class BaseStockCash implements java.io.Serializable {

	// Fields

	private String stockId;
	private String currencyId;
	private String currency;
	private double reserveAmount;
	private boolean isModified = true; // 是否发生了更改
	private java.lang.String prjId; // 项目id
	private java.lang.String prjName; // 项目名称

	// Constructors

	/** default constructor */
	public BaseStockCash() {
	}

	/** minimal constructor */
	public BaseStockCash(String stockId) {
		this.stockId = stockId;
	}

	/** full constructor */
	public BaseStockCash(String stockId, String currency, double reserveAmount, boolean isModified, String prjId,
			String prjName) {
		this.stockId = stockId;
		this.currency = currency;
		this.reserveAmount = reserveAmount;
		this.isModified = isModified;
		this.prjId = prjId;
		this.prjName = prjName;
	}

	// Property accessors
	@Id
	@Column(name = "stock_id", unique = true, nullable = false, length = 32)
	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
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

	@Column(name = "prj_id", length = 32)
	public java.lang.String getPrjId() {
		return prjId;
	}
	public void setPrjId(java.lang.String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "prj_name", length = 128)
	public java.lang.String getPrjName() {
		return prjName;
	}
	public void setPrjName(java.lang.String prjName) {
		this.prjName = prjName;
	}
}