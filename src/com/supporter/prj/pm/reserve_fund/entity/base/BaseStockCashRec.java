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
public class BaseStockCashRec implements java.io.Serializable {

	// Fields

	private String id;
	private String stockId;
	private String currencyId;
	private String currency;
	private String nature;
	private Date reserveDate;
	private double reserveAmount;
	private String voucherNo;
	private String applyReason;

	// Constructors

	/** default constructor */
	public BaseStockCashRec() {
	}

	/** minimal constructor */
	public BaseStockCashRec(String id) {
		this.id = id;
	}

	/** full constructor */
	public BaseStockCashRec(String id,String stockId, String currency, String nature,
			Date reserveDate, double reserveAmount) {
		this.id = id;
		this.stockId = stockId;
		this.currency = currency;
		this.nature = nature;
		this.reserveDate = reserveDate;
		this.reserveAmount = reserveAmount;
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
	
	@Column(name = "stock_id", length = 32)
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

	@Column(name = "nature", length = 32)
	public String getNature() {
		return this.nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reserve_date", length = 19)
	public Date getReserveDate() {
		return this.reserveDate;
	}

	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}

	@Column(name = "reserve_amount", precision = 18, scale = 3)
	public double getReserveAmount() {
		return this.reserveAmount;
	}

	public void setReserveAmount(double reserveAmount) {
		this.reserveAmount = reserveAmount;
	}
	
	@Column(name = "voucher_no", length = 64)
	public String getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	@Column(name = "apply_reason", length = 512)
	public String getApplyReason() {
		return this.applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

}