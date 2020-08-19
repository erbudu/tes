package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.base;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PcPrePrjInfoAdjust entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePrePrjInfoAdjust implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String filingId;
	private String prjNameZh;
	private String prjNameEn;
	private String ownerNameZh;
	private String ownerNameEn;
	private String prjCompanyNameZh;
	private String prjCompanyNameEn;
	private String market;
	private String prjLevel;
	private String area;
	private String province;
	private String country;
	private String engineeringLocalZh;
	private String engineeringLocalEn;
	private String prjNature;
	private String engineeringScale;
	private String prjAreaId;
	private String prjArea;
	private Double estimatedInvestment;
	private String prjOverview;
	private String foreignAgents;
	private String financingRequirements;
	private String financingGuarantee;
	private Date tenderClosingDate;
	private String developmentWay;
	private String developmentMode;
	private String collaborator;
	private String membership;
	private String createdById;
	private String createdBy;
	private Date createdDate;
	private String linkTel;
	private String leaderId;
	private String leaderName;
	private String deptId;
	private String deptName;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDeptId;
	private String modifiedDeptName;
	private Date modifiedDate;
	private String modifiedContent;
	private Integer status;
	private String procId;
	private String cooperationDeptId;
	private String cooperationDeptName;

	// Constructors


	/** default constructor */
	public BasePrePrjInfoAdjust() {
	}

	/** minimal constructor */
	public BasePrePrjInfoAdjust(String id) {
		this.id = id;
	}

	/** full constructor */
	public BasePrePrjInfoAdjust(String id, String prjNameZh, String prjNameEn, String ownerNameZh, String ownerNameEn,
			String prjCompanyNameZh, String prjCompanyNameEn, String market, String prjLevel, String area,
			String province, String country, String engineeringLocalZh, String engineeringLocalEn, String prjNature,
			String engineeringScale, String prjAreaId, String prjArea, Double estimatedInvestment, String prjOverview,
			String foreignAgents, String financingRequirements, String financingGuarantee, Date tenderClosingDate,
			String developmentWay, String developmentMode, String collaborator, String membership, String createdById,
			String createdBy, String linkTel, String leaderId, String leaderName, String deptId, String deptName,
			String modifiedById, String modifiedBy, String modifiedDeptId, String modifiedDeptName,
			Date modifiedDate, String modifiedContent, Integer status, String procId) {
		this.id = id;
		this.prjNameZh = prjNameZh;
		this.prjNameEn = prjNameEn;
		this.ownerNameZh = ownerNameZh;
		this.ownerNameEn = ownerNameEn;
		this.prjCompanyNameZh = prjCompanyNameZh;
		this.prjCompanyNameEn = prjCompanyNameEn;
		this.market = market;
		this.prjLevel = prjLevel;
		this.area = area;
		this.province = province;
		this.country = country;
		this.engineeringLocalZh = engineeringLocalZh;
		this.engineeringLocalEn = engineeringLocalEn;
		this.prjNature = prjNature;
		this.engineeringScale = engineeringScale;
		this.prjAreaId = prjAreaId;
		this.prjArea = prjArea;
		this.estimatedInvestment = estimatedInvestment;
		this.prjOverview = prjOverview;
		this.foreignAgents = foreignAgents;
		this.financingRequirements = financingRequirements;
		this.financingGuarantee = financingGuarantee;
		this.tenderClosingDate = tenderClosingDate;
		this.developmentWay = developmentWay;
		this.developmentMode = developmentMode;
		this.collaborator = collaborator;
		this.membership = membership;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.linkTel = linkTel;
		this.leaderId = leaderId;
		this.leaderName = leaderName;
		this.deptId = deptId;
		this.deptName = deptName;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDeptId = modifiedDeptId;
		this.modifiedDeptName = modifiedDeptName;
		this.modifiedDate = modifiedDate;
		this.modifiedContent = modifiedContent;
		this.status = status;
		this.procId = procId;
	}

	// Property accessors
	@Id

	@Column(name = "ID", unique = true, nullable = false, length = 32)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Column(name = "OWNER_NAME_ZH", length = 32)

	public String getOwnerNameZh() {
		return this.ownerNameZh;
	}

	public void setOwnerNameZh(String ownerNameZh) {
		this.ownerNameZh = ownerNameZh;
	}

	@Column(name = "OWNER_NAME_EN", length = 64)

	public String getOwnerNameEn() {
		return this.ownerNameEn;
	}

	public void setOwnerNameEn(String ownerNameEn) {
		this.ownerNameEn = ownerNameEn;
	}

	@Column(name = "PRJ_COMPANY_NAME_ZH", length = 128)

	public String getPrjCompanyNameZh() {
		return this.prjCompanyNameZh;
	}

	public void setPrjCompanyNameZh(String prjCompanyNameZh) {
		this.prjCompanyNameZh = prjCompanyNameZh;
	}

	@Column(name = "PRJ_COMPANY_NAME_EN", length = 512)

	public String getPrjCompanyNameEn() {
		return this.prjCompanyNameEn;
	}

	public void setPrjCompanyNameEn(String prjCompanyNameEn) {
		this.prjCompanyNameEn = prjCompanyNameEn;
	}

	@Column(name = "MARKET", length = 10)

	public String getMarket() {
		return this.market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	@Column(name = "PRJ_LEVEL", length = 32)

	public String getPrjLevel() {
		return this.prjLevel;
	}

	public void setPrjLevel(String prjLevel) {
		this.prjLevel = prjLevel;
	}

	@Column(name = "AREA", length = 32)

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "PROVINCE", length = 64)

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "COUNTRY", length = 128)

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "ENGINEERING_LOCAL_ZH", length = 256)

	public String getEngineeringLocalZh() {
		return this.engineeringLocalZh;
	}

	public void setEngineeringLocalZh(String engineeringLocalZh) {
		this.engineeringLocalZh = engineeringLocalZh;
	}

	@Column(name = "ENGINEERING_LOCAL_EN", length = 512)

	public String getEngineeringLocalEn() {
		return this.engineeringLocalEn;
	}

	public void setEngineeringLocalEn(String engineeringLocalEn) {
		this.engineeringLocalEn = engineeringLocalEn;
	}

	@Column(name = "PRJ_NATURE", length = 32)

	public String getPrjNature() {
		return this.prjNature;
	}

	public void setPrjNature(String prjNature) {
		this.prjNature = prjNature;
	}

	@Column(name = "ENGINEERING_SCALE", length = 12)

	public String getEngineeringScale() {
		return this.engineeringScale;
	}

	public void setEngineeringScale(String engineeringScale) {
		this.engineeringScale = engineeringScale;
	}

	@Column(name = "PRJ_AREA_ID", length = 32)

	public String getPrjAreaId() {
		return this.prjAreaId;
	}

	public void setPrjAreaId(String prjAreaId) {
		this.prjAreaId = prjAreaId;
	}

	@Column(name = "PRJ_AREA", length = 128)

	public String getPrjArea() {
		return this.prjArea;
	}

	public void setPrjArea(String prjArea) {
		this.prjArea = prjArea;
	}

	@Column(name = "ESTIMATED_INVESTMENT", precision = 18, scale = 3)

	public Double getEstimatedInvestment() {
		return this.estimatedInvestment;
	}

	public void setEstimatedInvestment(Double estimatedInvestment) {
		this.estimatedInvestment = estimatedInvestment;
	}

	@Lob
	@Column(name = "PRJ_OVERVIEW")

	public String getPrjOverview() {
		return this.prjOverview;
	}

	public void setPrjOverview(String prjOverview) {
		this.prjOverview = prjOverview;
	}

	@Column(name = "FOREIGN_AGENTS", length = 512)

	public String getForeignAgents() {
		return this.foreignAgents;
	}

	public void setForeignAgents(String foreignAgents) {
		this.foreignAgents = foreignAgents;
	}

	@Column(name = "FINANCING_REQUIREMENTS", length = 64)

	public String getFinancingRequirements() {
		return this.financingRequirements;
	}

	public void setFinancingRequirements(String financingRequirements) {
		this.financingRequirements = financingRequirements;
	}

	@Column(name = "FINANCING_GUARANTEE", length = 32)

	public String getFinancingGuarantee() {
		return this.financingGuarantee;
	}

	public void setFinancingGuarantee(String financingGuarantee) {
		this.financingGuarantee = financingGuarantee;
	}

	@Column(name = "TENDER_CLOSING_DATE", length = 11)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTenderClosingDate() {
		return this.tenderClosingDate;
	}

	public void setTenderClosingDate(Date tenderClosingDate) {
		this.tenderClosingDate = tenderClosingDate;
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

	@Column(name = "CREATED_BY_ID", length = 32)

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_BY", length = 64)

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATED_DATE", length = 11)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "LINK_TEL", length = 32)

	public String getLinkTel() {
		return this.linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	@Column(name = "LEADER_ID", length = 32)

	public String getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	@Column(name = "LEADER_NAME", length = 64)

	public String getLeaderName() {
		return this.leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@Column(name = "DEPT_ID", length = 32)

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 128)

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "MODIFIED__BY_ID", length = 32)

	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_BY", length = 64)

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DEPT_ID", length = 32)

	public String getModifiedDeptId() {
		return this.modifiedDeptId;
	}

	public void setModifiedDeptId(String modifiedDeptId) {
		this.modifiedDeptId = modifiedDeptId;
	}

	@Column(name = "MODIFIED_DEPT_NAME", length = 128)

	public String getModifiedDeptName() {
		return this.modifiedDeptName;
	}

	public void setModifiedDeptName(String modifiedDeptName) {
		this.modifiedDeptName = modifiedDeptName;
	}

	@Column(name = "MODIFIED_DATE", length = 11)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "MODIFIED_CONTENT", length = 512)

	public String getModifiedContent() {
		return this.modifiedContent;
	}

	public void setModifiedContent(String modifiedContent) {
		this.modifiedContent = modifiedContent;
	}

	@Column(name = "STATUS", precision = 3, scale = 0)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "PROC_ID", length = 32)

	public String getProcId() {
		return this.procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
	
	@Column(name = "FILING_ID", length = 32)
	public String getFilingId() {
		return filingId;
	}
	
	public void setFilingId(String filingId) {
		this.filingId = filingId;
	}
	@Column(name = "COOPERATION_DEPT_ID", length = 128)
	public String getCooperationDeptId() {
		return cooperationDeptId;
	}

	public void setCooperationDeptId(String cooperationDeptId) {
		this.cooperationDeptId = cooperationDeptId;
	}
	@Column(name = "COOPERATION_DEPT_NAME", length = 258)
	public String getCooperationDeptName() {
		return cooperationDeptName;
	}

	public void setCooperationDeptName(String cooperationDeptName) {
		this.cooperationDeptName = cooperationDeptName;
	}
}