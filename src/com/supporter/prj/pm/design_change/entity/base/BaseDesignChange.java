package com.supporter.prj.pm.design_change.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseDesignChange implements java.io.Serializable{

	// primary key
	private java.lang.String applyId;//主键

	// fields
	private java.lang.String applyNo;
	private java.lang.String projectNo;
	private String prjId;
	private String prjName;
	private java.util.Date applyDate;
	private java.lang.String singleEngiee;
	private java.lang.String deptEngiee;
	private java.lang.String diviseEngiee;
	private java.lang.String drawsName;
	private java.lang.String drawsNo;
	private java.lang.String specialityId;
	private java.lang.String speciality;
	private java.lang.String bookNo;
	private java.lang.String bookName;
	private java.lang.String changeReasonId;
	private java.lang.String changeReason;
	private java.lang.String createdById;
	private java.lang.String createdBy;
	private java.lang.String createdDept;
	private java.lang.String createdDeptId;
	private java.util.Date createdDate;
	private java.lang.String modifiedBy;
	private java.lang.String modifiedById;
	private java.util.Date modifiedDate;
	private java.lang.Integer status;
	private java.lang.String fileType;
	private java.lang.String fileTypeId;
	private java.lang.String modelTypeId;
	private java.lang.String modelType;
	private int isRewinding;//文档编写是否归卷
	private int isExist;//文档编写是否存在
	private java.lang.String procId;
	
	/** default constructor */
	public BaseDesignChange() {
	}

	/** minimal constructor */
	public BaseDesignChange(String applyId) {
		this.applyId = applyId;
	}
	
	/** full constructor */
	public BaseDesignChange(String applyId, String applyNo,String prjId,String prjName,String projectNo,
			Date applyDate,String singleEngiee,String deptEngiee, String diviseEngiee,
			String drawsName,String drawsNo,String speciality,String bookNo,String bookName,
			String changeReasonId,
			String changeReason,String createdById,String createdBy,String createdDept,
			String createdDeptId,Date createdDate,String modifiedById,String modifiedBy,
			Date modifiedDate,Integer status,String fileType,String fileTypeId,
			String modelTypeId,String modelType,int isRewinding,int isExist) {
		this.applyId = applyId;
		this.applyNo = applyNo;
		this.projectNo = projectNo;
		this.prjId = prjId;
		this.prjName = prjName;
		this.applyDate = applyDate;
		this.singleEngiee = singleEngiee;
		this.deptEngiee = deptEngiee;
		this.diviseEngiee = diviseEngiee;
		this.drawsName = drawsName;
		this.drawsNo = drawsNo;
		this.speciality = speciality;
		this.bookName = bookName;
		this.bookNo = bookNo;
		this.changeReasonId = changeReasonId;
		this.changeReason = changeReason;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDept = createdDept;
		this.createdDeptId = createdDeptId;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.status = status;
		this.fileType = fileType;
		this.fileTypeId = fileTypeId;
		this.modelTypeId = modelTypeId;
		this.modelType = modelType;
		this.isRewinding = isRewinding;
		this.isExist = isExist;
		
	}
	@Id
	@Column(name = "apply_id", unique = true, nullable = false, length = 32)
	public java.lang.String getApplyId() {
		return applyId;
	}

	public void setApplyId(java.lang.String applyId) {
		this.applyId = applyId;
	}

	@Column(name = "prj_id", length = 32)
	public java.lang.String getPrjId() {
		return prjId;
	}

	public void setPrjId(java.lang.String prjId) {
		this.prjId = prjId;
	}
	
	@Column(name = "prj_name", length = 128)
	public java.lang.String getPrjName() {
		return prjName;
	}

	public void setPrjName(java.lang.String prjName) {
		this.prjName = prjName;
	}
	@Column(name = "created_by_id", length = 32)
	public java.lang.String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(java.lang.String createdById) {
		this.createdById = createdById;
	}
     
	@Column(name = "created_by", length = 64)
	public java.lang.String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(java.lang.String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_dept", length = 64)
	public java.lang.String getCreatedDept() {
		return createdDept;
	}

	public void setCreatedDept(java.lang.String createdDept) {
		this.createdDept = createdDept;
	}

	@Column(name = "created_dept_id", length = 32)
	public java.lang.String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(java.lang.String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}
	
	@Column(name = "created_date", length = 7)
	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "modified_by", length = 64)
	public java.lang.String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(java.lang.String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "modified_by_id", length = 32)
	public java.lang.String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(java.lang.String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "modified_date", length = 7)
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "status", length = 11)
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	@Column(name = "apply_no", length = 32)
	public java.lang.String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(java.lang.String applyNo) {
		this.applyNo = applyNo;
	}

	@Column(name = "project_no", length = 32)
	public java.lang.String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(java.lang.String projectNo) {
		this.projectNo = projectNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "apply_date", length = 7)
	public java.util.Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(java.util.Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "single_engiee", length = 64)
	public java.lang.String getSingleEngiee() {
		return singleEngiee;
	}

	public void setSingleEngiee(java.lang.String singleEngiee) {
		this.singleEngiee = singleEngiee;
	}

	@Column(name = "dept_engiee", length = 64)
	public java.lang.String getDeptEngiee() {
		return deptEngiee;
	}

	public void setDeptEngiee(java.lang.String deptEngiee) {
		this.deptEngiee = deptEngiee;
	}

	@Column(name = "divise_engiee", length = 64)
	public java.lang.String getDiviseEngiee() {
		return diviseEngiee;
	}

	public void setDiviseEngiee(java.lang.String diviseEngiee) {
		this.diviseEngiee = diviseEngiee;
	}

	@Column(name = "draws_name", length = 64)
	public java.lang.String getDrawsName() {
		return drawsName;
	}

	public void setDrawsName(java.lang.String drawsName) {
		this.drawsName = drawsName;
	}

	@Column(name = "draws_no", length = 32)
	public java.lang.String getDrawsNo() {
		return drawsNo;
	}

	public void setDrawsNo(java.lang.String drawsNo) {
		this.drawsNo = drawsNo;
	}


	@Column(name = "speciality_id", length = 32)
	public java.lang.String getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(java.lang.String specialityId) {
		this.specialityId = specialityId;
	}
	
	@Column(name = "speciality", length = 64)
	public java.lang.String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(java.lang.String speciality) {
		this.speciality = speciality;
	}

	@Column(name = "book_no", length = 32)
	public java.lang.String getBookNo() {
		return bookNo;
	}

	public void setBookNo(java.lang.String bookNo) {
		this.bookNo = bookNo;
	}

	@Column(name = "book_name", length = 64)
	public java.lang.String getBookName() {
		return bookName;
	}

	public void setBookName(java.lang.String bookName) {
		this.bookName = bookName;
	}

	@Column(name = "change_reason", length = 64)
	public java.lang.String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(java.lang.String changeReason) {
		this.changeReason = changeReason;
	}

	@Column(name = "change_reason_id", length = 32)
	public java.lang.String getChangeReasonId() {
		return changeReasonId;
	}

	public void setChangeReasonId(java.lang.String changeReasonId) {
		this.changeReasonId = changeReasonId;
	}
	
	@Column(name = "file_type", length = 32)
	public java.lang.String getFileType() {
		return fileType;
	}

	public void setFileType(java.lang.String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "file_type_id", length = 32)
	public java.lang.String getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(java.lang.String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	@Column(name = "model_type_id", length = 32)
	public java.lang.String getModelTypeId() {
		return modelTypeId;
	}

	public void setModelTypeId(java.lang.String modelTypeId) {
		this.modelTypeId = modelTypeId;
	}

	@Column(name = "model_type", length = 32)
	public java.lang.String getModelType() {
		return modelType;
	}

	public void setModelType(java.lang.String modelType) {
		this.modelType = modelType;
	}
	
	@Column(name = "is_rewinding")
	public int getIsRewinding() {
		return isRewinding;
	}

	public void setIsRewinding(int isRewinding) {
		this.isRewinding = isRewinding;
	}
	
	@Column(name = "is_exist", length = 11)
	public int getIsExist() {
		return isExist;
	}

	public void setIsExist(int isExist) {
		this.isExist = isExist;
	}
	
	@Column(name = "proc_id", length = 32)
	public java.lang.String getProcId() {
		return procId;
	}

	public void setProcId(java.lang.String procId) {
		this.procId = procId;
	}
}
