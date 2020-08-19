package com.supporter.prj.ppm.contract.pact.review.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review.entity.base.BaseContractPactReviewBfdF;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_BFD_F ", schema = "")
public class ContractPactReviewBfdF extends BaseContractPactReviewBfdF {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造
	 */
	public ContractPactReviewBfdF() {
		super();
	}

	/**
	 * 全参构造
	 */
	public ContractPactReviewBfdF(String recordId, String bfdId, String reviewId, String fuFileId, int isUseSeal, int displayOrder) {
		super(recordId, bfdId, reviewId, fuFileId, isUseSeal, displayOrder);
	}

	/**
	 * 构造函数
	 */
	public ContractPactReviewBfdF(String recordId) {
		super(recordId);
	}

}
