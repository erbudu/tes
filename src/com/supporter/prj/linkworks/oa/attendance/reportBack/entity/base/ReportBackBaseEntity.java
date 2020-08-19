/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.reportBack.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @discription 销假单-业务数据模型实体类
 * @author YUEYUN
 * @date 2019年7月19日
 * 
 */
@MappedSuperclass
public class ReportBackBaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REPORT_BACK_ID", nullable=false, length=32)
	private String reportBackId;    //REPORT_BACK_ID	VARCHAR2(32)	主键ID
	
	@Column(name="LEAVE_ID",length=32)
	private String leaveId;			//LEAVE_ID	VARCHAR2(32)	请假单ID
	
	@Column(name="REPORT_BACK_TIME",length=32)
	private String reportBackTime;	//REPORT_BACK_TIME	VARCHAR2(32)	销假时间
	
	@Column(name="REMARKS",length=1024)
	private String remark;			//REMARKS	VARCHAR2(1024)	备注
	
	@Column(name="STATUS")
	private int status;				//STATUS	NUMBER	审批状态（0：草稿，1：审批中，2：审批完成）
	
	@Column(name="PROC_ID",length=32)
	private String procId;			//PROC_ID	VARCHAR2(32)	流程ID
	
	@Column(name="CREATED_BY_ID",length=32)
	private String createById;		//CREATED_BY_ID	VARCHAR2(32)	创建人（销假人）ID
	
	@Column(name="CREATED_BY",length=64)
	private String createBy;		//CREATED_BY	VARCHAR2(64)	创建人（销假人）名称
	
	@Column(name="CREATED_DATE",length=32)
	private String createDate;		//CREATED_DATE	VARCHAR2(32)	创建时间
	
	@Column(name="MODIFIED_BY_ID",length=32)
	private String modifiedById;	//MODIFIED_BY_ID	VARCHAR2(32)	修改人ID
	
	@Column(name="MODIFIED_BY",length=64)
	private String modifiedBy;		//MODIFIED_BY	VARCHAR2(64)	修改人名称
	
	@Column(name="MODIFIED_DATE",length=32)
	private String modifiedDate;	//MODIFIED_DATE	VARCHAR2(32)	修改时间

	public ReportBackBaseEntity() {
		super();
	}
	
	public ReportBackBaseEntity(String reportBackId) {
		this.reportBackId = reportBackId;
	}

	public String getReportBackId() {
		return reportBackId;
	}

	public void setReportBackId(String reportBackId) {
		this.reportBackId = reportBackId;
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getReportBackTime() {
		return reportBackTime;
	}

	public void setReportBackTime(String reportBackTime) {
		this.reportBackTime = reportBackTime;
	}

	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	

}
