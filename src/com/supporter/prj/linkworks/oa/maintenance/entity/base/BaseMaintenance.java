package com.supporter.prj.linkworks.oa.maintenance.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseMaintenance implements java.io.Serializable{
	   // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maintenanceId;
    private String title;
    private String maintenanceNo;
    private String fileName;
    private String maintenanceProperties;
    private String solutions;
    private String deptId;
    private Long swfStatus;
    private String createdBy;
    private String createdById;
    private String createdDate;
    private String modifiedBy;
    private String modifiedById;
    private String modifiedDate;
    private Long modifiedContentCode;
    private String modifiedContent;
    private String swfInnerName;
    private String swfName;
    private Long prjId;
    private String prjName;
    private String prjDeptName;
    private String phoneCode;   
    private String procId;
	private boolean history;
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
	public boolean getHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}


   // Constructors


/** default constructor */
   public BaseMaintenance() {
   }

	/** minimal constructor */
   public BaseMaintenance(String maintenanceId) {
       this.maintenanceId = maintenanceId;
   }
   
   /** full constructor */
   public BaseMaintenance(String maintenanceId, String title, String maintenanceNo, String fileName, String maintenanceProperties, String solutions, String deptId, Long swfStatus, String createdBy, String createdById, String createdDate, String modifiedBy, String modifiedById, String modifiedDate, Long modifiedContentCode, String modifiedContent, String swfInnerName, String swfName, Long prjId, String prjName, String prjDeptName, String phoneCode,String procId) {
       this.maintenanceId = maintenanceId;
       this.title = title;
       this.maintenanceNo = maintenanceNo;
       this.fileName = fileName;
       this.maintenanceProperties = maintenanceProperties;
       this.solutions = solutions;
       this.deptId = deptId;
       this.swfStatus = swfStatus;
       this.createdBy = createdBy;
       this.createdById = createdById;
       this.createdDate = createdDate;
       this.modifiedBy = modifiedBy;
       this.modifiedById = modifiedById;
       this.modifiedDate = modifiedDate;
       this.modifiedContentCode = modifiedContentCode;
       this.modifiedContent = modifiedContent;
       this.swfInnerName = swfInnerName;
       this.swfName = swfName;
       this.prjId = prjId;
       this.prjName = prjName;
       this.prjDeptName = prjDeptName;
       this.phoneCode = phoneCode;
       this.procId = procId;
   }

  
   // Property accessors
   @Id 
   
   @Column(name="MAINTENANCE_ID", unique=true, nullable=false, length=32)

   public String getMaintenanceId() {
       return this.maintenanceId;
   }
   
   public void setMaintenanceId(String maintenanceId) {
       this.maintenanceId = maintenanceId;
   }
   
   @Column(name="TITLE", length=256)

   public String getTitle() {
       return this.title;
   }
   
   public void setTitle(String title) {
       this.title = title;
   }
   
   @Column(name="MAINTENANCE_NO", length=32)

   public String getMaintenanceNo() {
       return this.maintenanceNo;
   }
   
   public void setMaintenanceNo(String maintenanceNo) {
       this.maintenanceNo = maintenanceNo;
   }
   
   @Column(name="FILE_NAME", length=512)

   public String getFileName() {
       return this.fileName;
   }
   
   public void setFileName(String fileName) {
       this.fileName = fileName;
   }
   
   @Column(name="MAINTENANCE_PROPERTIES", length=256)

   public String getMaintenanceProperties() {
       return this.maintenanceProperties;
   }
   
   public void setMaintenanceProperties(String maintenanceProperties) {
       this.maintenanceProperties = maintenanceProperties;
   }
   
   @Column(name="SOLUTIONS", length=1024)

   public String getSolutions() {
       return this.solutions;
   }
   
   public void setSolutions(String solutions) {
       this.solutions = solutions;
   }
   
   @Column(name="DEPT_ID", length=32)

   public String getDeptId() {
       return this.deptId;
   }
   
   public void setDeptId(String deptId) {
       this.deptId = deptId;
   }
   
   @Column(name="SWF_STATUS", precision=22, scale=0)

   public Long getSwfStatus() {
       return this.swfStatus;
   }
   
   public void setSwfStatus(Long swfStatus) {
       this.swfStatus = swfStatus;
   }
   
   @Column(name="CREATED_BY", length=32)

   public String getCreatedBy() {
       return this.createdBy;
   }
   
   public void setCreatedBy(String createdBy) {
       this.createdBy = createdBy;
   }
   
   @Column(name="CREATED_BY_ID", length=32)

   public String getCreatedById() {
       return this.createdById;
   }
   
   public void setCreatedById(String createdById) {
       this.createdById = createdById;
   }
   
   @Column(name="CREATED_DATE", length=32)

   public String getCreatedDate() {
       return this.createdDate;
   }
   
   public void setCreatedDate(String createdDate) {
       this.createdDate = createdDate;
   }
   
   @Column(name="MODIFIED_BY", length=32)

   public String getModifiedBy() {
       return this.modifiedBy;
   }
   
   public void setModifiedBy(String modifiedBy) {
       this.modifiedBy = modifiedBy;
   }
   
   @Column(name="MODIFIED_BY_ID", length=32)

   public String getModifiedById() {
       return this.modifiedById;
   }
   
   public void setModifiedById(String modifiedById) {
       this.modifiedById = modifiedById;
   }
   
   @Column(name="MODIFIED_DATE", length=32)

   public String getModifiedDate() {
       return this.modifiedDate;
   }
   
   public void setModifiedDate(String modifiedDate) {
       this.modifiedDate = modifiedDate;
   }
   
   @Column(name="MODIFIED_CONTENT_CODE", precision=22, scale=0)

   public Long getModifiedContentCode() {
       return this.modifiedContentCode;
   }
   
   public void setModifiedContentCode(Long modifiedContentCode) {
       this.modifiedContentCode = modifiedContentCode;
   }
   
   @Column(name="MODIFIED_CONTENT", length=32)

   public String getModifiedContent() {
       return this.modifiedContent;
   }
   
   public void setModifiedContent(String modifiedContent) {
       this.modifiedContent = modifiedContent;
   }
   
   @Column(name="SWF_INNER_NAME")

   public String getSwfInnerName() {
       return this.swfInnerName;
   }
   
   public void setSwfInnerName(String swfInnerName) {
       this.swfInnerName = swfInnerName;
   }
   
   @Column(name="SWF_NAME")

   public String getSwfName() {
       return this.swfName;
   }
   
   public void setSwfName(String swfName) {
       this.swfName = swfName;
   }
   
   @Column(name="PRJ_ID", precision=22, scale=0)

   public Long getPrjId() {
       return this.prjId;
   }
   
   public void setPrjId(Long prjId) {
       this.prjId = prjId;
   }
   
   @Column(name="PRJ_NAME", length=128)

   public String getPrjName() {
       return this.prjName;
   }
   
   public void setPrjName(String prjName) {
       this.prjName = prjName;
   }
   
   @Column(name="PRJ_DEPT_NAME", length=128)

   public String getPrjDeptName() {
       return this.prjDeptName;
   }
   
   public void setPrjDeptName(String prjDeptName) {
       this.prjDeptName = prjDeptName;
   }
   
   @Column(name="PHONE_CODE", length=32)

   public String getPhoneCode() {
       return this.phoneCode;
   }
   
   public void setPhoneCode(String phoneCode) {
       this.phoneCode = phoneCode;
   }
   
   @Column(name="PROC_ID", length=32)
   public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
 

}
