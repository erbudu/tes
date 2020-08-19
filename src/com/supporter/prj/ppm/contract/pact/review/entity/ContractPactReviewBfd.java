package com.supporter.prj.ppm.contract.pact.review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review.entity.base.BaseContractPactReviewBfd;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_BFD ", schema = "")
public class ContractPactReviewBfd extends BaseContractPactReviewBfd {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造
	 */
	public ContractPactReviewBfd() {
		super();
	}

	/**
	 * 全参构造
	 */
	public ContractPactReviewBfd(String reviewBfdId, String reviewId, String fileTypeId, String fileTypeName) {
		super(reviewBfdId, reviewId, fileTypeId, fileTypeName);
	}

	/**
	 * 构造函数
	 */
	public ContractPactReviewBfd(String reviewBfdId) {
		super(reviewBfdId);
	}

}
