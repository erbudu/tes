package com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaDeptMealNonEmpsRec entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseDeptMealNonEmpsRec implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nonEmpId;
	private String nonEmpIds;
	private String nonEmpName;
	private Long nonEmpType;
	private Long isAvailable;
	private Long displayOrder;

	// Constructors

	/** default constructor */
	public BaseDeptMealNonEmpsRec() {
	}

	/** minimal constructor */
	public BaseDeptMealNonEmpsRec(String nonEmpId) {
		this.nonEmpId = nonEmpId;
	}

	/** full constructor */
	public BaseDeptMealNonEmpsRec(String nonEmpId, String nonEmpIds,
			String nonEmpName, Long nonEmpType, Long isAvailable,
			Long displayOrder) {
		this.nonEmpId = nonEmpId;
		this.nonEmpIds = nonEmpIds;
		this.nonEmpName = nonEmpName;
		this.nonEmpType = nonEmpType;
		this.isAvailable = isAvailable;
		this.displayOrder = displayOrder;
	}

	// Property accessors
	@Id
	@Column(name = "NON_EMP_ID", unique = true, nullable = false, length = 32)
	public String getNonEmpId() {
		return this.nonEmpId;
	}

	public void setNonEmpId(String nonEmpId) {
		this.nonEmpId = nonEmpId;
	}

	@Column(name = "NON_EMP_IDS", length = 32)
	public String getNonEmpIds() {
		return this.nonEmpIds;
	}

	public void setNonEmpIds(String nonEmpIds) {
		this.nonEmpIds = nonEmpIds;
	}

	@Column(name = "NON_EMP_NAME", length = 128)
	public String getNonEmpName() {
		return this.nonEmpName;
	}

	public void setNonEmpName(String nonEmpName) {
		this.nonEmpName = nonEmpName;
	}

	@Column(name = "NON_EMP_TYPE", precision = 22, scale = 0)
	public Long getNonEmpType() {
		return this.nonEmpType;
	}

	public void setNonEmpType(Long nonEmpType) {
		this.nonEmpType = nonEmpType;
	}

	@Column(name = "IS_AVAILABLE", precision = 22, scale = 0)
	public Long getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(Long isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "DISPLAY_ORDER", precision = 22, scale = 0)
	public Long getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

}