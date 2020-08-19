package com.supporter.prj.cneec.tpc.custom.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_CUSTOM_PAYACCOUNT,字段与数据库字段一一对应.
 * @author: liuermeng
 * @date: 2018-09-05
 * @version: V1.0
 */
@MappedSuperclass
public class BaseCustomPayaccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String payaccountId;
	private String customId;
	private String companyname;
	private String bank;
	private String bankCode;
	private String remitProvince;
	private String remitProvinceId;
	private String remitCity;
	private String remitCityId;
	private String bankAccount;
	private boolean isOtherPay;

	/**
	 * 无参构造函数.
	 */
	public BaseCustomPayaccount() {
	}

	/**
	 * 构造函数.
	 *
	 * @param payaccountId
	 */
	public BaseCustomPayaccount(String payaccountId) {
		this.payaccountId = payaccountId;
	}

	@Id
	@Column(name = "PAYACCOUNT_ID", nullable = false, length = 32)
	public String getPayaccountId() {
		return this.payaccountId;
	}

	public void setPayaccountId(String payaccountId) {
		this.payaccountId = payaccountId;
	}

	@Column(name = "CUSTOM_ID", nullable = true, length = 32)
	public String getCustomId() {
		return this.customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	@Column(name = "COMPANYNAME", nullable = true, length = 128)
	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	@Column(name = "BANK", nullable = true, length = 32)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "BANK_CODE", nullable = true, length = 32)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "REMIT_PROVINCE", nullable = true, length = 64)
	public String getRemitProvince() {
		return this.remitProvince;
	}

	public void setRemitProvince(String remitProvince) {
		this.remitProvince = remitProvince;
	}

	@Column(name = "REMIT_PROVINCE_ID", nullable = true, length = 32)
	public String getRemitProvinceId() {
		return this.remitProvinceId;
	}

	public void setRemitProvinceId(String remitProvinceId) {
		this.remitProvinceId = remitProvinceId;
	}

	@Column(name = "REMIT_CITY", nullable = true, length = 64)
	public String getRemitCity() {
		return this.remitCity;
	}

	public void setRemitCity(String remitCity) {
		this.remitCity = remitCity;
	}

	@Column(name = "REMIT_CITY_ID", nullable = true, length = 32)
	public String getRemitCityId() {
		return this.remitCityId;
	}

	public void setRemitCityId(String remitCityId) {
		this.remitCityId = remitCityId;
	}

	@Column(name = "BANK_ACCOUNT", nullable = true, length = 128)
	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "IS_OTHER_PAY", nullable = true, length = 32)
	@org.hibernate.annotations.Type(type="true_false")
	public boolean getIsOtherPay() {
		return this.isOtherPay;
	}

	public void setIsOtherPay(boolean isOtherPay) {
		this.isOtherPay = isOtherPay;
	}

}
