package com.supporter.prj.ppm.ecc.base_info.fund_plan.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.fund_plan.entity.base.BaseEccFundPlan;

/**
 * @Title: Entity
 * @Description: 出口管制资金安排.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_FUND_PLAN", schema = "")
public class EccFundPlan extends BaseEccFundPlan implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccFundPlan() {
		super();
	}

	/**
	 * 构造函数.
	 * @param fpId
	 */
	public EccFundPlan(String fpId) {
		super(fpId);
	}

}
