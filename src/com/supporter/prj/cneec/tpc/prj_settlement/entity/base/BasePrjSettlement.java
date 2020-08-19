package com.supporter.prj.cneec.tpc.prj_settlement.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

@MappedSuperclass
public class BasePrjSettlement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SETTLEMENT_ID", unique = true, nullable = false, length = 32)
	private String settlementId;
	//
	@Column(name = "SETTLEMENT_NO", nullable = true, length = 64)
	private String settlementNo;
	//
	@Column(name = "SETTLEMENT_INDEX", precision = 10, scale = 0, nullable = true)
	private int settlementIndex;
	//
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	private String prjId;
	//
	@Column(name = "PRJ_NAME", nullable = true, length = 128)
	private String prjName;
	//
	@Column(name = "CONTRACTOR_NAME", nullable = true, length = 128)
	private String contractorName;
	//
	@Column(name = "GATHERING_UNIT", nullable = true, length = 128)
	private String gatheringUnit;
	//
	@Column(name = "CONTRACTOR_BANK", nullable = true, length = 256)
	private String contractorBank;
	//
	@Column(name = "CONTRACTOR_BANK_ACCOUNT", nullable = true, length = 256)
	private String contractorBankAccount;
	//
	@Column(name = "HAS_BANK_CODE", precision = 10, nullable = true)
	private int hasBankCode;
	//
	@Column(name = "BANK_CODE", nullable = true, length = 24)
	private String bankCode;
	//
	@Column(name = "REMIT_PROVINCE_ID", nullable = true, length = 32)
	private String remitProvinceId;
	//
	@Column(name = "REMIT_PROVINCE", nullable = true, length = 30)
	private String remitProvince;
	//
	@Column(name = "REMIT_CITY_ID", nullable = true, length = 32)
	private String remitCityId;
	//
	@Column(name = "REMIT_CITY", nullable = true, length = 30)
	private String remitCity;
	//
	@Column(name = "SETTLEMENT_APPLY_DATE", nullable = true, length = 27)
	private String settlementApplyDate;
	// -
	@Column(name = "CONFIRM_DATE", nullable = true, length = 27)
	private String confirmDate;
	// -
	@Column(name = "REAL_PAYMENT_DATE", nullable = true, length = 27)
	private String realPaymentDate;
	//
	@Column(name = "SETTLEMENT_YEAR", precision = 10, scale = 0, nullable = true)
	private int settlementYear;
	//
	@Column(name = "SETTLEMENT_MONTH", precision = 10, scale = 0, nullable = true)
	private int settlementmonth;
	//
	@Column(name = "SETTLEMENT_STATUS", precision = 10, scale = 0, nullable = true)
	private int settlementStatus;
	//
	@Column(name = "PAYMENT_STATUS", precision = 10, scale = 0, nullable = true)
	private int paymentStatus;
	// --
	@Column(name = "SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double settlementAmount;
	//
	@Column(name = "ON_WAY_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double onWayAmount;
	//
	@Column(name = "REAL_SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double realSettlementAmount;
	//
	@Column(name = "PAY_METHOD", nullable = true, length = 64)
	private String payMethod;
	//
	@Column(name = "SETTLEMENT_REASON", nullable = true, length = 1024)
	private String settlementReason;
	// -
	@Column(name = "AUDITED_BY_ID", nullable = true, length = 32)
	private String auditedById;
	// -
	@Column(name = "AUDITED_BY", nullable = true, length = 64)
	private String auditedBy;
	//
	@Column(name = "CREATED_BY", length = 64, nullable = true)
	private String createdBy;
	//
	@Column(name = "CREATED_BY_ID", length = 32, nullable = true)
	private String createdById;
	//
	@Column(name = "CREATED_DATE", length = 27, nullable = true)
	private String createdDate;
	//
	@Column(name = "MODIFIED_BY", length = 64, nullable = true)
	private String modifiedBy;
	//
	@Column(name = "MODIFIED_BY_ID", length = 32, nullable = true)
	private String modifiedById;
	//
	@Column(name = "MODIFIED_DATE", length = 27, nullable = true)
	private String modifiedDate;
	//
	@Column(name = "PAYER_DEPT_ID", nullable = true, length = 32)
	private String payerDeptId;
	//
	@Column(name = "PAYER_DEPT_NAME", nullable = true, length = 128)
	private String payerDeptName;
	//
	@Column(name = "NO_MONTH_REPORT_SUBMIT", precision = 10, scale = 0, nullable = true)
	private int noMonthReportSubmit;
	//
	@Column(name = "PAID_BY_ID", length = 32, nullable = true)
	private String paidById;
	//
	@Column(name = "PAID_BY", length = 64, nullable = true)
	private String paidBy;
	// true/false-付款标识
	@Type(type = "true_false")
	@Column(name = "PAID_FLAG", length = 1, nullable = true)
	private boolean paidFlag;
	// 付款途径（未用到）
	@Column(name = "SETTLEMENT_WAY", length = 64, nullable = true)
	private String settlementWay;
	//
	@Column(name = "IS_FULL_SWF", precision = 10, scale = 0, nullable = true)
	private int isFillSwf;
	//
	@Column(name = "PRINT_COUNT", precision = 10, scale = 0, nullable = true)
	private int printCount;
	//
	@Column(name = "BANK_TELLER_IDS", nullable = true, length = 512)
	private String bankTellerIds;
	//
	@Column(name = "BANK_TELLER_NAMES", nullable = true, length = 512)
	private String bankTellerNames;
	// 项目经理
	@Column(name = "PRJ_MANAGER_ID", length = 32, nullable = true)
	private String prjManagerId;

	@Column(name = "PRJ_MANAGER", length = 32, nullable = true)
	private String prjManager;

	@Column(name="REMITTANCE_PURPOSE" ,length=500, nullable = true)
	private String remittancePurpose;

	@Column(name="CAPTICAL_PURPOSE_CODE" ,length=10, nullable = true)
	private String capticalPurposeCode;

	// 流程id
	@Column(name = "PROC_ID", length = 32, nullable = true)
	private String procId;

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public BasePrjSettlement() {
		super();
	}

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getSettlementNo() {
		return settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}

	public int getSettlementIndex() {
		return settlementIndex;
	}

	public void setSettlementIndex(int settlementIndex) {
		this.settlementIndex = settlementIndex;
	}

	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getSettlementApplyDate() {
		return settlementApplyDate;
	}

	public void setSettlementApplyDate(String settlementApplyDate) {
		this.settlementApplyDate = settlementApplyDate;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getRealPaymentDate() {
		return realPaymentDate;
	}

	public void setRealPaymentDate(String realPaymentDate) {
		this.realPaymentDate = realPaymentDate;
	}

	public int getSettlementYear() {
		return settlementYear;
	}

	public void setSettlementYear(int settlementYear) {
		this.settlementYear = settlementYear;
	}

	public int getSettlementmonth() {
		return settlementmonth;
	}

	public void setSettlementmonth(int settlementmonth) {
		this.settlementmonth = settlementmonth;
	}

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public double getOnWayAmount() {
		return onWayAmount;
	}

	public void setOnWayAmount(double onWayAmount) {
		this.onWayAmount = onWayAmount;
	}

	public double getRealSettlementAmount() {
		return realSettlementAmount;
	}

	public void setRealSettlementAmount(double realSettlementAmount) {
		this.realSettlementAmount = realSettlementAmount;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getContractorBank() {
		return contractorBank;
	}

	public void setContractorBank(String contractorBank) {
		this.contractorBank = contractorBank;
	}

	public String getContractorBankAccount() {
		return contractorBankAccount;
	}

	public void setContractorBankAccount(String contractorBankAccount) {
		this.contractorBankAccount = contractorBankAccount;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getGatheringUnit() {
		return gatheringUnit;
	}

	public void setGatheringUnit(String gatheringUnit) {
		this.gatheringUnit = gatheringUnit;
	}

	public String getSettlementReason() {
		return settlementReason;
	}

	public void setSettlementReason(String settlementReason) {
		this.settlementReason = settlementReason;
	}

	public String getAuditedById() {
		return auditedById;
	}

	public void setAuditedById(String auditedById) {
		this.auditedById = auditedById;
	}

	public String getAuditedBy() {
		return auditedBy;
	}

	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPayerDeptId() {
		return payerDeptId;
	}

	public void setPayerDeptId(String payerDeptId) {
		this.payerDeptId = payerDeptId;
	}

	public String getPayerDeptName() {
		return payerDeptName;
	}

	public void setPayerDeptName(String payerDeptName) {
		this.payerDeptName = payerDeptName;
	}

	public int getNoMonthReportSubmit() {
		return noMonthReportSubmit;
	}

	public void setNoMonthReportSubmit(int noMonthReportSubmit) {
		this.noMonthReportSubmit = noMonthReportSubmit;
	}

	public String getPaidById() {
		return paidById;
	}

	public void setPaidById(String paidById) {
		this.paidById = paidById;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public boolean getPaidFlag() {
		return paidFlag;
	}

	public void setPaidFlag(boolean paidFlag) {
		this.paidFlag = paidFlag;
	}

	public String getSettlementWay() {
		return settlementWay;
	}

	public void setSettlementWay(String settlementWay) {
		this.settlementWay = settlementWay;
	}

	public int getIsFillSwf() {
		return isFillSwf;
	}

	public void setIsFillSwf(int isFillSwf) {
		this.isFillSwf = isFillSwf;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public String getBankTellerIds() {
		return bankTellerIds;
	}

	public void setBankTellerIds(String bankTellerIds) {
		this.bankTellerIds = bankTellerIds;
	}

	public String getBankTellerNames() {
		return bankTellerNames;
	}

	public void setBankTellerNames(String bankTellerNames) {
		this.bankTellerNames = bankTellerNames;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getRemitProvinceId() {
		return remitProvinceId;
	}

	public void setRemitProvinceId(String remitProvinceId) {
		this.remitProvinceId = remitProvinceId;
	}

	public String getRemitProvince() {
		return remitProvince;
	}

	public void setRemitProvince(String remitProvince) {
		this.remitProvince = remitProvince;
	}

	public String getRemitCityId() {
		return remitCityId;
	}

	public void setRemitCityId(String remitCityId) {
		this.remitCityId = remitCityId;
	}

	public String getRemitCity() {
		return remitCity;
	}

	public void setRemitCity(String remitCity) {
		this.remitCity = remitCity;
	}

	public int getHasBankCode() {
		return hasBankCode;
	}

	public void setHasBankCode(int hasBankCode) {
		this.hasBankCode = hasBankCode;
	}

	public String getPrjManagerId() {
		return prjManagerId;
	}

	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}

	public String getPrjManager() {
		return prjManager;
	}

	public void setPrjManager(String prjManager) {
		this.prjManager = prjManager;
	}

	public String getRemittancePurpose() {
		return this.remittancePurpose;
	}

	public void setRemittancePurpose(String remittancePurpose) {
		this.remittancePurpose = remittancePurpose;
	}

	public String getCapticalPurposeCode() {
		return this.capticalPurposeCode;
	}

	public void setCapticalPurposeCode(String capticalPurposeCode) {
		this.capticalPurposeCode = capticalPurposeCode;
	}

}
