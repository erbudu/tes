package com.supporter.prj.cneec.tpc.prj_contract_table.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_SETTLEMENT_PLAN,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-03-18
 * @version V1.0
 */
@MappedSuperclass
public class BaseSettlementPlan implements Serializable {

	private static final long serialVersionUID = 1L;
	private String settlementPlanId;
	private String contractId;
	private String projectId;
	private int settlementYear;
	private int settlementMonth;
	private double settlementAmount;

	/**
	 * 无参构造函数.
	 */
	public BaseSettlementPlan() {
	}

	/**
	 * 构造函数.
	 *
	 * @param settlementPlanId
	 */
	public BaseSettlementPlan(String settlementPlanId) {
		this.settlementPlanId = settlementPlanId;
	}

	/**
	 * GET方法: 取得SETTLEMENT_PLAN_ID.
	 * 
	 * @return: String SETTLEMENT_PLAN_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "SETTLEMENT_PLAN_ID", nullable = false, length = 32)
	public String getSettlementPlanId() {
		return this.settlementPlanId;
	}

	/**
	 * SET方法: 设置SETTLEMENT_PLAN_ID.
	 * 
	 * @param: String SETTLEMENT_PLAN_ID
	 */
	public void setSettlementPlanId(String settlementPlanId) {
		this.settlementPlanId = settlementPlanId;
	}

	/**
	 * GET方法: 取得CONTRACT_ID.
	 * 
	 * @return: String CONTRACT_ID
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * SET方法: 设置CONTRACT_ID.
	 * 
	 * @param: String CONTRACT_ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 * 
	 * @return: String PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 * 
	 * @param: String PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得SETTLEMENT_YEAR.
	 * 
	 * @return: int SETTLEMENT_YEAR
	 */
	@Column(name = "SETTLEMENT_YEAR", nullable = true, precision = 10)
	public int getSettlementYear() {
		return this.settlementYear;
	}

	/**
	 * SET方法: 设置SETTLEMENT_YEAR.
	 * 
	 * @param: int SETTLEMENT_YEAR
	 */
	public void setSettlementYear(int settlementYear) {
		this.settlementYear = settlementYear;
	}

	/**
	 * GET方法: 取得SETTLEMENT_MONTH.
	 * 
	 * @return: int SETTLEMENT_MONTH
	 */
	@Column(name = "SETTLEMENT_MONTH", nullable = true, precision = 10)
	public int getSettlementMonth() {
		return this.settlementMonth;
	}

	/**
	 * SET方法: 设置SETTLEMENT_MONTH.
	 * 
	 * @param: int SETTLEMENT_MONTH
	 */
	public void setSettlementMonth(int settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	/**
	 * GET方法: 取得SETTLEMENT_AMOUNT.
	 * 
	 * @return: double SETTLEMENT_AMOUNT
	 */
	@Column(name = "SETTLEMENT_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getSettlementAmount() {
		return this.settlementAmount;
	}

	/**
	 * SET方法: 设置SETTLEMENT_AMOUNT.
	 * 
	 * @param: double SETTLEMENT_AMOUNT
	 */
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

}
