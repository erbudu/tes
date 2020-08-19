package com.supporter.prj.cneec.tpc.prj_contract_table.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.base.BaseSettlementPlan;

/**
 * @Title: Entity
 * @Description: TPC_SETTLEMENT_PLAN.
 * @author Yanweichao
 * @date 2018-03-18
 * @version V1.0
 */
@Entity
@Table(name = "TPC_SETTLEMENT_PLAN", schema = "")
public class SettlementPlan extends BaseSettlementPlan implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public SettlementPlan() {
		super();
	}

	/**
	 * 构造函数.
	 * @param settlementPlanId
	 */
	public SettlementPlan(String settlementPlanId) {
		super(settlementPlanId);
	}

}
