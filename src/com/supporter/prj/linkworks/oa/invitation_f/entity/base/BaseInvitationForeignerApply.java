package com.supporter.prj.linkworks.oa.invitation_f.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaInvitationForeignerApply entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseInvitationForeignerApply implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String invitationId;
	private String applyReason;
	private String planInboundDate;
	private String visitPlaces;
	private String visaProcessingOffice;
	private String visaExpDate;
	private String visaEffectiveNumber;
	private String inboundCommentFile;
	private Long stayDays;
	private String scheduleFile;
	private String applyId;
	private String applyName;
	private String deptId;
	private String deptName;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private Integer invitationStatus;
	private String companyLeaderId;
	private String companyLeaderName;
	private String procId;
	private boolean history;
	private boolean isPoliticians;
	private String politiciansFile;
	private String telephone;
	private String fax;
	private String mail;
	
	
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
	public boolean getHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}
	// Constructors
	@Column(name = "PROC_ID", length = 64)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	/** default constructor */
	public BaseInvitationForeignerApply() {
	}

	/** minimal constructor */
	public BaseInvitationForeignerApply(String invitationId) {
		this.invitationId = invitationId;
	}

	/** full constructor */
	public BaseInvitationForeignerApply(String invitationId,
			String applyReason, String planInboundDate, String visitPlaces,
			String visaProcessingOffice, String visaExpDate,
			String visaEffectiveNumber, String inboundCommentFile,
			Long stayDays, String scheduleFile, String applyId,
			String applyName, String deptId, String deptName, String createdBy,
			String createdById, String createdDate, String modifiedBy,
			String modifiedById, String modifiedDate, Integer invitationStatus,
			String companyLeaderId, String companyLeaderName, String procId,
			boolean isPoliticians,String politiciansFile, String telephone,
			String fax, String mail) {
		this.invitationId = invitationId;
		this.applyReason = applyReason;
		this.planInboundDate = planInboundDate;
		this.visitPlaces = visitPlaces;
		this.visaProcessingOffice = visaProcessingOffice;
		this.visaExpDate = visaExpDate;
		this.visaEffectiveNumber = visaEffectiveNumber;
		this.inboundCommentFile = inboundCommentFile;
		this.stayDays = stayDays;
		this.scheduleFile = scheduleFile;
		this.applyId = applyId;
		this.applyName = applyName;
		this.deptId = deptId;
		this.deptName = deptName;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.invitationStatus = invitationStatus;
		this.companyLeaderId = companyLeaderId;
		this.companyLeaderName = companyLeaderName;
		this.procId = procId;
		this.isPoliticians = isPoliticians;
		this.politiciansFile = politiciansFile;
		this.telephone = telephone;
		this.fax = fax;
		this.mail = mail;
	}

	// Property accessors
	@Id
	@Column(name = "INVITATION_ID", unique = true, nullable = false, length = 32)
	public String getInvitationId() {
		return this.invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	@Column(name = "APPLY_REASON", length = 1024)
	public String getApplyReason() {
		return this.applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	@Column(name = "PLAN_INBOUND_DATE", length = 27)
	public String getPlanInboundDate() {
		return this.planInboundDate;
	}

	public void setPlanInboundDate(String planInboundDate) {
		this.planInboundDate = planInboundDate;
	}

	@Column(name = "VISIT_PLACES", length = 512)
	public String getVisitPlaces() {
		return this.visitPlaces;
	}

	public void setVisitPlaces(String visitPlaces) {
		this.visitPlaces = visitPlaces;
	}

	@Column(name = "VISA_PROCESSING_OFFICE", length = 512)
	public String getVisaProcessingOffice() {
		return this.visaProcessingOffice;
	}

	public void setVisaProcessingOffice(String visaProcessingOffice) {
		this.visaProcessingOffice = visaProcessingOffice;
	}

	@Column(name = "VISA_EXP_DATE", length = 512)
	public String getVisaExpDate() {
		return this.visaExpDate;
	}

	public void setVisaExpDate(String visaExpDate) {
		this.visaExpDate = visaExpDate;
	}

	@Column(name = "VISA_EFFECTIVE_NUMBER", length = 64)
	public String getVisaEffectiveNumber() {
		return this.visaEffectiveNumber;
	}

	public void setVisaEffectiveNumber(String visaEffectiveNumber) {
		this.visaEffectiveNumber = visaEffectiveNumber;
	}

	@Column(name = "INBOUND_COMMENT_FILE")
	public String getInboundCommentFile() {
		return this.inboundCommentFile;
	}

	public void setInboundCommentFile(String inboundCommentFile) {
		this.inboundCommentFile = inboundCommentFile;
	}

	@Column(name = "STAY_DAYS", precision = 22, scale = 0)
	public Long getStayDays() {
		return this.stayDays;
	}

	public void setStayDays(Long stayDays) {
		this.stayDays = stayDays;
	}

	@Column(name = "SCHEDULE_FILE")
	public String getScheduleFile() {
		return this.scheduleFile;
	}

	public void setScheduleFile(String scheduleFile) {
		this.scheduleFile = scheduleFile;
	}

	@Column(name = "APPLY_ID", length = 32)
	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@Column(name = "APPLY_NAME", length = 32)
	public String getApplyName() {
		return this.applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 64)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	@Column(name = "CREATED_DATE", length = 27)
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

	@Column(name = "MODIFIED_DATE", length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "INVITATION_STATUS", precision = 22, scale = 0)
	public Integer getInvitationStatus() {
		return this.invitationStatus;
	}

	public void setInvitationStatus(Integer invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

	@Column(name = "COMPANY_LEADER_ID", length = 32)
	public String getCompanyLeaderId() {
		return this.companyLeaderId;
	}

	public void setCompanyLeaderId(String companyLeaderId) {
		this.companyLeaderId = companyLeaderId;
	}

	@Column(name = "COMPANY_LEADER_NAME", length = 128)
	public String getCompanyLeaderName() {
		return this.companyLeaderName;
	}

	public void setCompanyLeaderName(String companyLeaderName) {
		this.companyLeaderName = companyLeaderName;
	}
	@Column(name = "IS_POLITICIANS")
	@org.hibernate.annotations.Type(type="true_false")
	public boolean getIsPoliticians() {
		return isPoliticians;
	}
	
	public void setIsPoliticians(boolean isPoliticians) {
		this.isPoliticians = isPoliticians;
	}
	
	@Column(name = "POLITICIANS_FILE")
	public String getPoliticiansFile() {
		return politiciansFile;
	}
	
	public void setPoliticiansFile(String politiciansFile) {
		this.politiciansFile = politiciansFile;
	}

	@Column(name = "TELEPHONE", length = 128)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "FAX", length = 128)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "MAIL", length = 128)
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}