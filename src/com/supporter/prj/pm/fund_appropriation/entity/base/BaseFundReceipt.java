package com.supporter.prj.pm.fund_appropriation.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseFundReceipt implements java.io.Serializable{

	// primary key
	private String receiptId;//主键

	// fields
	private String fundId;
	private String sourceId;
	private String source;
	private String currency;
	private String currencyId;
	private double incomeAmount;
	private double exchangeRate;
	private double rmbAmount;
	private double allAmount;

	
	/** default constructor */
	public BaseFundReceipt() {
	}

	/** minimal constructor */
	public BaseFundReceipt(String receiptId) {
		this.receiptId = receiptId;
	}
	
	/** full constructor */
	public BaseFundReceipt(String receiptId, String fundId,String sourceId,String source,
			String currency,String currencyId,double incomeAmount,double exchangeRate,double rmbAmount) {
		this.receiptId = receiptId;
		this.fundId = fundId;
		this.sourceId = sourceId;
		this.source = source;
		this.currency = currency;
		this.currencyId = currencyId;
		this.incomeAmount = incomeAmount;
		this.exchangeRate = exchangeRate;
		this.rmbAmount = rmbAmount;

	}
	@Id
	@Column(name = "receipt_id", unique = true, nullable = false, length = 32)
	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
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

	@Column(name = "income_amount", precision = 18, scale = 3)
	public double getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	@Column(name = "exchange_rate", precision = 18, scale = 4)
	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
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

	@Column(name = "rmb_amount", precision = 18, scale = 3)
	public double getRmbAmount() {
		return rmbAmount;
	}

	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}
	
	@Column(name = "all_amount", precision = 18, scale = 3)
	public double getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(double allAmount) {
		this.allAmount = allAmount;
	}
}
