package com.supporter.prj.cneec.tpc.custom.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.custom.entity.base.BaseCustom;
import com.supporter.util.CodeTable;


/**
 * 注册客户
 */
@Entity
@Table(name = "TPC_CUSTOM", schema = "")
public class Custom extends BaseCustom {

	public static final String MODULE_ID = "TPCCUSTOM";
	// Constructors

    /** default constructor */
    public Custom() {
    }

	/** minimal constructor */
    public Custom(String customId) {
        super(customId);        
    }
    
    /** full constructor */
    public Custom(String customId, String customerName, String customerNo, String address, String postCode, String website, String isForeign, String areaName, String areaCode, String areaItemName, String areaItemCode, Double regAmount, String currency, String currencyTypeCode, String isListed, String createYear, String employeeNumber, String remarks, Integer status, String createdBy, String createdById, String createdDate, String modifiedBy, String modifiedById, String modifiedDate, String customControlStatus, String customControlStatusCode) {
        super(customId, customerName, customerNo, address, postCode, website, isForeign, areaName, areaCode, areaItemName, areaItemCode, regAmount, currency, currencyTypeCode, isListed, createYear, employeeNumber, remarks, status, createdBy, createdById, createdDate, modifiedBy, modifiedById, modifiedDate, customControlStatus, customControlStatusCode);        
    }
    
	private static final long serialVersionUID = 1L;
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
	
	public static final int DRAFT = 0; // 未提交
	public static final int CLOSED = 20;// 已提交

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static CodeTable getStatusCodeTable() {
		CodeTable ct = new CodeTable();
		ct.insertItem(DRAFT, "未提交");
		ct.insertItem(CLOSED, "已提交");
		return ct;
	}

	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "未提交");
		map.put(CLOSED, "已提交");
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
		ct.insertItem(IS_FOREIGN, "是");
		ct.insertItem(IS_NOT_FOREIGN, "否");
		return ct;
	}
	
	public static Map<Integer, String> getForeignStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(IS_FOREIGN, "是");
		map.put(IS_NOT_FOREIGN, "否");
		return map;
	}
	
	/**
	 * 获取是否上市
	 * @return
	 */
	public static final int IS_LISTED = 1;
	public static final int IS_NOT_LISTED = 0;
	
	public static CodeTable getListedTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(IS_LISTED, "是");
		ct.insertItem(IS_NOT_LISTED, "否");
		return ct;
	}
	
	public static Map<Integer, String> getListedStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(IS_LISTED, "是");
		map.put(IS_NOT_LISTED, "否");
		return map;
	}
	
	// 获取提交状态
	@Transient
	public String getStatusDesc() {
		if (this.getStatus() != null) {
			return getStatusCodeTable().getDisplay(this.getStatus());
		}
		return "";
	}
	
	// 获取国外状态
	@Transient
	public String getForeignDesc() {
		if (this.getIsForeign()!=null) {
			return getForeignTable().getDisplay(this.getIsForeign());
		}
		return "";
	}
	
	// 获取上市状态
	@Transient
	public String getListedDesc() {
		if (this.getIsListed()!=null) {
			return getListedTable().getDisplay(this.getIsListed());
		}
		return "";
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
	
	private List<CustomLinkman> linkmanList;
	
	@Transient
	public List<CustomLinkman> getLinkmanList() {
		return linkmanList;
	}

	public void setLinkmanList(List<CustomLinkman> linkmanList) {
		this.linkmanList = linkmanList;
	}
	
	@Transient
	public String getCustomControlStatusDesc(){
		return getStatusCodeTable().getDisplay(this.getCustomControlStatusCode());
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
