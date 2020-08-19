package com.supporter.prj.cneec.tpc.invoice.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-11-22 16:25:15
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseInvoiceRec  implements Serializable {
	private static final long serialVersionUID = 1L; 
	// Fields

	private String recordId;
	private String invoiceId;
	private String contractId;
	private Double apInvoiceAmount;
	private Double sumSettlementAmount;
	private Double sumInvoiceAmount;
	private Double contractAmount;
	private String invoiceNote;
	 
	// Constructors

	/** default constructor */
	public BaseInvoiceRec() {
	}
	
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseInvoiceRec(String recordId){
		this.recordId = recordId;
	}
	
	/** full constructor */
	public BaseInvoiceRec(String invoiceId, String contractId,
			Double apInvoiceAmount, Double sumSettlementAmount,
			Double sumInvoiceAmount, Double contractAmount, String invoiceNote) {
		this.invoiceId = invoiceId;
		this.contractId = contractId;
		this.apInvoiceAmount = apInvoiceAmount;
		this.sumSettlementAmount = sumSettlementAmount;
		this.sumInvoiceAmount = sumInvoiceAmount;
		this.contractAmount = contractAmount;
		this.invoiceNote = invoiceNote;
	}
	
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "assigned")
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "INVOICE_ID", length = 32)
	public String getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "CONTRACT_ID", length = 128)
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "AP_INVOICE_AMOUNT", precision = 18, scale = 3)
	public Double getApInvoiceAmount() {
		return this.apInvoiceAmount;
	}

	public void setApInvoiceAmount(Double apInvoiceAmount) {
		this.apInvoiceAmount = apInvoiceAmount;
	}

	@Column(name = "SUM_SETTLEMENT_AMOUNT", precision = 18, scale = 3)
	public Double getSumSettlementAmount() {
		return this.sumSettlementAmount;
	}

	public void setSumSettlementAmount(Double sumSettlementAmount) {
		this.sumSettlementAmount = sumSettlementAmount;
	}

	@Column(name = "SUM_INVOICE_AMOUNT", precision = 18, scale = 3)
	public Double getSumInvoiceAmount() {
		return this.sumInvoiceAmount;
	}

	public void setSumInvoiceAmount(Double sumInvoiceAmount) {
		this.sumInvoiceAmount = sumInvoiceAmount;
	}

	@Column(name = "CONTRACT_AMOUNT", precision = 18, scale = 3)
	public Double getContractAmount() {
		return this.contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	@Column(name = "INVOICE_NOTE", length = 128)
	public String getInvoiceNote() {
		return this.invoiceNote;
	}

	public void setInvoiceNote(String invoiceNote) {
		this.invoiceNote = invoiceNote;
	}
	 
}
