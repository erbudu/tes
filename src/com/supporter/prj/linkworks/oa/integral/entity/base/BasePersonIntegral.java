package com.supporter.prj.linkworks.oa.integral.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * OaPersonIntegral entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@MappedSuperclass
public class BasePersonIntegral implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String deptId;
	private String deptNo;
	private String deptName;
	private String personName;
	private String personId;
	private String personNo;
	private Integer basicIntegral;
	private String year;
	private Integer rewardIntegral;
	private Integer deductionIntegral;
	private Integer finalIntegral;
	private Integer lastYearIntegral;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;
	private String isChief;
	// Constructors
	
	@Column(name = "IS_CHIEF", length = 16)
	public String getIsChief() {
		return isChief;
	}

	public void setIsChief(String isChief) {
		this.isChief = isChief;
	}

	/** default constructor */
	public BasePersonIntegral() {
	}

	/** minimal constructor */
	public BasePersonIntegral(String id) {
		this.id = id;
	}

	/** full constructor */
	public BasePersonIntegral(String id, String deptId, String deptNo,
			String deptName, String personName, String personId,
			String personNo, Integer basicIntegral, String year,
			Integer rewardIntegral, Integer deductionIntegral, Integer finalIntegral,
			Integer lastYearIntegral, String createdById, String createdBy,
			String createdDate, String modifiedById, String modifiedBy,
			String modifiedDate,String isChief) {
		this.id = id;
		this.deptId = deptId;
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.personName = personName;
		this.personId = personId;
		this.personNo = personNo;
		this.basicIntegral = basicIntegral;
		this.year = year;
		this.rewardIntegral = rewardIntegral;
		this.deductionIntegral = deductionIntegral;
		this.finalIntegral = finalIntegral;
		this.lastYearIntegral = lastYearIntegral;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isChief = isChief;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NO", length = 32)
	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	@Column(name = "DEPT_NAME", length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "PERSON_NAME", length = 64)
	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "PERSON_ID", length = 32)
	public String getPersonId() {
		return this.personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "PERSON_NO", length = 32)
	public String getPersonNo() {
		return this.personNo;
	}

	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}

	@Column(name = "BASIC_INTEGRAL", precision = 22, scale = 0)
	public Integer getBasicIntegral() {
		return this.basicIntegral;
	}

	public void setBasicIntegral(Integer basicIntegral) {
		this.basicIntegral = basicIntegral;
	}

	@Column(name = "YEAR", length = 16)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "REWARD_INTEGRAL", precision = 22, scale = 0)
	public Integer getRewardIntegral() {
		return this.rewardIntegral;
	}

	public void setRewardIntegral(Integer rewardIntegral) {
		this.rewardIntegral = rewardIntegral;
	}

	@Column(name = "DEDUCTION_INTEGRAL", precision = 22, scale = 0)
	public Integer getDeductionIntegral() {
		return this.deductionIntegral;
	}

	public void setDeductionIntegral(Integer deductionIntegral) {
		this.deductionIntegral = deductionIntegral;
	}

	@Column(name = "FINAL_INTEGRAL", precision = 22, scale = 0)
	public Integer getFinalIntegral() {
		return this.finalIntegral;
	}

	public void setFinalIntegral(Integer finalIntegral) {
		this.finalIntegral = finalIntegral;
	}

	@Column(name = "LAST_YEAR_INTEGRAL", precision = 22, scale = 0)
	public Integer getLastYearIntegral() {
		return this.lastYearIntegral;
	}

	public void setLastYearIntegral(Integer lastYearIntegral) {
		this.lastYearIntegral = lastYearIntegral;
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

}