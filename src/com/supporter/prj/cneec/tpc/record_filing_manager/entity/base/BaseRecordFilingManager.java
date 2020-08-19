package com.supporter.prj.cneec.tpc.record_filing_manager.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractTpcRecordFilingManager entity provides the base persistence
 * definition of the TpcRecordFilingManager entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class BaseRecordFilingManager implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private String recordFilingId;
	private String recordFilingNo;
	private String recordFilingType;
	private String recordFilingTypeCode;
	private String projectId;
	private String projectName;
	private String reviewId;
	private String reviewNo;
	private String isUseSeal;
	private String sealId;
	private String sealNo;
	private String businessNo;
	private String filesName;
	private String negotiateImportantRecord;
	private Integer swfStatus;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String deptName;
	private String deptId;
	private String preformId;// 该ID存储备案前置单ID（合同签约评审合同的inforId,或是衍生合同单的derivativeId）
	private String contractName;
	private int useSealCopies;
	// Constructors

	/** default constructor */
	public BaseRecordFilingManager() {
	}

	/** minimal constructor */
	public BaseRecordFilingManager(String recordFilingId) {
		this.recordFilingId = recordFilingId;
	}

	/** full constructor */
	public BaseRecordFilingManager(String recordFilingId,
			String recordFilingNo, String recordFilingType,
			String recordFilingTypeCode, String projectId, String projectName,
			String reviewId, String reviewNo, String isUseSeal, String sealId,
			String sealNo, String businessNo, String filesName,
			String negotiateImportantRecord, Integer swfStatus,
			String createdBy, String createdById, String createdDate,
			String modifiedBy, String modifiedById, String modifiedDate) {
		this.recordFilingId = recordFilingId;
		this.recordFilingNo = recordFilingNo;
		this.recordFilingType = recordFilingType;
		this.recordFilingTypeCode = recordFilingTypeCode;
		this.projectId = projectId;
		this.projectName = projectName;
		this.reviewId = reviewId;
		this.reviewNo = reviewNo;
		this.isUseSeal = isUseSeal;
		this.sealId = sealId;
		this.sealNo = sealNo;
		this.businessNo = businessNo;
		this.filesName = filesName;
		this.negotiateImportantRecord = negotiateImportantRecord;
		this.swfStatus = swfStatus;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@Id
	@Column(name = "RECORD_FILING_ID", nullable = false, length = 32)
	public String getRecordFilingId() {
		return this.recordFilingId;
	}

	public void setRecordFilingId(String recordFilingId) {
		this.recordFilingId = recordFilingId;
	}

	@Column(name = "RECORD_FILING_NO", length = 64)
	public String getRecordFilingNo() {
		return this.recordFilingNo;
	}

	public void setRecordFilingNo(String recordFilingNo) {
		this.recordFilingNo = recordFilingNo;
	}

	@Column(name = "RECORD_FILING_TYPE", length = 64)
	public String getRecordFilingType() {
		return this.recordFilingType;
	}

	public void setRecordFilingType(String recordFilingType) {
		this.recordFilingType = recordFilingType;
	}

	@Column(name = "RECORD_FILING_TYPE_CODE", length = 32)
	public String getRecordFilingTypeCode() {
		return this.recordFilingTypeCode;
	}

	public void setRecordFilingTypeCode(String recordFilingTypeCode) {
		this.recordFilingTypeCode = recordFilingTypeCode;
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

	@Column(name = "REVIEW_ID", length = 32)
	public String getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "REVIEW_NO", length = 768)
	public String getReviewNo() {
		return this.reviewNo;
	}

	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}

	@Column(name = "IS_USE_SEAL", length = 1)
	public String getIsUseSeal() {
		return this.isUseSeal;
	}

	public void setIsUseSeal(String isUseSeal) {
		this.isUseSeal = isUseSeal;
	}

	@Column(name = "SEAL_ID", length = 32)
	public String getSealId() {
		return this.sealId;
	}

	public void setSealId(String sealId) {
		this.sealId = sealId;
	}

	@Column(name = "SEAL_NO", length = 32)
	public String getSealNo() {
		return this.sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	@Column(name = "BUSINESS_NO", length = 32)
	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Column(name = "FILES_NAME", length = 1024)
	public String getFilesName() {
		return this.filesName;
	}

	public void setFilesName(String filesName) {
		this.filesName = filesName;
	}

	@Column(name = "NEGOTIATE_IMPORTANT_RECORD", length = 1024)
	public String getNegotiateImportantRecord() {
		return this.negotiateImportantRecord;
	}

	public void setNegotiateImportantRecord(String negotiateImportantRecord) {
		this.negotiateImportantRecord = negotiateImportantRecord;
	}

	@Column(name = "SWF_STATUS", precision = 6, scale = 0)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
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

	@Column(name = "PREFORM_ID", nullable = true, length = 32)
	public String getPreformId() {
		return this.preformId;
	}

	public void setPreformId(String preformId) {
		this.preformId = preformId;
	}

	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "USE_SEAL_COPIES", nullable = true, precision = 10)
	public int getUseSealCopies() {
		return this.useSealCopies;
	}

	public void setUseSealCopies(int useSealCopies) {
		this.useSealCopies = useSealCopies;
	}

}