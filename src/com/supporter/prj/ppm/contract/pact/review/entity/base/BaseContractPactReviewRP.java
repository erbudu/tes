package com.supporter.prj.ppm.contract.pact.review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactReviewRP implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reviewPointId; // 主键id
	private String reviewEmpId; // 评审人员id
	private String reviewId; // 评审id
	private String prjId; // 拟评估项目ID
	private String revPointsId; // 评审要点ID,对应用印的模板中的大纲目录ID

	/**
	 * 无参构造
	 */
	public BaseContractPactReviewRP() {
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReviewRP(String reviewPointId, String reviewEmpId, String reviewId, String prjId, String revPointsId) {
		this.reviewPointId = reviewPointId;
		this.reviewEmpId = reviewEmpId;
		this.reviewId = reviewId;
		this.prjId = prjId;
		this.revPointsId = revPointsId;
	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReviewRP(String reviewPointId) {
		this.reviewPointId = reviewPointId;
	}

	@Id
	@Column(name = "rp_id", nullable = false, length = 32)
	public String getReviewPointId() {
		return reviewPointId;
	}

	public void setReviewPointId(String reviewPointId) {
		this.reviewPointId = reviewPointId;
	}

	@Column(name = "rev_emp_id", nullable = true, length = 32)
	public String getReviewEmpId() {
		return reviewEmpId;
	}

	public void setReviewEmpId(String reviewEmpId) {
		this.reviewEmpId = reviewEmpId;
	}

	@Column(name = "rev_id", nullable = true, length = 32)
	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "prj_id", nullable = true, length = 32)
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "rev_points_id", nullable = true, length = 32)
	public String getRevPointsId() {
		return revPointsId;
	}

	public void setRevPointsId(String revPointsId) {
		this.revPointsId = revPointsId;
	}




}
