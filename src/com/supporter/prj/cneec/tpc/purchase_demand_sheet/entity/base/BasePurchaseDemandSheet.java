package com.supporter.prj.cneec.tpc.purchase_demand_sheet.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_PURCHASE_DEMAND_SHEET,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-04-10
 * @version V1.0
 */
@MappedSuperclass
public class BasePurchaseDemandSheet implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sheetId;// 主键ID（记录数量与客户需求单下采购合同数量一样）
	// 客户采购主表字段
	private String projectId;
	private String projectName;
	private String projectClassification;
	private String projectClassificationId;
	private String reviewClassification;
	private String singleCategoryCode;
	private String demandId;
	private String batchNo;
	private String purchaseDemandName;
	private double estimatedAmount;
	private String currency;
	private String currencyId;
	private double rate;
	private double usdRate;
	// 客户采购客户表字段
	private String recordId;
	private String customerId;
	private String customerName;
	// 自有字段
	private Integer reviewStatus;
	private String reviewConclusion;

	/**
	 * 无参构造函数.
	 */
	public BasePurchaseDemandSheet() {
	}

	/**
	 * 构造函数.
	 *
	 * @param sheetId
	 */
	public BasePurchaseDemandSheet(String sheetId) {
		this.sheetId = sheetId;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "SHEET_ID", nullable = false, length = 32)
	public String getSheetId() {
		return this.sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "PROJECT_CLASSIFICATION", nullable = true, length = 32)
	public String getProjectClassification() {
		return this.projectClassification;
	}

	public void setProjectClassification(String projectClassification) {
		this.projectClassification = projectClassification;
	}

	@Column(name = "PROJECT_CLASSIFICATION_ID", nullable = true, length = 32)
	public String getProjectClassificationId() {
		return this.projectClassificationId;
	}

	public void setProjectClassificationId(String projectClassificationId) {
		this.projectClassificationId = projectClassificationId;
	}

	@Column(name = "REVIEW_CLASSIFICATION", nullable = true, length = 32)
	public String getReviewClassification() {
		return this.reviewClassification;
	}

	public void setReviewClassification(String reviewClassification) {
		this.reviewClassification = reviewClassification;
	}

	@Column(name = "SINGLE_CATEGORY_CODE", nullable = true, length = 32)
	public String getSingleCategoryCode() {
		return this.singleCategoryCode;
	}

	public void setSingleCategoryCode(String singleCategoryCode) {
		this.singleCategoryCode = singleCategoryCode;
	}

	@Column(name = "DEMAND_ID", nullable = false, length = 32)
	public String getDemandId() {
		return this.demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	@Column(name = "BATCH_NO", nullable = true, length = 64)
	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "PURCHASE_DEMAND_NAME", nullable = true, length = 128)
	public String getPurchaseDemandName() {
		return this.purchaseDemandName;
	}

	public void setPurchaseDemandName(String purchaseDemandName) {
		this.purchaseDemandName = purchaseDemandName;
	}

	@Column(name = "ESTIMATED_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getEstimatedAmount() {
		return this.estimatedAmount;
	}

	public void setEstimatedAmount(double estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}

	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "RATE", nullable = true, precision = 10, scale = 4)
	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Column(name = "USD_RATE", nullable = true, precision = 10, scale = 4)
	public double getUsdRate() {
		return this.usdRate;
	}

	public void setUsdRate(double usdRate) {
		this.usdRate = usdRate;
	}

	@Column(name = "RECORD_ID", nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "CUSTOMER_ID", nullable = true, length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", nullable = true, length = 512)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "REVIEW_STATUS", nullable = true, precision = 10)
	public Integer getReviewStatus() {
		return this.reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name = "REVIEW_CONCLUSION", nullable = true, length = 32)
	public String getReviewConclusion() {
		return this.reviewConclusion;
	}

	public void setReviewConclusion(String reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
	}

}
