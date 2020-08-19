package com.supporter.prj.pm.procure_contract.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: 涉及工程部位
 * @Description: PM_PROCURE_CONTRACT_SITE, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:04:17
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseProcureContractSite  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *SITE_ID.
	 */
	@Id
	@GeneratedValue(generator = "assigned")
  	@GenericGenerator(name = "assigned",  strategy = "assigned")
	@Column(name = "SITE_ID", nullable = false, length = 32)
	private java.lang.String siteId;
	/**
	 *PARENT_SITE_ID.
	 */
	@Column(name = "PARENT_SITE_ID", nullable = true, length = 32)
	private java.lang.String parentSiteId;
	/**
	 *CONTRACT_ID.
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	private java.lang.String contractId;
	/**
	 *WBS_ID.
	 */
	@Column(name = "WBS_ID", nullable = true, length = 32)
	private java.lang.String wbsId;
	/**
	 *WBS_NAME.
	 */
	@Column(name = "WBS_NAME", nullable = true, length = 1024)
	private java.lang.String wbsName;
	/**
	 *BUY_COUNT.
	 */
	@Column(name = "BUY_COUNT", nullable = true, precision = 10, scale = 3)
	private double buyCount;
	/**
	 *COUNT_UNIT.
	 */
	@Column(name = "COUNT_UNIT", nullable = true, length = 32)
	private java.lang.String countUnit;
	/**
	 *PRICE.
	 */
	@Column(name = "PRICE", nullable = true, precision = 10, scale = 3)
	private double price;
	/**
	 *PRICE_UNIT.
	 */
	@Column(name = "PRICE_UNIT", nullable = true, length = 32)
	private java.lang.String priceUnit;
	/**
	 *TOTAL.
	 */
	@Column(name = "TOTAL", nullable = true, precision = 10, scale = 3)
	private double total;
	/**
	 * PRICE.
	 */
	@Column(name = "YFK_SCALE", nullable = true, precision = 10, scale = 3)
	private double yfkScale;
	/**
	 * PRICE.
	 */
	@Column(name = "ZBJ_SCALE", nullable = true, precision = 10, scale = 3)
	private double zbjScale;
	
	/**
	 *方法: 取得SITE_ID.
	 *@return java.lang.String  SITE_ID
	 */
	public java.lang.String getSiteId() {
		return this.siteId;
	}

	/**
	 *方法: 设置SITE_ID.
	 *@param siteId  SITE_ID
	 */
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	/**
	 *方法: 取得PARENT_SITE_ID.
	 *@return java.lang.String  PARENT_SITE_ID
	 */
	public java.lang.String getParentSiteId() {
		return this.parentSiteId;
	}

	/**
	 *方法: 设置PARENT_SITE_ID.
	 *@param parentSiteId  PARENT_SITE_ID
	 */
	public void setParentSiteId(java.lang.String parentSiteId) {
		this.parentSiteId = parentSiteId;
	}
	/**
	 *方法: 取得CONTRACT_ID.
	 *@return java.lang.String  CONTRACT_ID
	 */
	public java.lang.String getContractId() {
		return this.contractId;
	}

	/**
	 *方法: 设置CONTRACT_ID.
	 *@param contractId  CONTRACT_ID
	 */
	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	/**
	 *方法: 取得WBS_ID.
	 *@return java.lang.String  WBS_ID
	 */
	public java.lang.String getWbsId() {
		return this.wbsId;
	}

	/**
	 *方法: 设置WBS_ID.
	 *@param wbsId  WBS_ID
	 */
	public void setWbsId(java.lang.String wbsId) {
		this.wbsId = wbsId;
	}
	/**
	 *方法: 取得WBS_NAME.
	 *@return java.lang.String  WBS_NAME
	 */
	public java.lang.String getWbsName() {
		return this.wbsName;
	}

	/**
	 *方法: 设置WBS_NAME.
	 *@param wbsName  WBS_NAME
	 */
	public void setWbsName(java.lang.String wbsName) {
		this.wbsName = wbsName;
	}
	/**
	 *方法: 取得BUY_COUNT.
	 *@return double  BUY_COUNT
	 */
	public double getBuyCount() {
		return this.buyCount;
	}

	/**
	 *方法: 设置BUY_COUNT.
	 *@param buyCount  BUY_COUNT
	 */
	public void setBuyCount(double buyCount) {
		this.buyCount = buyCount;
	}
	/**
	 *方法: 取得COUNT_UNIT.
	 *@return java.lang.String  COUNT_UNIT
	 */
	public java.lang.String getCountUnit() {
		return this.countUnit;
	}

	/**
	 *方法: 设置COUNT_UNIT.
	 *@param countUnit  COUNT_UNIT
	 */
	public void setCountUnit(java.lang.String countUnit) {
		this.countUnit = countUnit;
	}
	/**
	 *方法: 取得PRICE.
	 *@return double  PRICE
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 *方法: 设置PRICE.
	 *@param price  PRICE
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 *方法: 取得PRICE_UNIT.
	 *@return java.lang.String  PRICE_UNIT
	 */
	public java.lang.String getPriceUnit() {
		return this.priceUnit;
	}

	/**
	 *方法: 设置PRICE_UNIT.
	 *@param priceUnit  PRICE_UNIT
	 */
	public void setPriceUnit(java.lang.String priceUnit) {
		this.priceUnit = priceUnit;
	}
	/**
	 *方法: 取得TOTAL.
	 *@return double  TOTAL
	 */
	public double getTotal() {
		return this.total;
	}

	/**
	 *方法: 设置TOTAL.
	 *@param total  TOTAL
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	
	public double getYfkScale() {
		return yfkScale;
	}

	public void setYfkScale(double yfkScale) {
		this.yfkScale = yfkScale;
	}

	public double getZbjScale() {
		return zbjScale;
	}

	public void setZbjScale(double zbjScale) {
		this.zbjScale = zbjScale;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseProcureContractSite() {
	
	}
	
	/**
	 * 构造函数.
	 * @param siteId
	 */
	public BaseProcureContractSite(String siteId) {
		this.siteId = siteId;
	} 
}
