package com.supporter.prj.cneec.data_migration.dept_data_migration.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaDeptDataMigration entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseDeptDataMigration implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deptDataMigrationId;
	private String originalDeptId;
	private String originalDeptName;
	private String newDeptId;
	private String newDeptName;
	private String createdDeptId;
	private String createdDeptName;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;

	// Constructors

	/** default constructor */
	public BaseDeptDataMigration() {
	}

	/** minimal constructor */
	public BaseDeptDataMigration(String deptDataMigrationId) {
		this.deptDataMigrationId = deptDataMigrationId;
	}

	/** full constructor */
	public BaseDeptDataMigration(String deptDataMigrationId,
			String originalDeptId, String originalDeptName, String newDeptId,
			String newDeptName, String createdDeptId, String createdDeptName,
			String createdById, String createdBy, String createdDate,
			String modifiedById, String modifiedBy, String modifiedDate) {
		this.deptDataMigrationId = deptDataMigrationId;
		this.originalDeptId = originalDeptId;
		this.originalDeptName = originalDeptName;
		this.newDeptId = newDeptId;
		this.newDeptName = newDeptName;
		this.createdDeptId = createdDeptId;
		this.createdDeptName = createdDeptName;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@Id
	@Column(name = "DEPT_DATA_MIGRATION_ID", unique = true, nullable = false, length = 32)
	public String getDeptDataMigrationId() {
		return this.deptDataMigrationId;
	}

	public void setDeptDataMigrationId(String deptDataMigrationId) {
		this.deptDataMigrationId = deptDataMigrationId;
	}

	@Column(name = "ORIGINAL_DEPT_ID", length = 32)
	public String getOriginalDeptId() {
		return this.originalDeptId;
	}

	public void setOriginalDeptId(String originalDeptId) {
		this.originalDeptId = originalDeptId;
	}

	@Column(name = "ORIGINAL_DEPT_NAME", length = 64)
	public String getOriginalDeptName() {
		return this.originalDeptName;
	}

	public void setOriginalDeptName(String originalDeptName) {
		this.originalDeptName = originalDeptName;
	}

	@Column(name = "NEW_DEPT_ID", length = 32)
	public String getNewDeptId() {
		return this.newDeptId;
	}

	public void setNewDeptId(String newDeptId) {
		this.newDeptId = newDeptId;
	}

	@Column(name = "NEW_DEPT_NAME", length = 64)
	public String getNewDeptName() {
		return this.newDeptName;
	}

	public void setNewDeptName(String newDeptName) {
		this.newDeptName = newDeptName;
	}

	@Column(name = "CREATED_DEPT_ID", length = 32)
	public String getCreatedDeptId() {
		return this.createdDeptId;
	}

	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}

	@Column(name = "CREATED_DEPT_NAME", length = 64)
	public String getCreatedDeptName() {
		return this.createdDeptName;
	}

	public void setCreatedDeptName(String createdDeptName) {
		this.createdDeptName = createdDeptName;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}