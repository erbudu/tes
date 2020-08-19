package com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_TARIFF_VAT_PAYMENT,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-12-20
 * @version V1.0
 */
@MappedSuperclass
public class BaseTariffVatPayment implements Serializable {

	private static final long serialVersionUID = 1L;
	private String paymentId;
	private String paymentNo;
	private String projectId;
	private String projectName;
	private String paymentType;
	private double paymentAmount;
	private String currency;
	private String currencyId;
	private String isAbroad;
	private String collectionUnit;
	private String depositBank;
	private String bankCode;
	private String remitProvince;
	private String remitProvinceId;
	private String remitCity;
	private String remitCityId;
	private String bankAccount;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String remittancePurpose;//汇款用途
	private String capticalPurposeCode;//资金用途
	private int printCount;

	/**
	 * 无参构造函数.
	 */
	public BaseTariffVatPayment() {
	}

	/**
	 * 构造函数.
	 *
	 * @param paymentId
	 */
	public BaseTariffVatPayment(String paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * GET方法: 取得PAYMENT_ID.
	 *
	 * @return: String  PAYMENT_ID
	 */
	@Id
	@Column(name = "PAYMENT_ID", nullable = false, length = 32)
	public String getPaymentId() {
		return this.paymentId;
	}

	/**
	 * SET方法: 设置PAYMENT_ID.
	 *
	 * @param: String  PAYMENT_ID
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * GET方法: 取得PAYMENT_NO.
	 *
	 * @return: String  PAYMENT_NO
	 */
	@Column(name = "PAYMENT_NO", nullable = true, length = 32)
	public String getPaymentNo() {
		return this.paymentNo;
	}

	/**
	 * SET方法: 设置PAYMENT_NO.
	 *
	 * @param: String  PAYMENT_NO
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 *
	 * @return: String  PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 *
	 * @param: String  PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得PROJECT_NAME.
	 *
	 * @return: String  PROJECT_NAME
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * SET方法: 设置PROJECT_NAME.
	 *
	 * @param: String  PROJECT_NAME
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * GET方法: 取得PAYMENT_TYPE.
	 *
	 * @return: String  PAYMENT_TYPE
	 */
	@Column(name = "PAYMENT_TYPE", nullable = true, length = 64)
	public String getPaymentType() {
		return this.paymentType;
	}

	/**
	 * SET方法: 设置PAYMENT_TYPE.
	 *
	 * @param: String  PAYMENT_TYPE
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * GET方法: 取得PAYMENT_AMOUNT.
	 *
	 * @return: double  PAYMENT_AMOUNT
	 */
	@Column(name = "PAYMENT_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getPaymentAmount() {
		return this.paymentAmount;
	}

	/**
	 * SET方法: 设置PAYMENT_AMOUNT.
	 *
	 * @param: double  PAYMENT_AMOUNT
	 */
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
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
	 * GET方法: 取得IS_ABROAD.
	 *
	 * @return: String  IS_ABROAD
	 */
	@Column(name = "IS_ABROAD", nullable = true, length = 1)
	public String getIsAbroad() {
		return this.isAbroad;
	}

	/**
	 * SET方法: 设置IS_ABROAD.
	 *
	 * @param: String  IS_ABROAD
	 */
	public void setIsAbroad(String isAbroad) {
		this.isAbroad = isAbroad;
	}

	/**
	 * GET方法: 取得COLLECTION_UNIT.
	 *
	 * @return: String  COLLECTION_UNIT
	 */
	@Column(name = "COLLECTION_UNIT", nullable = true, length = 128)
	public String getCollectionUnit() {
		return this.collectionUnit;
	}

	/**
	 * SET方法: 设置COLLECTION_UNIT.
	 *
	 * @param: String  COLLECTION_UNIT
	 */
	public void setCollectionUnit(String collectionUnit) {
		this.collectionUnit = collectionUnit;
	}

	/**
	 * GET方法: 取得DEPOSIT_BANK.
	 *
	 * @return: String  DEPOSIT_BANK
	 */
	@Column(name = "DEPOSIT_BANK", nullable = true, length = 128)
	public String getDepositBank() {
		return this.depositBank;
	}

	/**
	 * SET方法: 设置DEPOSIT_BANK.
	 *
	 * @param: String  DEPOSIT_BANK
	 */
	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	/**
	 * GET方法: 取得BANK_CODE.
	 *
	 * @return: String  BANK_CODE
	 */
	@Column(name = "BANK_CODE", nullable = true, length = 24)
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * SET方法: 设置BANK_CODE.
	 *
	 * @param: String  BANK_CODE
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * GET方法: 取得REMIT_PROVINCE.
	 *
	 * @return: String  REMIT_PROVINCE
	 */
	@Column(name = "REMIT_PROVINCE", nullable = true, length = 64)
	public String getRemitProvince() {
		return this.remitProvince;
	}

	/**
	 * SET方法: 设置REMIT_PROVINCE.
	 *
	 * @param: String  REMIT_PROVINCE
	 */
	public void setRemitProvince(String remitProvince) {
		this.remitProvince = remitProvince;
	}

	/**
	 * GET方法: 取得REMIT_PROVINCE_ID.
	 *
	 * @return: String  REMIT_PROVINCE_ID
	 */
	@Column(name = "REMIT_PROVINCE_ID", nullable = true, length = 32)
	public String getRemitProvinceId() {
		return this.remitProvinceId;
	}

	/**
	 * SET方法: 设置REMIT_PROVINCE_ID.
	 *
	 * @param: String  REMIT_PROVINCE_ID
	 */
	public void setRemitProvinceId(String remitProvinceId) {
		this.remitProvinceId = remitProvinceId;
	}

	/**
	 * GET方法: 取得REMIT_CITY.
	 *
	 * @return: String  REMIT_CITY
	 */
	@Column(name = "REMIT_CITY", nullable = true, length = 64)
	public String getRemitCity() {
		return this.remitCity;
	}

	/**
	 * SET方法: 设置REMIT_CITY.
	 *
	 * @param: String  REMIT_CITY
	 */
	public void setRemitCity(String remitCity) {
		this.remitCity = remitCity;
	}

	/**
	 * GET方法: 取得REMIT_CITY_ID.
	 *
	 * @return: String  REMIT_CITY_ID
	 */
	@Column(name = "REMIT_CITY_ID", nullable = true, length = 32)
	public String getRemitCityId() {
		return this.remitCityId;
	}

	/**
	 * SET方法: 设置REMIT_CITY_ID.
	 *
	 * @param: String  REMIT_CITY_ID
	 */
	public void setRemitCityId(String remitCityId) {
		this.remitCityId = remitCityId;
	}

	/**
	 * GET方法: 取得BANK_ACCOUNT.
	 *
	 * @return: String  BANK_ACCOUNT
	 */
	@Column(name = "BANK_ACCOUNT", nullable = true, length = 128)
	public String getBankAccount() {
		return this.bankAccount;
	}

	/**
	 * SET方法: 设置BANK_ACCOUNT.
	 *
	 * @param: String  BANK_ACCOUNT
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * GET方法: 取得SWF_STATUS.
	 *
	 * @return: Integer  SWF_STATUS
	 */
	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	/**
	 * SET方法: 设置SWF_STATUS.
	 *
	 * @param: Integer  SWF_STATUS
	 */
	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	/**
	 * GET方法: 取得DEPT_NAME.
	 *
	 * @return: String  DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * SET方法: 设置DEPT_NAME.
	 *
	 * @param: String  DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * GET方法: 取得DEPT_ID.
	 *
	 * @return: String  DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * SET方法: 设置DEPT_ID.
	 *
	 * @param: String  DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 *
	 * @return: String  CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 *
	 * @param: String  CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 *
	 * @return: String  CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 *
	 * @param: String  CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * GET方法: 取得CREATED_DATE.
	 *
	 * @return: String  CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 *
	 * @param: String  CREATED_DATE
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_BY.
	 *
	 * @return: String  MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * SET方法: 设置MODIFIED_BY.
	 *
	 * @param: String  MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * GET方法: 取得MODIFIED_BY_ID.
	 *
	 * @return: String  MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * SET方法: 设置MODIFIED_BY_ID.
	 *
	 * @param: String  MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 *
	 * @return: String  MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 *
	 * @param: String  MODIFIED_DATE
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * GET方法: 取得REMITTANCE_PURPOSE.
	 *
	 * @return: String  REMITTANCE_PURPOSE
	 */
	@Column(name="REMITTANCE_PURPOSE" ,length=500, nullable = true)
	public String getRemittancePurpose() {
		return this.remittancePurpose;
	}

	/**
	 * SET方法: 设置REMITTANCE_PURPOSE.
	 *
	 * @param: String  REMITTANCE_PURPOSE
	 */
	public void setRemittancePurpose(String remittancePurpose) {
		this.remittancePurpose = remittancePurpose;
	}

	/**
	 * GET方法: 取得CAPTICAL_PURPOSE_CODE.
	 *
	 * @return: String  CAPTICAL_PURPOSE_CODE
	 */
	@Column(name="CAPTICAL_PURPOSE_CODE" ,length=10, nullable = true)
	public String getCapticalPurposeCode() {
		return this.capticalPurposeCode;
	}

	/**
	 * SET方法: 设置CAPTICAL_PURPOSE_CODE.
	 *
	 * @param: String  CAPTICAL_PURPOSE_CODE
	 */
	public void setCapticalPurposeCode(String capticalPurposeCode) {
		this.capticalPurposeCode = capticalPurposeCode;
	}
	
	/**
	 * GET方法: 取得PRINT_COUNT.
	 *
	 * @return: int  PRINT_COUNT
	 */
	@Column(name="PRINT_COUNT" ,length=10, nullable = true)
	public int getPrintCount() {
		return this.printCount;
	}
	
	/**
	 * SET方法: 设置PRINT_COUNT.
	 *
	 * @param: int  PRINT_COUNT
	 */
	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

}
