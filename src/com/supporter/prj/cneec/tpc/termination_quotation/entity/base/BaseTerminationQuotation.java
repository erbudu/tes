package com.supporter.prj.cneec.tpc.termination_quotation.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_TERMINATION_QUOTATION,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-09-18 15:47:27
 * @version V1.0
 * 
 */
@MappedSuperclass
public class BaseTerminationQuotation implements Serializable {

	private static final long serialVersionUID = 1L;
	private java.lang.String quotationId;// QUOTATION_ID.
	private java.lang.String projectId;// PROJECT_ID.
	private java.lang.String projectName;// PROJECT_NAME.
	private java.lang.String terminationReson;// TERMINATION_RESON.
	private java.lang.String terminationTime;// TERMINATION_TIME.
	private java.lang.String notifyPersonId;// NOTIFY_PERSON_ID.
	private java.lang.String notifyPerson;// NOTIFY_PERSON.
	private Integer swfStatus;// SWF_STATUS.
	private java.lang.String deptName;// DEPT_NAME.
	private java.lang.String deptId;// DEPT_ID.
	private java.lang.String createdBy;// CREATED_BY.
	private java.lang.String createdById;// CREATED_BY_ID.
	private java.lang.String createdDate;// CREATED_DATE.
	private java.lang.String modifiedBy;// MODIFIED_BY.
	private java.lang.String modifiedById;// MODIFIED_BY_ID.
	private java.lang.String modifiedDate;// MODIFIED_DATE.
	private java.lang.String procId;// PROC_ID.

	/**
	 * 无参构造函数.
	 */
	public BaseTerminationQuotation() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param quotationId
	 */
	public BaseTerminationQuotation(String quotationId) {
		this.quotationId = quotationId;
	}

	/**
	 * 方法: 取得QUOTATION_ID.
	 * 
	 * @return: java.lang.String QUOTATION_ID
	 */
	@Id
	@Column(name = "QUOTATION_ID", nullable = false, length = 32)
	public java.lang.String getQuotationId() {
		return this.quotationId;
	}

	/**
	 * 方法: 设置QUOTATION_ID.
	 * 
	 * @param: java.lang.String QUOTATION_ID
	 */
	public void setQuotationId(java.lang.String quotationId) {
		this.quotationId = quotationId;
	}

	/**
	 * 方法: 取得PROJECT_ID.
	 * 
	 * @return: java.lang.String PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public java.lang.String getProjectId() {
		return this.projectId;
	}

	/**
	 * 方法: 设置PROJECT_ID.
	 * 
	 * @param: java.lang.String PROJECT_ID
	 */
	public void setProjectId(java.lang.String projectId) {
		this.projectId = projectId;
	}

	/**
	 * 方法: 取得PROJECT_NAME.
	 * 
	 * @return: java.lang.String PROJECT_NAME
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public java.lang.String getProjectName() {
		return this.projectName;
	}

	/**
	 * 方法: 设置PROJECT_NAME.
	 * 
	 * @param: java.lang.String PROJECT_NAME
	 */
	public void setProjectName(java.lang.String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 方法: 取得TERMINATION_RESON.
	 * 
	 * @return: java.lang.String TERMINATION_RESON
	 */
	@Column(name = "TERMINATION_RESON", nullable = true, length = 512)
	public java.lang.String getTerminationReson() {
		return this.terminationReson;
	}

	/**
	 * 方法: 设置TERMINATION_RESON.
	 * 
	 * @param: java.lang.String TERMINATION_RESON
	 */
	public void setTerminationReson(java.lang.String terminationReson) {
		this.terminationReson = terminationReson;
	}

	/**
	 * 方法: 取得TERMINATION_TIME.
	 * 
	 * @return: java.lang.String TERMINATION_TIME
	 */
	@Column(name = "TERMINATION_TIME", nullable = true, length = 27)
	public java.lang.String getTerminationTime() {
		return this.terminationTime;
	}

	/**
	 * 方法: 设置TERMINATION_TIME.
	 * 
	 * @param: java.lang.String TERMINATION_TIME
	 */
	public void setTerminationTime(java.lang.String terminationTime) {
		this.terminationTime = terminationTime;
	}

	/**
	 * 方法: 取得NOTIFY_PERSON_ID.
	 * 
	 * @return: java.lang.String NOTIFY_PERSON_ID
	 */
	@Column(name = "NOTIFY_PERSON_ID", nullable = true, length = 128)
	public java.lang.String getNotifyPersonId() {
		return this.notifyPersonId;
	}

	/**
	 * 方法: 设置NOTIFY_PERSON_ID.
	 * 
	 * @param: java.lang.String NOTIFY_PERSON_ID
	 */
	public void setNotifyPersonId(java.lang.String notifyPersonId) {
		this.notifyPersonId = notifyPersonId;
	}

	/**
	 * 方法: 取得NOTIFY_PERSON.
	 * 
	 * @return: java.lang.String NOTIFY_PERSON
	 */
	@Column(name = "NOTIFY_PERSON", nullable = true, length = 128)
	public java.lang.String getNotifyPerson() {
		return this.notifyPerson;
	}

	/**
	 * 方法: 设置NOTIFY_PERSON.
	 * 
	 * @param: java.lang.String NOTIFY_PERSON
	 */
	public void setNotifyPerson(java.lang.String notifyPerson) {
		this.notifyPerson = notifyPerson;
	}

	/**
	 * 方法: 取得SWF_STATUS.
	 * 
	 * @return: int SWF_STATUS
	 */
	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	/**
	 * 方法: 设置SWF_STATUS.
	 * 
	 * @param: int SWF_STATUS
	 */
	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	/**
	 * 方法: 取得DEPT_NAME.
	 * 
	 * @return: java.lang.String DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public java.lang.String getDeptName() {
		return this.deptName;
	}

	/**
	 * 方法: 设置DEPT_NAME.
	 * 
	 * @param: java.lang.String DEPT_NAME
	 */
	public void setDeptName(java.lang.String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 方法: 取得DEPT_ID.
	 * 
	 * @return: java.lang.String DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public java.lang.String getDeptId() {
		return this.deptId;
	}

	/**
	 * 方法: 设置DEPT_ID.
	 * 
	 * @param: java.lang.String DEPT_ID
	 */
	public void setDeptId(java.lang.String deptId) {
		this.deptId = deptId;
	}

	/**
	 * 方法: 取得CREATED_BY.
	 * 
	 * @return: java.lang.String CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public java.lang.String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 方法: 设置CREATED_BY.
	 * 
	 * @param: java.lang.String CREATED_BY
	 */
	public void setCreatedBy(java.lang.String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 方法: 取得CREATED_BY_ID.
	 * 
	 * @return: java.lang.String CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public java.lang.String getCreatedById() {
		return this.createdById;
	}

	/**
	 * 方法: 设置CREATED_BY_ID.
	 * 
	 * @param: java.lang.String CREATED_BY_ID
	 */
	public void setCreatedById(java.lang.String createdById) {
		this.createdById = createdById;
	}

	/**
	 * 方法: 取得CREATED_DATE.
	 * 
	 * @return: java.lang.String CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 32)
	public java.lang.String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * 方法: 设置CREATED_DATE.
	 * 
	 * @param: java.lang.String CREATED_DATE
	 */
	public void setCreatedDate(java.lang.String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 方法: 取得MODIFIED_BY.
	 * 
	 * @return: java.lang.String MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public java.lang.String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * 方法: 设置MODIFIED_BY.
	 * 
	 * @param: java.lang.String MODIFIED_BY
	 */
	public void setModifiedBy(java.lang.String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * 方法: 取得MODIFIED_BY_ID.
	 * 
	 * @return: java.lang.String MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public java.lang.String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * 方法: 设置MODIFIED_BY_ID.
	 * 
	 * @param: java.lang.String MODIFIED_BY_ID
	 */
	public void setModifiedById(java.lang.String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * 方法: 取得MODIFIED_DATE.
	 * 
	 * @return: java.lang.String MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 32)
	public java.lang.String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * 方法: 设置MODIFIED_DATE.
	 * 
	 * @param: java.lang.String MODIFIED_DATE
	 */
	public void setModifiedDate(java.lang.String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	/**
	 * 方法: 取得PROC_ID.
	 * 
	 * @return: java.lang.String PROC_ID
	 */
	@Column(name = "PROC_ID", nullable = true, length = 32)
	public java.lang.String getProcId() {
		return this.procId;
	}

	/**
	 * 方法: 设置PROC_ID.
	 * 
	 * @param: java.lang.String PROC_ID
	 */
	public void setProcId(java.lang.String procId) {
		this.procId = procId;
	}

}
