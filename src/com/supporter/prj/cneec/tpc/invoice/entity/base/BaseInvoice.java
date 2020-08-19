package com.supporter.prj.cneec.tpc.invoice.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-11-22 16:25:15
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseInvoice  implements Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private String invoiceId;
	private String prjId;
	private String prjName;
	private String invoiceNo;
	private String contractorName;
	private Double invoiceAmount;
	private String auditedBy;
	private String auditedById;
	private String invoiceNote;
	private String deptId;
	private String deptName;
	private String applyPersonId;
	private String applyPerson;
	private int invoiceStatus;
	private String applyDate;
	private String confirmDate;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private int invoiceType;
	private String contractId;
	private Double assignInvoiceAmount;
	private String procId;

	// Constructors

	/** default constructor */
	public BaseInvoice() {
	}
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseInvoice(String invoiceId){
		this.invoiceId = invoiceId;
	}
	/** full constructor */
	public BaseInvoice(String prjId, String prjName, String invoiceNo,
			String contractorName, Double invoiceAmount, String auditedBy,
			String auditedById, String invoiceNote, String deptId,
			String deptName, String applyPersonId, String applyPerson,
			int invoiceStatus, String applyDate, String confirmDate,
			String createdBy, String createdById, String createdDate,
			String modifiedBy, String modifiedById, String modifiedDate,
			int invoiceType, String contractId, Double assignInvoiceAmount) {
		this.prjId = prjId;
		this.prjName = prjName;
		this.invoiceNo = invoiceNo;
		this.contractorName = contractorName;
		this.invoiceAmount = invoiceAmount;
		this.auditedBy = auditedBy;
		this.auditedById = auditedById;
		this.invoiceNote = invoiceNote;
		this.deptId = deptId;
		this.deptName = deptName;
		this.applyPersonId = applyPersonId;
		this.applyPerson = applyPerson;
		this.invoiceStatus = invoiceStatus;
		this.applyDate = applyDate;
		this.confirmDate = confirmDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.invoiceType = invoiceType;
		this.contractId = contractId;
		this.assignInvoiceAmount = assignInvoiceAmount;
	}

	// Property accessors
	
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "INVOICE_ID", unique = true, nullable = false, length = 32)
	public String getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "PRJ_ID", length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "PRJ_NAME", length = 128)
	public String getPrjName() {
		return this.prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	@Column(name = "INVOICE_NO", length = 300)
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "CONTRACTOR_NAME")
	public String getContractorName() {
		return this.contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	@Column(name = "INVOICE_AMOUNT", precision = 18, scale = 3)
	public Double getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	@Column(name = "AUDITED_BY", length = 64)
	public String getAuditedBy() {
		return this.auditedBy;
	}

	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}

	@Column(name = "AUDITED_BY_ID", length = 32)
	public String getAuditedById() {
		return this.auditedById;
	}

	public void setAuditedById(String auditedById) {
		this.auditedById = auditedById;
	}

	@Column(name = "INVOICE_NOTE", length = 512)
	public String getInvoiceNote() {
		return this.invoiceNote;
	}

	public void setInvoiceNote(String invoiceNote) {
		this.invoiceNote = invoiceNote;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "APPLY_PERSON_ID", length = 32)
	public String getApplyPersonId() {
		return this.applyPersonId;
	}

	public void setApplyPersonId(String applyPersonId) {
		this.applyPersonId = applyPersonId;
	}

	@Column(name = "APPLY_PERSON", length = 64)
	public String getApplyPerson() {
		return this.applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	@Column(name = "INVOICE_STATUS", precision = 22, scale = 0)
	public int getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@Column(name = "APPLY_DATE", length = 27)
	public String getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "CONFIRM_DATE", length = 27)
	public String getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name = "CREATED_BY", length = 64)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 64)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "INVOICE_TYPE", precision = 22, scale = 0)
	public int getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "CONTRACT_ID")
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "ASSIGN_INVOICE_AMOUNT", precision = 18, scale = 3)
	public Double getAssignInvoiceAmount() {
		return this.assignInvoiceAmount;
	}

	public void setAssignInvoiceAmount(Double assignInvoiceAmount) {
		this.assignInvoiceAmount = assignInvoiceAmount;
	}

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	
}
