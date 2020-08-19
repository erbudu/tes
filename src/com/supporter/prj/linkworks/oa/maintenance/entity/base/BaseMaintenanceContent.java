package com.supporter.prj.linkworks.oa.maintenance.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseMaintenanceContent implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String maintenanceId;
     private String problemDescription;


    // Constructors

    /** default constructor */
    public BaseMaintenanceContent() {
    }

	/** minimal constructor */
    public BaseMaintenanceContent(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }
    
    /** full constructor */
    public BaseMaintenanceContent(String maintenanceId, String problemDescription) {
        this.maintenanceId = maintenanceId;
        this.problemDescription = problemDescription;
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
    
    @Column(name="PROBLEM_DESCRIPTION")

    public String getProblemDescription() {
        return this.problemDescription;
    }
    
    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }
   
}
