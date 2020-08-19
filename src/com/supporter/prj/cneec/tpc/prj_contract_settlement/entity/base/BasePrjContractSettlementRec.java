package com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasePrjContractSettlementRec implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 32)
	private String recordId;

	// 付款单id
	@Column(name = "SETTLEMENT_ID", length = 32, nullable = true)
	private String settlementId;

	// 付款条款id
	@Column(name = "PAYMENT_TERMS_ID", length = 32, nullable = true)
	private String paymentTermsId;

	// 付款条款
	@Column(name = "PAYMENT_TERMS", length = 32, nullable = true)
	private String paymentTerms;
	//
	@Column(name = "BUDGET_YEAR", precision = 4, scale = 0, nullable = true)
	private String budgetYear;

	//
	@Column(name = "BUDGET_MONTH", precision = 4, scale = 0, nullable = true)
	private String budgetMonth;

	// 本次申请付款金额
	@Column(name = "SETTLEMENT_AMOUNT_F", precision = 18, scale = 3, nullable = true)
	private double settlementAmountF;

	// 折合人民币金额
	@Column(name = "SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double settlementAmount;

	// 预算额
	@Column(name = "CONTRACT_BUDGET_VALUE", precision = 18, scale = 3, nullable = true)
	private double contractBudgetValue;

	// 付款计划额
	@Column(name = "SETTLEMENT_PLAN_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double settlementPlanAmount;

	// 已付款
	@Column(name = "USED_SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double usedSettlementAmount;

	// 可付金额
	@Column(name = "USEABLE_SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double useableSettlementAmount;

	// 币别编号
	@Column(name = "CURRENCY_TYPE_CODE", length = 32, nullable = true)
	private String currencyTypeCode;

	// 币别名称
	@Column(name = "CURRENCY_TYPE", length = 32, nullable = true)
	private String currencyType;

	//
	@Column(name = "CURRENT_PLAN_PERCENT", length = 16, nullable = true)
	private int currentPlanPercent;

	//
	@Column(name = "ON_WAY_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double onWayAmount;

	//
	@Column(name = "ON_WAY_AMOUNT_F", precision = 18, scale = 3, nullable = true)
	private double onWayAmountF;

	//
	@Column(name = "REAL_SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double realSettlementAmount;

	//
	@Column(name = "REAL_SETTLEMENT_AMOUNT_F", precision = 18, scale = 3, nullable = true)
	private double realSettlementAmountF;

	//
	@Column(name = "REAL_CURRENCY_TYPE_CODE", length = 32, nullable = true)
	private String realCurrencyTypeCode;

	//
	@Column(name = "REAL_CURRENCY_TYPE", length = 32, nullable = true)
	private String realCurrencyType;

	//
	@Column(name = "REAL_SETTLEMENT_AMOUNT_P_F", precision = 18, scale = 3, nullable = true)
	private double realSettlementAmountPF;

	// 描述
	@Column(name = "SETTLEMENT_DESC", length = 1024, nullable = true)
	private String settlementDesc;

	public BasePrjContractSettlementRec() {
		super();
	}

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getSettlementId() {
		return this.settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getPaymentTermsId() {
		return this.paymentTermsId;
	}

	public void setPaymentTermsId(String paymentTermsId) {
		this.paymentTermsId = paymentTermsId;
	}

	public String getPaymentTerms() {
		return this.paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getBudgetYear() {
		return this.budgetYear;
	}

	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}

	public String getBudgetMonth() {
		return this.budgetMonth;
	}

	public void setBudgetMonth(String budgetMonth) {
		this.budgetMonth = budgetMonth;
	}

	public double getSettlementAmountF() {
		return this.settlementAmountF;
	}

	public void setSettlementAmountF(double settlementAmountF) {
		this.settlementAmountF = settlementAmountF;
	}

	public double getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public double getContractBudgetValue() {
		return this.contractBudgetValue;
	}

	public void setContractBudgetValue(double contractBudgetValue) {
		this.contractBudgetValue = contractBudgetValue;
	}

	public double getSettlementPlanAmount() {
		return this.settlementPlanAmount;
	}

	public void setSettlementPlanAmount(double settlementPlanAmount) {
		this.settlementPlanAmount = settlementPlanAmount;
	}

	public double getUsedSettlementAmount() {
		return this.usedSettlementAmount;
	}

	public void setUsedSettlementAmount(double usedSettlementAmount) {
		this.usedSettlementAmount = usedSettlementAmount;
	}

	public double getUseableSettlementAmount() {
		return this.useableSettlementAmount;
	}

	public void setUseableSettlementAmount(double useableSettlementAmount) {
		this.useableSettlementAmount = useableSettlementAmount;
	}

	public String getCurrencyTypeCode() {
		return this.currencyTypeCode;
	}

	public void setCurrencyTypeCode(String currencyTypeCode) {
		this.currencyTypeCode = currencyTypeCode;
	}

	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public int getCurrentPlanPercent() {
		return this.currentPlanPercent;
	}

	public void setCurrentPlanPercent(int currentPlanPercent) {
		this.currentPlanPercent = currentPlanPercent;
	}

	public double getOnWayAmount() {
		return this.onWayAmount;
	}

	public void setOnWayAmount(double onWayAmount) {
		this.onWayAmount = onWayAmount;
	}

	public double getOnWayAmountF() {
		return this.onWayAmountF;
	}

	public void setOnWayAmountF(double onWayAmountF) {
		this.onWayAmountF = onWayAmountF;
	}

	public double getRealSettlementAmount() {
		return this.realSettlementAmount;
	}

	public void setRealSettlementAmount(double realSettlementAmount) {
		this.realSettlementAmount = realSettlementAmount;
	}

	public double getRealSettlementAmountF() {
		return this.realSettlementAmountF;
	}

	public void setRealSettlementAmountF(double realSettlementAmountF) {
		this.realSettlementAmountF = realSettlementAmountF;
	}

	public String getRealCurrencyTypeCode() {
		return this.realCurrencyTypeCode;
	}

	public void setRealCurrencyTypeCode(String realCurrencyTypeCode) {
		this.realCurrencyTypeCode = realCurrencyTypeCode;
	}

	public String getRealCurrencyType() {
		return this.realCurrencyType;
	}

	public void setRealCurrencyType(String realCurrencyType) {
		this.realCurrencyType = realCurrencyType;
	}

	public double getRealSettlementAmountPF() {
		return this.realSettlementAmountPF;
	}

	public void setRealSettlementAmountPF(double realSettlementAmountPF) {
		this.realSettlementAmountPF = realSettlementAmountPF;
	}

	public String getSettlementDesc() {
		return this.settlementDesc;
	}

	public void setSettlementDesc(String settlementDesc) {
		this.settlementDesc = settlementDesc;
	}

}
