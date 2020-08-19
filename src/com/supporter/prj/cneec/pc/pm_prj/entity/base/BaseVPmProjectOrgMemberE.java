package com.supporter.prj.cneec.pc.pm_prj.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseVPmProjectOrgMemberE implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectId;
     private String projectName;
     private String personId;


    // Constructors

    /** default constructor */
    public BaseVPmProjectOrgMemberE() {
    }

    
    /** full constructor */
    public BaseVPmProjectOrgMemberE(String projectId, String projectName, String personId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.personId = personId;
    }

   
    // Property accessors
    @Id
    @Column(name="PROJECT_ID", length=60)

    public String getProjectId() {
        return this.projectId;
    }
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Column(name="PROJECT_NAME", length=384)

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name="PERSON_ID", length=60)
    public String getPersonId() {
        return this.personId;
    }
    
    public void setPersonId(String personId) {
        this.personId = personId;
    }
   


}
