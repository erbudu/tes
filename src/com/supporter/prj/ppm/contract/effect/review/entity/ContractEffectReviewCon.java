package com.supporter.prj.ppm.contract.effect.review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.review.entity.base.BaseContractEffectReviewCon;

/**
 * @Title: Entity
 * @Description: 评审结果.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@Entity
@Table(name = "ppm_contract_effect_rev_con", schema = "")
public class ContractEffectReviewCon extends BaseContractEffectReviewCon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectReviewCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param contractEffectRevConId
	 */
	public ContractEffectReviewCon(String contractEffectRevConId) {
		super(contractEffectRevConId);
	}

}
