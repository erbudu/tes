package com.supporter.prj.linkworks.oa.dept_meal_limit.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * OaDeptMealLimit entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseDeptMealLimit implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deptMealLimitId;
	private String deptId;
	private String deptName;
	private String createdBy;
	private String createdDate;
	private String modifiedDate;
	private String modifiedBy;
	private Double limitAmount;
	private Long displayOrder;

	// Constructors

	/** default constructor */
	public BaseDeptMealLimit() {
	}

	/** minimal constructor */
	public BaseDeptMealLimit(String deptMealLimitId) {
		this.deptMealLimitId = deptMealLimitId;
	}

	/** full constructor */
	public BaseDeptMealLimit(String deptMealLimitId, String deptId,
			String deptName, String createdBy, String createdDate,
			String modifiedDate, String modifiedBy, Double limitAmount,
			Long displayOrder) {
		this.deptMealLimitId = deptMealLimitId;
		this.deptId = deptId;
		this.deptName = deptName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.limitAmount = limitAmount;
		this.displayOrder = displayOrder;
	}

	// Property accessors
	@Id
	@Column(name = "DEPT_MEAL_LIMIT_ID", unique = true, nullable = false, length = 32)
	public String getDeptMealLimitId() {
		return this.deptMealLimitId;
	}

	public void setDeptMealLimitId(String deptMealLimitId) {
		this.deptMealLimitId = deptMealLimitId;
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

	@Column(name = "LIMIT_AMOUNT", precision = 18, scale = 3)
	public Double getLimitAmount() {
		return this.limitAmount;
	}

	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}

	@Column(name = "DISPLAY_ORDER", precision = 18, scale = 0)
	public Long getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

}