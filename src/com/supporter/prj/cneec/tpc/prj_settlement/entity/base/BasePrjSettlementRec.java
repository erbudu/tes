package com.supporter.prj.cneec.tpc.prj_settlement.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasePrjSettlementRec implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 32)
	private String recordId;
	//
	@Column(name = "SETTLEMENT_ID", nullable = true, length = 32)
	private String settlementId;
	//
	@Column(name = "BUDGET_ID", nullable = true, length = 64)
	private String budgetId;
	//
	@Column(name = "BUDGET_NAME", nullable = true, length = 64)
	private String budgetName;
	//
	@Column(name = "BUDGET_YEAR", precision = 10, scale = 0, nullable = true)
	private int budgetYear;
	//
	@Column(name = "BUDGET_MONTH", precision = 10, scale = 0, nullable = true)
	private int budgetMonth;
	//本次付款原币
	@Column(name = "SETTLEMENT_AMOUNT_F", precision = 18, scale = 3, nullable = true)
	private double settlementAmountF;
	//币别code
	@Column(name = "CURRENCY_TYPE_CODE", nullable = true, length = 32)
	private String currencyTypeCode;
	//
	@Column(name = "CURRENCY_TYPE", nullable = true, length = 32)
	private String currencyType;
	//付款折人民币
	@Column(name = "SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double settlementAmount;
	//预算额
	@Column(name = "BUDGET_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double budgetAmount;
	//已付金额
	@Column(name = "USED_SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double usedSettlementAmount;
	//可付金额
	@Column(name = "USEABLE_SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double useableSettlementAmount;
	//
	@Column(name = "SETTLEMENT_DESC", nullable = true, length = 1024)
	private String settlementDesc;
	//实际付款原币额
	@Column(name = "REAL_SETTLEMENT_AMOUNT_F", precision = 18, scale = 3, nullable = true)
	private double realSettlementAmountF;
	//实际付款折人民币
	@Column(name = "REAL_SETTLEMENT_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double realSettlementAmount;
	//在途原币额
	@Column(name = "ON_WAY_AMOUNT_F", precision = 18, scale = 3, nullable = true)
	private double onWayAmountF;
	//在途折人民币额
	@Column(name = "ON_WAY_AMOUNT", precision = 18, scale = 3, nullable = true)
	private double onWayAmount;
	//实际付款币别code
	@Column(name = "REAL_CURRENCY_TYPE_CODE", nullable = true, length = 32)
	private String realCurrencyTypeCode;
	//
	@Column(name = "REAL_CURRENCY_TYPE", nullable = true, length = 32)
	private String realCurrencyType;
	//
	@Column(name = "REAL_SETTLEMENT_AMOUNT_P_F", precision = 18, scale = 3, nullable = true)
	private double realSettlementAmountPF;
	
	public BasePrjSettlementRec() {
		super();
	}

	public BasePrjSettlementRec(String recordId, String settlementId,
			String budgetId, String budgetName, int budgetYear,
			int budgetMonth, double settlementAmountF, String currencyTypeCode,
			String currencyType, double settlementAmount, double budgetAmount,
			double usedSettlementAmount, double useableSettlementAmount,
			String settlementDesc, double realSettlementAmountF,
			double realSettlementAmount, double onWayAmountF,
			double onWayAmount, String realCurrencyTypeCode,
			String realCurrencyType, double realSettlementAmountPF) {
		super();
		this.recordId = recordId;
		this.settlementId = settlementId;
		this.budgetId = budgetId;
		this.budgetName = budgetName;
		this.budgetYear = budgetYear;
		this.budgetMonth = budgetMonth;
		this.settlementAmountF = settlementAmountF;
		this.currencyTypeCode = currencyTypeCode;
		this.currencyType = currencyType;
		this.settlementAmount = settlementAmount;
		this.budgetAmount = budgetAmount;
		this.usedSettlementAmount = usedSettlementAmount;
		this.useableSettlementAmount = useableSettlementAmount;
		this.settlementDesc = settlementDesc;
		this.realSettlementAmountF = realSettlementAmountF;
		this.realSettlementAmount = realSettlementAmount;
		this.onWayAmountF = onWayAmountF;
		this.onWayAmount = onWayAmount;
		this.realCurrencyTypeCode = realCurrencyTypeCode;
		this.realCurrencyType = realCurrencyType;
		this.realSettlementAmountPF = realSettlementAmountPF;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	public int getBudgetYear() {
		return budgetYear;
	}

	public void setBudgetYear(int budgetYear) {
		this.budgetYear = budgetYear;
	}

	public int getBudgetMonth() {
		return budgetMonth;
	}

	public void setBudgetMonth(int budgetMonth) {
		this.budgetMonth = budgetMonth;
	}

	public double getSettlementAmountF() {
		return settlementAmountF;
	}

	public void setSettlementAmountF(double settlementAmountF) {
		this.settlementAmountF = settlementAmountF;
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

	public double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public double getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public double getUsedSettlementAmount() {
		return usedSettlementAmount;
	}

	public void setUsedSettlementAmount(double usedSettlementAmount) {
		this.usedSettlementAmount = usedSettlementAmount;
	}

	public double getUseableSettlementAmount() {
		return useableSettlementAmount;
	}

	public void setUseableSettlementAmount(double useableSettlementAmount) {
		this.useableSettlementAmount = useableSettlementAmount;
	}

	public String getSettlementDesc() {
		return settlementDesc;
	}

	public void setSettlementDesc(String settlementDesc) {
		this.settlementDesc = settlementDesc;
	}

	public double getRealSettlementAmountF() {
		return realSettlementAmountF;
	}

	public void setRealSettlementAmountF(double realSettlementAmountF) {
		this.realSettlementAmountF = realSettlementAmountF;
	}

	public double getRealSettlementAmount() {
		return realSettlementAmount;
	}

	public void setRealSettlementAmount(double realSettlementAmount) {
		this.realSettlementAmount = realSettlementAmount;
	}

	public double getOnWayAmountF() {
		return onWayAmountF;
	}

	public void setOnWayAmountF(double onWayAmountF) {
		this.onWayAmountF = onWayAmountF;
	}

	public double getOnWayAmount() {
		return onWayAmount;
	}

	public void setOnWayAmount(double onWayAmount) {
		this.onWayAmount = onWayAmount;
	}

	public String getRealCurrencyTypeCode() {
		return realCurrencyTypeCode;
	}

	public void setRealCurrencyTypeCode(String realCurrencyTypeCode) {
		this.realCurrencyTypeCode = realCurrencyTypeCode;
	}

	public String getRealCurrencyType() {
		return realCurrencyType;
	}

	public void setRealCurrencyType(String realCurrencyType) {
		this.realCurrencyType = realCurrencyType;
	}

	public double getRealSettlementAmountPF() {
		return realSettlementAmountPF;
	}

	public void setRealSettlementAmountPF(double realSettlementAmountPF) {
		this.realSettlementAmountPF = realSettlementAmountPF;
	}
	
	
	

}
