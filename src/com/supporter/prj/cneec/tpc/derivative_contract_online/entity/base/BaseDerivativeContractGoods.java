package com.supporter.prj.cneec.tpc.derivative_contract_online.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: TPC_CONTRACT_GOODS,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BaseDerivativeContractGoods implements Serializable {

	private static final long serialVersionUID = 1L;
	private String goodsId;
	private String contractId;
	private String itemName;
	private String hsCode;
	private String specification;
	private String country;
	private String countryId;
	private String manufacturer;
	private double num;
	private String unit;
	private String currency;
	private String currencyId;
	private double amount;
	private double rmbAmount;
	private double taxRebateRate;

	/**
	 * 无参构造函数.
	 */
	public BaseDerivativeContractGoods() {
	}

	/**
	 * 构造函数.
	 *
	 * @param goodsId
	 */
	public BaseDerivativeContractGoods(String goodsId) {
		this.goodsId = goodsId;
	}

	/**
	 * GET方法: 取得GOODS_ID.
	 *
	 * @return: String  GOODS_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "GOODS_ID", nullable = false, length = 32)
	public String getGoodsId() {
		return this.goodsId;
	}

	/**
	 * SET方法: 设置GOODS_ID.
	 *
	 * @param: String  GOODS_ID
	 */
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	/**
	 * GET方法: 取得CONTRACT_ID.
	 *
	 * @return: String  CONTRACT_ID
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * SET方法: 设置CONTRACT_ID.
	 *
	 * @param: String  CONTRACT_ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * GET方法: 取得ITEM_NAME.
	 *
	 * @return: String  ITEM_NAME
	 */
	@Column(name = "ITEM_NAME", nullable = true, length = 128)
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * SET方法: 设置ITEM_NAME.
	 *
	 * @param: String  ITEM_NAME
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * GET方法: 取得HS_CODE.
	 *
	 * @return: String  HS_CODE
	 */
	@Column(name = "HS_CODE", nullable = true, length = 64)
	public String getHsCode() {
		return this.hsCode;
	}

	/**
	 * SET方法: 设置HS_CODE.
	 *
	 * @param: String  HS_CODE
	 */
	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	/**
	 * GET方法: 取得SPECIFICATION.
	 *
	 * @return: String  SPECIFICATION
	 */
	@Column(name = "SPECIFICATION", nullable = true, length = 64)
	public String getSpecification() {
		return this.specification;
	}

	/**
	 * SET方法: 设置SPECIFICATION.
	 *
	 * @param: String  SPECIFICATION
	 */
	public void setSpecification(String specification) {
		this.specification = specification;
	}

	/**
	 * GET方法: 取得COUNTRY.
	 *
	 * @return: String  COUNTRY
	 */
	@Column(name = "COUNTRY", nullable = true, length = 64)
	public String getCountry() {
		return this.country;
	}

	/**
	 * SET方法: 设置COUNTRY.
	 *
	 * @param: String  COUNTRY
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * GET方法: 取得COUNTRY_ID.
	 *
	 * @return: String  COUNTRY_ID
	 */
	@Column(name = "COUNTRY_ID", nullable = true, length = 32)
	public String getCountryId() {
		return this.countryId;
	}

	/**
	 * SET方法: 设置COUNTRY_ID.
	 *
	 * @param: String  COUNTRY_ID
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * GET方法: 取得MANUFACTURER.
	 *
	 * @return: String  MANUFACTURER
	 */
	@Column(name = "MANUFACTURER", nullable = true, length = 512)
	public String getManufacturer() {
		return this.manufacturer;
	}

	/**
	 * SET方法: 设置MANUFACTURER.
	 *
	 * @param: String  MANUFACTURER
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * GET方法: 取得NUM.
	 *
	 * @return: double  NUM
	 */
	@Column(name = "NUM", nullable = true, precision = 10, scale = 3)
	public double getNum() {
		return this.num;
	}

	/**
	 * SET方法: 设置NUM.
	 *
	 * @param: double  NUM
	 */
	public void setNum(double num) {
		this.num = num;
	}

	/**
	 * GET方法: 取得UNIT.
	 *
	 * @return: String  UNIT
	 */
	@Column(name = "UNIT", nullable = true, length = 32)
	public String getUnit() {
		return this.unit;
	}

	/**
	 * SET方法: 设置UNIT.
	 *
	 * @param: String  UNIT
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * GET方法: 取得CURRENCY.
	 *
	 * @return: String  CURRENCY
	 */
	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * SET方法: 设置CURRENCY.
	 *
	 * @param: String  CURRENCY
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * GET方法: 取得CURRENCY_ID.
	 *
	 * @return: String  CURRENCY_ID
	 */
	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	/**
	 * SET方法: 设置CURRENCY_ID.
	 *
	 * @param: String  CURRENCY_ID
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * GET方法: 取得AMOUNT.
	 *
	 * @return: double  AMOUNT
	 */
	@Column(name = "AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getAmount() {
		return this.amount;
	}

	/**
	 * SET方法: 设置AMOUNT.
	 *
	 * @param: double  AMOUNT
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * GET方法: 取得RMB_AMOUNT.
	 *
	 * @return: double  RMB_AMOUNT
	 */
	@Column(name = "RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRmbAmount() {
		return this.rmbAmount;
	}

	/**
	 * SET方法: 设置RMB_AMOUNT.
	 *
	 * @param: double  RMB_AMOUNT
	 */
	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	/**
	 * GET方法: 取得TAX_REBATE_RATE.
	 *
	 * @return: double  TAX_REBATE_RATE
	 */
	@Column(name = "TAX_REBATE_RATE", nullable = true, precision = 10, scale = 3)
	public double getTaxRebateRate() {
		return this.taxRebateRate;
	}

	/**
	 * SET方法: 设置TAX_REBATE_RATE.
	 *
	 * @param: double  TAX_REBATE_RATE
	 */
	public void setTaxRebateRate(double taxRebateRate) {
		this.taxRebateRate = taxRebateRate;
	}

}
