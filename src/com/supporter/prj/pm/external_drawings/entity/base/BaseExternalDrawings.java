package com.supporter.prj.pm.external_drawings.entity.base;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * PmSealManage entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseExternalDrawings implements java.io.Serializable {

	// Fields
	private String externalId;
	private String externalNo;
	private Date externalDate;
	private String topic;
	private String summary;
	private Integer status;
	private java.lang.String designDept;           			//设计方名称
	private java.lang.String designDeptId;       			//设计方ID
	private java.lang.String createdId;
	private java.lang.String createdName;
	private java.lang.String createdDeptId;
	private java.lang.String createdDept;
	private java.util.Date createdDate;
	private java.lang.String modifiedId;
	private java.lang.String modifiedName;
	private java.util.Date modifiedDate;
	private String procId;
	private int progressStatus;//通知状态：0 未处理，1 已处理 2.以后处理
	private int isSend;//发送设代：0 发，1 不发 
	private java.lang.String prjId;// 项目id
	private java.lang.String prjName;// 项目名称

	@Id
	@Column(name = "external_id", unique = true, nullable = false, length = 32)
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@Column(name = "external_no", length = 64)
	public String getExternalNo() {
		return externalNo;
	}

	public void setExternalNo(String externalNo) {
		this.externalNo = externalNo;
	}

	@Column(name = "external_date", length = 7)
	public Date getExternalDate() {
		return externalDate;
	}

	public void setExternalDate(Date externalDate) {
		this.externalDate = externalDate;
	}

	@Column(name = "topic", length = 128)
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Column(name = "summary", length = 256)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "design_dept", length = 64)
	public java.lang.String getDesignDept() {
		return designDept;
	}

	public void setDesignDept(java.lang.String designDept) {
		this.designDept = designDept;
	}

	@Column(name = "design_dept_id", length = 32)
	public java.lang.String getDesignDeptId() {
		return designDeptId;
	}

	public void setDesignDeptId(java.lang.String designDeptId) {
		this.designDeptId = designDeptId;
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
	@Column(name = "proc_id", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}

	@Column(name = "progress_status", length = 11)
	public int getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(int progressStatus) {
		this.progressStatus = progressStatus;
	}
	@Column(name = "is_send", length = 11)
	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
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