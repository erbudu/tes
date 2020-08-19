package com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

@MappedSuperclass
public class BasePrjContractSettlement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SETTLEMENT_ID", unique = true, nullable = false, length = 32)
	private String settlementId;
	
	//
	@Column(name="SETTLEMENT_NO" ,length=64, nullable = true)
	private String settlementNo;
	
	//付款编号-
	@Column(name = "SETTLEMENT_INDEX", precision = 10, scale = 0, nullable = true)
	private int settlementIndex;
	
	//审计人id
	@Column(name="AUDITED_BY_ID" ,length=32, nullable = true)
	private String auditedById;

	//审计人姓名
	@Column(name="AUDITED_BY" ,length=64, nullable = true)
	private String auditedBy;

	//付款人id-
	@Column(name="PAID_BY_ID" ,length=32, nullable = true)
	private String paidById;

	//付款人名称-
	@Column(name="PAID_BY" ,length=64, nullable = true)
	private String paidBy;

	//项目id-
	@Column(name="PRJ_ID" ,length=32, nullable = true)
	private String prjId;

	//项目名称-
	@Column(name="PRJ_NAME" ,length=128, nullable = true)
	private String prjName;

	//合同id-
	@Column(name="CONTRACT_ID" ,length=32, nullable = true)
	private String contractId;

	// 合同编号
	@Column(name = "CONTRACT_NO", length = 128, nullable = true)
	private String contractNo;

	// 合同名称-
	@Column(name = "CONTRACT_NAME", length = 512, nullable = true)
	private String contractName;

	//供应商id
	@Column(name = "CONTRACTOR_ID", length = 32, nullable = true)
	private String contractorId;

	// 供应商名称-
	@Column(name = "CONTRACTOR_NAME", length = 128, nullable = true)
	private String contractorName;

	// 收款单位-
	@Column(name = "GATHERING_UNIT", length = 128, nullable = true)
	private String gatheringUnit;

	// 供应商付款银行
	@Column(name = "CONTRACTOR_BANK", length = 256, nullable = true)
	private String contractorBank;

	// 供应商付款银行账户
	@Column(name = "CONTRACTOR_BANK_ACCOUNT", length = 256, nullable = true)
	private String contractorBankAccount;

	@Column(name = "BANK_CODE", nullable = true, length = 24)
	private String bankCode;

	//
	@Column(name="AMOUNT_SETTLEMENT" ,precision = 18, scale = 3, nullable = true)
	private double amountSettlement;

	//
	@Column(name="SETTLEMENT_AMOUNT_ACT" ,precision = 18, scale = 3, nullable = true)
	private double settlementAmountAct;

	//-
	@Column(name="SETTLMENT_DATE" ,length=27, nullable = true)
	private String settlementDate;
	
	//付款部门id-
	@Column(name="PAYER_DEPT_ID" ,length=32, nullable = true)
	private String payerDeptId;

	//受理年份-
	@Column(name="SETTLEMENT_YEAR" ,precision = 4, scale = 0, nullable = true)
	private int settlementYear;

	//币种码表编号
	@Column(name="CURRENCY_TYPE_CODE" ,length=32, nullable = true)
	private String currencyTypeCode;

	//币种名称
	@Column(name="CURRENCY_TYPE" ,length=32, nullable = true)
	private String currencyType;

	//
	@Column(name="TASK_NO" ,length=32, nullable = true)
	private String taskNo;

	//付款方式
	@Column(name="PAY_METHOD" ,length=64, nullable = true)
	private String payMethod;

	//
	@Column(name="SETTLEMENT_AMOUNT_F" ,precision = 18, scale = 3, nullable = true)
	private double settlementAmountF;
	
	//
	@Column(name="CONTRACT_MANAGER" ,length=32, nullable = true)
	private String contractManager;
	
	//
	@Column(name="CONTRACT_AUDITOR" ,length=32, nullable = true)
	private String contractAuditor;

	//
	@Column(name="SETTLEMENT_HANDLER_ID" ,length=32, nullable = true)
	private String settlementHandlerId;

	//-
	@Column(name="SETTLEMENT_HANDLER" ,length=32, nullable = true)
	private String settlementHandler;

	//合同审批状态
	@Column(name="SETTLEMENT_STATUS" ,precision = 2, scale = 0, nullable = true)
	private int settlementStatus;

	//创建人名称-
	@Column(name="CREATED_BY" ,length=64, nullable = true)
	private String createdBy;

	//创建人id-
	@Column(name="CREATED_BY_ID" ,length=32, nullable = true)
	private String createdById;

	//创建时间-
	@Column(name="CREATED_DATE" ,length=27, nullable = true)
	private String createdDate;

	//修改人名称-
	@Column(name="MODIFIED_BY" ,length=64, nullable = true)
	private String modifiedBy;

	//修改人id-
	@Column(name="MODIFIED_BY_ID" ,length=32, nullable = true)
	private String modifiedById;

	//修改时间-
	@Column(name="MODIFIED_DATE" ,length=27, nullable = true)
	private String modifiedDate;
	
	//
	@Column(name="CONFIRM_DATE" ,length=27, nullable = true)
	private String confirmDate;
	
	//true/false-
	@Type(type = "true_false")
	@Column(name="PAID_FLAG" ,length=1, nullable = true)
	private boolean paidFlag;

	//
	@Column(name="SETTLEMENT_ACT_DESC" ,length=512, nullable = true)
	private String settlementActDesc;

	//付款部门名称-
	@Column(name="PAYER_DEPT_NAME" ,length=128, nullable = true)
	private String payerDeptName;

	//
	@Column(name="SETTLEMENT_REASON" ,length=1024, nullable = true)
	private String settlementReason;

	//
	@Column(name="TASK_NAME" ,length=255, nullable = true)
	private String taskName;

	//
	@Column(name="STTL_AMOUNT_F_CURRENCY_TYPE" ,length=64, nullable = true)
	private String sttlAmountFCurrentyType;
	
	//提交日期-
	@Column(name="SETTLEMENT_APPLY_DATE" ,length=27, nullable = true)
	private String settlementApplyDate;

	//是否是合格供方
	@Column(name="IS_QUALIFIED" ,length=1, nullable = true)
	private String isQualified;

	//受理月份-
	@Column(name="SETTLEMENT_MONTH" ,precision = 2, scale = 0, nullable = true)
	private int settlementMonth;

	//
	@Column(name="PAYMENT_STATUS" ,precision = 2, scale = 0, nullable = true)
	private int paymentStatus;

	//
	@Column(name="ON_WAY_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double onWayAmount;

	//
	@Column(name="ON_WAY_AMOUNT_F" ,precision = 18, scale = 3, nullable = true)
	private double onWayAmountF;

	//
	@Column(name="REAL_SETTLEMENT_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double realSettlementAmount;

	//
	@Column(name="REAL_SETTLEMENT_AMOUNT_F" ,precision = 18, scale = 3, nullable = true)
	private double realSettlementAmountF;
	
	//
	@Column(name="TOTAL_SETTLEMENT_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double totalSettlementAmount;
	
	//
	@Column(name="TOTAL_SETTLEMENT_RATE" ,precision = 18, scale = 5, nullable = true)
	private double totalSettlementRate;

	//
	@Column(name="TOTAL_INVOICE_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double totalInvoiceAmount;

	//
	@Column(name="TOTAL_INVOICE_RATE" ,precision = 18, scale = 3, nullable = true)
	private double totalInvoiceRate;

	//
	@Column(name="REAL_PAYMENT_DATE" ,length=27, nullable = true)
	private String realPaymentDate;

	//
	@Column(name="CONTROL_STATUS" ,length=32, nullable = true)
	private String controlStatus;

	//
	@Column(name="CONTROL_STATUS_CODE" ,length=32, nullable = true)
	private String controlStatusCode;

	//
	@Column(name="NO_MONTH_REPORT_SUBMIT" ,precision = 2, scale = 0, nullable = true)
	private int noMonthReportSubmit;

	//
	@Column(name="IS_HISTORY" ,precision = 2, scale = 0, nullable = true)
	private int isHistory;
	
	// 付款途径
	@Column(name="IS_SITE_PAY" ,precision = 2, scale = 0, nullable = true)
	private int isSitePay;
	//
	@Column(name="IS_FULL_SWF" ,precision = 2, scale = 0, nullable = true)
	private int isFullSwf;
	
	//
	@Column(name="PRINT_COUNT" ,precision = 2, scale = 0, nullable = true)
	private int printCount;
	
	//
	@Column(name="BANK_TELLER_IDS" ,length=512, nullable = true)
	private String bankTellerIds;
	
	//
	@Column(name="BANK_TELLER_NAMES" ,length=512, nullable = true)
	private String bankTellerNames;

	//流程id
	@Column(name="PROC_ID" ,length=32, nullable = true)
	private String procId;
	
	//项目负责人id
	@Column(name="PRJ_MANAGER_ID" ,length=32, nullable = true)
	private String prjManagerId;

	@Column(name="PRJ_MANAGER" ,length=32, nullable = true)
	private String prjManager;

	//是否需要公司领导审批
	@Column(name="IS_LEADERS_EXAM" ,precision = 2, scale = 0, nullable = true)
	private int isLeadersExam;

	@Column(name = "REFUND_STATUS", nullable = true, precision = 10)
	private int refundStatus;

	@Column(name="REMITTANCE_PURPOSE" ,length=500, nullable = true)
	private String remittancePurpose;

	@Column(name="CAPTICAL_PURPOSE_CODE" ,length=10, nullable = true)
	private String capticalPurposeCode;

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
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

	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public double getAmountSettlement() {
		return amountSettlement;
	}

	public void setAmountSettlement(double amoutSettlement) {
		this.amountSettlement = amoutSettlement;
	}

	public double getSettlementAmountAct() {
		return settlementAmountAct;
	}

	public void setSettlementAmountAct(double settlementAmountAct) {
		this.settlementAmountAct = settlementAmountAct;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getPayerDeptId() {
		return payerDeptId;
	}

	public void setPayerDeptId(String payerDeptId) {
		this.payerDeptId = payerDeptId;
	}

	public int getSettlementYear() {
		return settlementYear;
	}

	public void setSettlementYear(int settlementYear) {
		this.settlementYear = settlementYear;
	}

	public String getCurrencyTypeCode() {
		return currencyTypeCode;
	}

	public void setCurrencyTypeCode(String currencyTypeCode) {
		this.currencyTypeCode = currencyTypeCode;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
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

	public double getSettlementAmountF() {
		return settlementAmountF;
	}

	public void setSettlementAmountF(double settlementAmountF) {
		this.settlementAmountF = settlementAmountF;
	}

	public String getContractManager() {
		return contractManager;
	}

	public void setContractManager(String contractManager) {
		this.contractManager = contractManager;
	}

	public String getContractAuditor() {
		return contractAuditor;
	}

	public void setContractAuditor(String contractAuditor) {
		this.contractAuditor = contractAuditor;
	}

	public String getSettlementHandler() {
		return settlementHandler;
	}

	public void setSettlementHandler(String settlementHandler) {
		this.settlementHandler = settlementHandler;
	}

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
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

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public boolean getPaidFlag() {
		return paidFlag;
	}

	public void setPaidFlag(boolean paidFlag) {
		this.paidFlag = paidFlag;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getSettlementActDesc() {
		return settlementActDesc;
	}

	public void setSettlementActDesc(String settlementActDesc) {
		this.settlementActDesc = settlementActDesc;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getPayerDeptName() {
		return payerDeptName;
	}

	public void setPayerDeptName(String payerDeptName) {
		this.payerDeptName = payerDeptName;
	}

	public String getSettlementReason() {
		return settlementReason;
	}

	public void setSettlementReason(String settlementReason) {
		this.settlementReason = settlementReason;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getSettlementHandlerId() {
		return settlementHandlerId;
	}

	public void setSettlementHandlerId(String settlementHandlerId) {
		this.settlementHandlerId = settlementHandlerId;
	}

	public String getSttlAmountFCurrentyType() {
		return sttlAmountFCurrentyType;
	}

	public void setSttlAmountFCurrentyType(String sttlAmountFCurrentyType) {
		this.sttlAmountFCurrentyType = sttlAmountFCurrentyType;
	}

	public String getSettlementApplyDate() {
		return settlementApplyDate;
	}

	public void setSettlementApplyDate(String settlementApplyDate) {
		this.settlementApplyDate = settlementApplyDate;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}

	public int getSettlementMonth() {
		return settlementMonth;
	}

	public void setSettlementMonth(int settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public double getOnWayAmount() {
		return onWayAmount;
	}

	public void setOnWayAmount(double onWayAmount) {
		this.onWayAmount = onWayAmount;
	}

	public double getOnWayAmountF() {
		return onWayAmountF;
	}

	public void setOnWayAmountF(double onWayAmountF) {
		this.onWayAmountF = onWayAmountF;
	}

	public double getRealSettlementAmount() {
		return realSettlementAmount;
	}

	public void setRealSettlementAmount(double realSettlementAmount) {
		this.realSettlementAmount = realSettlementAmount;
	}

	public double getRealSettlementAmountF() {
		return realSettlementAmountF;
	}

	public void setRealSettlementAmountF(double realSettlementAmountF) {
		this.realSettlementAmountF = realSettlementAmountF;
	}

	public double getTotalSettlementAmount() {
		return totalSettlementAmount;
	}

	public void setTotalSettlementAmount(double totalSettlementAmount) {
		this.totalSettlementAmount = totalSettlementAmount;
	}

	public double getTotalSettlementRate() {
		return totalSettlementRate;
	}

	public void setTotalSettlementRate(double totalSettlementRate) {
		this.totalSettlementRate = totalSettlementRate;
	}

	public double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(double totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}

	public double getTotalInvoiceRate() {
		return totalInvoiceRate;
	}

	public void setTotalInvoiceRate(double totalInvoiceRate) {
		this.totalInvoiceRate = totalInvoiceRate;
	}

	public String getGatheringUnit() {
		return gatheringUnit;
	}

	public void setGatheringUnit(String gatheringUnit) {
		this.gatheringUnit = gatheringUnit;
	}

	public String getRealPaymentDate() {
		return realPaymentDate;
	}

	public void setRealPaymentDate(String realPaymentDate) {
		this.realPaymentDate = realPaymentDate;
	}

	public String getControlStatus() {
		return controlStatus;
	}

	public void setControlStatus(String controlStatus) {
		this.controlStatus = controlStatus;
	}

	public String getControlStatusCode() {
		return controlStatusCode;
	}

	public void setControlStatusCode(String controlStatusCode) {
		this.controlStatusCode = controlStatusCode;
	}

	public int getNoMonthReportSubmit() {
		return noMonthReportSubmit;
	}

	public void setNoMonthReportSubmit(int noMonthReportSubmit) {
		this.noMonthReportSubmit = noMonthReportSubmit;
	}

	public int getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(int isHistory) {
		this.isHistory = isHistory;
	}

	public int getIsSitePay() {
		return isSitePay;
	}

	public void setIsSitePay(int isSitePay) {
		this.isSitePay = isSitePay;
	}

	public int getIsFullSwf() {
		return isFullSwf;
	}

	public void setIsFullSwf(int isFullSwf) {
		this.isFullSwf = isFullSwf;
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

	public int getIsLeadersExam() {
		return isLeadersExam;
	}

	public void setIsLeadersExam(int isLeadersExam) {
		this.isLeadersExam = isLeadersExam;
	}

	public int getRefundStatus() {
		return this.refundStatus;
	}

	public void setRefundStatus(int refundStatus) {
		this.refundStatus = refundStatus;
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

	public BasePrjContractSettlement() {
		super();
	}
	
}
