package com.supporter.prj.linkworks.oa.bank_account.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseBankAccountOpenApply implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    // Fields

     private String applyId;
     private String accountOpener;
     private String applicationNumber;
     private String isRelevanceprj;
     private String projectName;
     private String nationality;
     private String accountProperty;
     private String isCooperation;
     private String openingBank;
     private String bankAddress;
     private String accountCurrency;
     private String accountNature;
     private String accountReason;
     private String projectStatus;
     private String authorization;
     private String authorizationPeriod;
     private String condition;
     private String credibilityLetter;
     private String projectManager;
     private String controlledCompany;
     private String targetDate;
     private String createdById;
     private String createdBy;
     private String createdDate;
     private String modifiedById;
     private String modifiedBy;
     private String modifiedDate;
     private Long status;
     private String authdateFrom;
     private String authdateTo;
     private String accountOpenerId;
     private String nationalityId;
     private String accountPropertyId;
     private String accountNatureId;
     private String procId;
     private String controlledCompanyId;
     private String authorizationPersonids;
     private String authorizationPersonnames;
     private String isPrjSystemInOrOut;
     private String accountCurrencyId;
     private String openingBankId;
     private String projectManagerId;
     private String accountName;
     private String openBankFirstNameId;
     private String openBankFirstName;
     private String accountProId;
     private String accountPro;
     private String isOutside;
     private Integer cmecResult;//报CMEC审批结果


    // Constructors

    /** default constructor */
    public BaseBankAccountOpenApply() {
    }

	/** minimal constructor */
    public BaseBankAccountOpenApply(String applyId) {
        this.applyId = applyId;
    }
    
    /** full constructor */
    public BaseBankAccountOpenApply(String applyId, String accountOpener, String applicationNumber, String isRelevanceprj, String projectName, String nationality, String accountProperty, String isCooperation, String openingBank, String bankAddress, String accountCurrency, String accountNature, String accountReason, String projectStatus, String authorization, String authorizationPeriod, String condition, String credibilityLetter, String projectManager, String controlledCompany, String targetDate, String createdById, String createdBy, String createdDate, String modifiedById, String modifiedBy, String modifiedDate, Long status, String authdateFrom, String authdateTo, String accountOpenerId, String nationalityId, String accountPropertyId, String accountNatureId, String procId, String controlledCompanyId, String authorizationPersonids, String authorizationPersonnames, String isPrjSystemInOrOut, String accountCurrencyId, String openingBankId, String projectManagerId, String accountName, String openBankFirstNameId, String openBankFirstName, String accountProId, String accountPro,String isOutside, Integer cmecResult) {
        this.applyId = applyId;
        this.accountOpener = accountOpener;
        this.applicationNumber = applicationNumber;
        this.isRelevanceprj = isRelevanceprj;
        this.projectName = projectName;
        this.nationality = nationality;
        this.accountProperty = accountProperty;
        this.isCooperation = isCooperation;
        this.openingBank = openingBank;
        this.bankAddress = bankAddress;
        this.accountCurrency = accountCurrency;
        this.accountNature = accountNature;
        this.accountReason = accountReason;
        this.projectStatus = projectStatus;
        this.authorization = authorization;
        this.authorizationPeriod = authorizationPeriod;
        this.condition = condition;
        this.credibilityLetter = credibilityLetter;
        this.projectManager = projectManager;
        this.controlledCompany = controlledCompany;
        this.targetDate = targetDate;
        this.createdById = createdById;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedById = modifiedById;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.authdateFrom = authdateFrom;
        this.authdateTo = authdateTo;
        this.accountOpenerId = accountOpenerId;
        this.nationalityId = nationalityId;
        this.accountPropertyId = accountPropertyId;
        this.accountNatureId = accountNatureId;
        this.procId = procId;
        this.controlledCompanyId = controlledCompanyId;
        this.authorizationPersonids = authorizationPersonids;
        this.authorizationPersonnames = authorizationPersonnames;
        this.isPrjSystemInOrOut = isPrjSystemInOrOut;
        this.accountCurrencyId = accountCurrencyId;
        this.openingBankId = openingBankId;
        this.projectManagerId = projectManagerId;
        this.accountName = accountName;
        this.openBankFirstNameId = openBankFirstNameId;
        this.openBankFirstName = openBankFirstName;
        this.accountProId = accountProId;
        this.accountPro = accountPro;
        this.isOutside = isOutside;
        this.cmecResult = cmecResult;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="APPLY_ID", unique=true, nullable=false, length=32)

    public String getApplyId() {
        return this.applyId;
    }
    
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    
    @Column(name="ACCOUNT_OPENER", length=256)

    public String getAccountOpener() {
        return this.accountOpener;
    }
    
    public void setAccountOpener(String accountOpener) {
        this.accountOpener = accountOpener;
    }
    
    @Column(name="APPLICATION_NUMBER", length=32)

    public String getApplicationNumber() {
        return this.applicationNumber;
    }
    
    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }
    
    @Column(name="IS_RELEVANCEPRJ", length=6)

    public String getIsRelevanceprj() {
        return this.isRelevanceprj;
    }
    
    public void setIsRelevanceprj(String isRelevanceprj) {
        this.isRelevanceprj = isRelevanceprj;
    }
    
    @Column(name="PROJECT_NAME", length=256)

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
    
    @Column(name="TARGET_DATE", length=27)

    public String getTargetDate() {
        return this.targetDate;
    }
    
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
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
    
    @Column(name="ACCOUNT_OPENER_ID", length=32)

    public String getAccountOpenerId() {
        return this.accountOpenerId;
    }
    
    public void setAccountOpenerId(String accountOpenerId) {
        this.accountOpenerId = accountOpenerId;
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
    
    @Column(name="ACCOUNT_NATURE_ID", length=32)

    public String getAccountNatureId() {
        return this.accountNatureId;
    }
    
    public void setAccountNatureId(String accountNatureId) {
        this.accountNatureId = accountNatureId;
    }
    
    @Column(name="PROC_ID", length=32)

    public String getProcId() {
        return this.procId;
    }
    
    public void setProcId(String procId) {
        this.procId = procId;
    }
    
    @Column(name="CONTROLLED_COMPANY_ID", length=32)

    public String getControlledCompanyId() {
        return this.controlledCompanyId;
    }
    
    public void setControlledCompanyId(String controlledCompanyId) {
        this.controlledCompanyId = controlledCompanyId;
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
    
    @Column(name="IS_PRJ_SYSTEM_IN_OR_OUT", length=6)

    public String getIsPrjSystemInOrOut() {
        return this.isPrjSystemInOrOut;
    }
    
    public void setIsPrjSystemInOrOut(String isPrjSystemInOrOut) {
        this.isPrjSystemInOrOut = isPrjSystemInOrOut;
    }
    
    @Column(name="ACCOUNT_CURRENCY_ID", length=32)

    public String getAccountCurrencyId() {
        return this.accountCurrencyId;
    }
    
    public void setAccountCurrencyId(String accountCurrencyId) {
        this.accountCurrencyId = accountCurrencyId;
    }
    
    @Column(name="OPENING_BANK_ID", length=32)

    public String getOpeningBankId() {
        return this.openingBankId;
    }
    
    public void setOpeningBankId(String openingBankId) {
        this.openingBankId = openingBankId;
    }
    
    @Column(name="PROJECT_MANAGER_ID", length=32)

    public String getProjectManagerId() {
        return this.projectManagerId;
    }
    
    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }
    
    @Column(name="ACCOUNT_NAME", length=256)

    public String getAccountName() {
        return this.accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
    @Column(name="IS_OUTSIDE", length=6)

    public String getIsOutside() {
        return this.isOutside;
    }
    
    public void setIsOutside(String isOutside) {
        this.isOutside = isOutside;
    }

    @Column(name="CMEC_RESULT", precision=22, scale=0)
	public Integer getCmecResult() {
		return cmecResult;
	}

	public void setCmecResult(Integer cmecResult) {
		this.cmecResult = cmecResult;
	}  

    



}
