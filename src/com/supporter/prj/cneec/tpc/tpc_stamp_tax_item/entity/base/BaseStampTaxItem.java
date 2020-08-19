package com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 贸易印花税税目表,字段与数据库字段一一对应.
 * @author: LEGO
 * @date: 2020-02-03
 * @version: V1.0
 */
@MappedSuperclass
public class BaseStampTaxItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String itemId;
	private String taxItemName;
	private double taxRate;
	private int orderNumber;

	/**
	 * 无参构造函数.
	 */
	public BaseStampTaxItem() {
	}

	/**
	 * 构造函数.
	 *
	 * @param itemId
	 */
	public BaseStampTaxItem(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * 方法: 取得主键ID.
	 * @return: java.lang.String 主键ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "ITEM_ID", nullable = false, length = 32)
	public String getItemId() {
		return this.itemId;
	}

	/**
	 * 方法: 设置主键ID.
	 * @param: java.lang.String 主键ID
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * 方法: 取得印花税税目.
	 * @return: java.lang.String 印花税税目
	 */
	@Column(name = "TAX_ITEM_NAME", nullable = true, length = 128)
	public String getTaxItemName() {
		return this.taxItemName;
	}

	/**
	 * 方法: 设置印花税税目.
	 * @param: java.lang.String 印花税税目
	 */
	public void setTaxItemName(String taxItemName) {
		this.taxItemName = taxItemName;
	}

	/**
	 * 方法: 取得印花税税率.
	 * @return: java.math.BigDecimal 印花税税率
	 */
	@Column(name = "TAX_RATE", nullable = true, precision = 10, scale = 3)
	public double getTaxRate() {
		return this.taxRate;
	}

	/**
	 * 方法: 设置印花税税率.
	 * @param: java.math.BigDecimal 印花税税率
	 */
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * 方法: 取得序号.
	 * @return: int 序号
	 */
	@Column(name = "ORDER_NUMBER", nullable = true, precision = 10)
	public int getOrderNumber() {
		return this.orderNumber;
	}

	/**
	 * 方法: 设置序号.
	 * @param: int 序号
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

}
