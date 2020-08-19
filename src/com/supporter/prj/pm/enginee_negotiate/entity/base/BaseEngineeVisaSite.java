package com.supporter.prj.pm.enginee_negotiate.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**   
 * @Title: 签证-工程部位
 * @Description: PM_ENGINEE_VISA_SITE, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseEngineeVisaSite  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *SITE_ID.
	 */
	@Id
	@Column(name = "SITE_ID", unique = true, nullable = false, length = 32)
	private java.lang.String siteId;
	/**
	 *PARENT_SITE_ID.
	 */
	@Column(name = "PARENT_SITE_ID", nullable = true, length = 32)
	private java.lang.String parentSiteId;
	/**
	 *VISA_ID.
	 */
	@Column(name = "VISA_ID", nullable = true, length = 32)
	private java.lang.String visaId;
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
	 *BUDGET_AMOUNT.
	 */
	@Column(name = "BUDGET_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double budgetAmount;
	/**
	 *DRAWING_NO.
	 */
	@Column(name = "DRAWING_NO", nullable = true, length = 32)
	private java.lang.String drawingNo;
	/**
	 *INPUT_WORK_TIME.
	 */
	@Column(name = "INPUT_WORK_TIME", nullable = true, length = 64)
	private double inputWorkTime;
	/**
	 *INPUT_DEVICE.
	 */
	@Column(name = "INPUT_DEVICE", nullable = true, length = 64)
	private java.lang.String inputDevice;
	/**
	 *INPUT_MATERIAL.
	 */
	@Column(name = "INPUT_MATERIAL", nullable = true, length = 64)
	private java.lang.String inputMaterial;
	
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	public java.lang.String getParentSiteId() {
		return this.parentSiteId;
	}
	public void setParentSiteId(java.lang.String parentSiteId) {
		this.parentSiteId = parentSiteId;
	}
	
	public java.lang.String getVisaId() {
		return this.visaId;
	}
	public void setVisaId(java.lang.String visaId) {
		this.visaId = visaId;
	}
	
	public java.lang.String getWbsId() {
		return this.wbsId;
	}
	public void setWbsId(java.lang.String wbsId) {
		this.wbsId = wbsId;
	}
	
	public java.lang.String getWbsName() {
		return this.wbsName;
	}
	public void setWbsName(java.lang.String wbsName) {
		this.wbsName = wbsName;
	}
	
	public double getBuyCount() {
		return this.buyCount;
	}
	public void setBuyCount(double buyCount) {
		this.buyCount = buyCount;
	}
	
	public java.lang.String getCountUnit() {
		return this.countUnit;
	}
	public void setCountUnit(java.lang.String countUnit) {
		this.countUnit = countUnit;
	}
	
	public double getPrice() {
		return this.price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public java.lang.String getPriceUnit() {
		return this.priceUnit;
	}
	public void setPriceUnit(java.lang.String priceUnit) {
		this.priceUnit = priceUnit;
	}
	
	public double getTotal() {
		return this.total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public double getBudgetAmount() {
		return this.budgetAmount;
	}
	public void setBudgetAmount(double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	
	public java.lang.String getDrawingNo() {
		return this.drawingNo;
	}
	public void setDrawingNo(java.lang.String drawingNo) {
		this.drawingNo = drawingNo;
	}
	
	public double getInputWorkTime() {
		return this.inputWorkTime;
	}
	public void setInputWorkTime(double inputWorkTime) {
		this.inputWorkTime = inputWorkTime;
	}
	
	public java.lang.String getInputDevice() {
		return this.inputDevice;
	}
	public void setInputDevice(java.lang.String inputDevice) {
		this.inputDevice = inputDevice;
	}
	
	public java.lang.String getInputMaterial() {
		return this.inputMaterial;
	}
	public void setInputMaterial(java.lang.String inputMaterial) {
		this.inputMaterial = inputMaterial;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseEngineeVisaSite() {
	
	}
	
	/**
	 * 构造函数.
	 * @param siteId
	 */
	public BaseEngineeVisaSite(String siteId) {
		this.siteId = siteId;
	} 
}
