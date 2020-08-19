package com.supporter.prj.linkworks.oa.membership_dues.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * OaMembershipDues entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseMembershipDues implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recId;
	private String dbYear;
	private String dbMonth;
	private String empId;
	private String empNo;
	private String empName;
	private Double duesBase;
	private Double payableAmount;
	private String deptId;
	private String deptName;
	private String yearMonth;
	// Constructors

	/** default constructor */
	public BaseMembershipDues() {
	}

	/** minimal constructor */
	public BaseMembershipDues(String recId) {
		this.recId = recId;
	}

	/** full constructor */
	public BaseMembershipDues(String recId, String dbYear, String dbMonth,
			String empId, String empNo, String empName, Double duesBase,
			Double payableAmount, String deptId, String deptName,String yearMonth) {
		this.recId = recId;
		this.dbYear = dbYear;
		this.dbMonth = dbMonth;
		this.empId = empId;
		this.empNo = empNo;
		this.empName = empName;
		this.duesBase = duesBase;
		this.payableAmount = payableAmount;
		this.deptId = deptId;
		this.deptName = deptName;
		this.yearMonth=yearMonth;
	}

	// Property accessors
	@Id
	@Column(name = "REC_ID", unique = true, nullable = false, length = 32)
	public String getRecId() {
		return this.recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	@Column(name = "DB_YEAR", length = 16)
	public String getDbYear() {
		return this.dbYear;
	}

	public void setDbYear(String dbYear) {
		this.dbYear = dbYear;
	}

	@Column(name = "DB_MONTH", length = 16)
	public String getDbMonth() {
		return this.dbMonth;
	}

	public void setDbMonth(String dbMonth) {
		this.dbMonth = dbMonth;
	}

	@Column(name = "EMP_ID", length = 32)
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name = "EMP_NO", length = 32)
	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	@Column(name = "EMP_NAME", length = 64)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "DUES_BASE", precision = 16)
	public Double getDuesBase() {
		return this.duesBase;
	}

	public void setDuesBase(Double duesBase) {
		this.duesBase = duesBase;
	}

	@Column(name = "PAYABLE_AMOUNT", precision = 16)
	public Double getPayableAmount() {
		return this.payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
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
	@Column(name = "YEAR_MONTH", length = 32)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

}