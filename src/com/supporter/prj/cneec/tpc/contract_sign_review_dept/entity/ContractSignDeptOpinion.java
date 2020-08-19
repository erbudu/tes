package com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.base.BaseContractSignDeptOpinion;

/**   
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_DEPT_OPINION.
 * @author Yanweichao
 * @date 2018-03-21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CONTRACT_SIGN_DEPT_OPINION", schema = "")
public class ContractSignDeptOpinion extends BaseContractSignDeptOpinion {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignDeptOpinion(){
		super();
	}

	/**
	 * 构造函数.
	 * @param opinionId
	 */
	public ContractSignDeptOpinion(String opinionId) {
		super(opinionId);
	}

}
