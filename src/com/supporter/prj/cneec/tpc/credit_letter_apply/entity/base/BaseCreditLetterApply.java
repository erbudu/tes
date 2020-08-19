package com.supporter.prj.cneec.tpc.credit_letter_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractTpcCreditLetterApply entity provides the base persistence definition
 * of the TpcCreditLetterApply entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BaseCreditLetterApply implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private String creditLetterId;
	private String projectId;
	private String projectName;
	private String contractId;
	private String contractName;
	private String contractRecordFilingNo;
	private String contractNo;
	private String creditLetterOrderNo;
	private String creditLetterNo;
	private String creditLetterBank;
	private String businessType;
	private String businessTypeCode;
	private Double businessRatio;
	private Double amountCreditLetter;
	private String currencyType;
	private String currencyTypeCode;
	private Double marginRatio;
	private String settlementTerm;
	private String settlementTermCode;
	private String futureDays;
	private String beneficiaryName;
	private String expArrivalDate;
	private String expPaidDate;
	private String importedForm;
	private String importedFormCode;
	private String importedGoods;
	private String recNote;
	private String filesName;
	private String applyPersonId;
	private String applyPerson;
	private String deptId;
	private String deptName;
	private String creditLetterApplyDate;
	private Integer swfStatus;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String prjManagerId;
	private int isLeadersExam;
	private int settlementStatus;
	private int itemNameCheck;
	private String createdNotifyId;
	private String createdNotify;
	private String prjManagerNotifyId;
	private String prjManagerNotify;
	private String prjIntegratedNotifyId;
	private String prjIntegratedNotify;

	// Constructors

	/** default constructor */
	public BaseCreditLetterApply() {
	}

	/** minimal constructor */
	public BaseCreditLetterApply(String creditLetterId) {
		this.creditLetterId = creditLetterId;
	}

	/** full constructor */
	public BaseCreditLetterApply(String creditLetterId,
			String projectId, String projectName, String contractId,
			String contractName, String contractRecordFilingNo, String contractNo,
			String creditLetterOrderNo, String creditLetterNo,
			String creditLetterBank, String businessType,
			String businessTypeCode, Double businessRatio,
			Double amountCreditLetter, String currencyType,
			String currencyTypeCode, Double marginRatio, String settlementTerm,
			String settlementTermCode, String futureDays,
			String beneficiaryName, String expArrivalDate, String expPaidDate,
			String importedForm, String importedFormCode, String importedGoods,
			String recNote, String filesName, String applyPersonId,
			String applyPerson, String deptId, String deptName,
			String creditLetterApplyDate, Integer swfStatus, String createdBy,
			String createdById, String createdDate, String modifiedBy,
			String modifiedById, String modifiedDate) {
		this.creditLetterId = creditLetterId;
		this.projectId = projectId;
		this.projectName = projectName;
		this.contractId = contractId;
		this.contractName = contractName;
		this.contractRecordFilingNo = contractRecordFilingNo;
		this.contractNo = contractNo;
		this.creditLetterOrderNo = creditLetterOrderNo;
		this.creditLetterNo = creditLetterNo;
		this.creditLetterBank = creditLetterBank;
		this.businessType = businessType;
		this.businessTypeCode = businessTypeCode;
		this.businessRatio = businessRatio;
		this.amountCreditLetter = amountCreditLetter;
		this.currencyType = currencyType;
		this.currencyTypeCode = currencyTypeCode;
		this.marginRatio = marginRatio;
		this.settlementTerm = settlementTerm;
		this.settlementTermCode = settlementTermCode;
		this.futureDays = futureDays;
		this.beneficiaryName = beneficiaryName;
		this.expArrivalDate = expArrivalDate;
		this.expPaidDate = expPaidDate;
		this.importedForm = importedForm;
		this.importedFormCode = importedFormCode;
		this.importedGoods = importedGoods;
		this.recNote = recNote;
		this.filesName = filesName;
		this.applyPersonId = applyPersonId;
		this.applyPerson = applyPerson;
		this.deptId = deptId;
		this.deptName = deptName;
		this.creditLetterApplyDate = creditLetterApplyDate;
		this.swfStatus = swfStatus;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@Id
	@Column(name = "CREDIT_LETTER_ID", unique = true, nullable = false, length = 32)
	public String getCreditLetterId() {
		return this.creditLetterId;
	}

	public void setCreditLetterId(String creditLetterId) {
		this.creditLetterId = creditLetterId;
	}

	@Column(name = "PROJECT_ID", length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "CONTRACT_ID", length = 32)
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "CONTRACT_NAME", length = 128)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "CONTRACT_RECORD_FILING_NO", length = 64)
	public String getContractRecordFilingNo() {
		return this.contractRecordFilingNo;
	}

	public void setContractRecordFilingNo(String contractRecordFilingNo) {
		this.contractRecordFilingNo = contractRecordFilingNo;
	}

	@Column(name = "CONTRACT_NO", length = 32)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CREDIT_LETTER_ORDER_NO", length = 32)
	public String getCreditLetterOrderNo() {
		return this.creditLetterOrderNo;
	}

	public void setCreditLetterOrderNo(String creditLetterOrderNo) {
		this.creditLetterOrderNo = creditLetterOrderNo;
	}

	@Column(name = "CREDIT_LETTER_NO", length = 32)
	public String getCreditLetterNo() {
		return this.creditLetterNo;
	}

	public void setCreditLetterNo(String creditLetterNo) {
		this.creditLetterNo = creditLetterNo;
	}

	@Column(name = "CREDIT_LETTER_BANK", length = 128)
	public String getCreditLetterBank() {
		return this.creditLetterBank;
	}

	public void setCreditLetterBank(String creditLetterBank) {
		this.creditLetterBank = creditLetterBank;
	}

	@Column(name = "BUSINESS_TYPE", length = 32)
	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "BUSINESS_TYPE_CODE", length = 32)
	public String getBusinessTypeCode() {
		return this.businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	@Column(name = "BUSINESS_RATIO", precision = 18, scale = 5)
	public Double getBusinessRatio() {
		return this.businessRatio;
	}

	public void setBusinessRatio(Double businessRatio) {
		this.businessRatio = businessRatio;
	}

	@Column(name = "AMOUNT_CREDIT_LETTER", precision = 18, scale = 3)
	public Double getAmountCreditLetter() {
		return this.amountCreditLetter;
	}

	public void setAmountCreditLetter(Double amountCreditLetter) {
		this.amountCreditLetter = amountCreditLetter;
	}

	@Column(name = "CURRENCY_TYPE", length = 32)
	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@Column(name = "CURRENCY_TYPE_CODE", length = 32)
	public String getCurrencyTypeCode() {
		return this.currencyTypeCode;
	}

	public void setCurrencyTypeCode(String currencyTypeCode) {
		this.currencyTypeCode = currencyTypeCode;
	}

	@Column(name = "MARGIN_RATIO", precision = 18, scale = 5)
	public Double getMarginRatio() {
		return this.marginRatio;
	}

	public void setMarginRatio(Double marginRatio) {
		this.marginRatio = marginRatio;
	}

	@Column(name = "SETTLEMENT_TERM", length = 32)
	public String getSettlementTerm() {
		return this.settlementTerm;
	}

	public void setSettlementTerm(String settlementTerm) {
		this.settlementTerm = settlementTerm;
	}

	@Column(name = "SETTLEMENT_TERM_CODE", length = 32)
	public String getSettlementTermCode() {
		return this.settlementTermCode;
	}

	public void setSettlementTermCode(String settlementTermCode) {
		this.settlementTermCode = settlementTermCode;
	}

	@Column(name = "FUTURE_DAYS", length = 32)
	public String getFutureDays() {
		return this.futureDays;
	}

	public void setFutureDays(String futureDays) {
		this.futureDays = futureDays;
	}

	@Column(name = "BENEFICIARY_NAME", length = 32)
	public String getBeneficiaryName() {
		return this.beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	@Column(name = "EXP_ARRIVAL_DATE", length = 32)
	public String getExpArrivalDate() {
		return this.expArrivalDate;
	}

	public void setExpArrivalDate(String expArrivalDate) {
		this.expArrivalDate = expArrivalDate;
	}

	@Column(name = "EXP_PAID_DATE", length = 32)
	public String getExpPaidDate() {
		return this.expPaidDate;
	}

	public void setExpPaidDate(String expPaidDate) {
		this.expPaidDate = expPaidDate;
	}

	@Column(name = "IMPORTED_FORM", length = 128)
	public String getImportedForm() {
		return this.importedForm;
	}

	public void setImportedForm(String importedForm) {
		this.importedForm = importedForm;
	}

	@Column(name = "IMPORTED_FORM_CODE", length = 32)
	public String getImportedFormCode() {
		return this.importedFormCode;
	}

	public void setImportedFormCode(String importedFormCode) {
		this.importedFormCode = importedFormCode;
	}

	@Column(name = "IMPORTED_GOODS", length = 128)
	public String getImportedGoods() {
		return this.importedGoods;
	}

	public void setImportedGoods(String importedGoods) {
		this.importedGoods = importedGoods;
	}

	@Column(name = "REC_NOTE", length = 1024)
	public String getRecNote() {
		return this.recNote;
	}

	public void setRecNote(String recNote) {
		this.recNote = recNote;
	}

	@Column(name = "FILES_NAME", length = 1024)
	public String getFilesName() {
		return this.filesName;
	}

	public void setFilesName(String filesName) {
		this.filesName = filesName;
	}

	@Column(name = "APPLY_PERSON_ID", length = 32)
	public String getApplyPersonId() {
		return this.applyPersonId;
	}

	public void setApplyPersonId(String applyPersonId) {
		this.applyPersonId = applyPersonId;
	}

	@Column(name = "APPLY_PERSON", length = 32)
	public String getApplyPerson() {
		return this.applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 32)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "CREDIT_LETTER_APPLY_DATE", length = 32)
	public String getCreditLetterApplyDate() {
		return this.creditLetterApplyDate;
	}

	public void setCreditLetterApplyDate(String creditLetterApplyDate) {
		this.creditLetterApplyDate = creditLetterApplyDate;
	}

	@Column(name = "SWF_STATUS", precision = 6, scale = 0)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", length = 32)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "PRJ_MANAGER_ID", nullable = true, length = 32)
	public String getPrjManagerId() {
		return this.prjManagerId;
	}

	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}

	@Column(name = "IS_LEADERS_EXAM", nullable = true, precision = 10)
	public int getIsLeadersExam() {
		return this.isLeadersExam;
	}

	public void setIsLeadersExam(int isLeadersExam) {
		this.isLeadersExam = isLeadersExam;
	}

	@Column(name = "SETTLEMENT_STATUS", nullable = true, precision = 10)
	public int getSettlementStatus() {
		return this.settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	@Column(name = "ITEM_NAME_CHECK", nullable = true, precision = 10)
	public int getItemNameCheck() {
		return this.itemNameCheck;
	}

	public void setItemNameCheck(int itemNameCheck) {
		this.itemNameCheck = itemNameCheck;
	}

	@Column(name = "CREATED_NOTIFY_ID", nullable = true, length = 256)
	public String getCreatedNotifyId() {
		return this.createdNotifyId;
	}

	public void setCreatedNotifyId(String createdNotifyId) {
		this.createdNotifyId = createdNotifyId;
	}

	@Column(name = "CREATED_NOTIFY", nullable = true, length = 512)
	public String getCreatedNotify() {
		return this.createdNotify;
	}

	public void setCreatedNotify(String createdNotify) {
		this.createdNotify = createdNotify;
	}

	@Column(name = "PRJ_MANAGER_NOTIFY_ID", nullable = true, length = 256)
	public String getPrjManagerNotifyId() {
		return this.prjManagerNotifyId;
	}

	public void setPrjManagerNotifyId(String prjManagerNotifyId) {
		this.prjManagerNotifyId = prjManagerNotifyId;
	}

	@Column(name = "PRJ_MANAGER_NOTIFY", nullable = true, length = 512)
	public String getPrjManagerNotify() {
		return this.prjManagerNotify;
	}

	public void setPrjManagerNotify(String prjManagerNotify) {
		this.prjManagerNotify = prjManagerNotify;
	}

	@Column(name = "PRJ_INTEGRATED_NOTIFY_ID", nullable = true, length = 256)
	public String getPrjIntegratedNotifyId() {
		return this.prjIntegratedNotifyId;
	}

	public void setPrjIntegratedNotifyId(String prjIntegratedNotifyId) {
		this.prjIntegratedNotifyId = prjIntegratedNotifyId;
	}

	@Column(name = "PRJ_INTEGRATED_NOTIFY", nullable = true, length = 512)
	public String getPrjIntegratedNotify() {
		return this.prjIntegratedNotify;
	}

	public void setPrjIntegratedNotify(String prjIntegratedNotify) {
		this.prjIntegratedNotify = prjIntegratedNotify;
	}

}