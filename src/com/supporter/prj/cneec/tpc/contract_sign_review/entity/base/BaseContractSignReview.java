package com.supporter.prj.cneec.tpc.contract_sign_review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_REVIEW,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-09-28
 * @version V1.0
 * 
 */
@MappedSuperclass
public class BaseContractSignReview implements Serializable {

	private static final long serialVersionUID = 1L;
	private String signId;
	private String projectId;
	private String projectName;
	private String projectNature;
	private String projectNatureId;
	private String reviewNo;
	private String reviewContent;
	private String demandId;
	private String batchNo;
	private String purchaseDemandName;
	private String externalId;
	private String quotationTitle;
	private String signerName;
	private String signerId;
	private String describes;
	private String reviewConclusion;
	private int reviewType;
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
	private int stepStatus;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignReview() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param signId
	 */
	public BaseContractSignReview(String signId) {
		this.signId = signId;
	}

	/**
	 * GET方法: 取得SIGN_ID.
	 * 
	 * @return: String SIGN_ID
	 */
	@Id
	@Column(name = "SIGN_ID", nullable = false, length = 32)
	public String getSignId() {
		return this.signId;
	}

	/**
	 * SET方法: 设置SIGN_ID.
	 * 
	 * @param: String SIGN_ID
	 */
	public void setSignId(String signId) {
		this.signId = signId;
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
	 * GET方法: 取得PROJECT_NATURE.
	 * 
	 * @return: String PROJECT_NATURE
	 */
	@Column(name = "PROJECT_NATURE", nullable = true, length = 128)
	public String getProjectNature() {
		return this.projectNature;
	}

	/**
	 * SET方法: 设置PROJECT_NATURE.
	 * 
	 * @param: String PROJECT_NATURE
	 */
	public void setProjectNature(String projectNature) {
		this.projectNature = projectNature;
	}

	/**
	 * GET方法: 取得PROJECT_NATURE_ID.
	 * 
	 * @return: String PROJECT_NATURE_ID
	 */
	@Column(name = "PROJECT_NATURE_ID", nullable = true, length = 128)
	public String getProjectNatureId() {
		return this.projectNatureId;
	}

	/**
	 * SET方法: 设置PROJECT_NATURE_ID.
	 * 
	 * @param: String PROJECT_NATURE_ID
	 */
	public void setProjectNatureId(String projectNatureId) {
		this.projectNatureId = projectNatureId;
	}

	/**
	 * GET方法: 取得REVIEW_NO.
	 * 
	 * @return: String REVIEW_NO
	 */
	@Column(name = "REVIEW_NO", nullable = true, length = 32)
	public String getReviewNo() {
		return this.reviewNo;
	}

	/**
	 * SET方法: 设置REVIEW_NO.
	 * 
	 * @param: String REVIEW_NO
	 */
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}

	/**
	 * GET方法: 取得REVIEW_CONTENT.
	 * 
	 * @return: String REVIEW_CONTENT
	 */
	@Column(name = "REVIEW_CONTENT", nullable = true, length = 512)
	public String getReviewContent() {
		return this.reviewContent;
	}

	/**
	 * SET方法: 设置REVIEW_CONTENT.
	 * 
	 * @param: String REVIEW_CONTENT
	 */
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	/**
	 * GET方法: 取得DEMAND_ID.
	 * 
	 * @return: String DEMAND_ID
	 */
	@Column(name = "DEMAND_ID", nullable = true, length = 512)
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
	@Column(name = "BATCH_NO", nullable = true, length = 1024)
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

	/**
	 * GET方法: 取得PURCHASE_DEMAND_NAME.
	 * 
	 * @return: String PURCHASE_DEMAND_NAME
	 */
	@Column(name = "PURCHASE_DEMAND_NAME", nullable = true, length = 1024)
	public String getPurchaseDemandName() {
		return this.purchaseDemandName;
	}

	/**
	 * SET方法: 设置PURCHASE_DEMAND_NAME.
	 * 
	 * @param: String PURCHASE_DEMAND_NAME
	 */
	public void setPurchaseDemandName(String purchaseDemandName) {
		this.purchaseDemandName = purchaseDemandName;
	}

	/**
	 * GET方法: 取得EXTERNAL_ID.
	 * 
	 * @return: String EXTERNAL_ID
	 */
	@Column(name = "EXTERNAL_ID", nullable = true, length = 512)
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
	 * GET方法: 取得QUOTATION_TITLE.
	 * 
	 * @return: String QUOTATION_TITLE
	 */
	@Column(name = "QUOTATION_TITLE", nullable = true, length = 1024)
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

	@Column(name = "SIGNER_NAME", nullable = true, length = 1024)
	public String getSignerName() {
		return signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	@Column(name = "SIGNER_ID", nullable = true, length = 512)
	public String getSignerId() {
		return signerId;
	}

	public void setSignerId(String signerId) {
		this.signerId = signerId;
	}

	/**
	 * GET方法: 取得DESCRIBES.
	 * 
	 * @return: String DESCRIBES
	 */
	@Column(name = "DESCRIBES", nullable = true, length = 512)
	public String getDescribes() {
		return this.describes;
	}

	/**
	 * SET方法: 设置DESCRIBES.
	 * 
	 * @param: String DESCRIBES
	 */
	public void setDescribes(String describes) {
		this.describes = describes;
	}

	/**
	 * GET方法: 取得REVIEW_CONCLUSION.
	 * 
	 * @return: String REVIEW_CONCLUSION
	 */
	@Column(name = "REVIEW_CONCLUSION", nullable = true, length = 32)
	public String getReviewConclusion() {
		return this.reviewConclusion;
	}

	/**
	 * SET方法: 设置REVIEW_CONCLUSION.
	 * 
	 * @param: String REVIEW_CONCLUSION
	 */
	public void setReviewConclusion(String reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
	}

	/**
	 * GET方法: 取得REVIEW_TYPE.
	 * 
	 * @return: int REVIEW_TYPE
	 */
	@Column(name = "REVIEW_TYPE", nullable = true, precision = 10)
	public int getReviewType() {
		return this.reviewType;
	}

	/**
	 * SET方法: 设置REVIEW_TYPE.
	 * 
	 * @param: int REVIEW_TYPE
	 */
	public void setReviewType(int reviewType) {
		this.reviewType = reviewType;
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
	 * @return: int SWF_STATUS
	 */
	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	/**
	 * SET方法: 设置SWF_STATUS.
	 * 
	 * @param: int SWF_STATUS
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
	@Column(name = "CREATED_DATE", nullable = true, length = 27)
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
	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
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

	@Column(name = "STEP_STATUS", nullable = true, precision = 10)
	public Integer getStepStatus() {
		return this.stepStatus;
	}

	public void setStepStatus(Integer stepStatus) {
		this.stepStatus = stepStatus;
	}

}
