package com.supporter.prj.linkworks.oa.bank_account.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseBankAccountChanApplyRecord  implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Fields

     private String changeRecordId;
     private String changeApplyId;
     private String effectiveId;
     private String projectName;
     private String recordNumber;
     private String changeNumber;
     private String openTime;
     private String thebankAccount;
     private String accountCurrency;
     private String accountNature;
     private String authorization;
     private String authorizationPeriod;
     private String createdById;
     private String createdBy;
     private String createdDate;
     private String modifiedById;
     private String modifiedBy;
     private String modifiedDate;
     private Long status;
     private String authdateFrom;
     private String authdateTo;
     private String applyId;
     private String accountNatureId;
     private String authorizationPersonids;
     private String authorizationPersonnames;
     private String accountCurrencyId;
     private String accountTerm;
     private String isRelevanceprj;
     private String nationality;
     private String accountProperty;
     private String isCooperation;
     private String openingBank;
     private String bankAddress;
     private String accountReason;
     private String projectStatus;
     private String condition;
     private String credibilityLetter;
     private String projectManager;
     private String controlledCompany;
     private String bankAccountName;
     private String nationalityId;
     private String accountPropertyId;
     private String openApplyRecordId;
     private String isPrjSystemInOrOut;
     private String controlledCompanyId;
     private String zipCode;
     private String bankContactsPhoneCalls;
     private String email;
     private String contact;
     private String departmentManagerId;
     private String departmentManager;
     private String note;
     private String thoseResponsibleId;
     private String thoseResponsible;
     private String chanReEffecDate;
     private String openBankFirstNameId;
     private String openBankFirstName;
     private String accountProId;
     private String accountPro;
     private String openingBankId;


    // Constructors

    /** default constructor */
    public BaseBankAccountChanApplyRecord() {
    }

	/** minimal constructor */
    public BaseBankAccountChanApplyRecord(String changeRecordId) {
        this.changeRecordId = changeRecordId;
    }
    
    /** full constructor */
    public BaseBankAccountChanApplyRecord(String changeRecordId, String changeApplyId, String effectiveId, String projectName, String recordNumber, String changeNumber, String openTime, String thebankAccount, String accountCurrency, String accountNature, String authorization, String authorizationPeriod, String createdById, String createdBy, String createdDate, String modifiedById, String modifiedBy, String modifiedDate, Long status, String authdateFrom, String authdateTo, String applyId, String accountNatureId, String authorizationPersonids, String authorizationPersonnames, String accountCurrencyId, String accountTerm, String isRelevanceprj, String nationality, String accountProperty, String isCooperation, String openingBank, String bankAddress, String accountReason, String projectStatus, String condition, String credibilityLetter, String projectManager, String controlledCompany, String bankAccountName, String nationalityId, String accountPropertyId, String openApplyRecordId, String isPrjSystemInOrOut, String controlledCompanyId, String zipCode, String bankContactsPhoneCalls, String email, String contact, String departmentManagerId, String departmentManager, String note, String thoseResponsibleId, String thoseResponsible, String chanReEffecDate, String openBankFirstNameId, String openBankFirstName, String accountProId, String accountPro,String openingBankId) {
        this.changeRecordId = changeRecordId;
        this.changeApplyId = changeApplyId;
        this.effectiveId = effectiveId;
        this.projectName = projectName;
        this.recordNumber = recordNumber;
        this.changeNumber = changeNumber;
        this.openTime = openTime;
        this.thebankAccount = thebankAccount;
        this.accountCurrency = accountCurrency;
        this.accountNature = accountNature;
        this.authorization = authorization;
        this.authorizationPeriod = authorizationPeriod;
        this.createdById = createdById;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedById = modifiedById;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.authdateFrom = authdateFrom;
        this.authdateTo = authdateTo;
        this.applyId = applyId;
        this.accountNatureId = accountNatureId;
        this.authorizationPersonids = authorizationPersonids;
        this.authorizationPersonnames = authorizationPersonnames;
        this.accountCurrencyId = accountCurrencyId;
        this.accountTerm = accountTerm;
        this.isRelevanceprj = isRelevanceprj;
        this.nationality = nationality;
        this.accountProperty = accountProperty;
        this.isCooperation = isCooperation;
        this.openingBank = openingBank;
        this.bankAddress = bankAddress;
        this.accountReason = accountReason;
        this.projectStatus = projectStatus;
        this.condition = condition;
        this.credibilityLetter = credibilityLetter;
        this.projectManager = projectManager;
        this.controlledCompany = controlledCompany;
        this.bankAccountName = bankAccountName;
        this.nationalityId = nationalityId;
        this.accountPropertyId = accountPropertyId;
        this.openApplyRecordId = openApplyRecordId;
        this.isPrjSystemInOrOut = isPrjSystemInOrOut;
        this.controlledCompanyId = controlledCompanyId;
        this.zipCode = zipCode;
        this.bankContactsPhoneCalls = bankContactsPhoneCalls;
        this.email = email;
        this.contact = contact;
        this.departmentManagerId = departmentManagerId;
        this.departmentManager = departmentManager;
        this.note = note;
        this.thoseResponsibleId = thoseResponsibleId;
        this.thoseResponsible = thoseResponsible;
        this.chanReEffecDate = chanReEffecDate;
        this.openBankFirstNameId = openBankFirstNameId;
        this.openBankFirstName = openBankFirstName;
        this.accountProId = accountProId;
        this.accountPro = accountPro;
        this.openingBankId=openingBankId;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="CHANGE_RECORD_ID", unique=true, nullable=false, length=32)

    public String getChangeRecordId() {
        return this.changeRecordId;
    }
    
    public void setChangeRecordId(String changeRecordId) {
        this.changeRecordId = changeRecordId;
    }
    
    @Column(name="CHANGE_APPLY_ID", length=32)

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
    
    @Column(name="CHANGE_NUMBER", length=32)

    public String getChangeNumber() {
        return this.changeNumber;
    }
    
    public void setChangeNumber(String changeNumber) {
        this.changeNumber = changeNumber;
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
    
    @Column(name="ACCOUNT_NATURE", length=32)

    public String getAccountNature() {
        return this.accountNature;
    }
    
    public void setAccountNature(String accountNature) {
        this.accountNature = accountNature;
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
    
    @Column(name="APPLY_ID", length=32)

    public String getApplyId() {
        return this.applyId;
    }
    
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    
    @Column(name="ACCOUNT_NATURE_ID", length=32)

    public String getAccountNatureId() {
        return this.accountNatureId;
    }
    
    public void setAccountNatureId(String accountNatureId) {
        this.accountNatureId = accountNatureId;
    }
    
    @Column(name="AUTHORIZATION_PERSONIDS", length=1024)

    public String getAuthorizationPersonids() {
        return this.authorizationPersonids;
    }
    
    public void setAuthorizationPersonids(String authorizationPersonids) {
        this.authorizationPersonids = authorizationPersonids;
    }
    
    @Column(name="AUTHORIZATION_PERSONNAMES", length=1024)

    public String getAuthorizationPersonnames() {
        return this.authorizationPersonnames;
    }
    
    public void setAuthorizationPersonnames(String authorizationPersonnames) {
        this.authorizationPersonnames = authorizationPersonnames;
    }
    
    @Column(name="ACCOUNT_CURRENCY_ID", length=32)

    public String getAccountCurrencyId() {
        return this.accountCurrencyId;
    }
    
    public void setAccountCurrencyId(String accountCurrencyId) {
        this.accountCurrencyId = accountCurrencyId;
    }
    
    @Column(name="ACCOUNT_TERM", length=27)

    public String getAccountTerm() {
        return this.accountTerm;
    }
    
    public void setAccountTerm(String accountTerm) {
        this.accountTerm = accountTerm;
    }
    
    @Column(name="IS_RELEVANCEPRJ", length=6)

    public String getIsRelevanceprj() {
        return this.isRelevanceprj;
    }
    
    public void setIsRelevanceprj(String isRelevanceprj) {
        this.isRelevanceprj = isRelevanceprj;
    }
    
    @Column(name="NATIONALITY", length=256)

    public String getNationality() {
        return this.nationality;
    }
    
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    @Column(name="ACCOUNT_PROPERTY", length=64)

    public String getAccountProperty() {
        return this.accountProperty;
    }
    
    public void setAccountProperty(String accountProperty) {
        this.accountProperty = accountProperty;
    }
    
    @Column(name="IS_COOPERATION", length=6)

    public String getIsCooperation() {
        return this.isCooperation;
    }
    
    public void setIsCooperation(String isCooperation) {
        this.isCooperation = isCooperation;
    }
    
    @Column(name="OPENING_BANK", length=256)

    public String getOpeningBank() {
        return this.openingBank;
    }
    
    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }
    
    @Column(name="BANK_ADDRESS", length=128)

    public String getBankAddress() {
        return this.bankAddress;
    }
    
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
    
    @Column(name="ACCOUNT_REASON", length=1024)

    public String getAccountReason() {
        return this.accountReason;
    }
    
    public void setAccountReason(String accountReason) {
        this.accountReason = accountReason;
    }
    
    @Column(name="PROJECT_STATUS", length=1024)

    public String getProjectStatus() {
        return this.projectStatus;
    }
    
    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
    
    @Column(name="CONDITION", length=1024)

    public String getCondition() {
        return this.condition;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    @Column(name="CREDIBILITY_LETTER", length=1024)

    public String getCredibilityLetter() {
        return this.credibilityLetter;
    }
    
    public void setCredibilityLetter(String credibilityLetter) {
        this.credibilityLetter = credibilityLetter;
    }
    
    @Column(name="PROJECT_MANAGER", length=64)

    public String getProjectManager() {
        return this.projectManager;
    }
    
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }
    
    @Column(name="CONTROLLED_COMPANY", length=64)

    public String getControlledCompany() {
        return this.controlledCompany;
    }
    
    public void setControlledCompany(String controlledCompany) {
        this.controlledCompany = controlledCompany;
    }
    
    @Column(name="BANK_ACCOUNT_NAME", length=256)

    public String getBankAccountName() {
        return this.bankAccountName;
    }
    
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
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
    
    @Column(name="OPEN_APPLY_RECORD_ID", length=32)

    public String getOpenApplyRecordId() {
        return this.openApplyRecordId;
    }
    
    public void setOpenApplyRecordId(String openApplyRecordId) {
        this.openApplyRecordId = openApplyRecordId;
    }
    
    @Column(name="IS_PRJ_SYSTEM_IN_OR_OUT", length=6)

    public String getIsPrjSystemInOrOut() {
        return this.isPrjSystemInOrOut;
    }
    
    public void setIsPrjSystemInOrOut(String isPrjSystemInOrOut) {
        this.isPrjSystemInOrOut = isPrjSystemInOrOut;
    }
    
    @Column(name="CONTROLLED_COMPANY_ID", length=32)

    public String getControlledCompanyId() {
        return this.controlledCompanyId;
    }
    
    public void setControlledCompanyId(String controlledCompanyId) {
        this.controlledCompanyId = controlledCompanyId;
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
    
    @Column(name="CONTACT", length=64)

    public String getContact() {
        return this.contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    @Column(name="DEPARTMENT_MANAGER_ID", length=32)

    public String getDepartmentManagerId() {
        return this.departmentManagerId;
    }
    
    public void setDepartmentManagerId(String departmentManagerId) {
        this.departmentManagerId = departmentManagerId;
    }
    
    @Column(name="DEPARTMENT_MANAGER", length=64)

    public String getDepartmentManager() {
        return this.departmentManager;
    }
    
    public void setDepartmentManager(String departmentManager) {
        this.departmentManager = departmentManager;
    }
    
    @Column(name="NOTE", length=1024)

    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    @Column(name="THOSE_RESPONSIBLE_ID", length=32)

    public String getThoseResponsibleId() {
        return this.thoseResponsibleId;
    }
    
    public void setThoseResponsibleId(String thoseResponsibleId) {
        this.thoseResponsibleId = thoseResponsibleId;
    }
    
    @Column(name="THOSE_RESPONSIBLE", length=64)

    public String getThoseResponsible() {
        return this.thoseResponsible;
    }
    
    public void setThoseResponsible(String thoseResponsible) {
        this.thoseResponsible = thoseResponsible;
    }
    
    @Column(name="CHAN_RE_EFFEC_DATE", length=27)

    public String getChanReEffecDate() {
        return this.chanReEffecDate;
    }
    
    public void setChanReEffecDate(String chanReEffecDate) {
        this.chanReEffecDate = chanReEffecDate;
    }
    
    @Column(name="OPEN_BANK_FIRST_NAME_ID", length=32)

    public String getOpenBankFirstNameId() {
        return this.openBankFirstNameId;
    }
    
    public void setOpenBankFirstNameId(String openBankFirstNameId) {
        this.openBankFirstNameId = openBankFirstNameId;
    }
    
    @Column(name="OPEN_BANK_FIRST_NAME", length=256)

    public String getOpenBankFirstName() {
        return this.openBankFirstName;
    }
    
    public void setOpenBankFirstName(String openBankFirstName) {
        this.openBankFirstName = openBankFirstName;
    }
    
    @Column(name="ACCOUNT_PRO_ID", length=32)

    public String getAccountProId() {
        return this.accountProId;
    }
    
    public void setAccountProId(String accountProId) {
        this.accountProId = accountProId;
    }
    
    @Column(name="ACCOUNT_PRO", length=32)

    public String getAccountPro() {
        return this.accountPro;
    }
    
    public void setAccountPro(String accountPro) {
        this.accountPro = accountPro;
    }
    @Column(name="OPENING_BANK_ID", length=32)

    public String getOpeningBankId() {
        return this.openingBankId;
    }
    
    public void setOpeningBankId(String openingBankId) {
        this.openingBankId = openingBankId;
    }   


}
