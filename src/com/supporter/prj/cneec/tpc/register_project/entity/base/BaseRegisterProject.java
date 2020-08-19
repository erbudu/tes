package com.supporter.prj.cneec.tpc.register_project.entity.base;

// default package

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractRegisterProject entity provides the base persistence definition of
 * the RegisterProject entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BaseRegisterProject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String projectId;
	private String projectName;
	private String projectNo;
	private String projectAbbreviation;
	private String projectDeptName;
	private String projectDeptNo;
	private String projectDeptId;
	private String projectCategory;
	private Integer projectCategoryId;
	private String purchaseType;
	private Integer purchaseTypeId;
	private String purchaseContent;
	private String projectClassification;
	private String projectClassificationId;
	private String projectNature;
	private String projectNatureId;
	private String singleCategoryCode;
	private String fundSource;
	private boolean hasFrameworkAgreement;
	private String customerName;
	private String customerId;
	private double estimatedAmount;
	private String projectCharge;
	private String projectChargeId;
	private String currency;
	private String currencyId;
	private String remarks;
	private String completeDate;
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
	public BaseRegisterProject() {
	}

	public BaseRegisterProject(String projectId) {
		this.projectId = projectId;
	}

	// Property accessors
	@Id
	@Column(name = "PROJECT_ID", unique = true, nullable = false, length = 32)
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

	@Column(name = "PROJECT_NO", length = 64)
	public String getProjectNo() {
		return this.projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	@Column(name = "PROJECT_ABBREVIATION", length = 32)
	public String getProjectAbbreviation() {
		return this.projectAbbreviation;
	}

	public void setProjectAbbreviation(String projectAbbreviation) {
		this.projectAbbreviation = projectAbbreviation;
	}

	@Column(name = "PROJECT_DEPT_NAME", length = 128)
	public String getProjectDeptName() {
		return projectDeptName;
	}

	public void setProjectDeptName(String projectDeptName) {
		this.projectDeptName = projectDeptName;
	}

	@Column(name = "PROJECT_DEPT_NO", length = 32)
	public String getProjectDeptNo() {
		return projectDeptNo;
	}

	public void setProjectDeptNo(String projectDeptNo) {
		this.projectDeptNo = projectDeptNo;
	}

	@Column(name = "PROJECT_DEPT_ID", length = 32)
	public String getProjectDeptId() {
		return projectDeptId;
	}

	public void setProjectDeptId(String projectDeptId) {
		this.projectDeptId = projectDeptId;
	}

	@Column(name = "PROJECT_CATEGORY", length = 64)
	public String getProjectCategory() {
		return this.projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	@Column(name = "PROJECT_CATEGORY_ID", precision = 6, scale = 0)
	public Integer getProjectCategoryId() {
		return this.projectCategoryId;
	}

	public void setProjectCategoryId(Integer projectCategoryId) {
		this.projectCategoryId = projectCategoryId;
	}

	@Column(name = "PURCHASE_TYPE", length = 32)
	public String getPurchaseType() {
		return this.purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Column(name = "PURCHASE_TYPE_ID", precision = 6, scale = 0)
	public Integer getPurchaseTypeId() {
		return this.purchaseTypeId;
	}

	public void setPurchaseTypeId(Integer purchaseTypeId) {
		this.purchaseTypeId = purchaseTypeId;
	}

	@Column(name = "PURCHASE_CONTENT", length = 512)
	public String getPurchaseContent() {
		return this.purchaseContent;
	}

	public void setPurchaseContent(String purchaseContent) {
		this.purchaseContent = purchaseContent;
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

	@Column(name = "PROJECT_NATURE", length = 128)
	public String getProjectNature() {
		return this.projectNature;
	}

	public void setProjectNature(String projectNature) {
		this.projectNature = projectNature;
	}

	@Column(name = "PROJECT_NATURE_ID", length = 128)
	public String getProjectNatureId() {
		return this.projectNatureId;
	}

	public void setProjectNatureId(String projectNatureId) {
		this.projectNatureId = projectNatureId;
	}

	@Column(name = "SINGLE_CATEGORY_CODE", length = 32)
	public String getSingleCategoryCode() {
		return singleCategoryCode;
	}

	public void setSingleCategoryCode(String singleCategoryCode) {
		this.singleCategoryCode = singleCategoryCode;
	}

	@Column(name = "FUND_SOURCE", length = 128)
	public String getFundSource() {
		return this.fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	@Column(name = "HAS_FRAMEWORK_AGREEMENT", length = 1)
	public boolean isHasFrameworkAgreement() {
		return hasFrameworkAgreement;
	}

	public void setHasFrameworkAgreement(boolean hasFrameworkAgreement) {
		this.hasFrameworkAgreement = hasFrameworkAgreement;
	}

	@Column(name = "CUSTOMER_NAME", length = 128)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "CUSTOMER_ID", length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "ESTIMATED_AMOUNT", precision = 18, scale = 3)
	public double getEstimatedAmount() {
		return this.estimatedAmount;
	}

	public void setEstimatedAmount(double estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}

	@Column(name = "PROJECT_CHARGE", length = 32)
	public String getProjectCharge() {
		return this.projectCharge;
	}

	public void setProjectCharge(String projectCharge) {
		this.projectCharge = projectCharge;
	}

	@Column(name = "PROJECT_CHARGE_ID", length = 32)
	public String getProjectChargeId() {
		return this.projectChargeId;
	}

	public void setProjectChargeId(String projectChargeId) {
		this.projectChargeId = projectChargeId;
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

	@Column(name = "REMARKS", length = 128)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "COMPLETE_DATE", length = 27)
	public String getCompleteDate() {
		return this.completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
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