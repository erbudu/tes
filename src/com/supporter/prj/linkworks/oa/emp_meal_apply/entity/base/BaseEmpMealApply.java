package com.supporter.prj.linkworks.oa.emp_meal_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaEmpMealApply entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseEmpMealApply implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;
	private String deptId;
	private String deptName;
	private Integer year;
	private Integer month;
	private String createdBy;
	private String createdDate;
	private String modifiedDate;
	private String modifiedBy;
	private Long empCount;
	private Double empInAmount;
	private Double empOutAmount;
	private String applyDate;
	private Integer status;
	private String empId;
	private String empName;
	private String procId;
	private boolean history;
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
	public boolean getHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}
	// Constructors
	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	/** default constructor */
	public BaseEmpMealApply() {
	}

	/** minimal constructor */
	public BaseEmpMealApply(String applyId) {
		this.applyId = applyId;
	}

	/** full constructor */
	public BaseEmpMealApply(String applyId, String deptId, String deptName,
			Integer year, Integer month, String createdBy, String createdDate,
			String modifiedDate, String modifiedBy, Long empCount,
			Double empInAmount, Double empOutAmount, String applyDate,
			Integer status, String empId, String empName, String procId) {
		this.applyId = applyId;
		this.deptId = deptId;
		this.deptName = deptName;
		this.year = year;
		this.month = month;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.empCount = empCount;
		this.empInAmount = empInAmount;
		this.empOutAmount = empOutAmount;
		this.applyDate = applyDate;
		this.status = status;
		this.empId = empId;
		this.empName = empName;
	}

	// Property accessors
	@Id
	@Column(name = "APPLY_ID", unique = true, nullable = false, length = 32)
	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
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

	@Column(name = "YEAR", precision = 22, scale = 0)
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "MONTH", precision = 22, scale = 0)
	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(Integer month) {
		this.month = month;
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

	@Column(name = "EMP_COUNT", precision = 22, scale = 0)
	public Long getEmpCount() {
		return this.empCount;
	}

	public void setEmpCount(Long empCount) {
		this.empCount = empCount;
	}

	@Column(name = "EMP_IN_AMOUNT", precision = 18, scale = 3)
	public Double getEmpInAmount() {
		return this.empInAmount;
	}

	public void setEmpInAmount(Double empInAmount) {
		this.empInAmount = empInAmount;
	}

	@Column(name = "EMP_OUT_AMOUNT", precision = 18, scale = 3)
	public Double getEmpOutAmount() {
		return this.empOutAmount;
	}

	public void setEmpOutAmount(Double empOutAmount) {
		this.empOutAmount = empOutAmount;
	}

	@Column(name = "APPLY_DATE", length = 27)
	public String getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "STATUS", precision = 22, scale = 0)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "EMP_ID", length = 32)
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name = "EMP_NAME", length = 32)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}