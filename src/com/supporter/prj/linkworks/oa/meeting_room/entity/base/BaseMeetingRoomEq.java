package com.supporter.prj.linkworks.oa.meeting_room.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



@MappedSuperclass
public class BaseMeetingRoomEq  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String equipmentId;
     private String equipmentName;
     private String equipmentDesc;
     private Long equipmentStatus;
     private String meetingRoomId;
     private Long displayOrder;


    // Constructors

    /** default constructor */
    public BaseMeetingRoomEq() {
    }

	/** minimal constructor */
    public BaseMeetingRoomEq(String equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    /** full constructor */
    public BaseMeetingRoomEq(String equipmentId, String equipmentName, String equipmentDesc, Long equipmentStatus, String meetingRoomId, Long displayOrder) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentDesc = equipmentDesc;
        this.equipmentStatus = equipmentStatus;
        this.meetingRoomId = meetingRoomId;
        this.displayOrder = displayOrder;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="EQUIPMENT_ID", unique=true, nullable=false, length=32)

    public String getEquipmentId() {
        return this.equipmentId;
    }
    
    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    @Column(name="EQUIPMENT_NAME", length=64)

    public String getEquipmentName() {
        return this.equipmentName;
    }
    
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    
    @Column(name="EQUIPMENT_DESC")

    public String getEquipmentDesc() {
        return this.equipmentDesc;
    }
    
    public void setEquipmentDesc(String equipmentDesc) {
        this.equipmentDesc = equipmentDesc;
    }
    
    @Column(name="EQUIPMENT_STATUS", precision=22, scale=0)

    public Long getEquipmentStatus() {
        return this.equipmentStatus;
    }
    
    public void setEquipmentStatus(Long equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }
    
    @Column(name="MEETING_ROOM_ID", length=32)

    public String getMeetingRoomId() {
        return this.meetingRoomId;
    }
    
    public void setMeetingRoomId(String meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }
    
    @Column(name="DISPLAY_ORDER", precision=22, scale=0)

    public Long getDisplayOrder() {
        return this.displayOrder;
    }
    
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }
   








}
