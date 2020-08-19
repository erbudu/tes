package com.supporter.prj.pm.fund_appropriation.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseFundReceiptActual implements java.io.Serializable{

	// primary key
	private String actualId;//主键

	// fields
	private String fundId;
	private String sourceId;
	private String source;
	private double amountUsd;
	private double amountLkr;
	private double usdRate;
	private double usdAmount;
	private double allAmount;

	
	/** default constructor */
	public BaseFundReceiptActual() {
	}

	/** minimal constructor */
	public BaseFundReceiptActual(String actualId) {
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

	@Column(name = "source_id", length = 32)
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "source", length = 64)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
	
	@Column(name = "all_amount", precision = 18, scale = 3)
	public double getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(double allAmount) {
		this.allAmount = allAmount;
	}
}
