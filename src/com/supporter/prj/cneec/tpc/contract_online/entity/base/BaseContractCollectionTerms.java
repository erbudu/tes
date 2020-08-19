package com.supporter.prj.cneec.tpc.contract_online.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: TPC_CONTRACT_COLLECTION_TERMS,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BaseContractCollectionTerms implements Serializable {

	private static final long serialVersionUID = 1L;
	private String termsId;
	private String contractId;
	private String receivables;
	private String currency;
	private String currencyId;
	private double receiveAmount;
	private double receiveProportion;
	private double receiveRmbAmount;
	private String receiveDate;
	private String collectionTerms;

	/**
	 * 无参构造函数.
	 */
	public BaseContractCollectionTerms() {
	}

	/**
	 * 构造函数.
	 *
	 * @param termsId
	 */
	public BaseContractCollectionTerms(String termsId) {
		this.termsId = termsId;
	}

	/**
	 * GET方法: 取得TERMS_ID.
	 *
	 * @return: String  TERMS_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "TERMS_ID", nullable = false, length = 32)
	public String getTermsId() {
		return this.termsId;
	}

	/**
	 * SET方法: 设置TERMS_ID.
	 *
	 * @param: String  TERMS_ID
	 */
	public void setTermsId(String termsId) {
		this.termsId = termsId;
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
	 * GET方法: 取得RECEIVABLES.
	 *
	 * @return: String  RECEIVABLES
	 */
	@Column(name = "RECEIVABLES", nullable = true, length = 128)
	public String getReceivables() {
		return this.receivables;
	}

	/**
	 * SET方法: 设置RECEIVABLES.
	 *
	 * @param: String  RECEIVABLES
	 */
	public void setReceivables(String receivables) {
		this.receivables = receivables;
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
	 * GET方法: 取得RECEIVE_AMOUNT.
	 *
	 * @return: double  RECEIVE_AMOUNT
	 */
	@Column(name = "RECEIVE_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getReceiveAmount() {
		return this.receiveAmount;
	}

	/**
	 * SET方法: 设置RECEIVE_AMOUNT.
	 *
	 * @param: double  RECEIVE_AMOUNT
	 */
	public void setReceiveAmount(double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	/**
	 * GET方法: 取得RECEIVE_PROPORTION.
	 *
	 * @return: double  RECEIVE_PROPORTION
	 */
	@Column(name = "RECEIVE_PROPORTION", nullable = true, precision = 10, scale = 3)
	public double getReceiveProportion() {
		return this.receiveProportion;
	}

	/**
	 * SET方法: 设置RECEIVE_PROPORTION.
	 *
	 * @param: double  RECEIVE_PROPORTION
	 */
	public void setReceiveProportion(double receiveProportion) {
		this.receiveProportion = receiveProportion;
	}

	/**
	 * GET方法: 取得RECEIVE_RMB_AMOUNT.
	 *
	 * @return: double  RECEIVE_RMB_AMOUNT
	 */
	@Column(name = "RECEIVE_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getReceiveRmbAmount() {
		return this.receiveRmbAmount;
	}

	/**
	 * SET方法: 设置RECEIVE_RMB_AMOUNT.
	 *
	 * @param: double  RECEIVE_RMB_AMOUNT
	 */
	public void setReceiveRmbAmount(double receiveRmbAmount) {
		this.receiveRmbAmount = receiveRmbAmount;
	}

	/**
	 * GET方法: 取得RECEIVE_DATE.
	 *
	 * @return: String  RECEIVE_DATE
	 */
	@Column(name = "RECEIVE_DATE", nullable = true, length = 27)
	public String getReceiveDate() {
		return this.receiveDate;
	}

	/**
	 * SET方法: 设置RECEIVE_DATE.
	 *
	 * @param: String  RECEIVE_DATE
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * GET方法: 取得COLLECTION_TERMS.
	 *
	 * @return: String  COLLECTION_TERMS
	 */
	@Column(name = "COLLECTION_TERMS", nullable = true, length = 512)
	public String getCollectionTerms() {
		return this.collectionTerms;
	}

	/**
	 * SET方法: 设置COLLECTION_TERMS.
	 *
	 * @param: String  COLLECTION_TERMS
	 */
	public void setCollectionTerms(String collectionTerms) {
		this.collectionTerms = collectionTerms;
	}

}
