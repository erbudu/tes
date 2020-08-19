package com.supporter.prj.linkworks.oa.user_defined.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * UEmpchange entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseEmpchange implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;
	private String changeNo;
	private String changeName;
	private String sex;
	private String oldDept;
	private String toDept;
	private String toDeptId;
	private String political;
	private String isAssignedRoom;
	private String unionWelfare;
	private String insuranceWelfare;
	private String changeDate;
	private String remarks;
	private String departmentHead;
	private String departmentHeadId;
	private Integer status;
	private String procId;
	private String deptId;
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
	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	// Constructors
	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	// Constructors

	/** default constructor */
	public BaseEmpchange() {
	}

	/** minimal constructor */
	public BaseEmpchange(String id) {
		this.id = id;
	}

	/** full constructor */
	public BaseEmpchange(String id, String createdById, String createdBy,
			String createdDate, String modifiedById, String modifiedBy,
			String modifiedDate, String changeNo, String changeName,
			String sex, String oldDept, String toDept, String toDeptId,
			String political, String isAssignedRoom, String unionWelfare,
			String insuranceWelfare, String changeDate, String remarks,
			String departmentHead, String departmentHeadId, Integer status,
			String procId) {
		this.id = id;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.changeNo = changeNo;
		this.changeName = changeName;
		this.sex = sex;
		this.oldDept = oldDept;
		this.toDept = toDept;
		this.toDeptId = toDeptId;
		this.procId = procId;
		this.political = political;
		this.isAssignedRoom = isAssignedRoom;
		this.unionWelfare = unionWelfare;
		this.insuranceWelfare = insuranceWelfare;
		this.changeDate = changeDate;
		this.remarks = remarks;
		this.departmentHead = departmentHead;
		this.departmentHeadId = departmentHeadId;
		this.status = status;
		
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

	@Column(name = "CHANGE_NO", length = 128)
	public String getChangeNo() {
		return this.changeNo;
	}

	public void setChangeNo(String changeNo) {
		this.changeNo = changeNo;
	}

	@Column(name = "CHANGE_NAME", length = 64)
	public String getChangeName() {
		return this.changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}

	@Column(name = "SEX", length = 6)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "OLD_DEPT", length = 64)
	public String getOldDept() {
		return this.oldDept;
	}

	public void setOldDept(String oldDept) {
		this.oldDept = oldDept;
	}

	@Column(name = "TO_DEPT", length = 64)
	public String getToDept() {
		return this.toDept;
	}

	public void setToDept(String toDept) {
		this.toDept = toDept;
	}

	@Column(name = "TO_DEPT_ID", length = 32)
	public String getToDeptId() {
		return this.toDeptId;
	}

	public void setToDeptId(String toDeptId) {
		this.toDeptId = toDeptId;
	}

	@Column(name = "POLITICAL", length = 6)
	public String getPolitical() {
		return this.political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	@Column(name = "IS_ASSIGNED_ROOM", length = 32)
	public String getIsAssignedRoom() {
		return this.isAssignedRoom;
	}

	public void setIsAssignedRoom(String isAssignedRoom) {
		this.isAssignedRoom = isAssignedRoom;
	}

	@Column(name = "UNION_WELFARE", length = 32)
	public String getUnionWelfare() {
		return this.unionWelfare;
	}

	public void setUnionWelfare(String unionWelfare) {
		this.unionWelfare = unionWelfare;
	}

	@Column(name = "INSURANCE_WELFARE", length = 32)
	public String getInsuranceWelfare() {
		return this.insuranceWelfare;
	}

	public void setInsuranceWelfare(String insuranceWelfare) {
		this.insuranceWelfare = insuranceWelfare;
	}

	@Column(name = "CHANGE_DATE", length = 32)
	public String getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	@Column(name = "REMARKS", length = 32)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "DEPARTMENT_HEAD", length = 64)
	public String getDepartmentHead() {
		return this.departmentHead;
	}

	public void setDepartmentHead(String departmentHead) {
		this.departmentHead = departmentHead;
	}

	@Column(name = "DEPARTMENT_HEAD_ID", length = 32)
	public String getDepartmentHeadId() {
		return this.departmentHeadId;
	}

	public void setDepartmentHeadId(String departmentHeadId) {
		this.departmentHeadId = departmentHeadId;
	}

	@Column(name = "STATUS", precision = 6, scale = 0)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}