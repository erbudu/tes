package com.supporter.prj.cneec.tpc.benefit_budget.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: BaseBenefitContractBudget
 * @Description: TPC_BENEFIT_CONTRACT_BUDGET,字段与数据库字段一一对应.
 * @author: Yanweichao
 * @date: 2018-05-29
 * @version: V1.0
 */
@MappedSuperclass
public class BaseBenefitContractBudget implements Serializable {

	private static final long serialVersionUID = 1L;
	private String processBudgetId;
	private String processId;
	private String serialNumber;
	private String budgetId;
	private String budgetName;
	private String parentBudgetId;
	private double currency1Amount;
	private double currency2Amount;
	private double currency3Amount;
	private double currency4Amount;
	private double currency5Amount;
	private double currency6Amount;
	private double currency7Amount;
	private double currency8Amount;
	private double currency9Amount;
	private double totalRmbAmount;
	private double proportion;
	private int orderDisplay;

	/**
	 * 无参构造函数.
	 */
	public BaseBenefitContractBudget() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param processBudgetId
	 */
	public BaseBenefitContractBudget(String processBudgetId) {
		this.processBudgetId = processBudgetId;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "PROCESS_BUDGET_ID", nullable = false, length = 32)
	public String getProcessBudgetId() {
		return this.processBudgetId;
	}

	public void setProcessBudgetId(String processBudgetId) {
		this.processBudgetId = processBudgetId;
	}

	@Column(name = "PROCESS_ID", nullable = true, length = 32)
	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Column(name = "SERIAL_NUMBER", nullable = true, length = 32)
	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "BUDGET_ID", nullable = true, length = 64)
	public String getBudgetId() {
		return this.budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	@Column(name = "BUDGET_NAME", nullable = true, length = 64)
	public String getBudgetName() {
		return this.budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	@Column(name = "PARENT_BUDGET_ID", nullable = true, length = 64)
	public String getParentBudgetId() {
		return this.parentBudgetId;
	}

	public void setParentBudgetId(String parentBudgetId) {
		this.parentBudgetId = parentBudgetId;
	}

	@Column(name = "CURRENCY1_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency1Amount() {
		return this.currency1Amount;
	}

	public void setCurrency1Amount(double currency1Amount) {
		this.currency1Amount = currency1Amount;
	}

	@Column(name = "CURRENCY2_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency2Amount() {
		return this.currency2Amount;
	}

	public void setCurrency2Amount(double currency2Amount) {
		this.currency2Amount = currency2Amount;
	}

	@Column(name = "CURRENCY3_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency3Amount() {
		return this.currency3Amount;
	}

	public void setCurrency3Amount(double currency3Amount) {
		this.currency3Amount = currency3Amount;
	}

	@Column(name = "CURRENCY4_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency4Amount() {
		return this.currency4Amount;
	}

	public void setCurrency4Amount(double currency4Amount) {
		this.currency4Amount = currency4Amount;
	}

	@Column(name = "CURRENCY5_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency5Amount() {
		return this.currency5Amount;
	}

	public void setCurrency5Amount(double currency5Amount) {
		this.currency5Amount = currency5Amount;
	}

	@Column(name = "CURRENCY6_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency6Amount() {
		return this.currency6Amount;
	}

	public void setCurrency6Amount(double currency6Amount) {
		this.currency6Amount = currency6Amount;
	}

	@Column(name = "CURRENCY7_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency7Amount() {
		return this.currency7Amount;
	}

	public void setCurrency7Amount(double currency7Amount) {
		this.currency7Amount = currency7Amount;
	}

	@Column(name = "CURRENCY8_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency8Amount() {
		return this.currency8Amount;
	}

	public void setCurrency8Amount(double currency8Amount) {
		this.currency8Amount = currency8Amount;
	}

	@Column(name = "CURRENCY9_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCurrency9Amount() {
		return this.currency9Amount;
	}

	public void setCurrency9Amount(double currency9Amount) {
		this.currency9Amount = currency9Amount;
	}

	@Column(name = "TOTAL_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getTotalRmbAmount() {
		return this.totalRmbAmount;
	}

	public void setTotalRmbAmount(double totalRmbAmount) {
		this.totalRmbAmount = totalRmbAmount;
	}

	@Column(name = "PROPORTION", nullable = true, precision = 10, scale = 4)
	public double getProportion() {
		return this.proportion;
	}

	public void setProportion(double proportion) {
		this.proportion = proportion;
	}

	@Column(name = "ORDER_DISPLAY", nullable = true, precision = 10)
	public int getOrderDisplay() {
		return this.orderDisplay;
	}

	public void setOrderDisplay(int orderDisplay) {
		this.orderDisplay = orderDisplay;
	}

}
