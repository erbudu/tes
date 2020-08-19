package com.supporter.prj.linkworks.oa.bank_account.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseBankAccountOpenApplyRecord implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Fields

     private String recordId;
     private String applyId;
     private String projectName;
     private String recordNumber;
     private String applicationNumber;
     private String accountName;
     private String openBank;
     private String openTime;
     private String thebankAccount;
     private String accountCurrency;
     private String accountNature;
     private String bankAddress;
     private String zipCode;
     private String bankContactsPhoneCalls;
     private String email;
     private String authorization;
     private String authorizationPeriod;
     private String note;
     private String thoseResponsible;
     private String contact;
     private String departmentManager;
     private String createdById;
     private String createdBy;
     private String createdDate;
     private String modifiedById;
     private String modifiedBy;
     private String modifiedDate;
     private Long status;
     private String authdateFrom;
     private String authdateTo;
     private String accountNatureId;//account_Nature_id 账户性质ID(码表)
     private String authorizationPersonIds;//授权人ids
     private String authorizationPersonNames;//授权人names
     private String departmentManagerId;//授权人ids
     private String thoseResponsibleId;//责任人id
     private String accountCurrencyId;//币别Id（码表）
     private String accountTerm;//账户期限

     


    // Constructors

    /** default constructor */
    public BaseBankAccountOpenApplyRecord() {
    }

	/** minimal constructor */
    public BaseBankAccountOpenApplyRecord(String recordId) {
        this.recordId = recordId;
    }
    
    /** full constructor */
    public BaseBankAccountOpenApplyRecord(String recordId, String applyId, String projectName, String recordNumber, String applicationNumber, String accountName, String openBank, String openTime, String thebankAccount, String accountCurrency, String accountNature, String bankAddress, String zipCode, String bankContactsPhoneCalls, String email, String authorization, String authorizationPeriod, String note, String thoseResponsible, String contact, String departmentManager, String createdById, String createdBy, String createdDate, String modifiedById, String modifiedBy, String modifiedDate, Long status, String authdateFrom, String authdateTo,String accountNatureId,String authorizationPersonIds,String authorizationPersonNames,String departmentManagerId,String thoseResponsibleId) {
        this.recordId = recordId;
        this.applyId = applyId;
        this.projectName = projectName;
        this.recordNumber = recordNumber;
        this.applicationNumber = applicationNumber;
        this.accountName = accountName;
        this.openBank = openBank;
        this.openTime = openTime;
        this.thebankAccount = thebankAccount;
        this.accountCurrency = accountCurrency;
        this.accountNature = accountNature;
        this.bankAddress = bankAddress;
        this.zipCode = zipCode;
        this.bankContactsPhoneCalls = bankContactsPhoneCalls;
        this.email = email;
        this.authorization = authorization;
        this.authorizationPeriod = authorizationPeriod;
        this.note = note;
        this.thoseResponsible = thoseResponsible;
        this.contact = contact;
        this.departmentManager = departmentManager;
        this.createdById = createdById;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedById = modifiedById;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.authdateFrom = authdateFrom;
        this.authdateTo = authdateTo;
        this.accountNatureId = accountNatureId;
        this.authorizationPersonIds=authorizationPersonIds;
        this.authorizationPersonNames=authorizationPersonNames;
        this.departmentManagerId=departmentManagerId;
        this.thoseResponsibleId=thoseResponsibleId;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="RECORD_ID", unique=true, nullable=false, length=32)

    public String getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    @Column(name="APPLY_ID", length=32)

    public String getApplyId() {
        return this.applyId;
    }
    
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    
    @Column(name="PROJECT_NAME", length=256)

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    @Column(name="RECORD_NUMBER", length=32)

    public String getRecordNumber() {
        return this.recordNumber;
    }
    
    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }
    
    @Column(name="APPLICATION_NUMBER", length=32)

    public String getApplicationNumber() {
        return this.applicationNumber;
    }
    
    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }
    
    @Column(name="ACCOUNT_NAME", length=256)

    public String getAccountName() {
        return this.accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    @Column(name="OPEN_BANK", length=256)

    public String getOpenBank() {
        return this.openBank;
    }
    
    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }
    
    @Column(name="OPEN_TIME", length=27)

    public String getOpenTime() {
        return this.openTime;
    }
    
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }
    
    @Column(name="THEBANK_ACCOUNT", length=32)

    public String getThebankAccount() {
        return this.thebankAccount;
    }
    
    public void setThebankAccount(String thebankAccount) {
        this.thebankAccount = thebankAccount;
    }
    
    @Column(name="ACCOUNT_CURRENCY", length=32)

    public String getAccountCurrency() {
        return this.accountCurrency;
    }
    
    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }
    
    @Column(name="ACCOUNT_NATURE", length=64)

    public String getAccountNature() {
        return this.accountNature;
    }
    
    public void setAccountNature(String accountNature) {
        this.accountNature = accountNature;
    }
    
    @Column(name="BANK_ADDRESS", length=256)

    public String getBankAddress() {
        return this.bankAddress;
    }
    
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
    
    @Column(name="ZIP_CODE", length=32)

    public String getZipCode() {
        return this.zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Column(name="BANK_CONTACTS_PHONE_CALLS", length=128)

    public String getBankContactsPhoneCalls() {
        return this.bankContactsPhoneCalls;
    }
    
    public void setBankContactsPhoneCalls(String bankContactsPhoneCalls) {
        this.bankContactsPhoneCalls = bankContactsPhoneCalls;
    }
    
    @Column(name="EMAIL", length=64)

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="AUTHORIZATION", length=1024)

    public String getAuthorization() {
        return this.authorization;
    }
    
    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
    
    @Column(name="AUTHORIZATION_PERIOD", length=256)

    public String getAuthorizationPeriod() {
        return this.authorizationPeriod;
    }
    
    public void setAuthorizationPeriod(String authorizationPeriod) {
        this.authorizationPeriod = authorizationPeriod;
    }
    
    @Column(name="NOTE", length=1024)

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    @Column(name="THOSE_RESPONSIBLE", length=64)

    public String getThoseResponsible() {
        return this.thoseResponsible;
    }
    
    public void setThoseResponsible(String thoseResponsible) {
        this.thoseResponsible = thoseResponsible;
    }
    
    @Column(name="CONTACT", length=64)

    public String getContact() {
        return this.contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    @Column(name="DEPARTMENT_MANAGER", length=64)

    public String getDepartmentManager() {
        return this.departmentManager;
    }
    
    public void setDepartmentManager(String departmentManager) {
        this.departmentManager = departmentManager;
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
    
    @Column(name="AUTHDATE_FROM", length=27)

    public String getAuthdateFrom() {
        return this.authdateFrom;
    }
    
    public void setAuthdateFrom(String authdateFrom) {
        this.authdateFrom = authdateFrom;
    }
    
    @Column(name="AUTHDATE_TO", length=27)

    public String getAuthdateTo() {
        return this.authdateTo;
    }
    
    public void setAuthdateTo(String authdateTo) {
        this.authdateTo = authdateTo;
    }
    @Column(name="ACCOUNT_NATURE_ID", length=32)

    public String getAccountNatureId() {
        return this.accountNatureId;
    }
    
    public void setAccountNatureId(String accountNatureId) {
        this.accountNatureId = accountNatureId;
    }
	@Column(name="AUTHORIZATION_PERSONIDS", length=1024)
    public String getAuthorizationPersonIds() {
		return authorizationPersonIds;
	}

	public void setAuthorizationPersonIds(String authorizationPersonIds) {
		this.authorizationPersonIds = authorizationPersonIds;
	}
	@Column(name="AUTHORIZATION_PERSONNAMES", length=1024)
	public String getAuthorizationPersonNames() {
		return authorizationPersonNames;
	}
	public void setAuthorizationPersonNames(String authorizationPersonNames) {
		this.authorizationPersonNames = authorizationPersonNames;
	}
	@Column(name="DEPARTMENT_MANAGER_ID", length=32)
	public String getDepartmentManagerId() {
		return departmentManagerId;
	}

	public void setDepartmentManagerId(String departmentManagerId) {
		this.departmentManagerId = departmentManagerId;
	}
	@Column(name="THOSE_RESPONSIBLE_ID", length=32)
	public String getThoseResponsibleId() {
		return thoseResponsibleId;
	}

	public void setThoseResponsibleId(String thoseResponsibleId) {
		this.thoseResponsibleId = thoseResponsibleId;
	}
	@Column(name="ACCOUNT_CURRENCY_ID", length=32)  
	public String getAccountCurrencyId() {
		return accountCurrencyId;
	}

	public void setAccountCurrencyId(String accountCurrencyId) {
		this.accountCurrencyId = accountCurrencyId;
	}
	@Column(name="ACCOUNT_TERM", length=27)  
    public String getAccountTerm() {
		return accountTerm;
	}

	public void setAccountTerm(String accountTerm) {
		this.accountTerm = accountTerm;
	}

}
