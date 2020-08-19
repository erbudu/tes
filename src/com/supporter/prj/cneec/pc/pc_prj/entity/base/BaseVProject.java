package com.supporter.prj.cneec.pc.pc_prj.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * BaseCneecVPcPrj entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass

public class BaseVProject  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prjId;
	
	private String prjNo;
	
     private String prjName;
     
     private String prjManagerId;
 
     private String prjManager;
    
     private String deptId;
     
     private String deptName;
    
     private int prjStatusId;
     
     private String prjStatus;
     
     private String ownerName;
    
     private String prjRank;
    
     private String contractInNo;
     
     private String prjCategory;


    // Constructors

    /** default constructor */
    public BaseVProject() {
    }

	/** minimal constructor */
    public BaseVProject(String prjId) {
        this.prjId = prjId;
    }
    
    /** full constructor */
    public BaseVProject(String  prjId, String prjNo,String deptId,String prjName, String prjManagerId,String prjManager, String deptName, int prjStatusId,String prjStatus, String ownerName, String prjRank,String contractInNo,String prjCategory) {
        this.prjId = prjId;
        
        this.prjNo=prjNo;
        
        this.deptId=deptId;
        
        this.prjName = prjName;
        
        this.prjManagerId=prjManagerId;
 
        this.prjManager = prjManager;
 
        this.deptName = deptName;
 
        this.prjStatusId=prjStatusId;
        
        this.prjStatus = prjStatus;
    
        this.ownerName = ownerName;
  
        this.prjRank = prjRank;
     
        this.contractInNo = contractInNo;
        
        this.prjCategory = prjCategory;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PRJ_ID", unique=true)

//    public Long getPrjId() {
//        return this.prjId;
//    }
//    
//    public void setPrjId(Long prjId) {
//        this.prjId = prjId;
//    }
    

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
    @Column(name="DEPT_ID")

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
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
       
    
    @Column(name="DEPT_NAME", length=64)

    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    
    @Column(name="PRJ_STATUS", length=64)

    public String getPrjStatus() {
        return this.prjStatus;
    }
    
    public void setPrjStatus(String prjStatus) {
        this.prjStatus = prjStatus;
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
    
    
    @Column(name="CONTRACT_IN_NO", length=128)
    public String getContractInNo() {
		return contractInNo;
	}

	public void setContractInNo(String contractInNo) {
		this.contractInNo = contractInNo;
	}
	
	@Column(name="PRJ_CATEGORY", length=60)
	public String getPrjCategory() {
		return prjCategory;
	}

	public void setPrjCategory(String prjCategory) {
		this.prjCategory = prjCategory;
	}
	@Column(name="PRJ_STATUS_ID")
	public int getPrjStatusId() {
		return prjStatusId;
	}

	public void setPrjStatusId(int prjStatusId) {
		this.prjStatusId = prjStatusId;
	}
   


}