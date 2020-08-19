package com.supporter.prj.cneec.tpc.supplier.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractTpcSupplierBankAccount entity provides the base persistence
 * definition of the TpcSupplierBankAccount entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class BaseSupplierBankAccount implements
		java.io.Serializable {

	// Fields

	private String id;
	private String supplierId;
	private String gatheringUnit;
	private String bank;
	private String bankAccount;
	private String bankCode;
	private String remitProvince;
	private String remitProvinceId;
	private String remitCity;
	private String remitCityId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;

	// Constructors

	/** default constructor */
	public BaseSupplierBankAccount() {
	}

	/** minimal constructor */
	public BaseSupplierBankAccount(String id) {
		this.id = id;
	}

	/** full constructor */
	public BaseSupplierBankAccount(String id, String supplierId,
			String gatheringUnit, String bank, String bankAccount,
			String bankCode, String remitProvince, String remitProvinceId,
			String remitCity, String remitCityId, String createdBy,
			String createdById, String createdDate, String modifiedBy,
			String modifiedById, String modifiedDate) {
		this.id = id;
		this.supplierId = supplierId;
		this.gatheringUnit = gatheringUnit;
		this.bank = bank;
		this.bankAccount = bankAccount;
		this.bankCode = bankCode;
		this.remitProvince = remitProvince;
		this.remitProvinceId = remitProvinceId;
		this.remitCity = remitCity;
		this.remitCityId = remitCityId;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "SUPPLIER_ID", length = 32)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "GATHERING_UNIT", length = 512)
	public String getGatheringUnit() {
		return this.gatheringUnit;
	}

	public void setGatheringUnit(String gatheringUnit) {
		this.gatheringUnit = gatheringUnit;
	}

	@Column(name = "BANK", length = 256)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "BANK_ACCOUNT", length = 256)
	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "BANK_CODE", length = 32)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "REMIT_PROVINCE", length = 32)
	public String getRemitProvince() {
		return this.remitProvince;
	}

	public void setRemitProvince(String remitProvince) {
		this.remitProvince = remitProvince;
	}

	@Column(name = "REMIT_PROVINCE_ID", length = 32)
	public String getRemitProvinceId() {
		return this.remitProvinceId;
	}

	public void setRemitProvinceId(String remitProvinceId) {
		this.remitProvinceId = remitProvinceId;
	}

	@Column(name = "REMIT_CITY", length = 32)
	public String getRemitCity() {
		return this.remitCity;
	}

	public void setRemitCity(String remitCity) {
		this.remitCity = remitCity;
	}

	@Column(name = "REMIT_CITY_ID", length = 32)
	public String getRemitCityId() {
		return this.remitCityId;
	}

	public void setRemitCityId(String remitCityId) {
		this.remitCityId = remitCityId;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", length = 32)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
