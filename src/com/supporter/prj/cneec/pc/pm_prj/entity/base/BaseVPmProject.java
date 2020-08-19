package com.supporter.prj.cneec.pc.pm_prj.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * BaseCneecVPcPmPrj entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass

public class BaseVPmProject  implements java.io.Serializable {



    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private String prjId;
	 
	 private String prjNo;
	 
	 private String deptId;
    
     private String deptName;
     
     private String prjName;
     
     private String prjManagerId;
     
     private String prjManager;
     

     private String ownerName;
     

     private String prjRank;
     
     private int prjStatusId;
     
     private String prjStatus;
     
     private String contractInNo;
     
     private String registerId;
     
     private String registerName;
     
     private String createdById;
     
     private String createdBy;

     private String prjCategory;

    // Constructors


	/** default constructor */
    public BaseVPmProject() {
    }

	/** minimal constructor */
    public BaseVPmProject(String prjId) {
        this.prjId = prjId;
    }
    
    /** full constructor */
    public BaseVPmProject(String prjId, String prjNo, String deptId,String deptName, String prjName, String prjManagerId,String prjManager,String ownerName,String prjRank, int prjStatusId,String prjStatus, String contractInNo,String registerId,
    		String registerName,String createdById,String createdBy,String prjCategory) {
        this.prjId = prjId;
        
        this.prjNo=prjNo;
        
        this.deptId=deptId;
        
        this.deptName = deptName;
        
        this.prjName = prjName;
        
        this.prjManagerId=prjManagerId;

        this.prjManager = prjManager;

        this.ownerName = ownerName;

        this.prjRank = prjRank;
        
        this.prjStatusId = prjStatusId;

        this.prjStatus = prjStatus;

        this.contractInNo=contractInNo;
        
        this.registerId = registerId;
        
        this.registerName = registerName;
        
        this.createdBy = createdBy;
        
        this.createdById = createdById;
        
        this.prjCategory = prjCategory;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PRJ_ID", unique=true)

    public String getPrjId() {
        return this.prjId;
    }
    
    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }
     
    @Column(name="PRJ_NO", length=96)

    public String getPrjNo() {
        return this.prjNo;
    }
    
    public void setPrjNo(String prjNo) {
        this.prjNo = prjNo;
    }
    @Column(name="DEPT_ID", length=96)

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    @Column(name="DEPT_NAME")

    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    @Column(name="PRJ_NAME", length=128)

    public String getPrjName() {
        return this.prjName;
    }
    
    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }
    
    @Column(name="PRJ_MANAGER_ID", length=60)

    public String getPrjManagerId() {
        return this.prjManagerId;
    }
    
    public void setPrjManagerId(String prjManagerId) {
        this.prjManagerId = prjManagerId;
    }
    
    @Column(name="PRJ_MANAGER", length=32)

    public String getPrjManager() {
        return this.prjManager;
    }
    
    public void setPrjManager(String prjManager) {
        this.prjManager = prjManager;
    }
      
    @Column(name="OWNER_NAME", length=256)

    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
     
    
    @Column(name="PRJ_RANK", length=32)

    public String getPrjRank() {
        return this.prjRank;
    }
    
    public void setPrjRank(String prjRank) {
        this.prjRank = prjRank;
    }
    
      
    @Column(name="PRJ_STATUS", length=64)

    public String getPrjStatus() {
        return this.prjStatus;
    }
    
    public void setPrjStatus(String prjStatus) {
        this.prjStatus = prjStatus;
    }
    
   
    @Column(name="CONTRACT_IN_NO", length=128)
    public String getContractInNo() {
		return contractInNo;
	}

	public void setContractInNo(String contractInNo) {
		this.contractInNo = contractInNo;
	}
	
	@Column(name="REGISTER_ID", length=60)

    public String getRegisterId() {
        return this.registerId;
    }
    
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }
    
    @Column(name="REGISTER_NAME", length=60)

    public String getRegisterName() {
        return this.registerName;
    }
    
    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    
    @Column(name="CREATED_BY_ID", length=60)

    public String getCreatedById() {
        return this.createdById;
    }
    
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }
    
    @Column(name="CREATED_BY", length=60)

    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
   
    @Column(name="PRJ_STATUS_ID", length=60)
	
    public int getPrjStatusId() {
		return prjStatusId;
	}

	public void setPrjStatusId(int prjStatusId) {
		this.prjStatusId = prjStatusId;
	}
	@Column(name="PRJ_CATEGORY", length=60)
	public String getPrjCategory() {
		return prjCategory;
	}

	public void setPrjCategory(String prjCategory) {
		this.prjCategory = prjCategory;
	}
    


   
}