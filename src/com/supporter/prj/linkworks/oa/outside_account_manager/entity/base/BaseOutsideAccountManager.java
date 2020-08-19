package com.supporter.prj.linkworks.oa.outside_account_manager.entity.base;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * BaseOutsideAccountManager entity provides the base persistence
 * definition of the OaOutsideAccountManager entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class BaseOutsideAccountManager implements
		java.io.Serializable {

	// Fields

	private String managerId;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;
	private Integer status;
	private String procId;

	// Constructors

	/** default constructor */
	public BaseOutsideAccountManager() {
	}

	/** minimal constructor */
	public BaseOutsideAccountManager(String managerId) {
		this.managerId = managerId;
	}

	/** full constructor */
	public BaseOutsideAccountManager(String managerId,
			String createdById, String createdBy, String createdDate,
			String modifiedById, String modifiedBy, String modifiedDate,
			Integer status, String procId) {
		this.managerId = managerId;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.status = status;
		this.procId = procId;
	}

	// Property accessors
	@Id
	@Column(name = "MANAGER_ID", unique = true, nullable = false, length = 32)
	public String getManagerId() {
		return this.managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
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

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return this.procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}