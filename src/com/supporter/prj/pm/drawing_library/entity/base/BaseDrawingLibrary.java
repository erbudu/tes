package com.supporter.prj.pm.drawing_library.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

@MappedSuperclass
public class BaseDrawingLibrary implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	// primary key
	private java.lang.String id;//主键
	private java.lang.String drawingNo;
	private String drawingName;
	private java.lang.String approvalNo;//报批表号
	private String drawingAttributeId;
	private String drawingAttribute;
	private String drawingVersion;
	private String regionId;
	private String regionName;
	private java.lang.Integer status;
	private java.lang.Integer isIn;//是否内审（否0/是1）
	private java.lang.Integer isOut;//是否外审（否0/是1）
	private java.lang.Integer isHistory;//是否历史记录（否0/是1）
	private java.lang.String summary;
	private java.lang.String createdId;
	private java.lang.String createdName;
	private java.lang.String createdDeptId;
	private java.lang.String createdDept;
	private java.util.Date createdDate;
	private java.lang.String modifiedId;
	private java.lang.String modifiedName;
	private java.util.Date modifiedDate;
	private boolean isModified = true; // 是否发生了更改
	private java.lang.String prjId;
	private java.lang.String prjName;
	
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Column(name = "drawing_no", length = 64)
	public java.lang.String getDrawingNo() {
		return drawingNo;
	}

	public void setDrawingNo(java.lang.String drawingNo) {
		this.drawingNo = drawingNo;
	}
	
	@Column(name = "drawing_name", length = 128)
	public String getDrawingName() {
		return drawingName;
	}

	public void setDrawingName(String drawingName) {
		this.drawingName = drawingName;
	}
	
	@Column(name = "approval_no", length = 128)
	public java.lang.String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(java.lang.String approvalNo) {
		this.approvalNo = approvalNo;
	}

	@Column(name = "drawing_attribute_id", length = 32)
	public String getDrawingAttributeId() {
		return drawingAttributeId;
	}

	public void setDrawingAttributeId(String drawingAttributeId) {
		this.drawingAttributeId = drawingAttributeId;
	}
	
	@Column(name = "drawing_attribute", length = 64)
	public String getDrawingAttribute() {
		return drawingAttribute;
	}

	public void setDrawingAttribute(String drawingAttribute) {
		this.drawingAttribute = drawingAttribute;
	}
	
	@Column(name = "drawing_version", length = 32)
	public String getDrawingVersion() {
		return drawingVersion;
	}

	public void setDrawingVersion(String drawingVersion) {
		this.drawingVersion = drawingVersion;
	}
	
	@Column(name = "status", length = 11)
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	@Column(name = "is_in", length = 11)
	public java.lang.Integer getIsIn() {
		return isIn;
	}

	public void setIsIn(java.lang.Integer isIn) {
		this.isIn = isIn;
	}

	@Column(name = "is_out", length = 11)
	public java.lang.Integer getIsOut() {
		return isOut;
	}

	public void setIsOut(java.lang.Integer isOut) {
		this.isOut = isOut;
	}

	@Column(name = "is_history", length = 11)
	public java.lang.Integer getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(java.lang.Integer isHistory) {
		this.isHistory = isHistory;
	}
	@Column(name = "summary", length = 256)
	public java.lang.String getSummary() {
		return summary;
	}

	public void setSummary(java.lang.String summary) {
		this.summary = summary;
	}
	
	@Column(name = "created_id", length = 32)
	public java.lang.String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(java.lang.String createdId) {
		this.createdId = createdId;
	}
	
	@Column(name = "created_name", length = 32)
	public java.lang.String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(java.lang.String createdName) {
		this.createdName = createdName;
	}
	
	@Column(name = "created_date", length = 7)
	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "created_dept_id", length = 32)
	public java.lang.String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(java.lang.String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}
	
	@Column(name = "created_dept", length = 200)
	public java.lang.String getCreatedDept() {
		return createdDept;
	}

	public void setCreatedDept(java.lang.String createdDept) {
		this.createdDept = createdDept;
	}
	
	@Column(name = "modified_id", length = 32)
	public java.lang.String getModifiedId() {
		return modifiedId;
	}

	public void setModifiedId(java.lang.String modifiedId) {
		this.modifiedId = modifiedId;
	}
	
	@Column(name = "modified_name", length = 32)
	public java.lang.String getModifiedName() {
		return modifiedName;
	}

	public void setModifiedName(java.lang.String modifiedName) {
		this.modifiedName = modifiedName;
	}

	@Column(name = "modified_date", length = 7)
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name = "region_id", length = 32)
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	@Column(name = "region_name", length = 64)
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "is_modified", length = 1)
	@Type(type = "true_false")
	public boolean getIsModified() {
		return isModified;
	}

	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
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

}
