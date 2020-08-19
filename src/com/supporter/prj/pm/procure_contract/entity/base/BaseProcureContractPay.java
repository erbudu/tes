package com.supporter.prj.pm.procure_contract.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: 付款条款
 * @Description: PM_PROCURE_CONTRACT_PAY, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:04:17
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseProcureContractPay  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *PAY_ID.
	 */
	@Id
	@GeneratedValue(generator = "uuid")
  	@GenericGenerator(name = "uuid",  strategy = "uuid")
	@Column(name = "PAY_ID", nullable = false, length = 32)
	private java.lang.String payId;
	/**
	 *CONTRACT_ID.
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	private java.lang.String contractId;
	/**
	 *PAY_ITEM.
	 */
	@Column(name = "PAY_ITEM", nullable = true, length = 128)
	private java.lang.String payItem;
	/**
	 *PAY_ITEM_ID.
	 */
	@Column(name = "PAY_ITEM_ID", nullable = true, length = 32)
	private java.lang.String payItemId;
	/**
	 *CURRENCY.
	 */
	@Column(name = "CURRENCY", nullable = true, length = 32)
	private java.lang.String currency;
	/**
	 *CURRENCY_ID.
	 */
	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	private java.lang.String currencyId;
	/**
	 *AMOUNT.
	 */
	@Column(name = "AMOUNT", nullable = true, precision = 10, scale = 3)
	private double amount;
	/**
	 *PROPORTION_IN.
	 */
	@Column(name = "PROPORTION_IN", nullable = true, precision = 10, scale = 3)
	private double proportionIn;
	/**
	 *PROPORTION_OUT.
	 */
	@Column(name = "PROPORTION_OUT", nullable = true, precision = 10, scale = 3)
	private double proportionOut;
	/**
	 *PAY_PROPORTION.
	 */
	@Column(name = "PAY_PROPORTION", nullable = true, precision = 10, scale = 3)
	private double payProportion;
	/**
	 *RECEIVE_RMB_AMOUNT.
	 */
	@Column(name = "RECEIVE_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double receiveRmbAmount;
	/**
	 *PAY_DATE.
	 */
	@Column(name = "PAY_DATE", nullable = true)
	private java.util.Date payDate;
	/**
	 *REMARK.
	 */
	@Column(name = "REMARK", nullable = true, length = 64)
	private java.lang.String remark;
	
	/**
	 *DEDUCTIONS.
	 */
	@Column(name = "DEDUCTIONS", nullable = true, length = 32)
	private String deductions;
	
	/**
	 *DEDUCTION_RULE.
	 */
	@Column(name = "DEDUCTION_RULE", nullable = true, length = 2)
	private String deductionRule;
	
	/**
	 *RECEIVE_RMB_AMOUNT.
	 */
	@Column(name = "RULE_DEFINITION", nullable = true, precision = 10, scale = 3)
	private double ruleDefinition;
	/**
	 *方法: 取得PAY_ID.
	 *@return java.lang.String  PAY_ID
	 */
	public java.lang.String getPayId() {
		return this.payId;
	}

	public String getDeductions() {
		return deductions;
	}

	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}
	
	public String getDeductionRule() {
		return deductionRule;
	}

	public void setDeductionRule(String deductionRule) {
		this.deductionRule = deductionRule;
	}

	/**
	 *方法: 设置PAY_ID.
	 *@param payId  PAY_ID
	 */
	public void setPayId(java.lang.String payId) {
		this.payId = payId;
	}
	/**
	 *方法: 取得CONTRACT_ID.
	 *@return java.lang.String  CONTRACT_ID
	 */
	public java.lang.String getContractId() {
		return this.contractId;
	}

	/**
	 *方法: 设置CONTRACT_ID.
	 *@param contractId  CONTRACT_ID
	 */
	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	/**
	 *方法: 取得PAY_ITEM.
	 *@return java.lang.String  PAY_ITEM
	 */
	public java.lang.String getPayItem() {
		return this.payItem;
	}

	/**
	 *方法: 设置PAY_ITEM.
	 *@param payItem  PAY_ITEM
	 */
	public void setPayItem(java.lang.String payItem) {
		this.payItem = payItem;
	}
	/**
	 *方法: 取得PAY_ITEM_ID.
	 *@return java.lang.String  PAY_ITEM_ID
	 */
	public java.lang.String getPayItemId() {
		return this.payItemId;
	}

	/**
	 *方法: 设置PAY_ITEM_ID.
	 *@param payItemId  PAY_ITEM_ID
	 */
	public void setPayItemId(java.lang.String payItemId) {
		this.payItemId = payItemId;
	}
	/**
	 *方法: 取得CURRENCY.
	 *@return java.lang.String  CURRENCY
	 */
	public java.lang.String getCurrency() {
		return this.currency;
	}

	/**
	 *方法: 设置CURRENCY.
	 *@param currency  CURRENCY
	 */
	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}
	/**
	 *方法: 取得CURRENCY_ID.
	 *@return java.lang.String  CURRENCY_ID
	 */
	public java.lang.String getCurrencyId() {
		return this.currencyId;
	}

	/**
	 *方法: 设置CURRENCY_ID.
	 *@param currencyId  CURRENCY_ID
	 */
	public void setCurrencyId(java.lang.String currencyId) {
		this.currencyId = currencyId;
	}
	/**
	 *方法: 取得AMOUNT.
	 *@return double  AMOUNT
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 *方法: 设置AMOUNT.
	 *@param amount  AMOUNT
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 *方法: 取得PROPORTION_IN.
	 *@return double  PROPORTION_IN
	 */
	public double getProportionIn() {
		return this.proportionIn;
	}

	/**
	 *方法: 设置PROPORTION_IN.
	 *@param proportionIn  PROPORTION_IN
	 */
	public void setProportionIn(double proportionIn) {
		this.proportionIn = proportionIn;
	}
	/**
	 *方法: 取得PROPORTION_OUT.
	 *@return double  PROPORTION_OUT
	 */
	public double getProportionOut() {
		return this.proportionOut;
	}

	/**
	 *方法: 设置PROPORTION_OUT.
	 *@param proportionIn  PROPORTION_OUT
	 */
	public void setProportionOut(double proportionOut) {
		this.proportionOut = proportionOut;
	}
	/**
	 *方法: 取得PAY_PROPORTION.
	 *@return double  PAY_PROPORTION
	 */
	public double getPayProportion() {
		return this.payProportion;
	}

	/**
	 *方法: 设置PAY_PROPORTION.
	 *@param payProportion  PAY_PROPORTION
	 */
	public void setPayProportion(double payProportion) {
		this.payProportion = payProportion;
	}
	/**
	 *方法: 取得RECEIVE_RMB_AMOUNT.
	 *@return double  RECEIVE_RMB_AMOUNT
	 */
	public double getReceiveRmbAmount() {
		return this.receiveRmbAmount;
	}

	/**
	 *方法: 设置RECEIVE_RMB_AMOUNT.
	 *@param receiveRmbAmount  RECEIVE_RMB_AMOUNT
	 */
	public void setReceiveRmbAmount(double receiveRmbAmount) {
		this.receiveRmbAmount = receiveRmbAmount;
	}
	/**
	 *方法: 取得PAY_DATE.
	 *@return java.util.Date  PAY_DATE
	 */
	public java.util.Date getPayDate() {
		return this.payDate;
	}

	/**
	 *方法: 设置PAY_DATE.
	 *@param payDate  PAY_DATE
	 */
	public void setPayDate(java.util.Date payDate) {
		this.payDate = payDate;
	}
	/**
	 *方法: 取得REMARK.
	 *@return java.lang.String  REMARK
	 */
	public java.lang.String getRemark() {
		return this.remark;
	}

	/**
	 *方法: 设置REMARK.
	 *@param remark  REMARK
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	/**
	 *方法: 取得RULE_DEFINITION
	 *@return double  RULE_DEFINITION
	 */
	public double getRuleDefinition() {
		return this.ruleDefinition;
	}

	/**
	 *方法: 设置RECEIVE_RMB_AMOUNT.
	 *@param receiveRmbAmount  RECEIVE_RMB_AMOUNT
	 */
	public void setRuleDefinition(double ruleDefinition) {
		this.ruleDefinition = ruleDefinition;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseProcureContractPay() {
	
	}
	
	/**
	 * 构造函数.
	 * @param payId
	 */
	public BaseProcureContractPay(String payId) {
		this.payId = payId;
	} 
}
