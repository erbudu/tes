package com.supporter.prj.linkworks.oa.user_defined.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * UBusinessRequirement entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseBusinessRequirement implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String createdBy;
	private String createdById;
	private String deptId;
	private String deptName;
	private String createdDate;
	private String nowBusinessDescribe;
	private String needBusinessDescribe;
	private String wantBusinessDescribe;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;
	private String no;
	private Integer status;
	private String procId;
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
	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	@Column(name = "STATUS", precision = 5, scale = 0)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	// Constructors
	@Column(name = "NO", length = 128)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	/** default constructor */
	public BaseBusinessRequirement() {
	}

	/** minimal constructor */
	public BaseBusinessRequirement(String id) {
		this.id = id;
	}

	/** full constructor */
	public BaseBusinessRequirement(String id, String createdBy,
			String createdById, String deptId, String deptName,
			String createdDate, String nowBusinessDescribe,
			String needBusinessDescribe, String wantBusinessDescribe,
			String modifiedById, String modifiedBy, String modifiedDate) {
		this.id = id;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.deptId = deptId;
		this.deptName = deptName;
		this.createdDate = createdDate;
		this.nowBusinessDescribe = nowBusinessDescribe;
		this.needBusinessDescribe = needBusinessDescribe;
		this.wantBusinessDescribe = wantBusinessDescribe;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
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

	@Column(name = "CREATED_BY", length = 64)
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

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "CREATED_DATE", length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "NOW_BUSINESS_DESCRIBE", length = 2048)
	public String getNowBusinessDescribe() {
		return this.nowBusinessDescribe;
	}

	public void setNowBusinessDescribe(String nowBusinessDescribe) {
		this.nowBusinessDescribe = nowBusinessDescribe;
	}

	@Column(name = "NEED_BUSINESS_DESCRIBE", length = 2048)
	public String getNeedBusinessDescribe() {
		return this.needBusinessDescribe;
	}

	public void setNeedBusinessDescribe(String needBusinessDescribe) {
		this.needBusinessDescribe = needBusinessDescribe;
	}

	@Column(name = "WANT_BUSINESS_DESCRIBE", length = 2048)
	public String getWantBusinessDescribe() {
		return this.wantBusinessDescribe;
	}

	public void setWantBusinessDescribe(String wantBusinessDescribe) {
		this.wantBusinessDescribe = wantBusinessDescribe;
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

}