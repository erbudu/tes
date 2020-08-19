package com.supporter.prj.cneec.tpc.external_quotation_review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_EXTERNAL_QUOTATION_REVIEW,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-09-25 17:15:46
 * @version V1.0
 * 
 */
@MappedSuperclass
public class BaseExternalQuotationReview implements Serializable {

	private static final long serialVersionUID = 1L;
	private String externalId;
	private String projectId;
	private String projectName;
	private String demandId;
	private String batchNo;
	private String purchaseDemandName;
	private String quotationTitle;
	private String signerName;
	private String signerId;
	private String latestQuoteTime;
	private String currency;
	private String currencyId;
	private double quotationAmount;
	private String cargoOverview;
	private double anticipatedProfit;
	private String projectClassification;
	private String projectClassificationId;
	private String reviewClassification;
	private double reviewAmount;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;

	/**
	 * 无参构造函数.
	 */
	public BaseExternalQuotationReview() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param externalId
	 */
	public BaseExternalQuotationReview(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * GET方法: 取得EXTERNAL_ID.
	 * 
	 * @return: String EXTERNAL_ID
	 */
	@Id
	@Column(name = "EXTERNAL_ID", nullable = false, length = 32)
	public String getExternalId() {
		return this.externalId;
	}

	/**
	 * SET方法: 设置EXTERNAL_ID.
	 * 
	 * @param: String EXTERNAL_ID
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 * 
	 * @return: String PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 * 
	 * @param: String PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得PROJECT_NAME.
	 * 
	 * @return: String PROJECT_NAME
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * SET方法: 设置PROJECT_NAME.
	 * 
	 * @param: String PROJECT_NAME
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * GET方法: 取得DEMAND_ID.
	 * 
	 * @return: String DEMAND_ID
	 */
	@Column(name = "DEMAND_ID", nullable = true, length = 32)
	public String getDemandId() {
		return this.demandId;
	}

	/**
	 * SET方法: 设置DEMAND_ID.
	 * 
	 * @param: String DEMAND_ID
	 */
	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	/**
	 * GET方法: 取得BATCH_NO.
	 * 
	 * @return: String BATCH_NO
	 */
	@Column(name = "BATCH_NO", nullable = true, length = 64)
	public String getBatchNo() {
		return this.batchNo;
	}

	/**
	 * SET方法: 设置BATCH_NO.
	 * 
	 * @param: String BATCH_NO
	 */
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

	/**
	 * GET方法: 取得QUOTATION_TITLE.
	 * 
	 * @return: String QUOTATION_TITLE
	 */
	@Column(name = "QUOTATION_TITLE", nullable = true, length = 64)
	public String getQuotationTitle() {
		return this.quotationTitle;
	}

	/**
	 * SET方法: 设置QUOTATION_TITLE.
	 * 
	 * @param: String QUOTATION_TITLE
	 */
	public void setQuotationTitle(String quotationTitle) {
		this.quotationTitle = quotationTitle;
	}

	/**
	 * GET方法: 取得SIGNER_NAME.
	 * 
	 * @return: String SIGNER_NAME
	 */
	@Column(name = "SIGNER_NAME", nullable = true, length = 1024)
	public String getSignerName() {
		return this.signerName;
	}

	/**
	 * SET方法: 设置SIGNER_NAME.
	 * 
	 * @param: String SIGNER_NAME
	 */
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	/**
	 * GET方法: 取得SIGNER_ID.
	 * 
	 * @return: String SIGNER_ID
	 */
	@Column(name = "SIGNER_ID", nullable = true, length = 512)
	public String getSignerId() {
		return this.signerId;
	}

	/**
	 * SET方法: 设置SIGNER_ID.
	 * 
	 * @param: String SIGNER_ID
	 */
	public void setSignerId(String signerId) {
		this.signerId = signerId;
	}

	/**
	 * GET方法: 取得LATEST_QUOTE_TIME.
	 * 
	 * @return: String LATEST_QUOTE_TIME
	 */
	@Column(name = "LATEST_QUOTE_TIME", nullable = true, length = 27)
	public String getLatestQuoteTime() {
		return this.latestQuoteTime;
	}

	/**
	 * SET方法: 设置LATEST_QUOTE_TIME.
	 * 
	 * @param: String LATEST_QUOTE_TIME
	 */
	public void setLatestQuoteTime(String latestQuoteTime) {
		this.latestQuoteTime = latestQuoteTime;
	}

	/**
	 * GET方法: 取得CURRENCY.
	 * 
	 * @return: String CURRENCY
	 */
	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * SET方法: 设置CURRENCY.
	 * 
	 * @param: String CURRENCY
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * GET方法: 取得CURRENCY_ID.
	 * 
	 * @return: String CURRENCY_ID
	 */
	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	/**
	 * SET方法: 设置CURRENCY_ID.
	 * 
	 * @param: String CURRENCY_ID
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * GET方法: 取得QUOTATION_AMOUNT.
	 * 
	 * @return: double QUOTATION_AMOUNT
	 */
	@Column(name = "QUOTATION_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getQuotationAmount() {
		return this.quotationAmount;
	}

	/**
	 * SET方法: 设置QUOTATION_AMOUNT.
	 * 
	 * @param: double QUOTATION_AMOUNT
	 */
	public void setQuotationAmount(double quotationAmount) {
		this.quotationAmount = quotationAmount;
	}

	/**
	 * GET方法: 取得CARGO_OVERVIEW.
	 * 
	 * @return: String CARGO_OVERVIEW
	 */
	@Column(name = "CARGO_OVERVIEW", nullable = true, length = 512)
	public String getCargoOverview() {
		return this.cargoOverview;
	}

	/**
	 * SET方法: 设置CARGO_OVERVIEW.
	 * 
	 * @param: String CARGO_OVERVIEW
	 */
	public void setCargoOverview(String cargoOverview) {
		this.cargoOverview = cargoOverview;
	}

	/**
	 * GET方法: 取得ANTICIPATED_PROFIT.
	 * 
	 * @return: double ANTICIPATED_PROFIT
	 */
	@Column(name = "ANTICIPATED_PROFIT", nullable = true, precision = 10, scale = 3)
	public double getAnticipatedProfit() {
		return this.anticipatedProfit;
	}

	/**
	 * SET方法: 设置ANTICIPATED_PROFIT.
	 * 
	 * @param: double ANTICIPATED_PROFIT
	 */
	public void setAnticipatedProfit(double anticipatedProfit) {
		this.anticipatedProfit = anticipatedProfit;
	}


	@Column(name = "PROJECT_CLASSIFICATION", nullable = true, length = 32)
	public String getProjectClassification() {
		return projectClassification;
	}

	public void setProjectClassification(String projectClassification) {
		this.projectClassification = projectClassification;
	}

	@Column(name = "PROJECT_CLASSIFICATION_ID", nullable = true, length = 32)
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

	@Column(name = "REVIEW_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getReviewAmount() {
		return reviewAmount;
	}

	public void setReviewAmount(double reviewAmount) {
		this.reviewAmount = reviewAmount;
	}

	/**
	 * GET方法: 取得SWF_STATUS.
	 * 
	 * @return: Integer SWF_STATUS
	 */
	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	/**
	 * SET方法: 设置SWF_STATUS.
	 * 
	 * @param: Integer SWF_STATUS
	 */
	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	/**
	 * GET方法: 取得DEPT_NAME.
	 * 
	 * @return: String DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * SET方法: 设置DEPT_NAME.
	 * 
	 * @param: String DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * GET方法: 取得DEPT_ID.
	 * 
	 * @return: String DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * SET方法: 设置DEPT_ID.
	 * 
	 * @param: String DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 * 
	 * @return: String CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 * 
	 * @param: String CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 * 
	 * @return: String CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 * 
	 * @param: String CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * GET方法: 取得CREATED_DATE.
	 * 
	 * @return: String CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 * 
	 * @param: String CREATED_DATE
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_BY.
	 * 
	 * @return: String MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * SET方法: 设置MODIFIED_BY.
	 * 
	 * @param: String MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * GET方法: 取得MODIFIED_BY_ID.
	 * 
	 * @return: String MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * SET方法: 设置MODIFIED_BY_ID.
	 * 
	 * @param: String MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 * 
	 * @return: String MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 32)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 * 
	 * @param: String MODIFIED_DATE
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
