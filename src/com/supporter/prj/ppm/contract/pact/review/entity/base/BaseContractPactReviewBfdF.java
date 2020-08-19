package com.supporter.prj.ppm.contract.pact.review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactReviewBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	private String recordId; // 清单文件id
	private String bfdId; // 资料清单id
	private String reviewId; // 评审id
	private String fuFileId; // 实际附件上传组件的文件ID
	private int isUseSeal; // 是否需要用印
	private int displayOrder; // 同一文件类型的排序号

	/**
	 * 无参构造
	 */
	public BaseContractPactReviewBfdF() {
	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReviewBfdF(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReviewBfdF(String recordId, String bfdId, String reviewId, String fuFileId, int isUseSeal, int displayOrder) {
		this.recordId = recordId;
		this.bfdId = bfdId;
		this.reviewId = reviewId;
		this.fuFileId = fuFileId;
		this.isUseSeal = isUseSeal;
		this.displayOrder = displayOrder;
	}

	@Id
	@Column(name = "record_id", nullable = true, length = 32)
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "bfd_id", nullable = true, length = 32)
	public String getBfdId() {
		return bfdId;
	}

	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	@Column(name = "rev_id", nullable = true, length = 32)
	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "fu_file_id", nullable = true, length = 32)
	public String getFuFileId() {
		return fuFileId;
	}

	public void setFuFileId(String fuFileId) {
		this.fuFileId = fuFileId;
	}

	@Column(name = "is_use_seal", nullable = true)
	public int getIsUseSeal() {
		return isUseSeal;
	}

	public void setIsUseSeal(int isUseSeal) {
		this.isUseSeal = isUseSeal;
	}

	@Column(name = "display_order", nullable = true)
	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}


}
