package com.supporter.prj.linkworks.oa.meeting_room.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BaseMeetingRoomType  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String typeId;
     private String typeName;
     private String typeAbbr;
     private Long displayOrder;


    // Constructors

    /** default constructor */
    public BaseMeetingRoomType() {
    }

	/** minimal constructor */
    public BaseMeetingRoomType(String typeId) {
        this.typeId = typeId;
    }
    
    /** full constructor */
    public BaseMeetingRoomType(String typeId, String typeName, String typeAbbr, Long displayOrder) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeAbbr = typeAbbr;
        this.displayOrder = displayOrder;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="TYPE_ID", unique=true, nullable=false, length=32)

    public String getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    
    @Column(name="TYPE_NAME", length=64)

    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    @Column(name="TYPE_ABBR", length=32)

    public String getTypeAbbr() {
        return this.typeAbbr;
    }
    
    public void setTypeAbbr(String typeAbbr) {
        this.typeAbbr = typeAbbr;
    }
    
    @Column(name="DISPLAY_ORDER", precision=22, scale=0)

    public Long getDisplayOrder() {
        return this.displayOrder;
    }
    
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }
   








}