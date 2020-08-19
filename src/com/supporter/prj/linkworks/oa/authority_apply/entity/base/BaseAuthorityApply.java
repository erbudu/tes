package com.supporter.prj.linkworks.oa.authority_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

 @MappedSuperclass
 public class BaseAuthorityApply  implements java.io.Serializable {


	    // Fields

	     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String applyId;
	     private String applyPersonId;
	     private String applyDeptId;
	     private String applyDept;
	     private Long copies;//份数
	     private String authorityApplyTime;
	     private Long applyStatus;//状态
	     private String createdById;
	     private String createdBy;
	     private String modifiedById;
	     private String modifiedBy;
	     private String modifiedDate;
	     private String createdDate; //创建日期 （授权申请日期）
	     private String documentName;
	     private String relatedIds;
	     private String relatedNames;
	     private String applyPerson;
	     private String otherFile;//其他上传文件（上传附件名称）
	     private String authorityApplyContentFile;
	     private String authorityApplyFile;
	     private String reason;
	     private String signNo;//编号
	     private String confirmDate;
	     private Long actresult; //
	     private String actresultname;
	     private String archiveDate;//归档日期
	     private String phoneCode;
	     private String applyTitle;
	     private String dateFrom;
	     private String dateTo;
	     private String currency;
	     private String amount;
	     private String purpose;
	     private String authorityNames;
	     private String datesFrom;
	     private String datesTo;
	     private Long authorityId;//
	     private Long authId; //
	     private Long poleId; //人员来源
	     private String type; //授权书格式
	     private String companyLeaderId;
	     private String companyLeaderName;
	     private Long authorityVersion;
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
	    public BaseAuthorityApply() {
	    }

		/** minimal constructor */
	    public BaseAuthorityApply(String applyId) {
	        this.applyId = applyId;
	    }
	    
	    /** full constructor */
	    public BaseAuthorityApply(String applyId, String applyPersonId, String applyDeptId, String applyDept, Long copies, String authorityApplyTime, Long applyStatus, String createdById, String createdBy, String modifiedById, String modifiedBy, String modifiedDate, String createdDate, String documentName, String relatedIds, String relatedNames, String applyPerson, String otherFile, String authorityApplyContentFile, String authorityApplyFile, String reason, String signNo, String confirmDate, Long actresult, String actresultname, String archiveDate, String phoneCode, String applyTitle, String dateFrom, String dateTo, String currency, String amount, String purpose, String authorityNames, String datesFrom, String datesTo, Long authorityId, Long authId, Long poleId, String type, String companyLeaderId, String companyLeaderName, Long authorityVersion,String procId) {
	        this.applyId = applyId;
	        this.applyPersonId = applyPersonId;
	        this.applyDeptId = applyDeptId;
	        this.applyDept = applyDept;
	        this.copies = copies;
	        this.authorityApplyTime = authorityApplyTime;
	        this.applyStatus = applyStatus;
	        this.createdById = createdById;
	        this.createdBy = createdBy;
	        this.modifiedById = modifiedById;
	        this.modifiedBy = modifiedBy;
	        this.modifiedDate = modifiedDate;
	        this.createdDate = createdDate;
	        this.documentName = documentName;
	        this.relatedIds = relatedIds;
	        this.relatedNames = relatedNames;
	        this.applyPerson = applyPerson;
	        this.otherFile = otherFile;
	        this.authorityApplyContentFile = authorityApplyContentFile;
	        this.authorityApplyFile = authorityApplyFile;
	        this.reason = reason;
	        this.signNo = signNo;
	        this.confirmDate = confirmDate;
	        this.actresult = actresult;
	        this.actresultname = actresultname;
	        this.archiveDate = archiveDate;
	        this.phoneCode = phoneCode;
	        this.applyTitle = applyTitle;
	        this.dateFrom = dateFrom;
	        this.dateTo = dateTo;
	        this.currency = currency;
	        this.amount = amount;
	        this.purpose = purpose;
	        this.authorityNames = authorityNames;
	        this.datesFrom = datesFrom;
	        this.datesTo = datesTo;
	        this.authorityId = authorityId;
	        this.authId = authId;
	        this.poleId = poleId;
	        this.type = type;
	        this.companyLeaderId = companyLeaderId;
	        this.companyLeaderName = companyLeaderName;
	        this.authorityVersion = authorityVersion;
	        this.procId = procId;
	    }

	   
	    // Property accessors
        @Id
	    @Column(name="APPLY_ID", nullable=false, length=18)

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

	    @Column(name="COPIES", precision=22, scale=0)

	    public Long getCopies() {
	        return this.copies;
	    }
	    
	    public void setCopies(Long copies) {
	        this.copies = copies;
	    }

	    @Column(name="AUTHORITY_APPLY_TIME", length=27)

	    public String getAuthorityApplyTime() {
	        return this.authorityApplyTime;
	    }
	    
	    public void setAuthorityApplyTime(String authorityApplyTime) {
	        this.authorityApplyTime = authorityApplyTime;
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

	    @Column(name="DOCUMENT_NAME", length=32)

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

	    @Column(name="OTHER_FILE", length=512)

	    public String getOtherFile() {
	        return this.otherFile;
	    }
	    
	    public void setOtherFile(String otherFile) {
	        this.otherFile = otherFile;
	    }

	    @Column(name="AUTHORITY_APPLY_CONTENT_FILE", length=128)

	    public String getAuthorityApplyContentFile() {
	        return this.authorityApplyContentFile;
	    }
	    
	    public void setAuthorityApplyContentFile(String authorityApplyContentFile) {
	        this.authorityApplyContentFile = authorityApplyContentFile;
	    }

	    @Column(name="AUTHORITY_APPLY_FILE", length=128)

	    public String getAuthorityApplyFile() {
	        return this.authorityApplyFile;
	    }
	    
	    public void setAuthorityApplyFile(String authorityApplyFile) {
	        this.authorityApplyFile = authorityApplyFile;
	    }

	    @Column(name="REASON", length=1024)

	    public String getReason() {
	        return this.reason;
	    }
	    
	    public void setReason(String reason) {
	        this.reason = reason;
	    }

	    @Column(name="SIGN_NO", length=64)

	    public String getSignNo() {
	        return this.signNo;
	    }
	    
	    public void setSignNo(String signNo) {
	        this.signNo = signNo;
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

	    @Column(name="APPLY_TITLE", length=128)

	    public String getApplyTitle() {
	        return this.applyTitle;
	    }
	    
	    public void setApplyTitle(String applyTitle) {
	        this.applyTitle = applyTitle;
	    }

	    @Column(name="DATE_FROM", length=27)

	    public String getDateFrom() {
	        return this.dateFrom;
	    }
	    
	    public void setDateFrom(String dateFrom) {
	        this.dateFrom = dateFrom;
	    }

	    @Column(name="DATE_TO", length=27)

	    public String getDateTo() {
	        return this.dateTo;
	    }
	    
	    public void setDateTo(String dateTo) {
	        this.dateTo = dateTo;
	    }

	    @Column(name="CURRENCY", length=32)

	    public String getCurrency() {
	        return this.currency;
	    }
	    
	    public void setCurrency(String currency) {
	        this.currency = currency;
	    }

	    @Column(name="AMOUNT", length=128)

	    public String getAmount() {
	        return this.amount;
	    }
	    
	    public void setAmount(String amount) {
	        this.amount = amount;
	    }

	    @Column(name="PURPOSE", length=32)

	    public String getPurpose() {
	        return this.purpose;
	    }
	    
	    public void setPurpose(String purpose) {
	        this.purpose = purpose;
	    }

	    @Column(name="AUTHORITY_NAMES", length=32)

	    public String getAuthorityNames() {
	        return this.authorityNames;
	    }
	    
	    public void setAuthorityNames(String authorityNames) {
	        this.authorityNames = authorityNames;
	    }

	    @Column(name="DATES_FROM", length=27)

	    public String getDatesFrom() {
	        return this.datesFrom;
	    }
	    
	    public void setDatesFrom(String datesFrom) {
	        this.datesFrom = datesFrom;
	    }

	    @Column(name="DATES_TO", length=27)

	    public String getDatesTo() {
	        return this.datesTo;
	    }
	    
	    public void setDatesTo(String datesTo) {
	        this.datesTo = datesTo;
	    }

	    @Column(name="AUTHORITY_ID", precision=18, scale=0)

	    public Long getAuthorityId() {
	        return this.authorityId;
	    }
	    
	    public void setAuthorityId(Long authorityId) {
	        this.authorityId = authorityId;
	    }

	    @Column(name="AUTH_ID", precision=18, scale=0)

	    public Long getAuthId() {
	        return this.authId;
	    }
	    
	    public void setAuthId(Long authId) {
	        this.authId = authId;
	    }

	    @Column(name="POLE_ID", precision=18, scale=0)

	    public Long getPoleId() {
	        return this.poleId;
	    }
	    
	    public void setPoleId(Long poleId) {
	        this.poleId = poleId;
	    }

	    @Column(name="TYPE", length=32)

	    public String getType() {
	        return this.type;
	    }
	    
	    public void setType(String type) {
	        this.type = type;
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

	    @Column(name="AUTHORITY_VERSION", precision=22, scale=0)

	    public Long getAuthorityVersion() {
	        return this.authorityVersion;
	    }
	    
	    public void setAuthorityVersion(Long authorityVersion) {
	        this.authorityVersion = authorityVersion;
	    }
	    
	    @Column(name="PROC_ID", length=32)
	    public String getProcId() {
	 		return procId;
	 	}

	 	public void setProcId(String procId) {
	 		this.procId = procId;
	 	}
	   
}
