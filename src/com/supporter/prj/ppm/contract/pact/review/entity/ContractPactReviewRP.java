package com.supporter.prj.ppm.contract.pact.review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review.entity.base.BaseContractPactReviewRP;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_RP ", schema = "")
public class ContractPactReviewRP extends BaseContractPactReviewRP {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造
	 */
	public ContractPactReviewRP() {
		super();
	}

	/**
	 * 全参构造
	 */
	public ContractPactReviewRP(String reviewPointId, String reviewEmpId, String reviewId, String prjId, String revPointsId) {
		super(reviewPointId, reviewEmpId, reviewId, prjId, revPointsId);
	}

	/**
	 * 构造函数
	 */
	public ContractPactReviewRP(String reviewPointId) {
		super(reviewPointId);
	}

}
