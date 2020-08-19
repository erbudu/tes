package com.supporter.prj.linkworks.oa.use_seal_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseVUseSealApply  implements java.io.Serializable {


	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		// Fields

	     private String applyId;
	     private String applyPersonId;
	     private String applyDeptId;
	     private String applyDept;
	     private String sealType;
	     private Long copies;
	     private String useSealTime;
	     private Long applyStatus;
	     private String createdById;
	     private String createdBy;
	     private String modifiedById;
	     private String modifiedBy;
	     private String modifiedDate;
	     private String createdDate;
	     private String sealTypeId;
	     private String senderDeptId;
	     private String senderDept;
	     private String documentName;
	     private String relatedIds;
	     private String relatedNames;
	     private String applyPerson;
	     private String otherFile;
	     private String useSealApplyContentFile;
	     private String useSealApplyFile;
	     private String reason;
	     private String signNo;
	     private String selectType;
	     private String selectTypeId;
	     private String confirmDate;
	     private Long actresult;
	     private String actresultname;
	     private String archiveDate;
	     private String phoneCode;
	     private String businessPretrial;
	     private String remark;
	     private String companyLeaderId;
	     private String companyLeaderName;
	     private String procId;
	     private Long needLeader;
	     private boolean history;
	     private String storageAndTransPretrial;
			@Column(name = "IS_HISTORY")
			@org.hibernate.annotations.Type(type="true_false")
			public boolean getHistory() {
				return history;
			}

			public void setHistory(boolean history) {
				this.history = history;
			}
	     private String useSealReason;


	    // Constructors

	    /** default constructor */
	    public BaseVUseSealApply() {
	    }

		/** minimal constructor */
	    public BaseVUseSealApply(String applyId) {
	        this.applyId = applyId;
	    }
	    
	    /** full constructor */
	    public BaseVUseSealApply(String applyId, String applyPersonId, String applyDeptId, String applyDept, String sealType, Long copies, String useSealTime, Long applyStatus, String createdById, String createdBy, String modifiedById, String modifiedBy, String modifiedDate, String createdDate, String sealTypeId, String senderDeptId, String senderDept, String documentName, String relatedIds, String relatedNames, String applyPerson, String otherFile, String useSealApplyContentFile, String useSealApplyFile, String reason, String signNo, String selectType, String selectTypeId, String confirmDate, Long actresult, String actresultname, String archiveDate, String phoneCode, String businessPretrial, String remark, String companyLeaderId, String companyLeaderName, String procId, Long needLeader, String useSealReason,String storageAndTransPretrial) {
	        this.applyId = applyId;
	        this.applyPersonId = applyPersonId;
	        this.applyDeptId = applyDeptId;
	        this.applyDept = applyDept;
	        this.sealType = sealType;
	        this.copies = copies;
	        this.useSealTime = useSealTime;
	        this.applyStatus = applyStatus;
	        this.createdById = createdById;
	        this.createdBy = createdBy;
	        this.modifiedById = modifiedById;
	        this.modifiedBy = modifiedBy;
	        this.modifiedDate = modifiedDate;
	        this.createdDate = createdDate;
	        this.sealTypeId = sealTypeId;
	        this.senderDeptId = senderDeptId;
	        this.senderDept = senderDept;
	        this.documentName = documentName;
	        this.relatedIds = relatedIds;
	        this.relatedNames = relatedNames;
	        this.applyPerson = applyPerson;
	        this.otherFile = otherFile;
	        this.useSealApplyContentFile = useSealApplyContentFile;
	        this.useSealApplyFile = useSealApplyFile;
	        this.reason = reason;
	        this.signNo = signNo;
	        this.selectType = selectType;
	        this.selectTypeId = selectTypeId;
	        this.confirmDate = confirmDate;
	        this.actresult = actresult;
	        this.actresultname = actresultname;
	        this.archiveDate = archiveDate;
	        this.phoneCode = phoneCode;
	        this.businessPretrial = businessPretrial;
	        this.remark = remark;
	        this.companyLeaderId = companyLeaderId;
	        this.companyLeaderName = companyLeaderName;
	        this.procId = procId;
	        this.needLeader = needLeader;
	        this.useSealReason = useSealReason;
	        this.storageAndTransPretrial=storageAndTransPretrial;
	    }

	   
	    // Property accessors
        @Id
	    @Column(name="APPLY_ID", nullable=false, length=32)

	    public String getApplyId() {
	        return this.applyId;
	    }
	    
	    public void setApplyId(String applyId) {
	        this.applyId = applyId;
	    }

	    @Column(name="APPLY_PERSON_ID", length=32)

	    public String getApplyPersonId() {
	        return this.applyPersonId;
	    }
	    
	    public void setApplyPersonId(String applyPersonId) {
	        this.applyPersonId = applyPersonId;
	    }

	    @Column(name="APPLY_DEPT_ID", length=32)

	    public String getApplyDeptId() {
	        return this.applyDeptId;
	    }
	    
	    public void setApplyDeptId(String applyDeptId) {
	        this.applyDeptId = applyDeptId;
	    }

	    @Column(name="APPLY_DEPT", length=64)

	    public String getApplyDept() {
	        return this.applyDept;
	    }
	    
	    public void setApplyDept(String applyDept) {
	        this.applyDept = applyDept;
	    }

	    @Column(name="SEAL_TYPE", length=512)

	    public String getSealType() {
	        return this.sealType;
	    }
	    
	    public void setSealType(String sealType) {
	        this.sealType = sealType;
	    }

	    @Column(name="COPIES", precision=22, scale=0)

	    public Long getCopies() {
	        return this.copies;
	    }
	    
	    public void setCopies(Long copies) {
	        this.copies = copies;
	    }

	    @Column(name="USE_SEAL_TIME", length=27)

	    public String getUseSealTime() {
	        return this.useSealTime;
	    }
	    
	    public void setUseSealTime(String useSealTime) {
	        this.useSealTime = useSealTime;
	    }

	    @Column(name="APPLY_STATUS", precision=22, scale=0)

	    public Long getApplyStatus() {
	        return this.applyStatus;
	    }
	    
	    public void setApplyStatus(Long applyStatus) {
	        this.applyStatus = applyStatus;
	    }

	    @Column(name="CREATED_BY_ID", length=32)

	    public String getCreatedById() {
	        return this.createdById;
	    }
	    
	    public void setCreatedById(String createdById) {
	        this.createdById = createdById;
	    }

	    @Column(name="CREATED_BY", length=32)

	    public String getCreatedBy() {
	        return this.createdBy;
	    }
	    
	    public void setCreatedBy(String createdBy) {
	        this.createdBy = createdBy;
	    }

	    @Column(name="MODIFIED_BY_ID", length=32)

	    public String getModifiedById() {
	        return this.modifiedById;
	    }
	    
	    public void setModifiedById(String modifiedById) {
	        this.modifiedById = modifiedById;
	    }

	    @Column(name="MODIFIED_BY", length=32)

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

	    @Column(name="CREATED_DATE", length=27)

	    public String getCreatedDate() {
	        return this.createdDate;
	    }
	    
	    public void setCreatedDate(String createdDate) {
	        this.createdDate = createdDate;
	    }

	    @Column(name="SEAL_TYPE_ID", length=256)

	    public String getSealTypeId() {
	        return this.sealTypeId;
	    }
	    
	    public void setSealTypeId(String sealTypeId) {
	        this.sealTypeId = sealTypeId;
	    }

	    @Column(name="SENDER_DEPT_ID", length=32)

	    public String getSenderDeptId() {
	        return this.senderDeptId;
	    }
	    
	    public void setSenderDeptId(String senderDeptId) {
	        this.senderDeptId = senderDeptId;
	    }

	    @Column(name="SENDER_DEPT", length=256)

	    public String getSenderDept() {
	        return this.senderDept;
	    }
	    
	    public void setSenderDept(String senderDept) {
	        this.senderDept = senderDept;
	    }

	    @Column(name="DOCUMENT_NAME", length=128)

	    public String getDocumentName() {
	        return this.documentName;
	    }
	    
	    public void setDocumentName(String documentName) {
	        this.documentName = documentName;
	    }

	    @Column(name="RELATED_IDS", length=256)

	    public String getRelatedIds() {
	        return this.relatedIds;
	    }
	    
	    public void setRelatedIds(String relatedIds) {
	        this.relatedIds = relatedIds;
	    }

	    @Column(name="RELATED_NAMES", length=1024)

	    public String getRelatedNames() {
	        return this.relatedNames;
	    }
	    
	    public void setRelatedNames(String relatedNames) {
	        this.relatedNames = relatedNames;
	    }

	    @Column(name="APPLY_PERSON", length=32)

	    public String getApplyPerson() {
	        return this.applyPerson;
	    }
	    
	    public void setApplyPerson(String applyPerson) {
	        this.applyPerson = applyPerson;
	    }

	    @Column(name="OTHER_FILE", length=2000)

	    public String getOtherFile() {
	        return this.otherFile;
	    }
	    
	    public void setOtherFile(String otherFile) {
	        this.otherFile = otherFile;
	    }

	    @Column(name="USE_SEAL_APPLY_CONTENT_FILE", length=128)

	    public String getUseSealApplyContentFile() {
	        return this.useSealApplyContentFile;
	    }
	    
	    public void setUseSealApplyContentFile(String useSealApplyContentFile) {
	        this.useSealApplyContentFile = useSealApplyContentFile;
	    }

	    @Column(name="USE_SEAL_APPLY_FILE", length=128)

	    public String getUseSealApplyFile() {
	        return this.useSealApplyFile;
	    }
	    
	    public void setUseSealApplyFile(String useSealApplyFile) {
	        this.useSealApplyFile = useSealApplyFile;
	    }

	    @Column(name="REASON", length=1024)

	    public String getReason() {
	        return this.reason;
	    }
	    
	    public void setReason(String reason) {
	        this.reason = reason;
	    }

	    @Column(name="SIGN_NO", length=32)

	    public String getSignNo() {
	        return this.signNo;
	    }
	    
	    public void setSignNo(String signNo) {
	        this.signNo = signNo;
	    }

	    @Column(name="SELECT_TYPE", length=512)

	    public String getSelectType() {
	        return this.selectType;
	    }
	    
	    public void setSelectType(String selectType) {
	        this.selectType = selectType;
	    }

	    @Column(name="SELECT_TYPE_ID", length=32)

	    public String getSelectTypeId() {
	        return this.selectTypeId;
	    }
	    
	    public void setSelectTypeId(String selectTypeId) {
	        this.selectTypeId = selectTypeId;
	    }

	    @Column(name="CONFIRM_DATE", length=32)

	    public String getConfirmDate() {
	        return this.confirmDate;
	    }
	    
	    public void setConfirmDate(String confirmDate) {
	        this.confirmDate = confirmDate;
	    }

	    @Column(name="ACTRESULT", precision=22, scale=0)

	    public Long getActresult() {
	        return this.actresult;
	    }
	    
	    public void setActresult(Long actresult) {
	        this.actresult = actresult;
	    }

	    @Column(name="ACTRESULTNAME", length=256)

	    public String getActresultname() {
	        return this.actresultname;
	    }
	    
	    public void setActresultname(String actresultname) {
	        this.actresultname = actresultname;
	    }

	    @Column(name="ARCHIVE_DATE", length=32)

	    public String getArchiveDate() {
	        return this.archiveDate;
	    }
	    
	    public void setArchiveDate(String archiveDate) {
	        this.archiveDate = archiveDate;
	    }

	    @Column(name="PHONE_CODE", length=32)

	    public String getPhoneCode() {
	        return this.phoneCode;
	    }
	    
	    public void setPhoneCode(String phoneCode) {
	        this.phoneCode = phoneCode;
	    }

	    @Column(name="BUSINESS_PRETRIAL", length=32)

	    public String getBusinessPretrial() {
	        return this.businessPretrial;
	    }
	    
	    public void setBusinessPretrial(String businessPretrial) {
	        this.businessPretrial = businessPretrial;
	    }

	    @Column(name="REMARK", length=1024)

	    public String getRemark() {
	        return this.remark;
	    }
	    
	    public void setRemark(String remark) {
	        this.remark = remark;
	    }

	    @Column(name="COMPANY_LEADER_ID", length=32)

	    public String getCompanyLeaderId() {
	        return this.companyLeaderId;
	    }
	    
	    public void setCompanyLeaderId(String companyLeaderId) {
	        this.companyLeaderId = companyLeaderId;
	    }

	    @Column(name="COMPANY_LEADER_NAME", length=128)

	    public String getCompanyLeaderName() {
	        return this.companyLeaderName;
	    }
	    
	    public void setCompanyLeaderName(String companyLeaderName) {
	        this.companyLeaderName = companyLeaderName;
	    }

	    @Column(name="PROC_ID", length=32)

	    public String getProcId() {
	        return this.procId;
	    }
	    
	    public void setProcId(String procId) {
	        this.procId = procId;
	    }

	    @Column(name="NEED_LEADER", precision=2, scale=0)

	    public Long getNeedLeader() {
	        return this.needLeader;
	    }
	    
	    public void setNeedLeader(Long needLeader) {
	        this.needLeader = needLeader;
	    }

	    @Column(name="USE_SEAL_REASON")

	    public String getUseSealReason() {
	        return this.useSealReason;
	    }
	    
	    public void setUseSealReason(String useSealReason) {
	        this.useSealReason = useSealReason;
	    }
	    
	    @Column(name="STORAGEANDTRANS_PRETRIAL", length=32)

	    public String getStorageAndTransPretrial() {
	        return this.storageAndTransPretrial;
	    }
	    
	    public void setStorageAndTransPretrial(String storageAndTransPretrial) {
	        this.storageAndTransPretrial = storageAndTransPretrial;
	    }
	    
}
