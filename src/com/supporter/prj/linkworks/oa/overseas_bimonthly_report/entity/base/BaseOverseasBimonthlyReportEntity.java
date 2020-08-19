/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: BaseOverseasbimonthlyreportEntity</p>
 *<p>Description: 境外机构双月报审批功能实体类字段与数据库字段一致</p>
 *<p>Company: 东华后盾</p>
 * @author YUEYUN
 * @date 2019年12月23日
 * 
 */
@MappedSuperclass
public abstract class BaseOverseasBimonthlyReportEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	//  数据库字段(字段长度)				字段注释
	private String reportId;				// REPORT_ID	VARCHAR2(32)	       		主键ID
	private String reportTitle;				// REPORT_TITLE	VARCHAR2(256)			标题
	private String reportNo;				// REPORT_NO	VARCHAR2(128)				编号
	private String circulatePersonId;		// CIRCULATE_PERSON_ID	VARCHAR2(512)	传阅人ID
	private String circulatePersonName;		// CIRCULATE_PERSON_NAME	VARCHAR2(512)	传阅人名称
	private String notifyPersonId;			// NOTIFY_PERSON_ID	VARCHAR2(512)		知会人ID
	private String notifyPersonName;		// NOTIFY_PERSON_NAME	VARCHAR2(512)	知会人名称
	private String deptId;					// DEPT_ID	VARCHAR2(32)				部门ID
	private String deptName;				// DEPT_NAME	VARCHAR2(128)				部门名称
	private String createdById;				// CREATED_BY_ID	VARCHAR2(32)			创建人ID
	private String createdByName;			// CREATED_BY	VARCHAR2(64)			创建人名称
	private String createdByDate;			// CREATED_DATE	VARCHAR2(32)			创建时间
	private String modifiedById;			// MODIFIED_BY_ID	VARCHAR2(32)		修改人ID
	private String modifiedByName;			// MODIFIED_BY	VARCHAR2(64)			修改人名称
	private String modifiedDate;			// MODIFIED_DATE	VARCHAR2(32)			修改时间
	private Integer status;					// STATUS	NUMBER						审批状态（0：草稿，1：审批中，2：审批完成）
	private String procId;					// PROC_ID	VARCHAR2(32)				流程ID
	private String telePhone;				// TELE_PHONE VARCHAR(128)				电话
	
	/** This method is constructor */
	public BaseOverseasBimonthlyReportEntity() {
		super();
	}
	
	/**
	 * get 主键
	 * @return the reportId
	 */
	@Id
	@Column(name="REPORT_ID",length=32)
	public String getReportId() {
		return reportId;
	}
	/**
	 * set 主键
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	/**
	 * get 标题
	 * @return the reportTitle
	 */
	@Column(name="REPORT_TITLE",length=256)
	public String getReportTitle() {
		return reportTitle;
	}
	
	/**
	 * set 标题
	 * @param reportTitle the reportTitle to set
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	
	/**
	 * get 编号
	 * @return the reportNo
	 */
	@Column(name="REPORT_NO",length=128)
	public String getReportNo() {
		return reportNo;
	}
	
	/**
	 * set 编号
	 * @param reportNo the reportNo to set
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	
	/**
	 * get 传阅人ID
	 * @return the circulatePersonId
	 */
	@Column(name="CIRCULATE_PERSON_ID",length=512)
	public String getCirculatePersonId() {
		return circulatePersonId;
	}
	
	/**
	 * set 传阅人ID
	 * @param circulatePersonId the circulatePersonId to set
	 */
	public void setCirculatePersonId(String circulatePersonId) {
		this.circulatePersonId = circulatePersonId;
	}
	
	/**
	 * get 传阅人名称
	 * @return the circulatePersonName
	 */
	@Column(name="CIRCULATE_PERSON_NAME",length=512)
	public String getCirculatePersonName() {
		return circulatePersonName;
	}
	/**
	 * set 传阅人名称
	 * @param circulatePersonName the circulatePersonName to set
	 */
	public void setCirculatePersonName(String circulatePersonName) {
		this.circulatePersonName = circulatePersonName;
	}
	
	/**
	 * get 知会人ID
	 * @return the notifyPersonId
	 */
	@Column(name="NOTIFY_PERSON_ID",length=512)
	public String getNotifyPersonId() {
		return notifyPersonId;
	}
	/**
	 * set 知会人ID
	 * @param notifyPersonId the notifyPersonId to set
	 */
	public void setNotifyPersonId(String notifyPersonId) {
		this.notifyPersonId = notifyPersonId;
	}
	
	/**
	 *  get 知会人名称
	 * @return the notifyPersonName
	 */
	@Column(name="NOTIFY_PERSON_NAME",length=512)
	public String getNotifyPersonName() {
		return notifyPersonName;
	}
	
	/**
	 * set 只会人名称
	 * @param notifyPersonName the notifyPersonName to set
	 */
	public void setNotifyPersonName(String notifyPersonName) {
		this.notifyPersonName = notifyPersonName;
	}
	
	/**
	 * get 创建人部门ID
	 * @return the deptId
	 */
	@Column(name="DEPT_ID",length=32)
	public String getDeptId() {
		return deptId;
	}
	/**
	 * set 创建人部门ID
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * get 创建人部门名称
	 * @return the deptName
	 */
	@Column(name="DEPT_NAME",length=218)
	public String getDeptName() {
		return deptName;
	}
	/**
	 * set 创建人部门名称
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	/**
	 * get 创建人ID
	 * @return the createdById
	 */
	@Column(name="CREATED_BY_ID",length=32)
	public String getCreatedById() {
		return createdById;
	}
	/**
	 * set 创建人ID
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	/**
	 * get 创建人名称
	 * @return the createdByName
	 */
	@Column(name="CREATED_BY",length=64)
	public String getCreatedByName() {
		return createdByName;
	}
	/**
	 * set 创建人名称
	 * @param createdByName the createdByName to set
	 */
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	/**
	 * get 创建 时间
	 * @return the createdByDate
	 */
	@Column(name="CREATED_DATE",length=32)
	public String getCreatedByDate() {
		return createdByDate;
	}
	/**
	 * set 创建时间
	 * @param createdByDate the createdByDate to set
	 */
	public void setCreatedByDate(String createdByDate) {
		this.createdByDate = createdByDate;
	}
	/**
	 * get 修改人ID
	 * @return the modifiedById
	 */
	@Column(name="MODIFIED_BY_ID",length=32)
	public String getModifiedById() {
		return modifiedById;
	}
	/**
	 * set 修改人ID
	 * @param modifiedById the modifiedById to set
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}
	/**
	 * get 修改人名称
	 * @return the modifiedByName
	 */
	@Column(name="MODIFIED_BY",length=64)
	public String getModifiedByName() {
		return modifiedByName;
	}
	/**
	 * set 修改人名称
	 * @param modifiedByName the modifiedByName to set
	 */
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	/**
	 * get 修改时间
	 * @return the modifiedDate
	 */
	@Column(name="MODIFIED_DATE",length=32)
	public String getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * set 修改时间
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * get 流程ID
	 * @return the sTATUS
	 */
	@Column(name="STATUS",length=32)
	public Integer getStatus() {
		return status;
	}
	/**
	 * set 流程状态
	 * @param sTATUS the sTATUS to set
	 */
	public void setStatus(Integer sTATUS) {
		status = sTATUS;
	}
	
	/**
	 * get 流程ID
	 * @return the procId
	 */
	@Column(name="PROC_ID",length=32)
	public String getProcId() {
		return procId;
	}
	/**
	 * set 流程ID
	 * @param procId the procId to set
	 */
	public void setProcId(String procId) {
		this.procId = procId;
	}

	/**
	 * @return the telePhone
	 */
	@Column(name="TELE_PHONE",length=128)
	public String getTelePhone() {
		return telePhone;
	}

	/**
	 * @param telePhone the telePhone to set
	 */
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}
	
	
	
				
}
