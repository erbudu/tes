package com.supporter.prj.cneec.tpc.prj_contract_table.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.base.BaseSettlementPlanBasic;

/**
 * @Title: Entity
 * @Description: TPC_SETTLEMENT_PLAN_BASIC.
 * @author Yanweichao
 * @date 2018-03-18
 * @version V1.0
 */
@Entity
@Table(name = "TPC_SETTLEMENT_PLAN_BASIC", schema = "")
public class SettlementPlanBasic extends BaseSettlementPlanBasic implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public SettlementPlanBasic() {
		super();
	}

	/**
	 * 构造函数.
	 * @param settlementPlanId
	 */
	public SettlementPlanBasic(String settlementPlanId) {
		super(settlementPlanId);
	}

}
