package com.supporter.prj.linkworks.oa.user_defined.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * UMaintain entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseNetMaintain implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;
	private String applyDate;
	private String project;
	private String handle;
	private String maintain;
	private boolean history;
	private String relatedFiles;
	
	@Column(name = "related_Files", length = 1024)
	public String getRelatedFiles() {
		return relatedFiles;
	}

	public void setRelatedFiles(String relatedFiles) {
		this.relatedFiles = relatedFiles;
	}

	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
	public boolean getHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}
	// Constructors

	/** default constructor */
	public BaseNetMaintain() {
	}

	/** minimal constructor */
	public BaseNetMaintain(String id) {
		this.id = id;
	}

	/** full constructor */
	public BaseNetMaintain(String id, String createdById, String createdBy,
			String createdDate, String modifiedById, String modifiedBy,
			String modifiedDate, String applyDate, String project,
			String handle, String maintain) {
		this.id = id;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.applyDate = applyDate;
		this.project = project;
		this.handle = handle;
		this.maintain = maintain;
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

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_BY", length = 64)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 32)
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

	@Column(name = "MODIFIED_BY", length = 64)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", length = 32)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "APPLY_DATE", length = 32)
	public String getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "PROJECT", length = 1024)
	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Column(name = "HANDLE", length = 32)
	public String getHandle() {
		return this.handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	@Column(name = "MAINTAIN", length = 32)
	public String getMaintain() {
		return this.maintain;
	}

	public void setMaintain(String maintain) {
		this.maintain = maintain;
	}

}