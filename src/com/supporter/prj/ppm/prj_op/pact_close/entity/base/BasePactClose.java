package com.supporter.prj.ppm.prj_op.pact_close.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass
public class BasePactClose implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;// 主键
	private String prjId;// 项目id
	private String prjNameZh;// 项目中文名称
	private String prjNameEn;// 项目英文名称
	private String reportId;// 报审id
	private String reportNo;// 报审编号
	private String pactName;// 协议名称
	private String closeReason;// 项目关闭原因
	private String explain;// 说明
	private String deptId;// 部门id
	private String deptName;// 部门名称
	private String contactId; // 联系人姓名id
	private String contactName;// 联系人姓名
	private String contactNumber;// 联系人电话
	private int status;// 状态
	private String creator;// 创建人
	private String creatorId;// 创建人id
	private java.util.Date creationDate;// 创建日期
	private String modifier;// 修改人
	private String modifierId;// 修改人id
	private java.util.Date modificationDate;// 修改日期
	private String submitter;// 提交人
	private String submitterId;// 提交人id
	private java.util.Date submitDate;// 提交日期
	private String procId;// 流程id

	/**
	 * 无参构造函数.
	 */
	public BasePactClose() {
	}

	/**
	 * 构造函数.
	 *
	 * @param id
	 */
	public BasePactClose(String id) {
		this.id = id;
	}

	/**
	 * 全参构造
	 */
	public BasePactClose(String id, String prjId, String prjNameZh, String prjNameEn, String reportId, String reportNo, String pactName,
			String closeReason, String explain, String deptId, String deptName, String contactId, String contactName, String contactNumber,
			int status, String creator, String creatorId, Date creationDate, String modifier, String modifierId, Date modificationDate,
			String submitter, String submitterId, Date submitDate, String procId) {
		this.id = id;
		this.prjId = prjId;
		this.prjNameZh = prjNameZh;
		this.prjNameEn = prjNameEn;
		this.reportId = reportId;
		this.reportNo = reportNo;
		this.pactName = pactName;
		this.closeReason = closeReason;
		this.explain = explain;
		this.deptId = deptId;
		this.deptName = deptName;
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactNumber = contactNumber;
		this.status = status;
		this.creator = creator;
		this.creatorId = creatorId;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modifierId = modifierId;
		this.modificationDate = modificationDate;
		this.submitter = submitter;
		this.submitterId = submitterId;
		this.submitDate = submitDate;
		this.procId = procId;
	}

	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "PRJ_NAME_ZH", nullable = true, length = 64)
	public String getPrjNameZh() {
		return this.prjNameZh;
	}

	public void setPrjNameZh(String prjNameZh) {
		this.prjNameZh = prjNameZh;
	}

	@Column(name = "PRJ_NAME_EN", nullable = true, length = 64)
	public String getPrjNameEn() {
		return this.prjNameEn;
	}

	public void setPrjNameEn(String prjNameEn) {
		this.prjNameEn = prjNameEn;
	}

	@Column(name = "REPORT_ID", nullable = true, length = 512)
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	@Column(name = "REPORT_NO", nullable = true, length = 512)
	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	@Column(name = "PACT_NAME", nullable = true, length = 1024)
	public String getPactName() {
		return pactName;
	}

	public void setPactName(String pactName) {
		this.pactName = pactName;
	}

	@Column(name = "CLOSE_REASON", nullable = true, length = 512)
	public String getCloseReason() {
		return this.closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	@Column(name = "EXPLAIN", nullable = true, length = 512)
	public String getExplain() {
		return this.explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", nullable = true, length = 64)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	@Column(name = "CONTACT_NUMBER", nullable = true, length = 32)
	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name = "STATUS", nullable = true, precision = 10)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	@Column(name = "SUBMIT_DATE", nullable = true)
	public java.util.Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(java.util.Date submitDate) {
		this.submitDate = submitDate;
	}

	@Column(name = "PROC_ID", nullable = true, length = 32)
	public String getProcId() {
		return this.procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}
