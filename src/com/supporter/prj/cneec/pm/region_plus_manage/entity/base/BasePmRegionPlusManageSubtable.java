package com.supporter.prj.cneec.pm.region_plus_manage.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractPmRegionPlusManageSubtable entity provides the base persistence
 * definition of the PmRegionPlusManageSubtable entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class BasePmRegionPlusManageSubtable implements
		java.io.Serializable {

	// Fields

	private String subtableId;
	private String manageId;
	private String areaId;
	private String areaName;
	private String areaItemId;
	private String areaItemName;

	// Constructors

	/** default constructor */
	public BasePmRegionPlusManageSubtable() {
	}

	/** minimal constructor */
	public BasePmRegionPlusManageSubtable(String subtableId) {
		this.subtableId = subtableId;
	}

	/** full constructor */
	public BasePmRegionPlusManageSubtable(String subtableId,
			String manageId, String areaId, String areaName, String areaItemId,
			String areaItemName) {
		this.subtableId = subtableId;
		this.manageId = manageId;
		this.areaId = areaId;
		this.areaName = areaName;
		this.areaItemId = areaItemId;
		this.areaItemName = areaItemName;
	}

	// Property accessors
	@Id
	@Column(name = "SUBTABLE_ID", unique = true, nullable = false, length = 32)
	public String getSubtableId() {
		return this.subtableId;
	}

	public void setSubtableId(String subtableId) {
		this.subtableId = subtableId;
	}

	@Column(name = "MANAGE_ID", length = 32)
	public String getManageId() {
		return this.manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	@Column(name = "AREA_ID", length = 32)
	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "AREA_NAME", length = 64)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "AREA_ITEM_ID", length = 32)
	public String getAreaItemId() {
		return this.areaItemId;
	}

	public void setAreaItemId(String areaItemId) {
		this.areaItemId = areaItemId;
	}

	@Column(name = "AREA_ITEM_NAME", length = 64)
	public String getAreaItemName() {
		return this.areaItemName;
	}

	public void setAreaItemName(String areaItemName) {
		this.areaItemName = areaItemName;
	}

}