package com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 贸易印花税金额表,字段与数据库字段一一对应.
 * @author: LEGO
 * @date: 2020-02-03
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractStampTaxAmount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String stampAmountId;
	private String contractId;
	private String confirmId;
	private String confirmDate;
	private String realPayDate;
	private String stampTaxItemId;
	private double stampAmount;
	private String createdDate;
	private String changeId;
	private double contractAmount;

	/**
	 * 无参构造函数.
	 */
	public BaseContractStampTaxAmount() {
	}

	/**
	 * 构造函数.
	 *
	 * @param stampAmountId
	 */
	public BaseContractStampTaxAmount(String stampAmountId) {
		this.stampAmountId = stampAmountId;
	}

	/**
	 * 方法: 取得主键ID.
	 * @return: java.lang.String 主键ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "STAMP_AMOUNT_ID", nullable = false, length = 32)
	public String getStampAmountId() {
		return this.stampAmountId;
	}

	/**
	 * 方法: 设置主键ID.
	 * @param: java.lang.String 主键ID
	 */
	public void setStampAmountId(String stampAmountId) {
		this.stampAmountId = stampAmountId;
	}

	/**
	 * 方法: 取得合同ID.
	 * @return: java.lang.String 合同ID
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 128)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * 方法: 设置合同ID.
	 * @param: java.lang.String 合同ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * 方法: 取得确认单ID.
	 * @return: java.lang.String 确认单ID
	 */
	@Column(name = "CONFIRM_ID", nullable = true, length = 32)
	public String getConfirmId() {
		return this.confirmId;
	}

	/**
	 * 方法: 设置确认单ID.
	 * @param: java.lang.String 确认单ID
	 */
	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}

	/**
	 * 方法: 取得确认时间.
	 * @return: java.lang.String 确认时间
	 */
	@Column(name = "CONFIRM_DATE", nullable = true, length = 32)
	public String getConfirmDate() {
		return this.confirmDate;
	}

	/**
	 * 方法: 设置确认时间.
	 * @param: java.lang.String 确认时间
	 */
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
	 * 方法: 取得实际支付时间.
	 * @return: java.lang.String 实际支付时间
	 */
	@Column(name = "REAL_PAY_DATE", nullable = true, length = 32)
	public String getRealPayDate() {
		return this.realPayDate;
	}

	/**
	 * 方法: 设置实际支付时间.
	 * @param: java.lang.String 实际支付时间
	 */
	public void setRealPayDate(String realPayDate) {
		this.realPayDate = realPayDate;
	}

	/**
	 * 方法: 取得印花税税目ID.
	 * @return: java.lang.String 印花税税目ID
	 */
	@Column(name = "STAMP_TAX_ITEM_ID", nullable = true, length = 32)
	public String getStampTaxItemId() {
		return this.stampTaxItemId;
	}

	/**
	 * 方法: 设置印花税税目ID.
	 * @param: java.lang.String 印花税税目ID
	 */
	public void setStampTaxItemId(String stampTaxItemId) {
		this.stampTaxItemId = stampTaxItemId;
	}

	/**
	 * 方法: 取得印花税金额.
	 * @return: java.math.BigDecimal 印花税金额
	 */
	@Column(name = "STAMP_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getStampAmount() {
		return this.stampAmount;
	}

	/**
	 * 方法: 设置印花税金额.
	 * @param: java.math.BigDecimal 印花税金额
	 */
	public void setStampAmount(double stampAmount) {
		this.stampAmount = stampAmount;
	}

	/**
	 * 方法: 取得创建时间.
	 * @return: java.lang.String 创建时间
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * 方法: 设置创建时间.
	 * @param: java.lang.String 创建时间
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 方法: 取得变更ID.
	 * @return: java.lang.String 变更ID
	 */
	@Column(name = "CHANGE_ID", nullable = true, length = 32)
	public String getChangeId() {
		return this.changeId;
	}

	/**
	 * 方法: 设置变更ID.
	 * @param: java.lang.String 变更ID
	 */
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	/**
	 * 方法: 取得合同金额.
	 * @return: java.math.BigDecimal 合同金额
	 */
	@Column(name = "CONTRACT_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getContractAmount() {
		return this.contractAmount;
	}

	/**
	 * 方法: 设置合同金额.
	 * @param: java.math.BigDecimal 合同金额
	 */
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}

}
