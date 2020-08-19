package com.supporter.prj.cneec.tpc.prj_settlement.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasePrjSettlementDiff implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "DIFF_ID", unique = true, nullable = false, length = 32)
	private String diffId;
	//
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	private String prjId;
	//
	@Column(name = "PRJ_NAME", nullable = true, length = 128)
	private String prjName;
	//
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	private String contractId;
	//
	@Column(name = "CONTRACT_NAME", nullable = true, length = 255)
	private String contractName;
	//
	@Column(name="MAIN_CONTRACT_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double mainContractAmount;
	//
	@Column(name="MAIN_CONTRACT_PROFIT" ,precision = 18, scale = 3, nullable = true)
	private double mainContractProfit;
	//
	@Column(name="SUB_CONTRACTS_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double subContractAmount;
	//
	@Column(name="EXAM_CONTRACTS_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double examContractAmount;
	//
	@Column(name="CONFIRMING_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double confirmingAmount;
	//
	@Column(name="NO_CONFIRM_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double noConfirmAmount;
	//
	@Column(name="COMPLETED_NO_CONTRACT" ,precision = 18, scale = 3, nullable = true)
	private double completedNoContract;
	//
	@Column(name="PROCESSING_NO_CONTRACT" ,precision = 18, scale = 3, nullable = true)
	private double processingNoContract;
	//
	@Column(name="CHANGING_ORIGINAL_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double changingOriginalAmount;
	//
	@Column(name="CHANGING_CHANGED_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double changingChangedAmount;
	//
	@Column(name="COMPLETED_EXPENSE_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double completedExpenseAmount;
	//
	@Column(name="PROCESSING_EXPENSE_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double processingExpenseAmount;
	//
	@Column(name="CAN_USED_AMOUNT" ,precision = 18, scale = 3, nullable = true)
	private double canUsedAmount;
	//
	@Column(name = "CONFIRM_DATE", nullable = true, length = 27)
	private String confirmDate;
	
	
	
	public BasePrjSettlementDiff() {
		super();
	}
	
	
	
	public BasePrjSettlementDiff(String diffId, String prjId, String prjName,
			String contractId, String contractName, double mainContractAmount,
			double mainContractProfit, double subContractAmount,
			double examContractAmount, double confirmingAmount,
			double noConfirmAmount, double completedNoContract,
			double processingNoContract, double changingOriginalAmount,
			double changingChangedAmount, double completedExpenseAmount,
			double processingExpenseAmount, double canUsedAmount,
			String confirmDate) {
		super();
		this.diffId = diffId;
		this.prjId = prjId;
		this.prjName = prjName;
		this.contractId = contractId;
		this.contractName = contractName;
		this.mainContractAmount = mainContractAmount;
		this.mainContractProfit = mainContractProfit;
		this.subContractAmount = subContractAmount;
		this.examContractAmount = examContractAmount;
		this.confirmingAmount = confirmingAmount;
		this.noConfirmAmount = noConfirmAmount;
		this.completedNoContract = completedNoContract;
		this.processingNoContract = processingNoContract;
		this.changingOriginalAmount = changingOriginalAmount;
		this.changingChangedAmount = changingChangedAmount;
		this.completedExpenseAmount = completedExpenseAmount;
		this.processingExpenseAmount = processingExpenseAmount;
		this.canUsedAmount = canUsedAmount;
		this.confirmDate = confirmDate;
	}



	public String getDiffId() {
		return diffId;
	}
	public void setDiffId(String diffId) {
		this.diffId = diffId;
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
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public double getMainContractAmount() {
		return mainContractAmount;
	}
	public void setMainContractAmount(double mainContractAmount) {
		this.mainContractAmount = mainContractAmount;
	}
	public double getMainContractProfit() {
		return mainContractProfit;
	}
	public void setMainContractProfit(double mainContractProfit) {
		this.mainContractProfit = mainContractProfit;
	}
	public double getSubContractAmount() {
		return subContractAmount;
	}
	public void setSubContractAmount(double subContractAmount) {
		this.subContractAmount = subContractAmount;
	}
	public double getExamContractAmount() {
		return examContractAmount;
	}
	public void setExamContractAmount(double examContractAmount) {
		this.examContractAmount = examContractAmount;
	}
	public double getConfirmingAmount() {
		return confirmingAmount;
	}
	public void setConfirmingAmount(double confirmingAmount) {
		this.confirmingAmount = confirmingAmount;
	}
	public double getNoConfirmAmount() {
		return noConfirmAmount;
	}
	public void setNoConfirmAmount(double noConfirmAmount) {
		this.noConfirmAmount = noConfirmAmount;
	}
	public double getCompletedNoContract() {
		return completedNoContract;
	}
	public void setCompletedNoContract(double completedNoContract) {
		this.completedNoContract = completedNoContract;
	}
	public double getProcessingNoContract() {
		return processingNoContract;
	}
	public void setProcessingNoContract(double processingNoContract) {
		this.processingNoContract = processingNoContract;
	}
	public double getChangingOriginalAmount() {
		return changingOriginalAmount;
	}
	public void setChangingOriginalAmount(double changingOriginalAmount) {
		this.changingOriginalAmount = changingOriginalAmount;
	}
	public double getChangingChangedAmount() {
		return changingChangedAmount;
	}
	public void setChangingChangedAmount(double changingChangedAmount) {
		this.changingChangedAmount = changingChangedAmount;
	}
	public double getCompletedExpenseAmount() {
		return completedExpenseAmount;
	}
	public void setCompletedExpenseAmount(double completedExpenseAmount) {
		this.completedExpenseAmount = completedExpenseAmount;
	}
	public double getProcessingExpenseAmount() {
		return processingExpenseAmount;
	}
	public void setProcessingExpenseAmount(double processingExpenseAmount) {
		this.processingExpenseAmount = processingExpenseAmount;
	}
	public double getCanUsedAmount() {
		return canUsedAmount;
	}
	public void setCanUsedAmount(double canUsedAmount) {
		this.canUsedAmount = canUsedAmount;
	}
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	
}
