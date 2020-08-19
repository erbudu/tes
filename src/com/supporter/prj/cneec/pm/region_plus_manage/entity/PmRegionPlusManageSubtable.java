package com.supporter.prj.cneec.pm.region_plus_manage.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.pm.region_plus_manage.entity.base.BasePmRegionPlusManageSubtable;

/**
 * PmRegionPlusManageSubtable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PM_REGION_PLUS_MANAGE_SUBTABLE", schema = "SUPP_APP")
public class PmRegionPlusManageSubtable extends
BasePmRegionPlusManageSubtable implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public PmRegionPlusManageSubtable() {
	}

	/** minimal constructor */
	public PmRegionPlusManageSubtable(String subtableId) {
		super(subtableId);
	}

	/** full constructor */
	public PmRegionPlusManageSubtable(String subtableId, String manageId,
			String areaId, String areaName, String areaItemId,
			String areaItemName) {
		super(subtableId, manageId, areaId, areaName, areaItemId, areaItemName);
	}

}
