package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PcPreDevelopProgress entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class PcPreDevelopProgress implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String progressId;
	private String filingId;
	private String prjNameZh;
	private String prjNameEn;
	private String developmentWay;
	private String developmentMode;
	private String developmentStatus;
	private String developmentStatusDesc;
	private String reviewStatus;
	private String developmentCore;
	private String foreignAgents;
	private String collaborator;
	private String membership;
	private String prjOverview;
	private String createdBy;
	private String createdById;
	private Date createdDate;
	private String deptId;
	private String deptName;
	private String modifiedBy;
	private String modifiedById;
	private Date modifiedDate;

	// Constructors

	/** default constructor */
	public PcPreDevelopProgress() {
	}

	/** minimal constructor */
	public PcPreDevelopProgress(String progressId) {
		this.progressId = progressId;
	}

	/** full constructor */
	public PcPreDevelopProgress(String progressId, String filingId, String prjNameZh, String prjNameEn,
			String developmentWay, String developmentMode, String developmentStatus, String developmentStatusDesc,
			String reviewStatus, String developmentCore, String foreignAgents, String collaborator, String membership,
			String prjOverview, String createdBy, String createdById, Date createdDate, String deptId,
			String deptName, String modifiedBy, String modifiedById, Date modifiedDate) {
		this.progressId = progressId;
		this.filingId = filingId;
		this.prjNameZh = prjNameZh;
		this.prjNameEn = prjNameEn;
		this.developmentWay = developmentWay;
		this.developmentMode = developmentMode;
		this.developmentStatus = developmentStatus;
		this.developmentStatusDesc = developmentStatusDesc;
		this.reviewStatus = reviewStatus;
		this.developmentCore = developmentCore;
		this.foreignAgents = foreignAgents;
		this.collaborator = collaborator;
		this.membership = membership;
		this.prjOverview = prjOverview;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.deptId = deptId;
		this.deptName = deptName;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@Id

	@Column(name = "PROGRESS_ID", unique = true, nullable = false, length = 32)

	public String getProgressId() {
		return this.progressId;
	}

	public void setProgressId(String progressId) {
		this.progressId = progressId;
	}

	@Column(name = "FILING_ID", length = 32)

	public String getFilingId() {
		return this.filingId;
	}

	public void setFilingId(String filingId) {
		this.filingId = filingId;
	}

	@Column(name = "PRJ_NAME_ZH", length = 128)

	public String getPrjNameZh() {
		return this.prjNameZh;
	}

	public void setPrjNameZh(String prjNameZh) {
		this.prjNameZh = prjNameZh;
	}

	@Column(name = "PRJ_NAME_EN", length = 512)

	public String getPrjNameEn() {
		return this.prjNameEn;
	}

	public void setPrjNameEn(String prjNameEn) {
		this.prjNameEn = prjNameEn;
	}

	@Column(name = "DEVELOPMENT_WAY", length = 32)

	public String getDevelopmentWay() {
		return this.developmentWay;
	}

	public void setDevelopmentWay(String developmentWay) {
		this.developmentWay = developmentWay;
	}

	@Column(name = "DEVELOPMENT_MODE", length = 32)

	public String getDevelopmentMode() {
		return this.developmentMode;
	}

	public void setDevelopmentMode(String developmentMode) {
		this.developmentMode = developmentMode;
	}

	@Column(name = "DEVELOPMENT_STATUS", length = 32)

	public String getDevelopmentStatus() {
		return this.developmentStatus;
	}

	public void setDevelopmentStatus(String developmentStatus) {
		this.developmentStatus = developmentStatus;
	}

	@Column(name = "DEVELOPMENT_STATUS_DESC", length = 128)

	public String getDevelopmentStatusDesc() {
		return this.developmentStatusDesc;
	}

	public void setDevelopmentStatusDesc(String developmentStatusDesc) {
		this.developmentStatusDesc = developmentStatusDesc;
	}

	@Column(name = "REVIEW_STATUS", length = 32)

	public String getReviewStatus() {
		return this.reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name = "DEVELOPMENT_CORE", length = 32)

	public String getDevelopmentCore() {
		return this.developmentCore;
	}

	public void setDevelopmentCore(String developmentCore) {
		this.developmentCore = developmentCore;
	}

	@Column(name = "FOREIGN_AGENTS", length = 128)

	public String getForeignAgents() {
		return this.foreignAgents;
	}

	public void setForeignAgents(String foreignAgents) {
		this.foreignAgents = foreignAgents;
	}

	@Column(name = "COLLABORATOR", length = 512)

	public String getCollaborator() {
		return this.collaborator;
	}

	public void setCollaborator(String collaborator) {
		this.collaborator = collaborator;
	}

	@Column(name = "MEMBERSHIP", length = 512)

	public String getMembership() {
		return this.membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	@Lob
	@Column(name = "PRJ_OVERVIEW")

	public String getPrjOverview() {
		return this.prjOverview;
	}

	public void setPrjOverview(String prjOverview) {
		this.prjOverview = prjOverview;
	}

	@Column(name = "CREATED_BY", length = 64)

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

	@Column(name = "CREATED_DATE", length = 11)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	@Column(name = "MODIFIED_BY", length = 64)

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

	@Column(name = "MODIFIED_DATE", length = 11)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}