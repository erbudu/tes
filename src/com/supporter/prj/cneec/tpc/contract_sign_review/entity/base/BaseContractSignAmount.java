package com.supporter.prj.cneec.tpc.contract_sign_review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_AMOUNT,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-04-10
 * @version V1.0
 */
@MappedSuperclass
public class BaseContractSignAmount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String amountId;
	private String inforId;
	private String signId;
	private int inforType;
	private String currency;
	private String currencyId;
	private double originalAmount;
	private double rate;
	private double rmbAmount;
	private double executeRate;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignAmount() {
	}

	/**
	 * 构造函数.
	 *
	 * @param amountId
	 */
	public BaseContractSignAmount(String amountId) {
		this.amountId = amountId;
	}

	public BaseContractSignAmount(String inforId, String signId, int inforType, String currency, String currencyId) {
		this.inforId = inforId;
		this.signId = signId;
		this.inforType = inforType;
		this.currency = currency;
		this.currencyId = currencyId;
	}

	@Id
	@Column(name = "AMOUNT_ID", nullable = false, length = 32)
	public String getAmountId() {
		return this.amountId;
	}

	public void setAmountId(String amountId) {
		this.amountId = amountId;
	}

	@Column(name = "INFOR_ID", nullable = false, length = 32)
	public String getInforId() {
		return this.inforId;
	}

	public void setInforId(String inforId) {
		this.inforId = inforId;
	}

	@Column(name = "SIGN_ID", nullable = true, length = 32)
	public String getSignId() {
		return this.signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	@Column(name = "INFOR_TYPE", nullable = true, precision = 10)
	public int getInforType() {
		return this.inforType;
	}

	public void setInforType(int inforType) {
		this.inforType = inforType;
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

	@Column(name = "ORIGINAL_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getOriginalAmount() {
		return this.originalAmount;
	}

	public void setOriginalAmount(double originalAmount) {
		this.originalAmount = originalAmount;
	}

	@Column(name = "RATE", nullable = true, precision = 10, scale = 4)
	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Column(name = "RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRmbAmount() {
		return this.rmbAmount;
	}

	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	@Column(name = "EXECUTE_RATE", nullable = true, precision = 10, scale = 4)
	public double getExecuteRate() {
		return this.executeRate;
	}

	public void setExecuteRate(double executeRate) {
		this.executeRate = executeRate;
	}

}
