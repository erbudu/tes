package com.supporter.prj.ppm.contract.pact.review_ver.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseContractPactRevVer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String revVerId; // 主键ID
	private String revVerNo; // 评审验证编号
	private String revId; // 评审ID
	private String revNo; // 评审编号
	private String reportId; // 报审ID
	private String reportNo; // 报审编号
	private String reportName; // 报审名称
	private String verificatContent; // 评审_验证内容
	private String revVerificatContent; // 评审验证_验证内容
	private String contractPactType; // 合同及协议分类
	private int status; // 状态
	private String prjId; // 项目ID
	private String prjName; // 项目名称
	private String prjNo; // 项目编号
	private String creator; // 创建人
	private String creatorId; // 创建人ID
	private java.util.Date creationDate; // 创建日期
	private String modifier; // 修改人
	private String modifierId; // 修改人ID
	private java.util.Date modificationDate; // 修改日期
	private String submitter; // 提交人
	private String submitterId; // 提交人ID
	private java.util.Date submissionDate; // 提交日期
	private String procId; // 流程ID

	/**
	 * 无参构造
	 */
	public BaseContractPactRevVer() {
	}

	/**
	 * 构造函数.
	 * @param revVerId
	 */
	public BaseContractPactRevVer(String revVerId) {
		this.revVerId = revVerId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactRevVer(String revVerId, String revVerNo, String revId, String revNo, String reportId, String reportNo, String reportName,
			String verificatContent, String revVerificatContent, String contractPactType, int status, String prjId, String prjName, String prjNo,
			String creator, String creatorId, Date creationDate, String modifier, String modifierId, Date modificationDate, String submitter,
			String submitterId, Date submissionDate, String procId) {
		this.revVerId = revVerId;
		this.revVerNo = revVerNo;
		this.revId = revId;
		this.revNo = revNo;
		this.reportId = reportId;
		this.reportNo = reportNo;
		this.reportName = reportName;
		this.verificatContent = verificatContent;
		this.revVerificatContent = revVerificatContent;
		this.contractPactType = contractPactType;
		this.status = status;
		this.prjId = prjId;
		this.prjName = prjName;
		this.prjNo = prjNo;
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
	@Column(name = "REV_VER_ID", nullable = false, length = 32)
	public String getRevVerId() {
		return this.revVerId;
	}

	public void setRevVerId(String revVerId) {
		this.revVerId = revVerId;
	}

	@Column(name = "REV_VER_NO", nullable = true, length = 32)
	public String getRevVerNo() {
		return this.revVerNo;
	}

	public void setRevVerNo(String revVerNo) {
		this.revVerNo = revVerNo;
	}

	@Column(name = "REV_ID", nullable = true, length = 32)
	public String getRevId() {
		return this.revId;
	}

	public void setRevId(String revId) {
		this.revId = revId;
	}

	@Column(name = "REV_NO", nullable = true, length = 32)
	public String getRevNo() {
		return this.revNo;
	}

	public void setRevNo(String revNo) {
		this.revNo = revNo;
	}

	@Column(name = "REPORT_ID", nullable = true, length = 32)
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	@Column(name = "REPORT_NO", nullable = true, length = 32)
	public String getReportNo() {
		return this.reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	@Column(name = "REPORT_NAME", nullable = true, length = 128)
	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Column(name = "VERIFICAT_CONTENT", nullable = true, length = 512)
	public String getVerificatContent() {
		return verificatContent;
	}

	public void setVerificatContent(String verificatContent) {
		this.verificatContent = verificatContent;
	}

	@Column(name = "REV_VERIFICAT_CONTENT", nullable = true, length = 512)
	public String getRevVerificatContent() {
		return this.revVerificatContent;
	}

	public void setRevVerificatContent(String revVerificatContent) {
		this.revVerificatContent = revVerificatContent;
	}

	@Column(name = "CONTRACT_PACT_TYPE", nullable = true, length = 64)
	public String getContractPactType() {
		return this.contractPactType;
	}

	public void setContractPactType(String contractPactType) {
		this.contractPactType = contractPactType;
	}

	@Column(name = "STATUS", nullable = true, precision = 10)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "PRJ_NAME", nullable = true, length = 128)
	public String getPrjName() {
		return this.prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	@Column(name = "PRJ_NO", nullable = true, length = 32)
	public String getPrjNo() {
		return this.prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	@Column(name = "CREATOR", nullable = true, length = 64)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATOR_ID", nullable = true, length = 32)
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE", nullable = true)
	public java.util.Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "MODIFIER", nullable = true, length = 64)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "MODIFIER_ID", nullable = true, length = 32)
	public String getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFICATION_DATE", nullable = true)
	public java.util.Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(java.util.Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Column(name = "SUBMITTER", nullable = true, length = 64)
	public String getSubmitter() {
		return this.submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	@Column(name = "SUBMITTER_ID", nullable = true, length = 32)
	public String getSubmitterId() {
		return this.submitterId;
	}

	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SUBMISSION_DATE", nullable = true)
	public java.util.Date getSubmissionDate() {
		return this.submissionDate;
	}

	public void setSubmissionDate(java.util.Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	@Column(name = "PROC_ID", nullable = true, length = 32)
	public String getProcId() {
		return this.procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}
