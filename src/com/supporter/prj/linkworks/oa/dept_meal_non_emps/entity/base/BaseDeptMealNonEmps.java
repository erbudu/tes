package com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaDeptMealNonEmps entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseDeptMealNonEmps implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nonEmpIds;
	private String deptId;
	private String deptName;
	private String createdBy;
	private String createdDate;
	private String modifiedDate;
	private String modifiedBy;
	private String createdById;
	private String modifiedById;

	// Constructors

	/** default constructor */
	public BaseDeptMealNonEmps() {
	}

	/** minimal constructor */
	public BaseDeptMealNonEmps(String nonEmpIds) {
		this.nonEmpIds = nonEmpIds;
	}

	/** full constructor */
	public BaseDeptMealNonEmps(String nonEmpIds, String deptId, String deptName,
			String createdBy, String createdDate, String modifiedDate,
			String modifiedBy, String createdById, String modifiedById) {
		this.nonEmpIds = nonEmpIds;
		this.deptId = deptId;
		this.deptName = deptName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.createdById = createdById;
		this.modifiedById = modifiedById;
	}

	// Property accessors
	@Id
	@Column(name = "NON_EMP_IDS", unique = true, nullable = false, length = 32)
	public String getNonEmpIds() {
		return this.nonEmpIds;
	}

	public void setNonEmpIds(String nonEmpIds) {
		this.nonEmpIds = nonEmpIds;
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

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_DATE", length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

}