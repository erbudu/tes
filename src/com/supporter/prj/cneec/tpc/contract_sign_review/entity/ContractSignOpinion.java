package com.supporter.prj.cneec.tpc.contract_sign_review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.base.BaseContractSignOpinion;

/**   
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_OPINION.
 * @author Yanweichao
 * @date 2017-09-28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CONTRACT_SIGN_OPINION", schema = "")
public class ContractSignOpinion extends BaseContractSignOpinion {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignOpinion(){
		super();
	}

	/**
	 * 构造函数.
	 * @param opinionId
	 */
	public ContractSignOpinion(String opinionId) {
		super(opinionId);
	}

}
