package com.supporter.prj.ppm.contract.sign.review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.review.entity.base.BaseContractSignReviewEmp;

/**
 * @Title: Entity
 * @Description: 评审人员.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_REVIEW_EMP", schema = "")
public class ContractSignReviewEmp extends BaseContractSignReviewEmp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignReviewEmp() {
		super();
	}

	/**
	 * 构造函数.
	 * @param reviewEmpId
	 */
	public ContractSignReviewEmp(String reviewEmpId) {
		super(reviewEmpId);
	}

}
