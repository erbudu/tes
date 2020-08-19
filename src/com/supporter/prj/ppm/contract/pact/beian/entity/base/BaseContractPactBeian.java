package com.supporter.prj.ppm.contract.pact.beian.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseContractPactBeian implements Serializable {

	private static final long serialVersionUID = 1L;
	private String recordId; // 主键ID
	private String recordNo; // 备案编号
	private String contractPactName; // 合同及协议名称
	private String contractPactType; // 合同及协议分类
	private String prjId; // 项目ID
	private String prjName; // 项目名称
	private String prjNo; // 项目编号
	private String reportId; // 报审ID
	private String reportNo; // 报审编号
	private String reportName; // 报审名称
	private java.util.Date recordDate; // 备案日期
	private String contactId; // 联系人ID
	private String contactName; // 联系人姓名
	private String contactTel; // 联系人电话
	private String contactMob; // 联系人手机
	private String contactFax; // 联系人传真
	private String creator; // 创建人
	private String creatorId; // 创建人ID
	private java.util.Date creationDate; // 创建日期
	private String modifier; // 修改人
	private String modifierId; // 修改人ID
	private java.util.Date modificationDate; // 修改日期
	private String submitter; // 提交人
	private String submitterId; // 提交人ID
	private java.util.Date submissionDate; // 提交日期
	private int status; // 状态
	private String procId; // 流程ID

	/**
	 * 无参构造
	 */
	public BaseContractPactBeian() {
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BaseContractPactBeian(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactBeian(String recordId, String recordNo, String contractPactName, String contractPactType, String prjId, String prjName,
			String prjNo, String reportId, String reportNo, String reportName, Date recordDate, String contactId, String contactName,
			String contactTel, String contactMob, String contactFax, String creator, String creatorId, Date creationDate, String modifier,
			String modifierId, Date modificationDate, String submitter, String submitterId, Date submissionDate, int status, String procId) {
		this.recordId = recordId;
		this.recordNo = recordNo;
		this.contractPactName = contractPactName;
		this.contractPactType = contractPactType;
		this.prjId = prjId;
		this.prjName = prjName;
		this.prjNo = prjNo;
		this.reportId = reportId;
		this.reportNo = reportNo;
		this.reportName = reportName;
		this.recordDate = recordDate;
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactTel = contactTel;
		this.contactMob = contactMob;
		this.contactFax = contactFax;
		this.creator = creator;
		this.creatorId = creatorId;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modifierId = modifierId;
		this.modificationDate = modificationDate;
		this.submitter = submitter;
		this.submitterId = submitterId;
		this.submissionDate = submissionDate;
		this.status = status;
		this.procId = procId;
	}

	@Id
	@Column(name = "RECORD_ID", nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "RECORD_NO", nullable = true, length = 32)
	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	@Column(name = "CONTRACT_PACT_NAME", nullable = true, length = 128)
	public String getContractPactName() {
		return this.contractPactName;
	}

	public void setContractPactName(String contractPactName) {
		this.contractPactName = contractPactName;
	}

	@Column(name = "CONTRACT_PACT_TYPE", nullable = true, length = 64)
	public String getContractPactType() {
		return this.contractPactType;
	}

	public void setContractPactType(String contractPactType) {
		this.contractPactType = contractPactType;
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

	@Column(name = "REPORT_ID", nullable = true, length = 32)
	public String getReportId() {
		return this.reportId;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "RECORD_DATE", nullable = true)
	public java.util.Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(java.util.Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "CONTACT_ID", nullable = true, length = 32)
	public String getContactId() {
		return this.contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	@Column(name = "CONTACT_NAME", nullable = true, length = 64)
	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "CONTACT_TEL", nullable = true, length = 32)
	public String getContactTel() {
		return this.contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	@Column(name = "CONTACT_MOB", nullable = true, length = 32)
	public String getContactMob() {
		return this.contactMob;
	}

	public void setContactMob(String contactMob) {
		this.contactMob = contactMob;
	}

	@Column(name = "CONTACT_FAX", nullable = true, length = 32)
	public String getContactFax() {
		return this.contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
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

	@Column(name = "STATUS", nullable = true, precision = 10)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "PROC_ID", nullable = true, length = 32)
	public String getProcId() {
		return this.procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}
