package com.supporter.prj.cneec.tpc.prj_contract_modify.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_PRJ_CONTRACT_COLLEC_TERMS,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-03-15
 * @version V1.0
 */
@MappedSuperclass
public class BasePrjContractCollectionTermsBM implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bmId;
	private String termsId;
	private String changeId;
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
	public BasePrjContractCollectionTermsBM() {
	}

	/**
	 * 构造函数.
	 *
	 * @param bmId
	 */
	public BasePrjContractCollectionTermsBM(String bmId) {
		this.bmId = bmId;
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

	@Column(name = "TERMS_ID", nullable = false, length = 32)
	public String getTermsId() {
		return this.termsId;
	}

	public void setTermsId(String termsId) {
		this.termsId = termsId;
	}

	@Column(name = "CHANGE_ID", nullable = true, length = 32)
	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	@Column(name = "RECEIVABLES", nullable = true, length = 128)
	public String getReceivables() {
		return this.receivables;
	}

	public void setReceivables(String receivables) {
		this.receivables = receivables;
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

	@Column(name = "RECEIVE_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getReceiveAmount() {
		return this.receiveAmount;
	}

	public void setReceiveAmount(double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	@Column(name = "RECEIVE_PROPORTION", nullable = true, precision = 10, scale = 3)
	public double getReceiveProportion() {
		return this.receiveProportion;
	}

	public void setReceiveProportion(double receiveProportion) {
		this.receiveProportion = receiveProportion;
	}

	@Column(name = "RECEIVE_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getReceiveRmbAmount() {
		return this.receiveRmbAmount;
	}

	public void setReceiveRmbAmount(double receiveRmbAmount) {
		this.receiveRmbAmount = receiveRmbAmount;
	}

	@Column(name = "RECEIVE_DATE", nullable = true, length = 27)
	public String getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	@Column(name = "COLLECTION_TERMS", nullable = true, length = 512)
	public String getCollectionTerms() {
		return this.collectionTerms;
	}

	public void setCollectionTerms(String collectionTerms) {
		this.collectionTerms = collectionTerms;
	}

}
