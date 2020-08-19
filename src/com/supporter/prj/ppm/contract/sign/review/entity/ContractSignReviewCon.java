package com.supporter.prj.ppm.contract.sign.review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.review.entity.base.BaseContractSignReviewCon;

/**
 * @Title: Entity
 * @Description: 评审结果.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_REVIEW_CON", schema = "")
public class ContractSignReviewCon extends BaseContractSignReviewCon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignReviewCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param contractSignRevConId
	 */
	public ContractSignReviewCon(String contractSignRevConId) {
		super(contractSignRevConId);
	}

}
