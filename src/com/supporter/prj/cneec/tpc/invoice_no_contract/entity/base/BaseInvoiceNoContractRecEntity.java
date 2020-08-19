package com.supporter.prj.cneec.tpc.invoice_no_contract.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 非合同付款收发票明细base实体类
 * @Title: BaseInvoiceNoContractRecEntity
 * @author CHENHAO
 *
 */

@MappedSuperclass
public class BaseInvoiceNoContractRecEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="RECORD_ID",length=32)
	private String recordId;			//RECORD_ID		VARCHAR2(32)	主键ID
	
	@Column(name="INVOICE_ID",length=32)
	private String invoiceId;			//INVOICE_ID	VARCHAR2(32)	主表ID
	
	@Column(name="AP_INVOICE_AMOUNT",precision = 18, scale = 3)
	private Double apInvoiceAmount;		//AP_INVOICE_AMOUNT		NUMBER(18,3)	分配发票金额
	
	@Column(name="PAYMENT_ID",length=32)
	private String paymentId;			//PAYMENT_ID		VARCHAR2(32)	付款单ID
	
	@Column(name="PAYMENT_NO",length=256)
	private String paymentNo;			//PAYMENT_NO		VARCHAR2(256)	付款单号
	
	@Column(name="PAYMENT_AMOUNT",precision = 18, scale = 3)
	private Double paymentAmount;		//PAYMENT_AMOUNT	NUMBER(18,3)	付款单申请金额
	
	@Column(name="ACTUAL_PAYMENT_AMOUNT",precision = 18, scale = 3)
	private Double actualPaymentAmount;		//ACTUAL_PAYMENT_AMOUNT	NUMBER(18,3)	实际付款金额
	
	@Column(name="SUM_RECEIVED_AMOUNT",precision = 18, scale = 3)
	private Double sumReceivedAmount;		//SUM_RECEIVED_AMOUNT	NUMBER(18,3)	累计已收到发票金额
	
	@Column(name="IS_END_UP")
	private Integer isEndUp;			//IS_END_UP 	NUMBER		是否收完（1：是，0：否）
	
	@Column(name="INVOICE_DESC",length=512)
	private String invoiceDesc;			//INVOICE_DESC		VARCHAR2(32)		发票描述

	@Column(name="CURRENCY_TYPE_CODE", length=32)
	private String currencyTypeCode;	//CURRENCY_TYPE_CODE	VARCHAR2(32)	币别CODE
	
	@Column(name="CURRENCY_TYPE", length=32)
	private String currencyType;		//CURRENCY_TYPE		VARCHAR2(32)		币别名称
	
	@Column(name="AP_INVOICE_AMOUNT_F", precision = 18, scale = 3)
	private Double apInvoiceAmountF;		//AP_INVOICE_AMOUNT_F	NUMBER(18,3)	分配发票原币金额
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getApInvoiceAmount() {
		return apInvoiceAmount;
	}

	public void setApInvoiceAmount(Double apInvoiceAmount) {
		this.apInvoiceAmount = apInvoiceAmount;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Double getActualPaymentAmount() {
		return actualPaymentAmount;
	}

	public void setActualPaymentAmount(Double actualPaymentAmount) {
		this.actualPaymentAmount = actualPaymentAmount;
	}

	public Double getSumReceivedAmount() {
		return sumReceivedAmount;
	}

	public void setSumReceivedAmount(Double sumReceivedAmount) {
		this.sumReceivedAmount = sumReceivedAmount;
	}

	public Integer getIsEndUp() {
		return isEndUp;
	}

	public void setIsEndUp(Integer isEndUp) {
		this.isEndUp = isEndUp;
	}

	public String getInvoiceDesc() {
		return invoiceDesc;
	}

	public void setInvoiceDesc(String invoiceDesc) {
		this.invoiceDesc = invoiceDesc;
	}

	public String getCurrencyTypeCode() {
		return currencyTypeCode;
	}

	public void setCurrencyTypeCode(String currencyTypeCode) {
		this.currencyTypeCode = currencyTypeCode;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public Double getApInvoiceAmountF() {
		return apInvoiceAmountF;
	}

	public void setApInvoiceAmountF(Double apInvoiceAmountF) {
		this.apInvoiceAmountF = apInvoiceAmountF;
	}
	
	
	
	
}
