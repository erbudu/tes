package com.supporter.prj.cneec.tpc.contract_change.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public class BaseContractChangeCollectionTerms implements Serializable {

	private static final long serialVersionUID = 1L;
	private String termsId;
	private String chId;
	private String termsOldId;
//	private String changeId;
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
	public BaseContractChangeCollectionTerms() {
	}

	/**
	 * 构造函数.
	 *
	 * @param termsId
	 */
	public BaseContractChangeCollectionTerms(String termsId) {
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
	 * GET方法: 取得CHANGE_ID.
	 *
	 * @return: String  CHANGE_ID
	 */
//	@Column(name = "CHANGE_ID", nullable = true, length = 32)
//	public String getChangeId() {
//		return this.changeId;
//	}
//
//	/**
//	 * SET方法: 设置CHANGE_ID.
//	 *
//	 * @param: String  CHANGE_ID
//	 */
//	public void setChangeId(String changeId) {
//		this.changeId = changeId;
//	}
	/**
	 * GET方法: 取得CH_ID.
	 *
	 * @return: String  CH_ID
	 */
	@Column(name = "CH_ID", nullable = true, length = 32)
	public String getChId() {
		return this.chId;
	}

	/**
	 * SET方法: 设置CH_ID.
	 *
	 * @param: String  CH_ID
	 */
	public void setChId(String chId) {
		this.chId = chId;
	}
	
	@Column(name = "TERMS_OLD_ID", nullable = true, length = 32)
	public String getTermsOldId() {
		return this.termsOldId;
	}

	public void setTermsOldId(String termsOldId) {
		this.termsOldId = termsOldId;
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
