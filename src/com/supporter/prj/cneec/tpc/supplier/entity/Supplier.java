package com.supporter.prj.cneec.tpc.supplier.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.supplier.entity.base.BaseSupplier;
import com.supporter.util.CodeTable;

/**
 * 注册供应商
 */
@Entity
@Table(name = "TPC_SUPPLIER", schema = "SUPP_APP")
public class Supplier extends BaseSupplier implements java.io.Serializable {

	public static final String MODULE_ID = "TPCSUPPLIER";
	// Constructors

	/** default constructor */
	public Supplier() {
	}

	/** minimal constructor */
	public Supplier(String supplierId) {
		super(supplierId);
	}

	/** full constructor */
	public Supplier(String supplierId, String supplierName, String address,
			String isForeign, String areaName, String areaCode,
			String areaItemName, String areaItemCode, String postCode,
			String website, String fax, String linkmanName, String linkmanTel,
			String remarks, String createdBy, String createdById,
			String createdDate, String modifiedBy, String modifiedById,
			String modifiedDate, String supplierControlStatus,
			String supplierControlStatusCode,String filesName) {
		super(supplierId, supplierName, address, isForeign, areaName, areaCode,
				areaItemName, areaItemCode, postCode, website, fax,
				linkmanName, linkmanTel, remarks, createdBy, createdById,
				createdDate, modifiedBy, modifiedById, modifiedDate,
				supplierControlStatus, supplierControlStatusCode, filesName);
	}
	
	public static final String EFFECTIV = "EFFECTIV";
	public static final String FAILURE = "FAILURE";
	public static final String EFFECTIV_DESC="有效";
	public static final String FAILURE_DESC = "失效";
	
	public static CodeTable getControlStatusCodeTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(EFFECTIV, EFFECTIV_DESC);
		ct.insertItem(FAILURE, FAILURE_DESC);
		return ct;
	}
	
	public static Map<String, String> getControlStatusCodeMap(){
		Map<String, String > map = new LinkedHashMap<String, String>();
		map.put(EFFECTIV, EFFECTIV_DESC);
		map.put(FAILURE, FAILURE_DESC);
		return map;
	}
	
	/**
	 * 获取是否国外
	 * @return
	 */
	public static final int IS_FOREIGN = 1;
	public static final int IS_NOT_FOREIGN = 0;
	
	public static CodeTable getForeignTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(IS_FOREIGN, "国外");
		ct.insertItem(IS_NOT_FOREIGN, "国内");
		return ct;
	}
	
	public static Map<Integer, String> getForeignStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(IS_FOREIGN, "国外");
		map.put(IS_NOT_FOREIGN, "国内");
		return map;
	}
	
	@Transient
	public String getIsforeignDesc(){
		return getForeignTable().getDisplay(this.getIsForeign());
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
	
	private boolean isNew;
	
	@Transient
	public boolean getIsNew(){
		return this.isNew;
	}
	
	public void setIsNew(boolean isNew){
		this.isNew = isNew;
	}
	
	private String delIds;

	@Transient
	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
}
