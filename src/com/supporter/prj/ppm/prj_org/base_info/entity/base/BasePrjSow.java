package com.supporter.prj.ppm.prj_org.base_info.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BasePrjSow  implements java.io.Serializable {


	// Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sowId;
    private String prjId;
    private String prjSowContent;


   // Constructors

   /** default constructor */
   public BasePrjSow() {
   }

   
   /** full constructor */
   public BasePrjSow(String prjId, String prjSowContent) {
       this.prjId = prjId;
       this.prjSowContent = prjSowContent;
   }

  
   // Property accessors
   @Id
   
   @Column(name="SOW_ID", unique=true, nullable=false, length=32)

   public String getSowId() {
       return this.sowId;
   }
   
   public void setSowId(String sowId) {
       this.sowId = sowId;
   }
   
   @Column(name="PRJ_ID", length=32)

   public String getPrjId() {
       return this.prjId;
   }
   
   public void setPrjId(String prjId) {
       this.prjId = prjId;
   }
   
   @Column(name="PRJ_SOW_CONTENT", length=256)

   public String getPrjSowContent() {
       return this.prjSowContent;
   }
   
   public void setPrjSowContent(String prjSowContent) {
       this.prjSowContent = prjSowContent;
   }
    
 
   
}