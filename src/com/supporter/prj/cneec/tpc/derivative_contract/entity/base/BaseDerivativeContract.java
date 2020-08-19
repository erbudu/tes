package com.supporter.prj.cneec.tpc.derivative_contract.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * @Title: Entity
 * @Description: TPC_DERIVATIVE_CONTRACT,字段与数据库字段一一对应.
 * @author: Yanweichao
 * @date: 2018-05-21
 * @version: V1.0
 */
@MappedSuperclass
public class BaseDerivativeContract implements Serializable {

	private static final long serialVersionUID = 1L;
	private String derivativeId;
	private String derivativeNo;
	private String projectId;
	private String projectName;
	private String saleContractId;
	private String saleContractNo;
	private String saleContractName;
	private String paymentType;
	private double paymentAmount;
	private String currency;
	private String currencyId;
	private double executeRate;// 执行汇率
	private boolean isAbroad;
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

	/**
	 * 无参构造函数.
	 */
	public BaseDerivativeContract() {
	}

	/**
	 * 构造函数.
	 *
	 * @param derivativeId
	 */
	public BaseDerivativeContract(String derivativeId) {
		this.derivativeId = derivativeId;
	}

	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "DERIVATIVE_ID", nullable = false, length = 32)
	public String getDerivativeId() {
		return this.derivativeId;
	}

	public void setDerivativeId(String derivativeId) {
		this.derivativeId = derivativeId;
	}

	@Column(name = "DERIVATIVE_NO", nullable = true, length = 32)
	public String getDerivativeNo() {
		return this.derivativeNo;
	}

	public void setDerivativeNo(String derivativeNo) {
		this.derivativeNo = derivativeNo;
	}

	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "CONTRACT_ID", nullable = true, length = 512)
	public String getSaleContractId() {
		return this.saleContractId;
	}

	public void setSaleContractId(String saleContractId) {
		this.saleContractId = saleContractId;
	}

	@Column(name = "CONTRACT_NO", nullable = true, length = 512)
	public String getSaleContractNo() {
		return this.saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	@Column(name = "CONTRACT_NAME", nullable = true, length = 1024)
	public String getSaleContractName() {
		return this.saleContractName;
	}

	public void setSaleContractName(String saleContractName) {
		this.saleContractName = saleContractName;
	}

	@Column(name = "PAYMENT_TYPE", nullable = true, length = 64)
	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "PAYMENT_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
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

	@Column(name = "EXECUTE_RATE", nullable = true, precision = 10, scale = 4)
	public double getExecuteRate() {
		return this.executeRate;
	}

	public void setExecuteRate(double executeRate) {
		this.executeRate = executeRate;
	}

	@Type(type = "true_false")
	@Column(name = "IS_ABROAD")
	public boolean getIsAbroad() {
		return this.isAbroad;
	}

	public void setIsAbroad(boolean isAbroad) {
		this.isAbroad = isAbroad;
	}

	@Column(name = "COLLECTION_UNIT", nullable = true, length = 128)
	public String getCollectionUnit() {
		return this.collectionUnit;
	}

	public void setCollectionUnit(String collectionUnit) {
		this.collectionUnit = collectionUnit;
	}

	@Column(name = "DEPOSIT_BANK", nullable = true, length = 128)
	public String getDepositBank() {
		return this.depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	@Column(name = "BANK_CODE", nullable = true, length = 24)
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

	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
