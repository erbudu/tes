package com.supporter.prj.ppm.contract.sign.review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.review.entity.base.BaseContractSignRevEmpRp;

/**
 * @Title: Entity
 * @Description: 评审人员的评审要点.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_REV_EMP_RP", schema = "")
public class ContractSignRevEmpRp extends BaseContractSignRevEmpRp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignRevEmpRp() {
		super();
	}

	/**
	 * 构造函数.
	 * @param rpId
	 */
	public ContractSignRevEmpRp(String rpId) {
		super(rpId);
	}

}
