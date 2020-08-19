package com.supporter.prj.cneec.data_migration.person_data_migration.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaPersonDataMigration entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePersonDataMigration implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String personDataMigrationId;
	private String originalDeptId;
	private String originalDeptName;
	private String newDeptId;
	private String newDeptName;
	private String personIds;
	private String personNames;
	private String createdDeptId;
	private String createdDeptName;
	private Long createdById;
	private String createdBy;
	private String createdDate;
	private Long modifiedById;
	private String modifiedBy;
	private String modifiedDate;

	// Constructors

	/** default constructor */
	public BasePersonDataMigration() {
	}

	/** minimal constructor */
	public BasePersonDataMigration(String personDataMigrationId) {
		this.personDataMigrationId = personDataMigrationId;
	}

	/** full constructor */
	public BasePersonDataMigration(String personDataMigrationId,
			String originalDeptId, String originalDeptName, String newDeptId,
			String newDeptName, String personIds, String personNames,
			String createdDeptId, String createdDeptName, Long createdById,
			String createdBy, String createdDate, Long modifiedById,
			String modifiedBy, String modifiedDate) {
		this.personDataMigrationId = personDataMigrationId;
		this.originalDeptId = originalDeptId;
		this.originalDeptName = originalDeptName;
		this.newDeptId = newDeptId;
		this.newDeptName = newDeptName;
		this.personIds = personIds;
		this.personNames = personNames;
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
	@Column(name = "PERSON_DATA_MIGRATION_ID", unique = true, nullable = false, length = 32)
	public String getPersonDataMigrationId() {
		return this.personDataMigrationId;
	}

	public void setPersonDataMigrationId(String personDataMigrationId) {
		this.personDataMigrationId = personDataMigrationId;
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

	@Column(name = "PERSON_IDS", length = 1024)
	public String getPersonIds() {
		return this.personIds;
	}

	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}

	@Column(name = "PERSON_NAMES", length = 1024)
	public String getPersonNames() {
		return this.personNames;
	}

	public void setPersonNames(String personNames) {
		this.personNames = personNames;
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

	@Column(name = "CREATED_BY_ID", precision = 18, scale = 0)
	public Long getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(Long createdById) {
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

	@Column(name = "MODIFIED_BY_ID", precision = 18, scale = 0)
	public Long getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(Long modifiedById) {
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