package com.supporter.prj.linkworks.oa.swf_prj_proc.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractSwfPrjProc entity provides the base persistence definition of the
 * SwfPrjProc entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BaseSwfPrjProc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String procId;
	private String prjId;
	private String prjName;
	private String prjNo;
	private String businessInnerName;
	private String businessName;
	private String procInnerName;
	private String procName;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;
	private String appPlatformCede;
	private String appPlatformName;

	// Constructors

	/** default constructor */
	public BaseSwfPrjProc() {
	}

	/** minimal constructor */
	public BaseSwfPrjProc(String procId) {
		this.procId = procId;
	}

	/** full constructor */
	public BaseSwfPrjProc(String procId, String prjId, String prjName,
			String prjNo, String businessInnerName, String businessName,
			String procInnerName, String procName, String createdById,
			String createdBy, String createdDate, String modifiedById,
			String modifiedBy, String modifiedDate, String appPlatformCede,
			String appPlatformName) {
		this.procId = procId;
		this.prjId = prjId;
		this.prjName = prjName;
		this.prjNo = prjNo;
		this.businessInnerName = businessInnerName;
		this.businessName = businessName;
		this.procInnerName = procInnerName;
		this.procName = procName;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.appPlatformCede = appPlatformCede;
		this.appPlatformName = appPlatformName;
	}

	// Property accessors
	@Id
	@Column(name = "PROC_ID", unique = true, nullable = false, length = 32)
	public String getProcId() {
		return this.procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
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

	@Column(name = "PRJ_NO", length = 32)
	public String getPrjNo() {
		return this.prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	@Column(name = "BUSINESS_INNER_NAME", length = 128)
	public String getBusinessInnerName() {
		return this.businessInnerName;
	}

	public void setBusinessInnerName(String businessInnerName) {
		this.businessInnerName = businessInnerName;
	}

	@Column(name = "BUSINESS_NAME", length = 256)
	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(name = "PROC_INNER_NAME", length = 128)
	public String getProcInnerName() {
		return this.procInnerName;
	}

	public void setProcInnerName(String procInnerName) {
		this.procInnerName = procInnerName;
	}

	@Column(name = "PROC_NAME", length = 256)
	public String getProcName() {
		return this.procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_BY", length = 64)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_BY", length = 64)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", length = 32)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "APP_PLATFORM_CEDE", length = 64)
	public String getAppPlatformCede() {
		return this.appPlatformCede;
	}

	public void setAppPlatformCede(String appPlatformCede) {
		this.appPlatformCede = appPlatformCede;
	}

	@Column(name = "APP_PLATFORM_NAME", length = 256)
	public String getAppPlatformName() {
		return this.appPlatformName;
	}

	public void setAppPlatformName(String appPlatformName) {
		this.appPlatformName = appPlatformName;
	}

}