package com.supporter.prj.cneec.tpc.prj_contract_modify.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_PRJ_CONTRACT_GOODS,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-03-15
 * @version V1.0
 */
@MappedSuperclass
public class BasePrjContractGoodsBM implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bmId;
	private String goodsId;
	private String changeId;
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
	public BasePrjContractGoodsBM() {
	}

	/**
	 * 构造函数.
	 *
	 * @param bmId
	 */
	public BasePrjContractGoodsBM(String bmId) {
		this.bmId = bmId;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "BM_ID", nullable = true, length = 32)
	public String getBmId() {
		return bmId;
	}

	public void setBmId(String bmId) {
		this.bmId = bmId;
	}
	
	@Column(name = "GOODS_ID", nullable = false, length = 32)
	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "CHANGE_ID", nullable = true, length = 32)
	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	@Column(name = "ITEM_NAME", nullable = true, length = 128)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "HS_CODE", nullable = true, length = 64)
	public String getHsCode() {
		return this.hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	@Column(name = "SPECIFICATION", nullable = true, length = 64)
	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "COUNTRY", nullable = true, length = 64)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "COUNTRY_ID", nullable = true, length = 32)
	public String getCountryId() {
		return this.countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	@Column(name = "MANUFACTURER", nullable = true, length = 512)
	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "NUM", nullable = true, precision = 10, scale = 3)
	public double getNum() {
		return this.num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	@Column(name = "UNIT", nullable = true, length = 32)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRmbAmount() {
		return this.rmbAmount;
	}

	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	@Column(name = "TAX_REBATE_RATE", nullable = true, precision = 10, scale = 3)
	public double getTaxRebateRate() {
		return this.taxRebateRate;
	}

	public void setTaxRebateRate(double taxRebateRate) {
		this.taxRebateRate = taxRebateRate;
	}

}
