package com.supporter.prj.ppm.contract.effect.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.review_ver.entity.base.BaseContractEffectRevVerRp;

/**
 * @Title: Entity
 * @Description: 验证评审要点表.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_EFFECT_REV_VER_RP", schema = "")
public class ContractEffectRevVerRp extends BaseContractEffectRevVerRp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectRevVerRp() {
		super();
	}

	/**
	 * 构造函数.
	 * @param rpId
	 */
	public ContractEffectRevVerRp(String rpId) {
		super(rpId);
	}

}
