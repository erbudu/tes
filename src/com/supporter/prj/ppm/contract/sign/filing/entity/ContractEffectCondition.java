package com.supporter.prj.ppm.contract.sign.filing.entity;

import com.supporter.prj.ppm.contract.sign.filing.entity.base.BaseContractEffectCondition;
import com.supporter.prj.ppm.contract.sign.filing.entity.base.BaseContractFilingBfdF;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD_F.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_EFFECT_CONDITION", schema = "")
public class ContractEffectCondition extends BaseContractEffectCondition implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectCondition() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractEffectCondition(String recordId) {
		super(recordId);
	}

}
