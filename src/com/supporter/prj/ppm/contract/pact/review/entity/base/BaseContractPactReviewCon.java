package com.supporter.prj.ppm.contract.pact.review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactReviewCon implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reviewConId; // 评审结论id
	private String reviewId; // 评审id
	private int reviewConStatus; // 评审结论状态
	private String reviewVerContent; // 评审验证内容

	/**
	 * 无参构造
	 */
	public BaseContractPactReviewCon() {
	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReviewCon(String reviewConId) {
		this.reviewConId = reviewConId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReviewCon(String reviewConId, String reviewId, int reviewConStatus, String reviewVerContent) {
		this.reviewConId = reviewConId;
		this.reviewId = reviewId;
		this.reviewConStatus = reviewConStatus;
		this.reviewVerContent = reviewVerContent;
	}

	@Id
	@Column(name = "rev_con_id", nullable = false, length = 32)
	public String getReviewConId() {
		return reviewConId;
	}

	public void setReviewConId(String reviewConId) {
		this.reviewConId = reviewConId;
	}

	@Column(name = "rev_id", nullable = true, length = 32)
	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "rev_con_status", nullable = true)
	public int getReviewConStatus() {
		return reviewConStatus;
	}

	public void setReviewConStatus(int reviewConStatus) {
		this.reviewConStatus = reviewConStatus;
	}

	@Column(name = "rev_verificat_content", nullable = true, length = 512)
	public String getReviewVerContent() {
		return reviewVerContent;
	}

	public void setReviewVerContent(String reviewVerContent) {
		this.reviewVerContent = reviewVerContent;
	}

}
