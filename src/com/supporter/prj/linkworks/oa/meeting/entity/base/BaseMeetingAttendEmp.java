package com.supporter.prj.linkworks.oa.meeting.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseMeetingAttendEmp  implements java.io.Serializable{

    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attendId;
     private String meetingId;
     private String empId;
     private String empName;
     private Long isNotified;
     private Long confirmedStatus;
     private Long memberSource;
     private String fromDeptId;
     private String fromDeptName;
     private String alertMode;
     private Long actAttendStatus;
     private Long displayOrder;


    // Constructors

    /** default constructor */
    public BaseMeetingAttendEmp() {
    }

	/** minimal constructor */
    public BaseMeetingAttendEmp(String attendId) {
        this.attendId = attendId;
    }
    
    /** full constructor */
    public BaseMeetingAttendEmp(String attendId, String meetingId, String empId, String empName, Long isNotified, Long confirmedStatus, Long memberSource, String fromDeptId, String fromDeptName, String alertMode, Long actAttendStatus, Long displayOrder) {
        this.attendId = attendId;
        this.meetingId = meetingId;
        this.empId = empId;
        this.empName = empName;
        this.isNotified = isNotified;
        this.confirmedStatus = confirmedStatus;
        this.memberSource = memberSource;
        this.fromDeptId = fromDeptId;
        this.fromDeptName = fromDeptName;
        this.alertMode = alertMode;
        this.actAttendStatus = actAttendStatus;
        this.displayOrder = displayOrder;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ATTEND_ID", unique=true, nullable=false, length=32)

    public String getAttendId() {
        return this.attendId;
    }
    
    public void setAttendId(String attendId) {
        this.attendId = attendId;
    }
    
    @Column(name="MEETING_ID", length=32)

    public String getMeetingId() {
        return this.meetingId;
    }
    
    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
    
    @Column(name="EMP_ID", length=32)

    public String getEmpId() {
        return this.empId;
    }
    
    public void setEmpId(String empId) {
        this.empId = empId;
    }
    
    @Column(name="EMP_NAME", length=32)

    public String getEmpName() {
        return this.empName;
    }
    
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    
    @Column(name="IS_NOTIFIED", precision=22, scale=0)

    public Long getIsNotified() {
        return this.isNotified;
    }
    
    public void setIsNotified(Long isNotified) {
        this.isNotified = isNotified;
    }
    
    @Column(name="CONFIRMED_STATUS", precision=22, scale=0)

    public Long getConfirmedStatus() {
        return this.confirmedStatus;
    }
    
    public void setConfirmedStatus(Long confirmedStatus) {
        this.confirmedStatus = confirmedStatus;
    }
    
    @Column(name="MEMBER_SOURCE", precision=22, scale=0)

    public Long getMemberSource() {
        return this.memberSource;
    }
    
    public void setMemberSource(Long memberSource) {
        this.memberSource = memberSource;
    }
    
    @Column(name="FROM_DEPT_ID", length=32)

    public String getFromDeptId() {
        return this.fromDeptId;
    }
    
    public void setFromDeptId(String fromDeptId) {
        this.fromDeptId = fromDeptId;
    }
    
    @Column(name="FROM_DEPT_NAME", length=64)

    public String getFromDeptName() {
        return this.fromDeptName;
    }
    
    public void setFromDeptName(String fromDeptName) {
        this.fromDeptName = fromDeptName;
    }
    
    @Column(name="ALERT_MODE", length=64)

    public String getAlertMode() {
        return this.alertMode;
    }
    
    public void setAlertMode(String alertMode) {
        this.alertMode = alertMode;
    }
    
    @Column(name="ACT_ATTEND_STATUS", precision=22, scale=0)

    public Long getActAttendStatus() {
        return this.actAttendStatus;
    }
    
    public void setActAttendStatus(Long actAttendStatus) {
        this.actAttendStatus = actAttendStatus;
    }
    
    @Column(name="DISPLAY_ORDER", precision=22, scale=0)

    public Long getDisplayOrder() {
        return this.displayOrder;
    }
    
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }
   

}
