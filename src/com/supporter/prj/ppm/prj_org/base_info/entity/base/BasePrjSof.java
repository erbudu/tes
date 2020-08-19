package com.supporter.prj.ppm.prj_org.base_info.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BasePrjSof  implements java.io.Serializable {


	// Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sofId;
    private String projectId;
    private String sofTypeId;
    private String sofType;
    private String ratio;


   // Constructors

   /** default constructor */
   public BasePrjSof() {
   }

   
   /** full constructor */
   public BasePrjSof(String projectId, String sofTypeId, String sofType, String ratio) {
       this.projectId = projectId;
       this.sofTypeId = sofTypeId;
       this.sofType = sofType;
       this.ratio = ratio;
   }

  
   // Property accessors
   @Id
   
   @Column(name="SOF_ID", unique=true, nullable=false, length=32)

   public String getSofId() {
       return this.sofId;
   }
   
   public void setSofId(String sofId) {
       this.sofId = sofId;
   }
   
   @Column(name="PROJECT_ID", length=32)

   public String getProjectId() {
       return this.projectId;
   }
   
   public void setProjectId(String projectId) {
       this.projectId = projectId;
   }
   
   @Column(name="SOF_TYPE_ID", length=32)

   public String getSofTypeId() {
       return this.sofTypeId;
   }
   
   public void setSofTypeId(String sofTypeId) {
       this.sofTypeId = sofTypeId;
   }
   
   @Column(name="SOF_TYPE", length=128)

   public String getSofType() {
       return this.sofType;
   }
   
   public void setSofType(String sofType) {
       this.sofType = sofType;
   }
   
   @Column(name="RATIO", length=18)

   public String getRatio() {
       return this.ratio;
   }
   
   public void setRatio(String ratio) {
       this.ratio = ratio;
   }
 
   
}