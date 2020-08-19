/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.leave.entiyt.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 请销假管理 - 请假单  表单数据模型 
 * @author YUEYUN
 * @date 2019年7月12日
 *
 */
@MappedSuperclass
public class LeaveBaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LEAVE_ID", nullable=false, length=32)
	private String leaveId;						//LEAVE_ID	VARCHAR2(32)	主键ID
	
	@Column(name="Leave_person_id",length=32)
	private String leavePersonId; 				//Leave_person_id	VARCHAR2(32)	请假人ID
	
	@Column(name="Leave_person_name",length=64)
	private String leavePersonName;  			//Leave_person_name	VARCHAR2(64)	请假人名称
	
	@Column(name="DEPT_ID",length=32)
	private String deptId;						//DEPT_ID	VARCHAR2(32)	部门ID
	
	@Column(name="DEPT_NAME",length=128)
	private String deptName;					//DEPT_NAME	VARCHAR2(128)	部门名称
	
	@Column(name="LEAVE_TYPE_CODE")
	private int leaveTypeCode;					//LEAVE_TYPE_CODE	NUMBER	请假类别CODE
	
	@Column(name="LEAVE_TYPE",length=64)
	private String leaveType;					//LEAVE_TYPE	VARCHAR2(64)	请假类别
	
	@Column(name="START_TIME",length=32)
	private String startTime;					//START_TIME	VARCHAR2(32)	请假开始时间
	
	@Column(name="END_TIME",length=32)
	private String endTime;						//END_TIME	VARCHAR2(32)	请假结束时间
	
	@Column(name="LEAVE_TIME")
	private String leaveTime;  					//LEAVE_TIME	VARCHAR2(64)	请假总天数
	
	@Column(name="TRAVEL_LEAVE")
	private String travelLeave;					//TRAVEL_LEAVE	VARCHAR2(64)	路程假天数
	
	@Column(name="VACATION_ADDRESS",length=256)
	private String vacationAddress;				 //VACATION_ADDRESS	VARCHAR2(256)	休假地址
	
	@Column(name="IS_SELLING_OFF")
	private int isSellingOff;					//IS_SELLING_OFF	NUMBER	是否销假（0：未销假，1：已销假）默认未销假
	
	@Column(name="STATUS")
	private int status;							//STATUS	NUMBER	审批状态（0：草稿，1：审批中，2：审批完成）
	
	@Column(name="PROC_ID",length=32)
	private String procId;						//PROC_ID	VARCHAR2(32)	流程ID
	
	@Column(name="CREATED_BY_ID",length=32)
	private String createById;					//CREATED_BY_ID	VARCHAR2(32)	创建人ID
	
	@Column(name="CREATED_BY",length=64)
	private String createBy;					//CREATED_BY	VARCHAR2(64)	创建人名称
	
	@Column(name="CREATED_DATE",length=32)
	private String createdDate;					//CREATED_DATE	VARCHAR2(32)	创建时间
	
	@Column(name="MODIFIED_BY_ID",length=32)
	private String modifiedById;				//MODIFIED_BY_ID	VARCHAR2(32)	修改人ID
	
	@Column(name="MODIFIED_BY",length=64)
	private String modifiedBy;					//MODIFIED_BY	VARCHAR2(64)	修改人名称
	
	@Column(name="MODIFIED_DATE",length=32)
	private String modifiedDate;				//MODIFIED_DATE	VARCHAR2(32) 修改时间
	
	@Column(name="CORRELATIONS")
	private String correlations;					//CORRELATIONS	NUMBER 与被探亲人关系（父母、配偶）
	
	@Column(name="MARITAL_STATUS")
	private String maritalStatus;					//MARITAL_STATUS	NUMBER 申请人婚姻状况（已婚、未婚）
	
	@Column(name="VISITED_RELATIVES_ADDRESS",length=256)
	private String visitedRelativesAddress;		//VISITED_RELATIVES_ADDRESS	VARCHAR2(32) 被探亲人居住地址
	
	@Column(name="LAST_TIME",length=32)
	private String lastTime;					//LAST_TIME	VARCHAR2(32) 上次探亲假时间
	
	
	public LeaveBaseEntity() {}

	public LeaveBaseEntity(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getLeavePersonId() {
		return leavePersonId;
	}

	public void setLeavePersonId(String leavePersonId) {
		this.leavePersonId = leavePersonId;
	}

	public String getLeavePersonName() {
		return leavePersonName;
	}

	public void setLeavePersonName(String leavePersonName) {
		this.leavePersonName = leavePersonName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getLeaveTypeCode() {
		return leaveTypeCode;
	}

	public void setLeaveTypeCode(int leaveTypeCode) {
		this.leaveTypeCode = leaveTypeCode;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getTravelLeave() {
		return travelLeave;
	}

	public void setTravelLeave(String travelLeave) {
		this.travelLeave = travelLeave;
	}

	public String getVacationAddress() {
		return vacationAddress;
	}

	public void setVacationAddress(String vacationAddress) {
		this.vacationAddress = vacationAddress;
	}

	public int getIsSellingOff() {
		return isSellingOff;
	}

	public void setIsSellingOff(int isSellingOff) {
		this.isSellingOff = isSellingOff;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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

	public String getCorrelations() {
		return correlations;
	}

	public void setCorrelations(String correlations) {
		this.correlations = correlations;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getVisitedRelativesAddress() {
		return visitedRelativesAddress;
	}

	public void setVisitedRelativesAddress(String visitedRelativesAddress) {
		this.visitedRelativesAddress = visitedRelativesAddress;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	

}
