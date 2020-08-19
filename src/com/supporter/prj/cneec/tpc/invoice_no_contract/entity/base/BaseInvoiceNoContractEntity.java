package com.supporter.prj.cneec.tpc.invoice_no_contract.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 非合同付款收发票base实体类
 * @Title: BaseInvoiceNoContractEntity
 * @author CHENHAO
 *
 */

@MappedSuperclass
public class BaseInvoiceNoContractEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="INVOICE_ID",length=32)
	private String invoiceId;			//INVOICE_ID	VARCHAR2(32)	主键ID
	
	@Column(name="PROJECT_ID",length=32)
	private String prjId;				//PROJECT_ID	VARCHAR2(32)	项目ID
	
	@Column(name="PROJECT_NAME",length=256)
	private String prjName;				//PROJECT_NAME	VARCHAR2(256)	项目名称
	
	@Column(name="INVOICE_NO",length=64)
	private String invoiceNo;			//INVOICE_NO	VARCHAR2(64)	发票号
	
	@Column(name="INCOICE_AMOUNT",precision = 18, scale = 3)
	private Double invoiceAmount;		//INCOICE_AMOUNT 	NUMBER(18,3)	发票金额
	
	@Column(name="INVOICE_TYPE_CODE",length=32)
	private String invoiceTypeCode;		//INVOICE_TYPE_CODE		VARCHAR2(32)	发票类型CODE
	
	@Column(name="INVOICE_TYPE",length=64)
	private String invoiceType;			//INVOICE_TYPE		VARCHAR2(64)	发票类型
	
	@Column(name="PROC_ID",length=32)
	private String procId;			//PROC_ID  VARCHAR2(32)		流程ID
	
	@Column(name="STATUS")
	private Integer status;			//STATUS	NUMBER(64)	审批状态（0：草稿，1：审批中，2：审批完成）
	
	@Column(name="CREATED_BY",length=64)
	private String createdBy;		//CREATED_BY	Varchar2(64)	创建人名称

	@Column(name="CREATED_BY_ID",length=32)
	private String createdById;		//CREATED_BY_ID	Varchar2(32)	创建人ID
	
	@Column(name="CREATED_DATE")
	private Date createdDate;		//CREATED_DATE	DATE	创建时间

	@Column(name="MODIFIED_BY",length=64)
	private String modifiedBy;		//MODIFIED_BY	Varchar2(64)	修改人名称

	@Column(name="MODIFIED_BY_ID",length=32)
	private String modifiedById;	//MODIFIED_BY_ID	Varchar2(32)	修改人ID

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;		//MODIFIED_DATE	DATE	修改时间
	
	@Column(name="DEPT_NAME",length=200)
	private String deptName;		//DEPT_NAME	Varchar2(200)	创建人部门名称

	@Column(name="DEPT_ID",length=32)
	private String deptId;			//DEPT_ID	Varchar2(32)	创建人部门ID\

	
	
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceTypeCode() {
		return invoiceTypeCode;
	}

	public void setInvoiceTypeCode(String invoiceTypeCode) {
		this.invoiceTypeCode = invoiceTypeCode;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
}
