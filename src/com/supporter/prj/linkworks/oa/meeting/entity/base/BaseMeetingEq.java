package com.supporter.prj.linkworks.oa.meeting.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseMeetingEq implements java.io.Serializable {

    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recordId;
     private String meetingId;
     private String equipmentId;
     private String equipmentName;


    // Constructors

    /** default constructor */
    public BaseMeetingEq() {
    }

	/** minimal constructor */
    public BaseMeetingEq(String recordId) {
        this.recordId = recordId;
    }
    
    /** full constructor */
    public BaseMeetingEq(String recordId, String meetingId, String equipmentId, String equipmentName) {
        this.recordId = recordId;
        this.meetingId = meetingId;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="RECORD_ID", unique=true, nullable=false, length=32)

    public String getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    @Column(name="MEETING_ID", length=32)

    public String getMeetingId() {
        return this.meetingId;
    }
    
    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
    
    @Column(name="EQUIPMENT_ID", length=32)

    public String getEquipmentId() {
        return this.equipmentId;
    }
    
    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }
    
    @Column(name="EQUIPMENT_NAME", length=32)

    public String getEquipmentName() {
        return this.equipmentName;
    }
    
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
   

}
