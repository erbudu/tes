package com.supporter.prj.cneec.pm.region_plus_manage.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractPmRegionPlusManage entity provides the base persistence definition of
 * the PmRegionPlusManage entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BasePmRegionPlusManage implements
		java.io.Serializable {

	// Fields

	private String manageId;
	private String regionPlusName;
	private String areaInername;
	private String manageLeaderId;
	private String manageLeaderName;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;

	// Constructors

	/** default constructor */
	public BasePmRegionPlusManage() {
	}

	/** minimal constructor */
	public BasePmRegionPlusManage(String manageId) {
		this.manageId = manageId;
	}

	/** full constructor */
	public BasePmRegionPlusManage(String manageId, String regionPlusName,
			String areaInername, String manageLeaderId,
			String manageLeaderName, String createdById, String createdBy,
			String createdDate, String modifiedById, String modifiedBy,
			String modifiedDate) {
		this.manageId = manageId;
		this.regionPlusName = regionPlusName;
		this.areaInername = areaInername;
		this.manageLeaderId = manageLeaderId;
		this.manageLeaderName = manageLeaderName;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@Id
	@Column(name = "MANAGE_ID", unique = true, nullable = false, length = 32)
	public String getManageId() {
		return this.manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	@Column(name = "REGION_PLUS_NAME", length = 64)
	public String getRegionPlusName() {
		return this.regionPlusName;
	}

	public void setRegionPlusName(String regionPlusName) {
		this.regionPlusName = regionPlusName;
	}

	@Column(name = "AREA_INERNAME", length = 64)
	public String getAreaInername() {
		return this.areaInername;
	}

	public void setAreaInername(String areaInername) {
		this.areaInername = areaInername;
	}

	@Column(name = "MANAGE_LEADER_ID", length = 32)
	public String getManageLeaderId() {
		return this.manageLeaderId;
	}

	public void setManageLeaderId(String manageLeaderId) {
		this.manageLeaderId = manageLeaderId;
	}

	@Column(name = "MANAGE_LEADER_NAME", length = 64)
	public String getManageLeaderName() {
		return this.manageLeaderName;
	}

	public void setManageLeaderName(String manageLeaderName) {
		this.manageLeaderName = manageLeaderName;
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

	@Column(name = "CREATED_DATE", length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
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

	@Column(name = "MODIFIED_DATE", length = 32)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}