package com.supporter.prj.linkworks.oa.meeting_room.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



@MappedSuperclass
public class BaseMeetingRoom  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roomId;
     private String roomName;
     private String roomDesc;
     private String deptId;
     private String deptName;
     private Long displayOrder;
     private String isControl;
     private String notityPartyIds;
     private String notityPartyNames;


    // Constructors

    /** default constructor */
    public BaseMeetingRoom() {
    }

	/** minimal constructor */
    public BaseMeetingRoom(String roomId) {
        this.roomId = roomId;
    }
    
    /** full constructor */
    public BaseMeetingRoom(String roomId, String roomName, String roomDesc, String deptId, String deptName, Long displayOrder, String isControl, String notityPartyIds, String notityPartyNames) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDesc = roomDesc;
        this.deptId = deptId;
        this.deptName = deptName;
        this.displayOrder = displayOrder;
        this.isControl = isControl;
        this.notityPartyIds = notityPartyIds;
        this.notityPartyNames = notityPartyNames;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ROOM_ID", unique=true, nullable=false, length=32)

    public String getRoomId() {
        return this.roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    @Column(name="ROOM_NAME", length=128)

    public String getRoomName() {
        return this.roomName;
    }
    
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    
    @Column(name="ROOM_DESC")

    public String getRoomDesc() {
        return this.roomDesc;
    }
    
    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }
    
    @Column(name="DEPT_ID", length=20)

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
    
    @Column(name="DISPLAY_ORDER", precision=22, scale=0)

    public Long getDisplayOrder() {
        return this.displayOrder;
    }
    
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    @Column(name="IS_CONTROL", length=1)

    public String getIsControl() {
        return this.isControl;
    }
    
    public void setIsControl(String isControl) {
        this.isControl = isControl;
    }
    
    @Column(name="NOTIFY_PARTY_IDS", length=1024)

	public String getNotityPartyIds() {
		return notityPartyIds;
	}

	public void setNotityPartyIds(String notityPartyIds) {
		this.notityPartyIds = notityPartyIds;
	}
    
    @Column(name="NOTIFY_PARTY_NAMES", length=1024)

	public String getNotityPartyNames() {
		return notityPartyNames;
	}

	public void setNotityPartyNames(String notityPartyNames) {
		this.notityPartyNames = notityPartyNames;
	}

   








}
