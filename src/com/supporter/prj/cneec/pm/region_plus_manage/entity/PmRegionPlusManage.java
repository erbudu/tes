package com.supporter.prj.cneec.pm.region_plus_manage.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.pm.region_plus_manage.entity.base.BasePmRegionPlusManage;
import com.supporter.util.CodeTable;

/**
 * PmRegionPlusManage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PM_REGION_PLUS_MANAGE", schema = "SUPP_APP")
public class PmRegionPlusManage extends BasePmRegionPlusManage implements
		java.io.Serializable {

	// Constructors
	
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public PmRegionPlusManage() {
	}

	/** minimal constructor */
	public PmRegionPlusManage(String manageId) {
		super(manageId);
	}

	/** full constructor */
	public PmRegionPlusManage(String manageId, String regionPlusName,
			String areaInername, String manageLeaderId,
			String manageLeaderName, String createdById, String createdBy,
			String createdDate, String modifiedById, String modifiedBy,
			String modifiedDate) {
		super(manageId, regionPlusName, areaInername, manageLeaderId,
				manageLeaderName, createdById, createdBy, createdDate,
				modifiedById, modifiedBy, modifiedDate);
	}
	
	//所属市场常量
	public final static String INTERNAL = "INTERNAL";
	public final static String FOREIGN = "FOREIGN";
	public final static String INTERNAL_DESC = "国内市场";
	public final static String FOREIGN_DESC = "国外市场";
	
	/**
	 * 获取所属市场codetable
	 * @return
	 */
	public static CodeTable getAreaInernameCodeTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(INTERNAL, INTERNAL_DESC);
		ct.insertItem(FOREIGN, FOREIGN_DESC);
		return ct;
	}
	
	/**
	 * 获取所属市场map
	 * @return
	 */
	public static Map<String, String> getAreaInernameMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(INTERNAL, INTERNAL_DESC);
		map.put(FOREIGN, FOREIGN_DESC);
		return map;
	}
	
	//获取所属市场描述
	@Transient
	public String getAreaInernameDesc(){
		return getAreaInernameCodeTable().getDisplay(this.getAreaInername());
	}
	
	// 搜索关键字
	private String keyword;
	
	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
