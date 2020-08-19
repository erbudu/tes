package com.supporter.prj.cneec.data_migration.prj_data_migration.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaPrjBusiness entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePrjBusiness implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prjBusinessId;
	private String prjDataMigrationId;
	private String businessRegistrationId;
	private String businessType;
	private String businessName;
	private String businessInnerName;

	// Constructors

	/** default constructor */
	public BasePrjBusiness() {
	}

	/** minimal constructor */
	public BasePrjBusiness(String prjBusinessId) {
		this.prjBusinessId = prjBusinessId;
	}

	/** full constructor */
	public BasePrjBusiness(String prjBusinessId, String prjDataMigrationId,
			String businessRegistrationId, String businessType,
			String businessName, String businessInnerName) {
		this.prjBusinessId = prjBusinessId;
		this.prjDataMigrationId = prjDataMigrationId;
		this.businessRegistrationId = businessRegistrationId;
		this.businessType = businessType;
		this.businessName = businessName;
		this.businessInnerName = businessInnerName;
	}

	// Property accessors
	@Id
	@Column(name = "PRJ_BUSINESS_ID", unique = true, nullable = false, length = 32)
	public String getPrjBusinessId() {
		return this.prjBusinessId;
	}

	public void setPrjBusinessId(String prjBusinessId) {
		this.prjBusinessId = prjBusinessId;
	}

	@Column(name = "PRJ_DATA_MIGRATION_ID", length = 32)
	public String getPrjDataMigrationId() {
		return this.prjDataMigrationId;
	}

	public void setPrjDataMigrationId(String prjDataMigrationId) {
		this.prjDataMigrationId = prjDataMigrationId;
	}

	@Column(name = "BUSINESS_REGISTRATION_ID", length = 32)
	public String getBusinessRegistrationId() {
		return this.businessRegistrationId;
	}

	public void setBusinessRegistrationId(String businessRegistrationId) {
		this.businessRegistrationId = businessRegistrationId;
	}

	@Column(name = "BUSINESS_TYPE", length = 2)
	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "BUSINESS_NAME")
	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(name = "BUSINESS_INNER_NAME")
	public String getBusinessInnerName() {
		return this.businessInnerName;
	}

	public void setBusinessInnerName(String businessInnerName) {
		this.businessInnerName = businessInnerName;
	}

}