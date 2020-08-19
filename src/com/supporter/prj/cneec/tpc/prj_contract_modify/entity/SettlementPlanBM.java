package com.supporter.prj.cneec.tpc.prj_contract_modify.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.base.BaseSettlementPlanBM;

/**
 * @Title: Entity
 * @Description: TPC_SETTLEMENT_PLAN_BM.
 * @author Yanweichao
 * @date 2018-03-18
 * @version V1.0
 */
@Entity
@Table(name = "TPC_SETTLEMENT_PLAN_BM", schema = "")
public class SettlementPlanBM extends BaseSettlementPlanBM implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public SettlementPlanBM() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bmId
	 */
	public SettlementPlanBM(String bmId) {
		super(bmId);
	}

}
