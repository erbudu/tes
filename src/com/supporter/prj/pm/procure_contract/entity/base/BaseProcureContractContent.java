package com.supporter.prj.pm.procure_contract.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: 采购内容
 * @Description: PM_PROCURE_CONTRACT_CONTENT, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:04:17
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseProcureContractContent  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *REC_ID.
	 */
	@Id
	@GeneratedValue(generator = "uuid")
  	@GenericGenerator(name = "uuid",  strategy = "uuid")
	@Column(name = "REC_ID", nullable = false, length = 32)
	private java.lang.String recId;
	/**
	 *CONTRACT_ID.
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	private java.lang.String contractId;
	/**
	 *BUY_ITEM.
	 */
	@Column(name = "BUY_ITEM", nullable = true, length = 128)
	private java.lang.String buyItem;
	/**
	 *BUY_COUNT.
	 */
	@Column(name = "BUY_COUNT", nullable = true, precision = 10, scale = 3)
	private double buyCount;
	/**
	 *UNIT.
	 */
	@Column(name = "UNIT", nullable = true, length = 32)
	private java.lang.String unit;
	/**
	 *SPECIFICATION.
	 */
	@Column(name = "SPECIFICATION", nullable = true, length = 32)
	private java.lang.String specification;
	/**
	 *PRICE.
	 */
	@Column(name = "PRICE", nullable = true, precision = 10, scale = 3)
	private double price;
	/**
	 *TOTAL.
	 */
	@Column(name = "TOTAL", nullable = true, precision = 10, scale = 3)
	private double total;
	@Column(name = "CNY_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double cnyAmount;
	/**
	 *BUDGET_ITEM_NAME.
	 */
	@Column(name = "BUDGET_ITEM_NAME", nullable = true, length = 64)
	private java.lang.String budgetItemName;
	/**
	 *BUDGET_ITEM_ID.
	 */
	@Column(name = "BUDGET_ITEM_ID", nullable = true, length = 32)
	private java.lang.String budgetItemId;
	
	/**
	 *方法: 取得REC_ID.
	 *@return java.lang.String  REC_ID
	 */
	public java.lang.String getRecId() {
		return this.recId;
	}

	/**
	 *方法: 设置REC_ID.
	 *@param recId  REC_ID
	 */
	public void setRecId(java.lang.String recId) {
		this.recId = recId;
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
	 *方法: 取得BUY_ITEM.
	 *@return java.lang.String  BUY_ITEM
	 */
	public java.lang.String getBuyItem() {
		return this.buyItem;
	}

	/**
	 *方法: 设置BUY_ITEM.
	 *@param buyItem  BUY_ITEM
	 */
	public void setBuyItem(java.lang.String buyItem) {
		this.buyItem = buyItem;
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
	 *方法: 取得UNIT.
	 *@return java.lang.String  UNIT
	 */
	public java.lang.String getUnit() {
		return this.unit;
	}

	/**
	 *方法: 设置UNIT.
	 *@param unit  UNIT
	 */
	public void setUnit(java.lang.String unit) {
		this.unit = unit;
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
	
	public double getCnyAmount() {
		return this.cnyAmount;
	}

	public void setCnyAmount(double cnyAmount) {
		this.cnyAmount = cnyAmount;
	}
	/**
	 *方法: 取得BUDGET_ITEM_NAME.
	 *@return java.lang.String  BUDGET_ITEM_NAME
	 */
	public java.lang.String getBudgetItemName() {
		return this.budgetItemName;
	}

	/**
	 *方法: 设置BUDGET_ITEM_NAME.
	 *@param budgetItemName  BUDGET_ITEM_NAME
	 */
	public void setBudgetItemName(java.lang.String budgetItemName) {
		this.budgetItemName = budgetItemName;
	}
	/**
	 *方法: 取得BUDGET_ITEM_ID.
	 *@return java.lang.String  BUDGET_ITEM_ID
	 */
	public java.lang.String getBudgetItemId() {
		return this.budgetItemId;
	}

	/**
	 *方法: 设置BUDGET_ITEM_ID.
	 *@param budgetItemId  BUDGET_ITEM_ID
	 */
	public void setBudgetItemId(java.lang.String budgetItemId) {
		this.budgetItemId = budgetItemId;
	}
	public java.lang.String getSpecification() {
		return specification;
	}

	public void setSpecification(java.lang.String specification) {
		this.specification = specification;
	}
	/**
	 * 无参构造函数.
	 */
	public BaseProcureContractContent() {
	
	}
	
	/**
	 * 构造函数.
	 * @param recId
	 */
	public BaseProcureContractContent(String recId) {
		this.recId = recId;
	} 
}
