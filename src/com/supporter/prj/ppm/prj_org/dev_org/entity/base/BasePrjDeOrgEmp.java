package com.supporter.prj.ppm.prj_org.dev_org.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * ContractPay entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePrjDeOrgEmp  implements java.io.Serializable {

    // Fields    
	private static final long serialVersionUID = 1L;
    private String deOrgEmpId;
    private String empId;
    private String empName;
    private String empRoleId;
    private String empRoleName;
    private String prjId;
    private Long displayOrder;

   // Constructors
   /** default constructor */
   public BasePrjDeOrgEmp() {
   }
   
   /** full constructor */
   public BasePrjDeOrgEmp(String empId, String empName, String empRoleId, String empRoleName, String prjId, Long displayOrder) {
       this.empId = empId;
       this.empName = empName;
       this.empRoleId = empRoleId;
       this.empRoleName = empRoleName;
       this.prjId = prjId;
       this.displayOrder = displayOrder;
   }

  
   // Property accessors
  @Id
   @Column(name="DE_ORG_EMP_ID", unique=true, nullable=false, length=32)

   public String getDeOrgEmpId() {
       return this.deOrgEmpId;
   }
   
   public void setDeOrgEmpId(String deOrgEmpId) {
       this.deOrgEmpId = deOrgEmpId;
   }
   
   @Column(name="EMP_ID", length=32)

   public String getEmpId() {
       return this.empId;
   }
   
   public void setEmpId(String empId) {
       this.empId = empId;
   }
   
   @Column(name="EMP_NAME", length=64)

   public String getEmpName() {
       return this.empName;
   }
   
   public void setEmpName(String empName) {
       this.empName = empName;
   }
   
   @Column(name="EMP_ROLE_ID", length=32)

   public String getEmpRoleId() {
       return this.empRoleId;
   }
   
   public void setEmpRoleId(String empRoleId) {
       this.empRoleId = empRoleId;
   }
   
   @Column(name="EMP_ROLE_NAME", length=128)

   public String getEmpRoleName() {
       return this.empRoleName;
   }
   
   public void setEmpRoleName(String empRoleName) {
       this.empRoleName = empRoleName;
   }
   
   @Column(name="PRJ_ID", length=32)

   public String getPrjId() {
       return this.prjId;
   }
   
   public void setPrjId(String prjId) {
       this.prjId = prjId;
   }
   
   @Column(name="DISPLAY_ORDER", precision=11, scale=0)

   public Long getDisplayOrder() {
       return this.displayOrder;
   }
   
   public void setDisplayOrder(Long displayOrder) {
       this.displayOrder = displayOrder;
   }
    
   
}