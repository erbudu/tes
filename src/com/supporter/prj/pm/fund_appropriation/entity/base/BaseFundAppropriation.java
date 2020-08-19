package com.supporter.prj.pm.fund_appropriation.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseFundAppropriation implements java.io.Serializable{

	// primary key
	private String fundId ;//主键
	private String fundNo ;
	private Integer budgetPeriod;
	private Date startDate;
	private Date endDate;
	private double lastDollarRest;
	private double lastDollarRestB;
	private double lastLocalRest;
	private double lastLocalRestB;
	private double estimateDollarIncome;
	private double estimateLocalIncome;
	private double estimateDollarPay;
	private double estimateLocalPay;
	private Integer fundProperty;
	private double appropriationAmount;
	private String bankId;
	private String bankAccountNo;
	private String remarks ;
	private String createdById;
	private String createdBy;
	private String createdDept;
	private String createdDeptId;
	private Date createdDate;
	private String modifiedBy;
	private String modifiedById;
	private Date modifiedDate;
	private Integer status;
	private String prjId;
	private String prjName;
	private String procId;
	//当地币兑美元汇率R
	private double localDollarRate;



	/** default constructor */
	public BaseFundAppropriation() {
	}

	/** minimal constructor */
	public BaseFundAppropriation(String fundId) {
		this.fundId = fundId;
	}
	
	@Id
	@Column(name = "fund_id", unique = true, nullable = false, length = 32)
	public String getFundId() {
		return fundId;
	}
	
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
	
	
	@Column(name = "fund_no", length = 32)
	public String getFundNo() {
		return fundNo;
	}

	public void setFundNo(String fundNo) {
		this.fundNo = fundNo;
	}

	@Column(name = "budget_period", length = 11)
	public Integer getBudgetPeriod() {
		return budgetPeriod;
	}

	public void setBudgetPeriod(Integer budgetPeriod) {
		this.budgetPeriod = budgetPeriod;
	}

	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "last_dollar_rest", precision = 18, scale = 3)
	public double getLastDollarRest() {
		return lastDollarRest;
	}

	public void setLastDollarRest(double lastDollarRest) {
		this.lastDollarRest = lastDollarRest;
	}
	
	@Column(name = "last_dollar_rest_b", precision = 18, scale = 3)
	public double getLastDollarRestB() {
		return lastDollarRestB;
	}

	public void setLastDollarRestB(double lastDollarRestB) {
		this.lastDollarRestB = lastDollarRestB;
	}

	@Column(name = "last_local_rest", precision = 18, scale = 3)
	public double getLastLocalRest() {
		return lastLocalRest;
	}

	public void setLastLocalRest(double lastLocalRest) {
		this.lastLocalRest = lastLocalRest;
	}
	
	@Column(name = "last_local_rest_b", precision = 18, scale = 3)
	public double getLastLocalRestB() {
		return lastLocalRestB;
	}

	public void setLastLocalRestB(double lastLocalRestB) {
		this.lastLocalRestB = lastLocalRestB;
	}

	@Column(name = "estimate_dollar_income", precision = 18, scale = 3)
	public double getEstimateDollarIncome() {
		return estimateDollarIncome;
	}

	public void setEstimateDollarIncome(double estimateDollarIncome) {
		this.estimateDollarIncome = estimateDollarIncome;
	}

	@Column(name = "estimate_local_income", precision = 18, scale = 3)
	public double getEstimateLocalIncome() {
		return estimateLocalIncome;
	}

	public void setEstimateLocalIncome(double estimateLocalIncome) {
		this.estimateLocalIncome = estimateLocalIncome;
	}

	@Column(name = "estimate_dollar_pay", precision = 18, scale = 3)
	public double getEstimateDollarPay() {
		return estimateDollarPay;
	}

	public void setEstimateDollarPay(double estimateDollarPay) {
		this.estimateDollarPay = estimateDollarPay;
	}

	@Column(name = "estimate_local_pay", precision = 18, scale = 3)
	public double getEstimateLocalPay() {
		return estimateLocalPay;
	}

	public void setEstimateLocalPay(double estimateLocalPay) {
		this.estimateLocalPay = estimateLocalPay;
	}

	@Column(name = "fund_property")
	public Integer getFundProperty() {
		return fundProperty;
	}

	public void setFundProperty(Integer fundProperty) {
		this.fundProperty = fundProperty;
	}

	@Column(name = "appropriation_amount", precision = 18, scale = 3)
	public double getAppropriationAmount() {
		return appropriationAmount;
	}

	public void setAppropriationAmount(double appropriationAmount) {
		this.appropriationAmount = appropriationAmount;
	}

	@Column(name = "bank_id",length = 32)
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	@Column(name = "bank_account_no",length = 64)
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	@Column(name = "remarks", length = 512)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "created_by_id", length = 32)
	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "created_by", length = 64)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_dept", length = 64)
	public String getCreatedDept() {
		return createdDept;
	}

	public void setCreatedDept(String createdDept) {
		this.createdDept = createdDept;
	}

	@Column(name = "created_dept_id", length = 32)
	public String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}
	
	@Column(name = "created_date", length = 7)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "modified_by", length = 64)
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "modified_by_id", length = 32)
	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "modified_date", length = 7)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "status", length = 11)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "prj_id", length = 32)
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	
	@Column(name = "prj_name", length = 128)
	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	
	@Column(name = "proc_id", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
	@Column(name = "local_dollar_rate", precision = 18, scale = 4)
	public double getLocalDollarRate() {
		return localDollarRate;
	}

	public void setLocalDollarRate(double localDollarRate) {
		this.localDollarRate = localDollarRate;
	}
}
