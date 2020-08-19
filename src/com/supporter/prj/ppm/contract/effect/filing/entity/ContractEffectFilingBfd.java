package com.supporter.prj.ppm.contract.effect.filing.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.filing.entity.base.BaseContractEffectFilingBfd;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@Entity
@Table(name = "ppm_contract_effect_filing_bfd", schema = "")
public class ContractEffectFilingBfd extends BaseContractEffectFilingBfd implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectFilingBfd() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractEffectFilingBfd(String bfdId) {
		super(bfdId);
	}

}
