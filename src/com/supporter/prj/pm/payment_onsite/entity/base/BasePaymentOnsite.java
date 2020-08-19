package com.supporter.prj.pm.payment_onsite.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * PmPaymentOnsite entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePaymentOnsite implements java.io.Serializable {

	// Fields

	private String id;
	private String prjId;
	private String prjName;
	private Integer address;
	private String contractId;
	private String contractNo;
	private String contractName;
	private String contractTypeId;
	private String contractType;
	private String balanceId;
	private String balanceNo;
	private String balanceName;
	private String purchaseId;
	private String materialContractNo;
	private String materialContractName;
	private Integer paymentType;
	private String paymentClauseId;
	private String paymentClause;
	private String paymentNo;
	private Integer paymentNature;
	private Integer purpose;
	private String relationContractId;
	private String relationContractNo;
	private String relationContractName;
	private String proposer;
	private Date applyDate;
	private String budgetId;
	private String budgetNos;
	private String budgetName;
	private String voucherNo;
	private String loanDept;
	private double borrowAmount;
	private double estimateAmount;
	private double budgetAmount;
	
	
	private Integer paymentMethod;
	private String currencyId;
	private String currency;
	private double paymentAmount;
	private double exchangeRate;
	private double rmbAmount;
	private double usdRate;
	private double usdAmount;
	private double exchangeRateActual;
	private double rmbAmountActual;
	private double usdRateActual;
	private double usdAmountActual;
	private double lkrRateActual;
	private double lkrAmountActual;
	private String accountId;
	private String accountNo;
	private String receiverBank;
	private String receiverAccount;
	
	private Integer paymentMethodTwo;
	private String currencyTwoId;
	private String currencyTwo;
	private double paymentAmountTwo;
	private double exchangeRateTwo;
	private double rmbAmountTwo;
	private double usdRateTwo;
	private double usdAmountTwo;
	private double exchangeRateActualTwo;
	private double rmbAmountActualTwo;
	private double usdRateActualTwo;
	private double usdAmountActualTwo;
	private double lkrRateActualTwo;
	private double lkrAmountActualTwo;
	private String accountIdTwo;
	private String accountNoTwo;
	private String receiverBankTwo;
	private String receiverAccountTwo;
	
	private double engineeringAmount;
	private double installAmount;
	private String applyReason;
	private Integer status;
	private Integer statusActual;
	private Integer statusScan;
	private String createdById;
	private String createdBy;
	private String createdDeptId;
	private String createdDept;
	private Date createdDate;
	private String modifiedById;
	private String modifiedBy;
	private Date modifiedDate;
	private String procId;
	
	//合同金额、已付款、未付款
	private double signAmount;
	private double signAmountTwo;
	private double paidAmount;
	private double paidAmountTwo;
	private double unpaidAmount;
	private double unpaidAmountTwo;
	
	private boolean isReserveProduct = false; //是否提留安全生成措施费保证金
	private boolean isReservePeasant = false; //是否提留农民工工资保证金
	private double reserveAmount;
	

	// Constructors

	/** default constructor */
	public BasePaymentOnsite() {
	}

	/** minimal constructor */
	public BasePaymentOnsite(String id) {
		this.id = id;
	}

	/** full constructor */
	public BasePaymentOnsite(String id,String prjId,String prjName, Integer address, String contractId,
			String contractNo,String contractName, String contractTypeId, String contractType,
			Integer paymentType, String paymentClauseId,String paymentClause, String paymentNo,
			Integer paymentNature, Integer paymentMethod, Integer purpose,
			String proposer, Date applyDate, String currency,
			String budgetId,String budgetNos,String budgetName, double paymentAmount, double exchangeRate,
			double rmbAmount, double engineeringAmount, double installAmount,
			double borrowAmount, double estimateAmount, String accountId,String accountNo,
			String receiverBank,String receiverAccount,String applyReason,
			Integer status, Integer statusScan, String createdById, String createdBy,
			String createdDeptId, String createdDept, Date createdDate,
			String modifiedById, String modifiedBy, Date modifiedDate,String procId) {
		this.id = id;
		this.prjId = prjId;
		this.prjName = prjName;
		this.address = address;
		this.contractId = contractId;
		this.contractNo = contractNo;
		this.contractName = contractName;
		this.contractTypeId = contractTypeId;
		this.contractType = contractType;
		this.paymentType = paymentType;
		this.paymentClauseId = paymentClauseId;
		this.paymentClause = paymentClause;
		this.paymentNo = paymentNo;
		this.paymentNature = paymentNature;
		this.paymentMethod = paymentMethod;
		this.purpose = purpose;
		this.proposer = proposer;
		this.applyDate = applyDate;
		this.currency = currency;
		this.budgetId = budgetId;
		this.budgetNos = budgetNos;
		this.budgetName = budgetName;
		this.paymentAmount = paymentAmount;
		this.exchangeRate = exchangeRate;
		this.rmbAmount = rmbAmount;
		this.engineeringAmount = engineeringAmount;
		this.installAmount = installAmount;
		this.borrowAmount = borrowAmount;
		this.estimateAmount = estimateAmount;
		this.accountId = accountId;
		this.accountNo = accountNo;
		this.receiverAccount = receiverAccount;
		this.receiverBank = receiverBank;
		this.applyReason = applyReason;
		this.status = status;
		this.statusScan = statusScan;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDeptId = createdDeptId;
		this.createdDept = createdDept;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.procId = procId;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "prj_id", length = 32)
	public java.lang.String getPrjId() {
		return prjId;
	}

	public void setPrjId(java.lang.String prjId) {
		this.prjId = prjId;
	}
	
	@Column(name = "prj_name", length = 128)
	public java.lang.String getPrjName() {
		return prjName;
	}

	public void setPrjName(java.lang.String prjName) {
		this.prjName = prjName;
	}

	@Column(name = "address")
	public Integer getAddress() {
		return this.address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}

	@Column(name = "contract_id", length = 32)
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
		
	@Column(name = "contract_no", length = 32)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "contract_name", length = 128)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "contract_type_id", length = 32)
	public String getContractTypeId() {
		return this.contractTypeId;
	}

	public void setContractTypeId(String contractTypeId) {
		this.contractTypeId = contractTypeId;
	}

	@Column(name = "contract_type", length = 64)
	public String getContractType() {
		return this.contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	@Column(name = "balance_id", length = 32)
	public String getBalanceId() {
		return this.balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}
		
	@Column(name = "balance_no", length = 32)
	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	@Column(name = "balance_name", length = 128)
	public String getBalanceName() {
		return this.balanceName;
	}

	public void setBalanceName(String balanceName) {
		this.balanceName = balanceName;
	}
	
	@Column(name = "purchase_id", length = 32)
	public String getPurchaseId() {
		return this.purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
		
	@Column(name = "material_contract_no", length = 32)
	public String getMaterialContractNo() {
		return materialContractNo;
	}

	public void setMaterialContractNo(String materialContractNo) {
		this.materialContractNo = materialContractNo;
	}

	@Column(name = "material_contract_name", length = 128)
	public String getMaterialContractName() {
		return this.materialContractName;
	}

	public void setMaterialContractName(String materialContractName) {
		this.materialContractName = materialContractName;
	}

	@Column(name = "payment_type")
	public Integer getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "payment_clause_id", length = 32)
	public String getPaymentClauseId() {
		return this.paymentClauseId;
	}

	public void setPaymentClauseId(String paymentClauseId) {
		this.paymentClauseId = paymentClauseId;
	}
	@Column(name = "payment_clause", length = 64)
	public String getPaymentClause() {
		return this.paymentClause;
	}

	public void setPaymentClause(String paymentClause) {
		this.paymentClause = paymentClause;
	}

	@Column(name = "payment_no", length = 32)
	public String getPaymentNo() {
		return this.paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	@Column(name = "payment_nature")
	public Integer getPaymentNature() {
		return this.paymentNature;
	}

	public void setPaymentNature(Integer paymentNature) {
		this.paymentNature = paymentNature;
	}

	@Column(name = "payment_method")
	public Integer getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	@Column(name = "payment_method_two")
	public Integer getPaymentMethodTwo() {
		return this.paymentMethodTwo;
	}

	public void setPaymentMethodTwo(Integer paymentMethodTwo) {
		this.paymentMethodTwo = paymentMethodTwo;
	}

	@Column(name = "purpose")
	public Integer getPurpose() {
		return this.purpose;
	}

	public void setPurpose(Integer purpose) {
		this.purpose = purpose;
	}
	
	@Column(name = "relation_contract_id", length = 32)
	public String getRelationContractId() {
		return this.relationContractId;
	}

	public void setRelationContractId(String relationContractId) {
		this.relationContractId = relationContractId;
	}
		
	@Column(name = "relation_contract_no", length = 32)
	public String getRelationContractNo() {
		return relationContractNo;
	}

	public void setRelationContractNo(String relationContractNo) {
		this.relationContractNo = relationContractNo;
	}

	@Column(name = "relation_contract_name", length = 128)
	public String getRelationContractName() {
		return this.relationContractName;
	}

	public void setRelationContractName(String relationContractName) {
		this.relationContractName = relationContractName;
	}

	@Column(name = "proposer", length = 32)
	public String getProposer() {
		return this.proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "apply_date", length = 19)
	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "currency_id", length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name = "currency", length = 32)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "budget_id", length = 32)
	public String getBudgetId() {
		return this.budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}
	
	@Column(name = "budget_nos", length = 32)
	public String getBudgetNos() {
		return this.budgetNos;
	}

	public void setBudgetNos(String budgetNos) {
		this.budgetNos = budgetNos;
	}
	
	@Column(name = "budget_name", length = 64)
	public String getBudgetName() {
		return this.budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}
	
	@Column(name = "voucher_no", length = 64)
	public String getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	@Column(name = "loan_dept", length = 200)
	public String getLoanDept() {
		return this.loanDept;
	}

	public void setLoanDept(String loanDept) {
		this.loanDept = loanDept;
	}

	@Column(name = "payment_amount", precision = 18, scale = 3)
	public double getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	@Column(name = "currency_two_id", length = 32)
	public String getCurrencyTwoId() {
		return this.currencyTwoId;
	}

	public void setCurrencyTwoId(String currencyTwoId) {
		this.currencyTwoId = currencyTwoId;
	}
	
	@Column(name = "currency_two", length = 32)
	public String getCurrencyTwo() {
		return this.currencyTwo;
	}

	public void setCurrencyTwo(String currencyTwo) {
		this.currencyTwo = currencyTwo;
	}
	
	@Column(name = "payment_amount_two", precision = 18, scale = 3)
	public double getPaymentAmountTwo() {
		return this.paymentAmountTwo;
	}

	public void setPaymentAmountTwo(double paymentAmountTwo) {
		this.paymentAmountTwo = paymentAmountTwo;
	}

	@Column(name = "exchange_rate", precision = 18, scale = 4)
	public double getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	@Column(name = "exchange_rate_two", precision = 18, scale = 4)
	public double getExchangeRateTwo() {
		return this.exchangeRateTwo;
	}

	public void setExchangeRateTwo(double exchangeRateTwo) {
		this.exchangeRateTwo = exchangeRateTwo;
	}

	@Column(name = "rmb_amount", precision = 18, scale = 3)
	public double getRmbAmount() {
		return this.rmbAmount;
	}

	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}
	
	@Column(name = "rmb_amount_two", precision = 18, scale = 3)
	public double getRmbAmountTwo() {
		return this.rmbAmountTwo;
	}

	public void setRmbAmountTwo(double rmbAmountTwo) {
		this.rmbAmountTwo = rmbAmountTwo;
	}
	
	@Column(name = "usd_rate", precision = 18, scale = 4)
	public double getUsdRate() {
		return this.usdRate;
	}

	public void setUsdRate(double usdRate) {
		this.usdRate = usdRate;
	}
	
	@Column(name = "usd_rate_two", precision = 18, scale = 4)
	public double getUsdRateTwo() {
		return this.usdRateTwo;
	}

	public void setUsdRateTwo(double usdRateTwo) {
		this.usdRateTwo = usdRateTwo;
	}

	@Column(name = "usd_amount", precision = 18, scale = 3)
	public double getUsdAmount() {
		return this.usdAmount;
	}

	public void setUsdAmount(double usdAmount) {
		this.usdAmount = usdAmount;
	}
	
	@Column(name = "usd_amount_two", precision = 18, scale = 3)
	public double getUsdAmountTwo() {
		return this.usdAmountTwo;
	}

	public void setUsdAmountTwo(double usdAmountTwo) {
		this.usdAmountTwo = usdAmountTwo;
	}
	
	@Column(name = "exchange_rate_actual", precision = 18, scale = 4)
	public double getExchangeRateActual() {
		return this.exchangeRateActual;
	}

	public void setExchangeRateActual(double exchangeRateActual) {
		this.exchangeRateActual = exchangeRateActual;
	}
	
	@Column(name = "exchange_rate_actual_two", precision = 18, scale = 4)
	public double getExchangeRateActualTwo() {
		return this.exchangeRateActualTwo;
	}

	public void setExchangeRateActualTwo(double exchangeRateActualTwo) {
		this.exchangeRateActualTwo = exchangeRateActualTwo;
	}

	@Column(name = "rmb_amount_actual", precision = 18, scale = 3)
	public double getRmbAmountActual() {
		return this.rmbAmountActual;
	}

	public void setRmbAmountActual(double rmbAmountActual) {
		this.rmbAmountActual = rmbAmountActual;
	}
	
	@Column(name = "rmb_amount_actual_two", precision = 18, scale = 3)
	public double getRmbAmountActualTwo() {
		return this.rmbAmountActualTwo;
	}

	public void setRmbAmountActualTwo(double rmbAmountActualTwo) {
		this.rmbAmountActualTwo = rmbAmountActualTwo;
	}
	
	@Column(name = "usd_rate_actual", precision = 18, scale = 4)
	public double getUsdRateActual() {
		return this.usdRateActual;
	}

	public void setUsdRateActual(double usdRateActual) {
		this.usdRateActual = usdRateActual;
	}
	
	@Column(name = "usd_rate_actual_two", precision = 18, scale = 4)
	public double getUsdRateActualTwo() {
		return this.usdRateActualTwo;
	}

	public void setUsdRateActualTwo(double usdRateActualTwo) {
		this.usdRateActualTwo = usdRateActualTwo;
	}

	@Column(name = "usd_amount_actual", precision = 18, scale = 3)
	public double getUsdAmountActual() {
		return this.usdAmountActual;
	}

	public void setUsdAmountActual(double usdAmountActual) {
		this.usdAmountActual = usdAmountActual;
	}
	
	@Column(name = "usd_amount_actual_two", precision = 18, scale = 3)
	public double getUsdAmountActualTwo() {
		return this.usdAmountActualTwo;
	}

	public void setUsdAmountActualTwo(double usdAmountActualTwo) {
		this.usdAmountActualTwo = usdAmountActualTwo;
	}
	
	@Column(name = "lkr_rate_actual", precision = 18, scale = 4)
	public double getLkrRateActual() {
		return this.lkrRateActual;
	}

	public void setLkrRateActual(double lkrRateActual) {
		this.lkrRateActual = lkrRateActual;
	}
	
	@Column(name = "lkr_rate_actual_two", precision = 18, scale = 4)
	public double getLkrRateActualTwo() {
		return this.lkrRateActualTwo;
	}

	public void setLkrRateActualTwo(double lkrRateActualTwo) {
		this.lkrRateActualTwo = lkrRateActualTwo;
	}
	
	@Column(name = "lkr_amount_actual", precision = 18, scale = 3)
	public double getLkrAmountActual() {
		return this.lkrAmountActual;
	}

	public void setLkrAmountActual(double lkrAmountActual) {
		this.lkrAmountActual = lkrAmountActual;
	}
	
	@Column(name = "lkr_amount_actual_two", precision = 18, scale = 3)
	public double getLkrAmountActualTwo() {
		return this.lkrAmountActualTwo;
	}

	public void setLkrAmountActualTwo(double lkrAmountActualTwo) {
		this.lkrAmountActualTwo = lkrAmountActualTwo;
	}

	@Column(name = "engineering_amount", precision = 18, scale = 3)
	public double getEngineeringAmount() {
		return this.engineeringAmount;
	}

	public void setEngineeringAmount(double engineeringAmount) {
		this.engineeringAmount = engineeringAmount;
	}

	@Column(name = "install_amount", precision = 18, scale = 3)
	public double getInstallAmount() {
		return this.installAmount;
	}

	public void setInstallAmount(double installAmount) {
		this.installAmount = installAmount;
	}

	@Column(name = "borrow_amount", precision = 18, scale = 3)
	public double getBorrowAmount() {
		return this.borrowAmount;
	}

	public void setBorrowAmount(double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	@Column(name = "estimate_amount", precision = 18, scale = 3)
	public double getEstimateAmount() {
		return this.estimateAmount;
	}

	public void setEstimateAmount(double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}
	
	@Column(name = "budget_amount", precision = 18, scale = 3)
	public double getBudgetAmount() {
		return this.budgetAmount;
	}

	public void setBudgetAmount(double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	
	@Column(name = "account_id", length = 32)
	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Column(name = "account_id_two", length = 32)
	public String getAccountIdTwo() {
		return this.accountIdTwo;
	}

	public void setAccountIdTwo(String accountIdTwo) {
		this.accountIdTwo = accountIdTwo;
	}
	
	@Column(name = "account_no", length = 64)
	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Column(name = "account_no_two", length = 64)
	public String getAccountNoTwo() {
		return this.accountNoTwo;
	}

	public void setAccountNoTwo(String accountNoTwo) {
		this.accountNoTwo = accountNoTwo;
	}
	
	@Column(name = "receiver_bank", length = 32)
	public String getReceiverBank() {
		return receiverBank;
	}

	public void setReceiverBank(String receiverBank) {
		this.receiverBank = receiverBank;
	}
	
	@Column(name = "receiver_bank_two", length = 32)
	public String getReceiverBankTwo() {
		return receiverBankTwo;
	}

	public void setReceiverBankTwo(String receiverBankTwo) {
		this.receiverBankTwo = receiverBankTwo;
	}

	@Column(name = "receiver_account", length = 32)
	public String getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount) {
		this.receiverAccount = receiverAccount;
	}
	
	@Column(name = "receiver_account_two", length = 32)
	public String getReceiverAccountTwo() {
		return receiverAccountTwo;
	}

	public void setReceiverAccountTwo(String receiverAccountTwo) {
		this.receiverAccountTwo = receiverAccountTwo;
	}

	@Column(name = "apply_reason", length = 256)
	public String getApplyReason() {
		return this.applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "status_actual")
	public Integer getStatusActual() {
		return this.statusActual;
	}

	public void setStatusActual(Integer statusActual) {
		this.statusActual = statusActual;
	}
	
	@Column(name = "status_scan")
	public Integer getStatusScan() {
		return this.statusScan;
	}

	public void setStatusScan(Integer statusScan) {
		this.statusScan = statusScan;
	}

	@Column(name = "created_by_id", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "created_by", length = 64)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_dept_id", length = 32)
	public String getCreatedDeptId() {
		return this.createdDeptId;
	}

	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}

	@Column(name = "created_dept", length = 200)
	public String getCreatedDept() {
		return this.createdDept;
	}

	public void setCreatedDept(String createdDept) {
		this.createdDept = createdDept;
	}

	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "modified_by_id", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "modified_by", length = 64)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "modified_date", length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "proc_id", length = 32)
	public java.lang.String getProcId() {
		return procId;
	}

	public void setProcId(java.lang.String procId) {
		this.procId = procId;
	}
	
	@Column(name = "sign_amount", precision = 18, scale = 3)
	public double getSignAmount() {
		return this.signAmount;
	}
	public void setSignAmount(double signAmount) {
		this.signAmount = signAmount;
	}
	@Column(name = "sign_amount_two", precision = 18, scale = 3)
	public double getSignAmountTwo() {
		return this.signAmountTwo;
	}
	public void setSignAmountTwo(double signAmountTwo) {
		this.signAmountTwo = signAmountTwo;
	}
	
	@Column(name = "paid_amount", precision = 18, scale = 3)
	public double getPaidAmount() {
		return this.paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	@Column(name = "paid_amount_two", precision = 18, scale = 3)
	public double getPaidAmountTwo() {
		return this.paidAmountTwo;
	}
	public void setPaidAmountTwo(double paidAmountTwo) {
		this.paidAmountTwo = paidAmountTwo;
	}
	
	@Column(name = "unpaid_amount", precision = 18, scale = 3)
	public double getUnpaidAmount() {
		return this.unpaidAmount;
	}
	public void setUnpaidAmount(double unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}
	@Column(name = "unpaid_amount_two", precision = 18, scale = 3)
	public double getUnpaidAmountTwo() {
		return this.unpaidAmountTwo;
	}
	public void setUnpaidAmountTwo(double unpaidAmountTwo) {
		this.unpaidAmountTwo = unpaidAmountTwo;
	}
	
	/**
	 * 保证金有关字段
	 */
	@Column(name = "is_reserve_product", nullable = false, length = 1)
	@Type(type = "true_false")
	public boolean getIsReserveProduct() {
		return this.isReserveProduct;
	}
	public void setIsReserveProduct(boolean isReserveProduct) {
		this.isReserveProduct = isReserveProduct;
	}
	
	@Column(name = "is_reserve_peasant", nullable = false, length = 1)
	@Type(type = "true_false")
	public boolean getIsReservePeasant() {
		return this.isReservePeasant;
	}
	public void setIsReservePeasant(boolean isReservePeasant) {
		this.isReservePeasant = isReservePeasant;
	}
	
	@Column(name = "reserve_amount", precision = 18, scale = 3)
	public double getReserveAmount() {
		return this.reserveAmount;
	}
	public void setReserveAmount(double reserveAmount) {
		this.reserveAmount = reserveAmount;
	}
}