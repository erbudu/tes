package com.supporter.prj.ppm.schedule_status.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**   
 * @Title: Entity
 * @Description: PPM_TOOL_TIPS,字段与数据库字段一一对应.
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BaseScheduleStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	private String scheduleId;
	//private String prjId;
	private String textDisplay;
	private String moduleId;//保存项目ID
	
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private Date createdDate;
	private String modifiedBy;
	private String modifiedById;
	private Date modifiedDate;

	/**
	 * 无参构造函数.
	 */
	public BaseScheduleStatus() {
	}

	/**
	 * 构造函数.
	 *
	 * @param id
	 */
	public BaseScheduleStatus(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Id
	@Column(name = "SCHEDULE_ID", nullable = false, length = 32)
	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Column(name = "TEXT_DISPLAY", nullable = true, length = 32)
	public String getTextDisplay() {
		return textDisplay;
	}

	public void setTextDisplay(String textDisplay) {
		this.textDisplay = textDisplay;
	}

	@Column(name = "MODULE_ID", nullable = true, length = 128)
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	

	/**
	 * GET方法: 取得DEPT_NAME.
	 *
	 * @return: String  DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * SET方法: 设置DEPT_NAME.
	 *
	 * @param: String  DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * GET方法: 取得DEPT_ID.
	 *
	 * @return: String  DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * SET方法: 设置DEPT_ID.
	 *
	 * @param: String  DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 *
	 * @return: String  CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 *
	 * @param: String  CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 *
	 * @return: String  CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 *
	 * @param: String  CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * GET方法: 取得CREATED_DATE.
	 *
	 * @return: String  CREATED_DATE
	 */
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 *
	 * @param: String  CREATED_DATE
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_BY.
	 *
	 * @return: String  MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * SET方法: 设置MODIFIED_BY.
	 *
	 * @param: String  MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * GET方法: 取得MODIFIED_BY_ID.
	 *
	 * @return: String  MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * SET方法: 设置MODIFIED_BY_ID.
	 *
	 * @param: String  MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 *
	 * @return: String  MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 *
	 * @param: String  MODIFIED_DATE
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

//	@Column(name = "PRJ_ID", nullable = true, length = 32)
//	public String getPrjId() {
//		return prjId;
//	}
//
//	public void setPrjId(String prjId) {
//		this.prjId = prjId;
//	}

	
}
