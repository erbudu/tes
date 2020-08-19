package com.supporter.prj.cneec.tpc.benefit_note.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_V_BENEFIT_NOTE_ASSEMBLE
 * @author: Yanweichao
 * @date: 2018-05-29
 * @version: V1.0
 */
@MappedSuperclass
public class BaseVBenefitNoteAssemble implements Serializable {

	private static final long serialVersionUID = 1L;
	private String assembleId;
	private String serialNumber;
	private String budgetId;
	private String budgetName;
	private String parentBudgetId;
	private double sumTotalAmount;
	private double sumTotalProportion;
	private int orderDisplay;
	private String projectId;

	/**
	 * 无参构造函数.
	 */
	public BaseVBenefitNoteAssemble() {
	}

	@Id
	@Column(name = "ASSEMBLE_ID", nullable = true, length = 97)
	public String getAssembleId() {
		return this.assembleId;
	}

	public void setAssembleId(String assembleId) {
		this.assembleId = assembleId;
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

	@Column(name = "SUM_TOTAL_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getSumTotalAmount() {
		return this.sumTotalAmount;
	}

	public void setSumTotalAmount(double sumTotalAmount) {
		this.sumTotalAmount = sumTotalAmount;
	}

	@Column(name = "SUM_TOTAL_PROPORTION", nullable = true, precision = 10, scale = 4)
	public double getSumTotalProportion() {
		return this.sumTotalProportion;
	}

	public void setSumTotalProportion(double sumTotalProportion) {
		this.sumTotalProportion = sumTotalProportion;
	}

	@Column(name = "ORDER_DISPLAY", nullable = true, precision = 10)
	public int getOrderDisplay() {
		return this.orderDisplay;
	}

	public void setOrderDisplay(int orderDisplay) {
		this.orderDisplay = orderDisplay;
	}

	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
