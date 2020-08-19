package com.supporter.prj.cneec.tpc.out_invoice.entity.base;
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
public  class BaseOutInvoice  implements Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private String invoiceOutId;
	private String prjId;
	private String prjName;
	private String ownerName;
	private String taxRegistrationNumber;
	private String ownerDesc;
	private String createdBy;
	private String createdById;
	private String ownerBankAccount;
	private String contractId;
	private String createdDate;
	private String contractName;
	private String modifiedBy;
	private String modifiedById;
	private String contractNo;
	private String modifiedDate;
	private Double contractActMount;
	private Double begforIvoiceOutAmount;
	private String invoiceNo;
	private Double nowInvoiceOutAmount;
	private String isGathering;
	private int invoiceType;
	private String deptId;
	private String deptName;
	private String invoiceOutDate;
	private String invoiceDesc;
	private int invoiceStatus;
	private String procId;

	// Constructors

	/** default constructor */
	public BaseOutInvoice() {
	}
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseOutInvoice(String invoiceOutId){
		this.invoiceOutId = invoiceOutId;
	}
	/** full constructor */
	public BaseOutInvoice(String prjId, String prjName, String ownerName,
			String taxRegistrationNumber, String ownerDesc, String createdBy,
			String createdById, String ownerBankAccount, String contractId,
			String createdDate, String contractName, String modifiedBy,
			String modifiedById, String contractNo, String modifiedDate,
			Double contractActMount, Double begforIvoiceOutAmount,
			String invoiceNo, Double nowInvoiceOutAmount, String isGathering,
			int invoiceType, String deptId, String deptName,
			String invoiceOutDate, String invoiceDesc, int invoiceStatus,String procId) {
		this.prjId = prjId;
		this.prjName = prjName;
		this.ownerName = ownerName;
		this.taxRegistrationNumber = taxRegistrationNumber;
		this.ownerDesc = ownerDesc;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.ownerBankAccount = ownerBankAccount;
		this.contractId = contractId;
		this.createdDate = createdDate;
		this.contractName = contractName;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.contractNo = contractNo;
		this.modifiedDate = modifiedDate;
		this.contractActMount = contractActMount;
		this.begforIvoiceOutAmount = begforIvoiceOutAmount;
		this.invoiceNo = invoiceNo;
		this.nowInvoiceOutAmount = nowInvoiceOutAmount;
		this.isGathering = isGathering;
		this.invoiceType = invoiceType;
		this.deptId = deptId;
		this.deptName = deptName;
		this.invoiceOutDate = invoiceOutDate;
		this.invoiceDesc = invoiceDesc;
		this.invoiceStatus = invoiceStatus;
		this.procId= procId;
	}

	// Property accessors
	
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "INVOICE_OUT_ID", unique = true, nullable = false, length = 32)
	public String getInvoiceOutId() {
		return this.invoiceOutId;
	}

	public void setInvoiceOutId(String invoiceOutId) {
		this.invoiceOutId = invoiceOutId;
	}

	@Column(name = "PRJ_ID", length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "PRJ_NAME")
	public String getPrjName() {
		return this.prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	@Column(name = "OWNER_NAME")
	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Column(name = "TAX_REGISTRATION_NUMBER", length = 128)
	public String getTaxRegistrationNumber() {
		return this.taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}

	@Column(name = "OWNER_DESC", length = 512)
	public String getOwnerDesc() {
		return this.ownerDesc;
	}

	public void setOwnerDesc(String ownerDesc) {
		this.ownerDesc = ownerDesc;
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

	@Column(name = "OWNER_BANK_ACCOUNT", length = 128)
	public String getOwnerBankAccount() {
		return this.ownerBankAccount;
	}

	public void setOwnerBankAccount(String ownerBankAccount) {
		this.ownerBankAccount = ownerBankAccount;
	}

	@Column(name = "CONTRACT_ID", length = 64)
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "CREATED_DATE", length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CONTRACT_NAME")
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
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

	@Column(name = "CONTRACT_NO", length = 64)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "MODIFIED_DATE", length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "CONTRACT_ACT_MOUNT", precision = 18, scale = 3)
	public Double getContractActMount() {
		return this.contractActMount;
	}

	public void setContractActMount(Double contractActMount) {
		this.contractActMount = contractActMount;
	}

	@Column(name = "BEGFOR_IVOICE_OUT_AMOUNT", precision = 18, scale = 3)
	public Double getBegforIvoiceOutAmount() {
		return this.begforIvoiceOutAmount;
	}

	public void setBegforIvoiceOutAmount(Double begforIvoiceOutAmount) {
		this.begforIvoiceOutAmount = begforIvoiceOutAmount;
	}

	@Column(name = "INVOICE_NO", length = 128)
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "NOW_INVOICE_OUT_AMOUNT", precision = 18, scale = 3)
	public Double getNowInvoiceOutAmount() {
		return this.nowInvoiceOutAmount;
	}

	public void setNowInvoiceOutAmount(Double nowInvoiceOutAmount) {
		this.nowInvoiceOutAmount = nowInvoiceOutAmount;
	}

	@Column(name = "IS_GATHERING", length = 1)
	public String getIsGathering() {
		return this.isGathering;
	}

	public void setIsGathering(String isGathering) {
		this.isGathering = isGathering;
	}

	@Column(name = "INVOICE_TYPE", precision = 22, scale = 0)
	public int getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 64)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "INVOICE_OUT_DATE", length = 27)
	public String getInvoiceOutDate() {
		return this.invoiceOutDate;
	}

	public void setInvoiceOutDate(String invoiceOutDate) {
		this.invoiceOutDate = invoiceOutDate;
	}

	@Column(name = "INVOICE_DESC", length = 512)
	public String getInvoiceDesc() {
		return this.invoiceDesc;
	}

	public void setInvoiceDesc(String invoiceDesc) {
		this.invoiceDesc = invoiceDesc;
	}

	@Column(name = "INVOICE_STATUS", precision = 18, scale = 0)
	public int getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
}
