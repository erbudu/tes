package com.supporter.prj.ppm.contract.pact.review.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseContractPactReview implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reviewId; // 评审id
	private String reviewNo; // 报告编号
	private String reportId; // 报审id
	private String reportNo; // 报审编号
	private String reportName; // 报审名称
	private String prjId; // 项目id
	private String prjNo; // 项目编码
	private String prjName; // 项目名称
	private String reviewType; // 评估性质
	private String contractPactType; // 合同及协议分类
	private int status; // 状态
	private String creator; // 创建人
	private String creatorId; // 创建人ID
	private Date creationDate; // 创建日期
	private String modifier; // 修改人
	private String modifierId; // 修改人ID
	private Date modificationDate; // 修改日期
	private String submitter; // 提交人
	private String submitterId; // 提交人ID
	private Date submissionDate; // 提交日期
	private String procId; // 流程ID

	/**
	 * 无参构造
	 */
	public BaseContractPactReview() {
	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReview(String reviewId) {
		this.reviewId = reviewId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReview(String reviewId, String reviewNo, String reportId, String reportNo, String reportName, String prjId, String prjNo,
			String prjName, String reviewType, String contractPactType, int status, String creator, String creatorId, Date creationDate,
			String modifier, String modifierId, Date modificationDate, String submitter, String submitterId, Date submissionDate, String procId) {
		this.reviewId = reviewId;
		this.reviewNo = reviewNo;
		this.reportId = reportId;
		this.reportNo = reportNo;
		this.reportName = reportName;
		this.prjId = prjId;
		this.prjNo = prjNo;
		this.prjName = prjName;
		this.reviewType = reviewType;
		this.contractPactType = contractPactType;
		this.status = status;
		this.creator = creator;
		this.creatorId = creatorId;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modifierId = modifierId;
		this.modificationDate = modificationDate;
		this.submitter = submitter;
		this.submitterId = submitterId;
		this.submissionDate = submissionDate;
		this.procId = procId;
	}

	@Id
	@Column(name = "review_id", nullable = false, length = 32)
	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "review_no", nullable = true, length = 32)
	public String getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}

	@Column(name = "report_id", nullable = true, length = 32)
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	@Column(name = "report_no", nullable = true, length = 32)
	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	@Column(name = "report_name", nullable = true, length = 128)
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Column(name = "prj_id", nullable = true, length = 32)
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "prj_no", nullable = true, length = 32)
	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	@Column(name = "prj_name", nullable = true, length = 128)
	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	@Column(name = "review_type", nullable = true, length = 128)
	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	@Column(name = "contract_pact_type", nullable = true, length = 64)
	public String getContractPactType() {
		return contractPactType;
	}

	public void setContractPactType(String contractPactType) {
		this.contractPactType = contractPactType;
	}

	@Column(name = "status", nullable = true)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "creator", nullable = true, length = 64)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "creator_id", nullable = true, length = 32)
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = true)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "modifier", nullable = true, length = 64)
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "modifier_id", nullable = true, length = 32)
	public String getModifierId() {
		return modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date", nullable = true)
	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Column(name = "submitter", nullable = true, length = 64)
	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	@Column(name = "submitter_id", nullable = true, length = 32)
	public String getSubmitterId() {
		return submitterId;
	}

	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "submission_date", nullable = true)
	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	@Column(name = "proc_id", nullable = true, length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}
