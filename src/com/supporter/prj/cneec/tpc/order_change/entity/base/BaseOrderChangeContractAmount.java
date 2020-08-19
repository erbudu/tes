package com.supporter.prj.cneec.tpc.order_change.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseOrderChangeContractAmount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String amountId;
	private String chId;
	private String amountOldId;
	private double originalAmount;
	private String currency;
	private String currencyId;
	private double rmbAmount;
	private double rate;

	/**
	 * 无参构造函数.
	 */
	public BaseOrderChangeContractAmount() {
	}

	/**
	 * 构造函数.
	 *
	 * @param amountId
	 */
	public BaseOrderChangeContractAmount(String amountId) {
		this.amountId = amountId;
	}

	/**
	 * GET方法: 取得AMOUNT_ID.
	 *
	 * @return: String  AMOUNT_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "AMOUNT_ID", nullable = false, length = 32)
	public String getAmountId() {
		return this.amountId;
	}

	/**
	 * SET方法: 设置AMOUNT_ID.
	 *
	 * @param: String  AMOUNT_ID
	 */
	public void setAmountId(String amountId) {
		this.amountId = amountId;
	}
	/**
	 * GET方法: 取得CH_ID.
	 *
	 * @return: String  CH_ID
	 */
	@Column(name = "CH_ID", nullable = true, length = 32)
	public String getChId() {
		return this.chId;
	}
	
	@Column(name = "AMOUNT_OLD_ID", nullable = true, length = 32)
	public String getAmountOldId() {
		return this.amountOldId;
	}

	public void setAmountOldId(String amountOldId) {
		this.amountOldId = amountOldId;
	}

	/**
	 * SET方法: 设置CH_ID.
	 *
	 * @param: String  CH_ID
	 */
	public void setChId(String chId) {
		this.chId = chId;
	}

	/**
	 * GET方法: 取得ORIGINAL_AMOUNT.
	 *
	 * @return: double  ORIGINAL_AMOUNT
	 */
	@Column(name = "ORIGINAL_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getOriginalAmount() {
		return this.originalAmount;
	}

	/**
	 * SET方法: 设置ORIGINAL_AMOUNT.
	 *
	 * @param: double  ORIGINAL_AMOUNT
	 */
	public void setOriginalAmount(double originalAmount) {
		this.originalAmount = originalAmount;
	}

	/**
	 * GET方法: 取得CURRENCY.
	 *
	 * @return: String  CURRENCY
	 */
	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * SET方法: 设置CURRENCY.
	 *
	 * @param: String  CURRENCY
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * GET方法: 取得CURRENCY_ID.
	 *
	 * @return: String  CURRENCY_ID
	 */
	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	/**
	 * SET方法: 设置CURRENCY_ID.
	 *
	 * @param: String  CURRENCY_ID
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * GET方法: 取得RMB_AMOUNT.
	 *
	 * @return: double  RMB_AMOUNT
	 */
	@Column(name = "RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRmbAmount() {
		return this.rmbAmount;
	}

	/**
	 * SET方法: 设置RMB_AMOUNT.
	 *
	 * @param: double  RMB_AMOUNT
	 */
	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	/**
	 * GET方法: 取得RATE.
	 *
	 * @return: double  RATE
	 */
	@Column(name = "RATE", nullable = true, precision = 10, scale = 4)
	public double getRate() {
		return this.rate;
	}

	/**
	 * SET方法: 设置RATE.
	 *
	 * @param: double  RATE
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

}
