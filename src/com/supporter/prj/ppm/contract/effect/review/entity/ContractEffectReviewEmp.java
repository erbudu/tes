package com.supporter.prj.ppm.contract.effect.review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.review.entity.base.BaseContractEffectReviewEmp;

/**
 * @Title: Entity
 * @Description: 评审人员.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_EFFECT_REVIEW_EMP", schema = "")
public class ContractEffectReviewEmp extends BaseContractEffectReviewEmp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectReviewEmp() {
		super();
	}

	/**
	 * 构造函数.
	 * @param reviewEmpId
	 */
	public ContractEffectReviewEmp(String reviewEmpId) {
		super(reviewEmpId);
	}

}
