package com.supporter.prj.cneec.tpc.purchase_demand.entity.base;

// default package

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractPurchaseDemand entity provides the base persistence definition of the
 * PurchaseDemand entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BasePurchaseDemand implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String demandId;
	private String projectId;
	private String projectName;
	private String projectClassification;
	private String projectClassificationId;
	private String reviewClassification;
	private String singleCategoryCode;
	private String reviewId;
	private String protocolName;
	private String batchNo;
	private String purchaseDemandName;
	private double estimatedAmount;
	private String currency;
	private String currencyId;
	private double rate;// 对人民币汇率
	private double usdRate;// 该币别对美元汇率
	private double usdAmount;// 预估金额折合美元
	private String customerId;
	private String customerName;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;

	// Constructors

	/** default constructor */
	public BasePurchaseDemand() {
	}

	/** minimal constructor */
	public BasePurchaseDemand(String demandId) {
		this.demandId = demandId;
	}

	// Property accessors
	@Id
	@Column(name = "DEMAND_ID", unique = true, nullable = false, length = 32)
	public String getDemandId() {
		return this.demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	@Column(name = "PROJECT_ID", length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	@Column(name = "PROJECT_CLASSIFICATION", length = 32)
	public String getProjectClassification() {
		return projectClassification;
	}

	public void setProjectClassification(String projectClassification) {
		this.projectClassification = projectClassification;
	}

	@Column(name = "PROJECT_CLASSIFICATION_ID", length = 32)
	public String getProjectClassificationId() {
		return projectClassificationId;
	}

	public void setProjectClassificationId(String projectClassificationId) {
		this.projectClassificationId = projectClassificationId;
	}

	@Column(name = "REVIEW_CLASSIFICATION", length = 32)
	public String getReviewClassification() {
		return reviewClassification;
	}

	public void setReviewClassification(String reviewClassification) {
		this.reviewClassification = reviewClassification;
	}

	@Column(name = "SINGLE_CATEGORY_CODE", length = 32)
	public String getSingleCategoryCode() {
		return singleCategoryCode;
	}

	public void setSingleCategoryCode(String singleCategoryCode) {
		this.singleCategoryCode = singleCategoryCode;
	}

	@Column(name = "REVIEW_ID", length = 32)
	public String getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "PROTOCOL_NAME", length = 64)
	public String getProtocolName() {
		return this.protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	@Column(name = "BATCH_NO", length = 64)
	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "PURCHASE_DEMAND_NAME", length = 128)
	public String getPurchaseDemandName() {
		return this.purchaseDemandName;
	}

	public void setPurchaseDemandName(String purchaseDemandName) {
		this.purchaseDemandName = purchaseDemandName;
	}

	@Column(name = "ESTIMATED_AMOUNT", precision = 18, scale = 3)
	public double getEstimatedAmount() {
		return this.estimatedAmount;
	}

	public void setEstimatedAmount(double estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}

	@Column(name = "CURRENCY", length = 32)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "CURRENCY_ID", length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "RATE", precision = 18, scale = 4)
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Column(name = "USD_RATE", precision = 18, scale = 4)
	public double getUsdRate() {
		return usdRate;
	}

	public void setUsdRate(double usdRate) {
		this.usdRate = usdRate;
	}

	@Column(name = "USD_AMOUNT", precision = 18, scale = 3)
	public double getUsdAmount() {
		return this.usdAmount;
	}

	public void setUsdAmount(double usdAmount) {
		this.usdAmount = usdAmount;
	}

	@Column(name = "CUSTOMER_ID", length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 512)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "SWF_STATUS", precision = 6, scale = 0)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	@Column(name = "DEPT_NAME", length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", length = 32)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}