package com.supporter.prj.ppm.contract.effect.review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.review.entity.base.BaseContractEffectRevEmpRp;

/**
 * @Title: Entity
 * @Description: 评审人员的评审要点.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@Entity
@Table(name = "ppm_contract_effect_rev_emp_rp", schema = "")
public class ContractEffectRevEmpRp extends BaseContractEffectRevEmpRp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectRevEmpRp() {
		super();
	}

	/**
	 * 构造函数.
	 * @param rpId
	 */
	public ContractEffectRevEmpRp(String rpId) {
		super(rpId);
	}

}
