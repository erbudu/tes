package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PcPrePrjManagerFiling entity. @author MyEclipse Persistence Tools
 */

@MappedSuperclass
public class BasePrePrjManagerFiling implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filingId;
	private String prjNameZh;//项目中文名称
	private String prjNameEn;//项目英文名称
	private String ownerNameZh;//业主中文名称
	private String ownerNameEn;//业主英文名称
	private String prjCompanyNameZh;//项目公司中文名称
	private String prjCompanyNameEn;//项目公司英文名称
	private String market;//国别
	private String prjLevel;//项目级别
	private String area;//所属区域
	private String province;//省份/国家
	private String country;
	private String ynArea;//是否此地区
	private String region;//地区
	private String engineeringLocalZh;//工程所在地（中文）
	private String engineeringLocalEn;//工程所在地（英文）
	private String prjNature;//项目性质
	private String engineeringScale;//工程规模
	private String prjClass;//项目分类
	private String prjAreaId;//项目领域
	private String prjArea;//项目领域
	private Double estimatedInvestment;//项目估算总投资（万美元）
	private String prjScopeIds;//项目范围
	private String prjScopeNames;//项目范围
	private String prjProducts;//项目所涉及主导产品
	private String foreignAgents;//国外代理商
	private String prjOverview;//项目概述
	private String createdById;//
	private String createdBy;//
	private String linkTel;
	private String followingManId;//
	private String followingMan;//跟踪人
	private String leaderId;//
	private String leaderName;//开发负责人
	private String checkingResult;//统自查结果
	private String coordinationPrjId;//开发协调项目
	private String coordinationPrjNo;//开发协调项目
	private String coordinationPrjName;//开发协调项目
	private String ynCompatible;//项目是否可协调
	private String deptId;
	private String deptName;//
	private String procId;
	private String areaId;
	private String provinceId;
	private String countryId;
	private String ownerId;
	private Integer status;
	private String modifiedById;
	private String modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	private Date finishDate;
	@Column(name = "FINISH_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	// Constructors
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	/** default constructor */
	public BasePrePrjManagerFiling() {
	}

	/** minimal constructor */
	public BasePrePrjManagerFiling(String filingId) {
		this.filingId = filingId;
	}

	/** full constructor */
	public BasePrePrjManagerFiling(String filingId, String prjNameZh, String prjNameEn, String ownerNameZh,
			String ownerNameEn, String prjCompanyNameZh, String prjCompanyNameEn, String market, String prjLevel,
			String area, String province, String country, String ynArea, String region, String engineeringLocalZh,
			String engineeringLocalEn, String prjNature, String engineeringScale, String prjClass, String prjAreaId,
			String prjArea, Double estimatedInvestment, String prjScopeIds, String prjScopeNames, String prjProducts,
			String foreignAgents, String prjOverview, String createdById, String createdBy, String linkTel,
			String followingManId, String followingMan, String leaderId, String leaderName, String checkingResult,
			String coordinationPrjId, String coordinationPrjNo, String coordinationPrjName, String ynCompatible,
			String deptId, String deptName, String procId, String areaId, String provinceId, String countryId,
			String ownerId, Integer status) {
		this.filingId = filingId;
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
		this.ynArea = ynArea;
		this.region = region;
		this.engineeringLocalZh = engineeringLocalZh;
		this.engineeringLocalEn = engineeringLocalEn;
		this.prjNature = prjNature;
		this.engineeringScale = engineeringScale;
		this.prjClass = prjClass;
		this.prjAreaId = prjAreaId;
		this.prjArea = prjArea;
		this.estimatedInvestment = estimatedInvestment;
		this.prjScopeIds = prjScopeIds;
		this.prjScopeNames = prjScopeNames;
		this.prjProducts = prjProducts;
		this.foreignAgents = foreignAgents;
		this.prjOverview = prjOverview;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.linkTel = linkTel;
		this.followingManId = followingManId;
		this.followingMan = followingMan;
		this.leaderId = leaderId;
		this.leaderName = leaderName;
		this.checkingResult = checkingResult;
		this.coordinationPrjId = coordinationPrjId;
		this.coordinationPrjNo = coordinationPrjNo;
		this.coordinationPrjName = coordinationPrjName;
		this.ynCompatible = ynCompatible;
		this.deptId = deptId;
		this.deptName = deptName;
		this.procId = procId;
		this.areaId = areaId;
		this.provinceId = provinceId;
		this.countryId = countryId;
		this.ownerId = ownerId;
		this.status = status;
	}

	// Property accessors
	@Id

	@Column(name = "FILING_ID", unique = true, nullable = false, length = 32)

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

	@Column(name = "YN_AREA", length = 10)

	public String getYnArea() {
		return this.ynArea;
	}

	public void setYnArea(String ynArea) {
		this.ynArea = ynArea;
	}

	@Column(name = "REGION", length = 128)

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	@Column(name = "PRJ_CLASS", length = 32)

	public String getPrjClass() {
		return this.prjClass;
	}

	public void setPrjClass(String prjClass) {
		this.prjClass = prjClass;
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

	@Column(name = "PRJ_SCOPE_IDS", length = 256)

	public String getPrjScopeIds() {
		return this.prjScopeIds;
	}

	public void setPrjScopeIds(String prjScopeIds) {
		this.prjScopeIds = prjScopeIds;
	}

	@Column(name = "PRJ_SCOPE_NAMES", length = 512)

	public String getPrjScopeNames() {
		return this.prjScopeNames;
	}

	public void setPrjScopeNames(String prjScopeNames) {
		this.prjScopeNames = prjScopeNames;
	}

	@Column(name = "PRJ_PRODUCTS", length = 512)

	public String getPrjProducts() {
		return this.prjProducts;
	}

	public void setPrjProducts(String prjProducts) {
		this.prjProducts = prjProducts;
	}

	@Column(name = "FOREIGN_AGENTS", length = 512)

	public String getForeignAgents() {
		return this.foreignAgents;
	}

	public void setForeignAgents(String foreignAgents) {
		this.foreignAgents = foreignAgents;
	}

	@Lob
	@Column(name = "PRJ_OVERVIEW")

	public String getPrjOverview() {
		return this.prjOverview;
	}

	public void setPrjOverview(String prjOverview) {
		this.prjOverview = prjOverview;
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

	@Column(name = "LINK_TEL", length = 13)

	public String getLinkTel() {
		return this.linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	@Column(name = "FOLLOWING_MAN_ID", length = 32)

	public String getFollowingManId() {
		return this.followingManId;
	}

	public void setFollowingManId(String followingManId) {
		this.followingManId = followingManId;
	}

	@Column(name = "FOLLOWING_MAN", length = 64)

	public String getFollowingMan() {
		return this.followingMan;
	}

	public void setFollowingMan(String followingMan) {
		this.followingMan = followingMan;
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

	@Column(name = "CHECKING_RESULT", length = 128)

	public String getCheckingResult() {
		return this.checkingResult;
	}

	public void setCheckingResult(String checkingResult) {
		this.checkingResult = checkingResult;
	}

	@Column(name = "COORDINATION_PRJ_ID", length = 32)

	public String getCoordinationPrjId() {
		return this.coordinationPrjId;
	}

	public void setCoordinationPrjId(String coordinationPrjId) {
		this.coordinationPrjId = coordinationPrjId;
	}

	@Column(name = "COORDINATION_PRJ_NO", length = 128)

	public String getCoordinationPrjNo() {
		return this.coordinationPrjNo;
	}

	public void setCoordinationPrjNo(String coordinationPrjNo) {
		this.coordinationPrjNo = coordinationPrjNo;
	}

	@Column(name = "COORDINATION_PRJ_NAME", length = 128)

	public String getCoordinationPrjName() {
		return this.coordinationPrjName;
	}

	public void setCoordinationPrjName(String coordinationPrjName) {
		this.coordinationPrjName = coordinationPrjName;
	}

	@Column(name = "YN_COMPATIBLE", length = 2)

	public String getYnCompatible() {
		return this.ynCompatible;
	}

	public void setYnCompatible(String ynCompatible) {
		this.ynCompatible = ynCompatible;
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

	@Column(name = "PROC_ID", length = 32)

	public String getProcId() {
		return this.procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	@Column(name = "AREA_ID", length = 32)

	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "PROVINCE_ID", length = 32)

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name = "COUNTRY_ID", length = 32)

	public String getCountryId() {
		return this.countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	@Column(name = "OWNER_ID", length = 32)

	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "STATUS", precision = 0)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}