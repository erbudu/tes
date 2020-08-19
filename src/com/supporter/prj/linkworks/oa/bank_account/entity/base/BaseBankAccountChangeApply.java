package com.supporter.prj.linkworks.oa.bank_account.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseBankAccountChangeApply implements java.io.Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Fields

     private String changeApplyId;
     private String effectiveId;
     private String changeNumber;
     private String bankAccountName;
     private String openBank;
     private String bankNumber;
     private String type;
     private String changesAs;
     private String reasonsChange;
     private String postChangeContent;
     private String projectManager;
     private String branchManager;
     private String createdById;
     private String createdBy;
     private String createdDate;
     private String modifiedById;
     private String modifiedBy;
     private String modifiedDate;
     private Long status;
     private String accountOpenerId; 
     private String accountOpener;
     private String openTime;
     private String openApplyRecordId;
     private String procId;//流程id
     private String projectName;//项目名称id
     private String nationalityId;//Nationality_Id 国别ID(码表)
     private String accountPropertyId;;//Account_Property_id 账户单位性质ID(码表)
     private String branchManagerId;//部门/子公司/分公司经理Id
     private String authorization;
     private String accountNature;//账户性质（流程标题中用到）
     private Integer cmecResult;//报CMEC审批结果

    


    // Constructors

    /** default constructor */
    public BaseBankAccountChangeApply() {
    }

	/** minimal constructor */
    public BaseBankAccountChangeApply(String changeApplyId) {
        this.changeApplyId = changeApplyId;
    }
    
    /** full constructor */
    public BaseBankAccountChangeApply(String changeApplyId, String effectiveId, String changeNumber, String bankAccountName, String openBank, String bankNumber, String type, String changesAs, String reasonsChange, String postChangeContent, String projectManager, String branchManager, String createdById, String createdBy, String createdDate, String modifiedById, String modifiedBy, String modifiedDate, Long status, String accountOpenerId,String accountOpener,String openTime,String openApplyRecordId,String procId,String projectName,String nationalityId,String accountPropertyId,String branchManagerId,String authorization, Integer cmecResult) {
        this.changeApplyId = changeApplyId;
        this.effectiveId = effectiveId;
        this.changeNumber = changeNumber;
        this.bankAccountName = bankAccountName;
        this.openBank = openBank;
        this.bankNumber = bankNumber;
        this.type = type;
        this.changesAs = changesAs;
        this.reasonsChange = reasonsChange;
        this.postChangeContent = postChangeContent;
        this.projectManager = projectManager;
        this.branchManager = branchManager;
        this.createdById = createdById;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedById = modifiedById;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.accountOpenerId = accountOpenerId;
        this.accountOpener = accountOpener;
        this.openTime=openTime;
        this.openApplyRecordId=openApplyRecordId;
        this.procId=procId;
        this.projectName=projectName;
        this.nationalityId = nationalityId;
        this.accountPropertyId = accountPropertyId;
        this.branchManagerId=branchManagerId;
        this.authorization=authorization;
        this.cmecResult = cmecResult;
        
    }

   
    // Property accessors
    @Id 
    
    @Column(name="CHANGE_APPLY_ID", unique=true, nullable=false, length=32)

    public String getChangeApplyId() {
        return this.changeApplyId;
    }
    
    public void setChangeApplyId(String changeApplyId) {
        this.changeApplyId = changeApplyId;
    }
    
    @Column(name="EFFECTIVE_ID", length=32)

    public String getEffectiveId() {
        return this.effectiveId;
    }
    
    public void setEffectiveId(String effectiveId) {
        this.effectiveId = effectiveId;
    }
    
    @Column(name="CHANGE_NUMBER", length=32)

    public String getChangeNumber() {
        return this.changeNumber;
    }
    
    public void setChangeNumber(String changeNumber) {
        this.changeNumber = changeNumber;
    }
    
    @Column(name="BANK_ACCOUNT_NAME", length=256)

    public String getBankAccountName() {
        return this.bankAccountName;
    }
    
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }
    
    @Column(name="OPEN_BANK", length=256)

    public String getOpenBank() {
        return this.openBank;
    }
    
    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }
    
    @Column(name="BANK_NUMBER", length=32)

    public String getBankNumber() {
        return this.bankNumber;
    }
    
    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }
    
    @Column(name="TYPE", length=32)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="CHANGES_AS", length=1024)

    public String getChangesAs() {
        return this.changesAs;
    }
    
    public void setChangesAs(String changesAs) {
        this.changesAs = changesAs;
    }
    
    @Column(name="REASONS_CHANGE", length=1024)

    public String getReasonsChange() {
        return this.reasonsChange;
    }
    
    public void setReasonsChange(String reasonsChange) {
        this.reasonsChange = reasonsChange;
    }
    
    @Column(name="POST_CHANGE_CONTENT", length=1024)

    public String getPostChangeContent() {
        return this.postChangeContent;
    }
    
    public void setPostChangeContent(String postChangeContent) {
        this.postChangeContent = postChangeContent;
    }
    
    @Column(name="PROJECT_MANAGER", length=64)

    public String getProjectManager() {
        return this.projectManager;
    }
    
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }
    
    @Column(name="BRANCH_MANAGER", length=64)

    public String getBranchManager() {
        return this.branchManager;
    }
    
    public void setBranchManager(String branchManager) {
        this.branchManager = branchManager;
    }
    
    @Column(name="CREATED_BY_ID", length=32)

    public String getCreatedById() {
        return this.createdById;
    }
    
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }
    
    @Column(name="CREATED_BY", length=64)

    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    @Column(name="CREATED_DATE", length=27)

    public String getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    
    @Column(name="MODIFIED_BY_ID", length=32)

    public String getModifiedById() {
        return this.modifiedById;
    }
    
    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }
    
    @Column(name="MODIFIED_BY", length=64)

    public String getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    @Column(name="MODIFIED_DATE", length=27)

    public String getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    @Column(name="STATUS", precision=22, scale=0)

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
   
    @Column(name="ACCOUNT_OPENER_ID", length=32)

    public String getAccountOpenerId() {
        return this.accountOpenerId;
    }
    
    public void setAccountOpenerId(String accountOpenerId) {
        this.accountOpenerId = accountOpenerId;
    }
    
    
    @Column(name="ACCOUNT_OPENER", length=256)

    public String getAccountOpener() {
        return this.accountOpener;
    }
    
    public void setAccountOpener(String accountOpener) {
        this.accountOpener = accountOpener;
    }
   

    @Column(name="OPEN_TIME", length=27)

    public String getOpenTime() {
        return this.openTime;
    }
    
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }
    @Column(name="OPEN_APPLY_RECORD_ID", length=32)

    public String getOpenApplyRecordId() {
        return this.openApplyRecordId;
    }
    
    public void setOpenApplyRecordId(String openApplyRecordId) {
        this.openApplyRecordId = openApplyRecordId;
    }
    @Column(name="PROC_ID", length=32)
    public String getProcId() {
 		return procId;
 	}

 	public void setProcId(String procId) {
 		this.procId = procId;
 	}
 	
    @Column(name="PROJECT_NAME", length=256)

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    @Column(name="NATIONALITY_ID", length=32)

    public String getNationalityId() {
        return this.nationalityId;
    }
    
    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }
    
    @Column(name="ACCOUNT_PROPERTY_ID", length=32)

    public String getAccountPropertyId() {
        return this.accountPropertyId;
    }
    
    public void setAccountPropertyId(String accountPropertyId) {
        this.accountPropertyId = accountPropertyId;
    }
    @Column(name="BRANCH_MANAGER_ID", length=32)
	public String getBranchManagerId() {
		return branchManagerId;
	}

	public void setBranchManagerId(String branchManagerId) {
		this.branchManagerId = branchManagerId;
	}
    @Column(name="AUTHORIZATION", length=1024)
	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

    @Column(name="ACCOUNT_NATURE", length=32)

    public String getAccountNature() {
        return this.accountNature;
    }
    
    public void setAccountNature(String accountNature) {
        this.accountNature = accountNature;
    }

    @Column(name="CMEC_RESULT", precision=22, scale=0)
	public Integer getCmecResult() {
		return cmecResult;
	}

	public void setCmecResult(Integer cmecResult) {
		this.cmecResult = cmecResult;
	} 


}
