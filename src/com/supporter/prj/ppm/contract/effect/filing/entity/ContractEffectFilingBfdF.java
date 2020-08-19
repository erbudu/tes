package com.supporter.prj.ppm.contract.effect.filing.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.filing.entity.base.BaseContractEffectFilingBfdF;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD_F.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@Entity
@Table(name = "ppm_contract_effect_fil_bfd_f", schema = "")
public class ContractEffectFilingBfdF extends BaseContractEffectFilingBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectFilingBfdF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractEffectFilingBfdF(String recordId) {
		super(recordId);
	}

}
