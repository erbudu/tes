package com.supporter.prj.cneec.tpc.prj_contract_modify.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_SETTLEMENT_PLAN_BASIC,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-03-18
 * @version V1.0
 */
@MappedSuperclass
public class BaseSettlementPlanBM implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bmId;
	private String settlementPlanId;
	private String changeId;
	private String projectId;
	private int settlementYear;
	private int settlementMonth;
	private double settlementAmount;

	/**
	 * 无参构造函数.
	 */
	public BaseSettlementPlanBM() {
	}

	/**
	 * 构造函数.
	 *
	 * @param bmId
	 */
	public BaseSettlementPlanBM(String bmId) {
		this.bmId = bmId;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "BM_ID", nullable = true, length = 32)
	public String getBmId() {
		return bmId;
	}

	public void setBmId(String bmId) {
		this.bmId = bmId;
	}

	@Column(name = "SETTLEMENT_PLAN_ID", nullable = false, length = 32)
	public String getSettlementPlanId() {
		return this.settlementPlanId;
	}

	public void setSettlementPlanId(String settlementPlanId) {
		this.settlementPlanId = settlementPlanId;
	}

	@Column(name = "CHANGE_ID", nullable = true, length = 32)
	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "SETTLEMENT_YEAR", nullable = true, precision = 10)
	public int getSettlementYear() {
		return this.settlementYear;
	}

	public void setSettlementYear(int settlementYear) {
		this.settlementYear = settlementYear;
	}

	@Column(name = "SETTLEMENT_MONTH", nullable = true, precision = 10)
	public int getSettlementMonth() {
		return this.settlementMonth;
	}

	public void setSettlementMonth(int settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	@Column(name = "SETTLEMENT_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

}
