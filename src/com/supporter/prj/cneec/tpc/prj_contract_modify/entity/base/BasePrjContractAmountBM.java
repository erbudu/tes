package com.supporter.prj.cneec.tpc.prj_contract_modify.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_PRJ_CONTRACT_AMOUNT,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-03-15
 * @version V1.0
 */
@MappedSuperclass
public class BasePrjContractAmountBM implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bmId;
	private String amountId;
	private String changeId;
	private double originalAmount;
	private String currency;
	private String currencyId;
	private double rmbAmount;
	private double rate;

	/**
	 * 无参构造函数.
	 */
	public BasePrjContractAmountBM() {
	}

	/**
	 * 构造函数.
	 *
	 * @param bmId
	 */
	public BasePrjContractAmountBM(String bmId) {
		this.bmId = amountId;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "BM_ID", nullable = true, length = 32)
	public String getBmId() {
		return bmId;
	}

	public void setBmId(String bmId) {
		this.bmId = bmId;
	}

	@Column(name = "AMOUNT_ID", nullable = false, length = 32)
	public String getAmountId() {
		return this.amountId;
	}

	public void setAmountId(String amountId) {
		this.amountId = amountId;
	}


	@Column(name = "CHANGE_ID", nullable = true, length = 32)
	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	@Column(name = "ORIGINAL_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getOriginalAmount() {
		return this.originalAmount;
	}

	public void setOriginalAmount(double originalAmount) {
		this.originalAmount = originalAmount;
	}

	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRmbAmount() {
		return this.rmbAmount;
	}

	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	@Column(name = "RATE", nullable = true, precision = 10, scale = 4)
	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
