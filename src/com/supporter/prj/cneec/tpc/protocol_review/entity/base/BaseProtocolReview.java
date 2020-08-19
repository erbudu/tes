package com.supporter.prj.cneec.tpc.protocol_review.entity.base;

// default package

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractProtocolReview entity provides the base persistence definition of the
 * ProtocolReview entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BaseProtocolReview implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String reviewId;
	private String projectId;
	private String projectName;
	private String projectNo;
	private String customerName;
	private String customerId;
	private String protocolName;
	private String reviewContent;
	private String signerName;
	private String signerId;
	private String reviewConclusion;
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
	public BaseProtocolReview() {
	}

	/** minimal constructor */
	public BaseProtocolReview(String reviewId) {
		this.reviewId = reviewId;
	}

	/** full constructor */
	public BaseProtocolReview(String projectId, String projectName, String projectNo, String customerName, String customerId, String protocolName, String reviewContent,
			String signerName, String signerId, String reviewConclusion, Integer swfStatus, String deptName, String deptId, String createdBy, String createdById,
			String createdDate, String modifiedBy, String modifiedById, String modifiedDate) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectNo = projectNo;
		this.customerName = customerName;
		this.customerId = customerId;
		this.protocolName = protocolName;
		this.reviewContent = reviewContent;
		this.signerName = signerName;
		this.signerId = signerId;
		this.reviewConclusion = reviewConclusion;
		this.swfStatus = swfStatus;
		this.deptName = deptName;
		this.deptId = deptId;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@Id
	@Column(name = "REVIEW_ID", unique = true, nullable = false, length = 32)
	public String getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
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

	@Column(name = "PROJECT_NO", length = 64)
	public String getProjectNo() {
		return this.projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	@Column(name = "CUSTOMER_NAME", length = 1024)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "CUSTOMER_ID", length = 512)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "PROTOCOL_NAME", length = 64)
	public String getProtocolName() {
		return this.protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	@Column(name = "REVIEW_CONTENT", length = 512)
	public String getReviewContent() {
		return this.reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	@Column(name = "SIGNER_NAME", length = 1024)
	public String getSignerName() {
		return this.signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	@Column(name = "SIGNER_ID", length = 512)
	public String getSignerId() {
		return this.signerId;
	}

	public void setSignerId(String signerId) {
		this.signerId = signerId;
	}

	@Column(name = "REVIEW_CONCLUSION", length = 32)
	public String getReviewConclusion() {
		return this.reviewConclusion;
	}

	public void setReviewConclusion(String reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
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