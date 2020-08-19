package com.supporter.prj.ppm.prj_org.dev_org.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BasePrjDeOrgDept  implements java.io.Serializable {


    // Fields    
	private static final long serialVersionUID = 1L;
	// Fields

    private String deOrgId;
    private String prjId;
    private String deptId;
    private String deptName;
    private String deptLevelId;
    private String deptLevelName;
    private Long levelNumber;


   // Constructors

   /** default constructor */
   public BasePrjDeOrgDept() {
   }

   
   /** full constructor */
   public BasePrjDeOrgDept(String prjId, String deptId, String deptName, String deptLevelId, String deptLevelName, Long levelNumber) {
       this.prjId = prjId;
       this.deptId = deptId;
       this.deptName = deptName;
       this.deptLevelId = deptLevelId;
       this.deptLevelName = deptLevelName;
       this.levelNumber = levelNumber;
   }

  
   // Property accessors
   @Id
   
   @Column(name="DE_ORG_ID", unique=true, nullable=false, length=32)

   public String getDeOrgId() {
       return this.deOrgId;
   }
   
   public void setDeOrgId(String deOrgId) {
       this.deOrgId = deOrgId;
   }
   
   @Column(name="PRJ_ID", length=32)

   public String getPrjId() {
       return this.prjId;
   }
   
   public void setPrjId(String prjId) {
       this.prjId = prjId;
   }
   
   @Column(name="DEPT_ID", length=32)

   public String getDeptId() {
       return this.deptId;
   }
   
   public void setDeptId(String deptId) {
       this.deptId = deptId;
   }
   
   @Column(name="DEPT_NAME", length=128)

   public String getDeptName() {
       return this.deptName;
   }
   
   public void setDeptName(String deptName) {
       this.deptName = deptName;
   }
   
   @Column(name="DEPT_LEVEL_ID", length=32)

   public String getDeptLevelId() {
       return this.deptLevelId;
   }
   
   public void setDeptLevelId(String deptLevelId) {
       this.deptLevelId = deptLevelId;
   }
   
   @Column(name="DEPT_LEVEL_NAME", length=64)

   public String getDeptLevelName() {
       return this.deptLevelName;
   }
   
   public void setDeptLevelName(String deptLevelName) {
       this.deptLevelName = deptLevelName;
   }
   
   @Column(name="LEVEL_NUMBER", precision=11, scale=0)

   public Long getLevelNumber() {
       return this.levelNumber;
   }
   
   public void setLevelNumber(Long levelNumber) {
       this.levelNumber = levelNumber;
   }
    
 
   
}