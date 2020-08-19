package com.supporter.prj.linkworks.oa.meeting.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseMeeting implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String meetingId;
     private String meetingName;//会议主题
     private int meetingType;//会议类型
     private String moderatorId;
     private String moderatorName;
     private String deptId;
     private String deptName;
     private String expStartDate;//会议开始时间
     private String expEndDate;
     private String actStartDate;
     private String actEndDate;
     private String meetingRoomId;
     private String meetingRoom;
     private Long meetingStatus;
     private String createdBy;
     private String createdDate;
     private String modifiedDate;
     private String modifiedBy;
     private String createdById;
     private String modifiedById;
     private String periodPoints;//时间段
     private String meetingDesc;//会议说明
     private String noticeModes;//通知方式
     private String linkmanPhone;//联系电话


    // Constructors

    /** default constructor */
    public BaseMeeting() {
    }

	/** minimal constructor */
    public BaseMeeting(String meetingId) {
        this.meetingId = meetingId;
    }
    
    /** full constructor */
    public BaseMeeting(String meetingId, String meetingName, int meetingType, String moderatorId, String moderatorName, String deptId, String deptName, String expStartDate, String expEndDate, String actStartDate, String actEndDate, String meetingRoomId, String meetingRoom, Long meetingStatus, String createdBy, String createdDate, String modifiedDate, String modifiedBy, String createdById, String modifiedById, String periodPoints, String meetingDesc, String noticeModes, String linkmanPhone) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingType = meetingType;
        this.moderatorId = moderatorId;
        this.moderatorName = moderatorName;
        this.deptId = deptId;
        this.deptName = deptName;
        this.expStartDate = expStartDate;
        this.expEndDate = expEndDate;
        this.actStartDate = actStartDate;
        this.actEndDate = actEndDate;
        this.meetingRoomId = meetingRoomId;
        this.meetingRoom = meetingRoom;
        this.meetingStatus = meetingStatus;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
        this.createdById = createdById;
        this.modifiedById = modifiedById;
        this.periodPoints = periodPoints;
        this.meetingDesc = meetingDesc;
        this.noticeModes = noticeModes;
        this.linkmanPhone = linkmanPhone;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MEETING_ID", unique=true, nullable=false, length=32)

    public String getMeetingId() {
        return this.meetingId;
    }
    
    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
    
    @Column(name="MEETING_NAME", length=128)

    public String getMeetingName() {
        return this.meetingName;
    }
    
    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }
    
    @Column(name="MEETING_TYPE", length=32)

    public int getMeetingType() {
        return this.meetingType;
    }
    
    public void setMeetingType(int meetingType) {
        this.meetingType = meetingType;
    }
    
    @Column(name="MODERATOR_ID", length=32)

    public String getModeratorId() {
        return this.moderatorId;
    }
    
    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
    }
    
    @Column(name="MODERATOR_NAME", length=64)

    public String getModeratorName() {
        return this.moderatorName;
    }
    
    public void setModeratorName(String moderatorName) {
        this.moderatorName = moderatorName;
    }
    
    @Column(name="DEPT_ID", length=32)

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
    @Column(name="DEPT_NAME", length=64)

    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    @Column(name="EXP_START_DATE", length=27)

    public String getExpStartDate() {
        return this.expStartDate;
    }
    
    public void setExpStartDate(String expStartDate) {
        this.expStartDate = expStartDate;
    }
    
    @Column(name="EXP_END_DATE", length=27)

    public String getExpEndDate() {
        return this.expEndDate;
    }
    
    public void setExpEndDate(String expEndDate) {
        this.expEndDate = expEndDate;
    }
    
    @Column(name="ACT_START_DATE", length=27)

    public String getActStartDate() {
        return this.actStartDate;
    }
    
    public void setActStartDate(String actStartDate) {
        this.actStartDate = actStartDate;
    }
    
    @Column(name="ACT_END_DATE", length=27)

    public String getActEndDate() {
        return this.actEndDate;
    }
    
    public void setActEndDate(String actEndDate) {
        this.actEndDate = actEndDate;
    }
    
    @Column(name="MEETING_ROOM_ID", length=32)

    public String getMeetingRoomId() {
        return this.meetingRoomId;
    }
    
    public void setMeetingRoomId(String meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }
    
    @Column(name="MEETING_ROOM")

    public String getMeetingRoom() {
        return this.meetingRoom;
    }
    
    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }
    
    @Column(name="MEETING_STATUS", precision=22, scale=0)

    public Long getMeetingStatus() {
        return this.meetingStatus;
    }
    
    public void setMeetingStatus(Long meetingStatus) {
        this.meetingStatus = meetingStatus;
    }
    
    @Column(name="CREATED_BY", length=32)

    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    @Column(name="CREATED_DATE", length=27)

    public String getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    
    @Column(name="MODIFIED_DATE", length=27)

    public String getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    @Column(name="MODIFIED_BY", length=32)

    public String getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    @Column(name="CREATED_BY_ID", length=32)

    public String getCreatedById() {
        return this.createdById;
    }
    
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }
    
    @Column(name="MODIFIED_BY_ID", length=32)

    public String getModifiedById() {
        return this.modifiedById;
    }
    
    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }
    
    @Column(name="PERIOD_POINTS", length=256)

    public String getPeriodPoints() {
        return this.periodPoints;
    }
    
    public void setPeriodPoints(String periodPoints) {
        this.periodPoints = periodPoints;
    }
    
    @Column(name="MEETING_DESC")

    public String getMeetingDesc() {
        return this.meetingDesc;
    }
    
    public void setMeetingDesc(String meetingDesc) {
        this.meetingDesc = meetingDesc;
    }
    
    @Column(name="NOTICE_MODES", length=32)

    public String getNoticeModes() {
        return this.noticeModes;
    }
    
    public void setNoticeModes(String noticeModes) {
        this.noticeModes = noticeModes;
    }
    
    @Column(name="LINKMAN_PHONE", length=32)

    public String getLinkmanPhone() {
        return this.linkmanPhone;
    }
    
    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }
}
