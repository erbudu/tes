package com.supporter.prj.ppm.poa.icc.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public abstract class BaseCoordinationResult implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String recordId;
	private String iccId;
	private String prjId;
	private int iccResultStatus;
	private String iccResultDesc;
	private Date createdDate;
	private String createdBy;
	private String createdById;
	private String createdByDept;
	private String createDeptId;
	private String modifiedId;
	private String modifiedName;
	private Date modifiedDate;
	
	//private String procId;
	
	// Constructors
	/** default constructor */
	public BaseCoordinationResult() {
		super();
	}
	
	public BaseCoordinationResult(String recordId, String iccId, String prjId, int iccResultStatus,
			String iccResultDesc, Date createdDate, String createdBy, String createdById, String createdByDept,
			String createDeptId, String modifiedId, String modifiedName, Date modifiedDate) {
		super();
		this.recordId = recordId;
		this.iccId = iccId;
		this.prjId = prjId;
		this.iccResultStatus = iccResultStatus;
		this.iccResultDesc = iccResultDesc;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdByDept = createdByDept;
		this.createDeptId = createDeptId;
		this.modifiedId = modifiedId;
		this.modifiedName = modifiedName;
		this.modifiedDate = modifiedDate;
	}

	/** minimal constructor */
	
	
	// Property accessors
	@Id
	@GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "REOCRD_ID", nullable = false, length = 32)
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	@Column(name = "ICC_ID", nullable = false, length = 32)
	public String getIccId() {
		return iccId;
	}
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}
	@Column(name = "ICC_RESULT_STATUS", nullable = true, length = 32)
	public int getIccResultStatus() {
		return iccResultStatus;
	}
	public void setIccResultStatus(int iccResultStatus) {
		this.iccResultStatus = iccResultStatus;
	}
	@Column(name = "ICC_RESULT_DESC", nullable = true, length = 32)
	public String getIccResultDesc() {
		return iccResultDesc;
	}
	public void setIccResultDesc(String iccResultDesc) {
		this.iccResultDesc = iccResultDesc;
	}
	
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	
	
	@Column(name = "CREATED_DATE", nullable = true, length = 32)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "CREATED_BY_NAME", nullable = true, length = 32)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	@Column(name = "CREATED_BY_DEPT", nullable = true, length = 32)
	public String getCreatedByDept() {
		return createdByDept;
	}
	public void setCreatedByDept(String createdByDept) {
		this.createdByDept = createdByDept;
	}
	@Column(name = "CREATED_DEPT_ID", nullable = true, length = 32)
	public String getCreateDeptId() {
		return createDeptId;
	}
	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}
	@Column(name = "MODIFIED_ID", nullable = true, length = 32)
	public String getModifiedId() {
		return modifiedId;
	}
	public void setModifiedId(String modifiedId) {
		this.modifiedId = modifiedId;
	}
	@Column(name = "MODIFIED_NAME", nullable = true, length = 32)
	public String getModifiedName() {
		return modifiedName;
	}
	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}
	@Column(name = "MODIFIED_DATE", nullable = true, length = 32)
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	

}
