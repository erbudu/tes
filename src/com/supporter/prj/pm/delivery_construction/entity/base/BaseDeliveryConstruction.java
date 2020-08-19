package com.supporter.prj.pm.delivery_construction.entity.base;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PmSealManage entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseDeliveryConstruction implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Fields
	 private java.lang.String deliveryId;
	 private String deliveryNo;
	 private java.util.Date deliveryDate;
	 private String deliveryDeptId;
	 private String deliveryDept;
	 private String deliveryManId;
	 private String deliveryMan;
	 private String receiveDept;
	 private String receiveDeptId;
	 private String receiver;
	 private Integer status;
	 private java.lang.String createdById;
	 private java.lang.String createdByName;
	 private java.lang.String createdDeptName;
	 private java.lang.String createdDeptId;
	 private java.util.Date createdDate;
	 private java.lang.String modifiedByName;
	 private java.lang.String modifiedById;
	 private java.util.Date modifiedDate;
	private java.lang.String prjId;
	private java.lang.String prjName;

	@Id
	@Column(name = "delivery_id", unique = true, nullable = false, length = 32)
	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	@Column(name = "delivery_no", length = 32)
	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "delivery_date", length = 7)
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@Column(name = "delivery_dept_id", length = 32)
	public String getDeliveryDeptId() {
		return deliveryDeptId;
	}

	public void setDeliveryDeptId(String deliveryDeptId) {
		this.deliveryDeptId = deliveryDeptId;
	}
	@Column(name = "delivery_dept", length = 200)
	public String getDeliveryDept() {
		return deliveryDept;
	}

	public void setDeliveryDept(String deliveryDept) {
		this.deliveryDept = deliveryDept;
	}
	@Column(name = "delivery_man_id", length = 32)
	public String getDeliveryManId() {
		return deliveryManId;
	}

	public void setDeliveryManId(String deliveryManId) {
		this.deliveryManId = deliveryManId;
	}
	@Column(name = "delivery_man", length = 32)
	public String getDeliveryMan() {
		return deliveryMan;
	}

	public void setDeliveryMan(String deliveryMan) {
		this.deliveryMan = deliveryMan;
	}
	@Column(name = "receive_dept", length = 200)
	public String getReceiveDept() {
		return receiveDept;
	}

	public void setReceiveDept(String receiveDept) {
		this.receiveDept = receiveDept;
	}
	@Column(name = "receive_dept_id", length = 32)
	public String getReceiveDeptId() {
		return receiveDeptId;
	}

	public void setReceiveDeptId(String receiveDeptId) {
		this.receiveDeptId = receiveDeptId;
	}

	@Column(name = "receiver", length = 128)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "created_by_id", length = 32)
	public java.lang.String getCreatedById() {
		return createdById;
	}
	
	public void setCreatedById(java.lang.String createdById) {
		this.createdById = createdById;
	}
	
	@Column(name = "created_by_name", length = 64)
	public java.lang.String getCreatedByName() {
		return createdByName;
	}
	
	public void setCreatedByName(java.lang.String createdByName) {
		this.createdByName = createdByName;
	}
	
	@Column(name = "created_dept_name", length = 200)
	public java.lang.String getCreatedDeptName() {
		return createdDeptName;
	}
	
	public void setCreatedDeptName(java.lang.String createdDeptName) {
		this.createdDeptName = createdDeptName;
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
	
	@Column(name = "modified_by_name", length = 64)
	public java.lang.String getModifiedByName() {
		return modifiedByName;
	}
	
	public void setModifiedByName(java.lang.String modifiedByName) {
		this.modifiedByName = modifiedByName;
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