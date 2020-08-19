package com.supporter.prj.cneec.data_migration.dept_data_migration.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaDeptBusiness entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseDeptBusiness implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deptBusinessId;
	private String deptDataMigrationId;
	private String businessRegistrationId;
	private String businessType;
	private String businessName;
	private String businessInnerName;
	private String isdispose;

	// Constructors

	/** default constructor */
	public BaseDeptBusiness() {
	}

	/** minimal constructor */
	public BaseDeptBusiness(String deptBusinessId) {
		this.deptBusinessId = deptBusinessId;
	}

	/** full constructor */
	public BaseDeptBusiness(String deptBusinessId, String deptDataMigrationId,
			String businessRegistrationId, String businessType,
			String businessName, String businessInnerName, String isdispose) {
		this.deptBusinessId = deptBusinessId;
		this.deptDataMigrationId = deptDataMigrationId;
		this.businessRegistrationId = businessRegistrationId;
		this.businessType = businessType;
		this.businessName = businessName;
		this.businessInnerName = businessInnerName;
		this.isdispose = isdispose;
	}

	// Property accessors
	@Id
	@Column(name = "DEPT_BUSINESS_ID", unique = true, nullable = false, length = 32)
	public String getDeptBusinessId() {
		return this.deptBusinessId;
	}

	public void setDeptBusinessId(String deptBusinessId) {
		this.deptBusinessId = deptBusinessId;
	}

	@Column(name = "DEPT_DATA_MIGRATION_ID", length = 32)
	public String getDeptDataMigrationId() {
		return this.deptDataMigrationId;
	}

	public void setDeptDataMigrationId(String deptDataMigrationId) {
		this.deptDataMigrationId = deptDataMigrationId;
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

	@Column(name = "ISDISPOSE", length = 1)
	public String getIsdispose() {
		return this.isdispose;
	}

	public void setIsdispose(String isdispose) {
		this.isdispose = isdispose;
	}

}