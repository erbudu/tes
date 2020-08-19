package com.supporter.prj.ppm.contract.pact.report.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseContractPactReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reportId; // 主键id
	private String reportNo; // 报审编号
	private String reportName; // 报审名称
	private String prjId; // 项目id
	private String prjNo; // 项目编码
	private String prjNameZh; // 项目中文名称
	private String prjNameEn; // 项目英文名称
	private String contractPactName; // 合同及协议名称
	private String contractPactTypeId; // 合同及协议分类ID
	private String contractPactType; // 合同及协议分类
	private Date startDate; // 报审开始日期
	private Date endDate; // 预计报审完成日期
	private String contactId; // 联系人ID
	private String contactName; // 联系人姓名
	private String contactTel; // 联系人电话
	private String contactMob; // 联系人手机
	private String contactFax; // 联系人传真
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
	public BaseContractPactReport() {

	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReport(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReport(String reportId, String reportNo, String reportName, String prjId, String prjNo, String prjNameZh, String prjNameEn,
			String contractPactName, String contractPactTypeId, String contractPactType, Date startDate, Date endDate, String contactId,
			String contactName, String contactTel, String contactMob, String contactFax, int status, String creator, String creatorId,
			Date creationDate, String modifier, String modifierId, Date modificationDate, String submitter, String submitterId, Date submissionDate,
			String procId) {
		this.reportId = reportId;
		this.reportNo = reportNo;
		this.reportName = reportName;
		this.prjId = prjId;
		this.prjNo = prjNo;
		this.prjNameZh = prjNameZh;
		this.prjNameEn = prjNameEn;
		this.contractPactName = contractPactName;
		this.contractPactTypeId = contractPactTypeId;
		this.contractPactType = contractPactType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactTel = contactTel;
		this.contactMob = contactMob;
		this.contactFax = contactFax;
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
	@Column(name = "report_id", nullable = false, length = 32)
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

	@Column(name = "prj_name_zh", nullable = true, length = 128)
	public String getPrjNameZh() {
		return prjNameZh;
	}

	public void setPrjNameZh(String prjNameZh) {
		this.prjNameZh = prjNameZh;
	}

	@Column(name = "prj_name_en", nullable = true, length = 128)
	public String getPrjNameEn() {
		return prjNameEn;
	}

	public void setPrjNameEn(String prjNameEn) {
		this.prjNameEn = prjNameEn;
	}

	@Column(name = "contract_pact_name", nullable = true, length = 128)
	public String getContractPactName() {
		return contractPactName;
	}

	public void setContractPactName(String contractPactName) {
		this.contractPactName = contractPactName;
	}

	@Column(name = "contract_pact_type_id", nullable = true, length = 64)
	public String getContractPactTypeId() {
		return contractPactTypeId;
	}

	public void setContractPactTypeId(String contractPactTypeId) {
		this.contractPactTypeId = contractPactTypeId;
	}

	@Column(name = "contract_pact_type", nullable = true, length = 64)
	public String getContractPactType() {
		return contractPactType;
	}

	public void setContractPactType(String contractPactType) {
		this.contractPactType = contractPactType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", nullable = true, length = 32)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = true, length = 32)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "contact_id", nullable = true, length = 32)
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	@Column(name = "contact_name", nullable = true, length = 64)
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "contact_tel", nullable = true, length = 32)
	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	@Column(name = "contact_mob", nullable = true, length = 32)
	public String getContactMob() {
		return contactMob;
	}

	public void setContactMob(String contactMob) {
		this.contactMob = contactMob;
	}

	@Column(name = "contact_fax", nullable = true, length = 32)
	public String getContactFax() {
		return contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	@Column(name = "status")
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
