package com.supporter.prj.linkworks.oa.meeting.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseMeetingType implements java.io.Serializable {

	 // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeCode;
    private String typeName;
    private Long displayOrder;


   // Constructors

   /** default constructor */
   public BaseMeetingType() {
   }

	/** minimal constructor */
   public BaseMeetingType(String typeCode) {
       this.typeCode = typeCode;
   }
   
   /** full constructor */
   public BaseMeetingType(String typeCode, String typeName, Long displayOrder) {
       this.typeCode = typeCode;
       this.typeName = typeName;
       this.displayOrder = displayOrder;
   }

  
   // Property accessors
   @Id 
   
   @Column(name="TYPE_CODE", unique=true, nullable=false, length=20)

   public String getTypeCode() {
       return this.typeCode;
   }
   
   public void setTypeCode(String typeCode) {
       this.typeCode = typeCode;
   }
   
   @Column(name="TYPE_NAME", length=64)

   public String getTypeName() {
       return this.typeName;
   }
   
   public void setTypeName(String typeName) {
       this.typeName = typeName;
   }
   
   @Column(name="DISPLAY_ORDER", precision=22, scale=0)

   public Long getDisplayOrder() {
       return this.displayOrder;
   }
   
   public void setDisplayOrder(Long displayOrder) {
       this.displayOrder = displayOrder;
   }

}
