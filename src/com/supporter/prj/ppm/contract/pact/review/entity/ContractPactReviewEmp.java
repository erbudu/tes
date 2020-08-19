package com.supporter.prj.ppm.contract.pact.review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review.entity.base.BaseContractPactReviewEmp;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_EMP ", schema = "")
public class ContractPactReviewEmp extends BaseContractPactReviewEmp {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造
	 */
	public ContractPactReviewEmp() {
		super();
	}

	/**
	 * 全参构造
	 */
	public ContractPactReviewEmp(String reviewEmpId, String reviewId, String prjId, String reviewRoleId, String reviewRole, String personId,
			String personName) {
		super(reviewEmpId, reviewId, prjId, reviewRoleId, reviewRole, personId, personName);
	}

	/**
	 * 构造函数
	 */
	public ContractPactReviewEmp(String reviewEmpId) {
		super(reviewEmpId);
	}

}
