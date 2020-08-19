package com.supporter.prj.ppm.contract.effect.seal_bfd.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.base.BaseContractEffectSealBfdF;

/**
 * @Title: Entity
 * @Description: 资料清单文件.
 * @author: YAN
 * @date: 2019-09-10
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_EFFECT_SEAL_BFD_F", schema = "")
public class ContractEffectSealBfdF extends BaseContractEffectSealBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectSealBfdF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractEffectSealBfdF(String recordId) {
		super(recordId);
	}

}
