package com.supporter.prj.cneec.tpc.derivative_contract_online.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: TPC_CONTRACT_CONTRACT_AMOUNT,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BaseDerivativeContractContractAmount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String amountId;
	private String contractId;
	private double originalAmount;
	private String currency;
	private String currencyId;
	private double rmbAmount;
	private double rate;
	private double taxRebateRate;

	/**
	 * 无参构造函数.
	 */
	public BaseDerivativeContractContractAmount() {
	}

	/**
	 * 构造函数.
	 *
	 * @param amountId
	 */
	public BaseDerivativeContractContractAmount(String amountId) {
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
	 * GET方法: 取得CONTRACT_ID.
	 *
	 * @return: String  CONTRACT_ID
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * SET方法: 设置CONTRACT_ID.
	 *
	 * @param: String  CONTRACT_ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
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

	/**
	 * GET方法: 取得TAX_REBATE_RATE.
	 *
	 * @return: double  TAX_REBATE_RATE
	 */
	@Column(name = "TAX_REBATE_RATE", nullable = true, precision = 10, scale = 3)
	public double getTaxRebateRate() {
		return this.taxRebateRate;
	}

	/**
	 * SET方法: 设置TAX_REBATE_RATE.
	 *
	 * @param: double  TAX_REBATE_RATE
	 */
	public void setTaxRebateRate(double taxRebateRate) {
		this.taxRebateRate = taxRebateRate;
	}

}
