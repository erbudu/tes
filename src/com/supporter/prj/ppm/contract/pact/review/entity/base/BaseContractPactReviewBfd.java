package com.supporter.prj.ppm.contract.pact.review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactReviewBfd implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reviewBfdId; // 资料清单id
	private String reviewId; // 评审id
	private String fileTypeId; // 文件类型ID
	private String fileTypeName; // 文件类型名称

	/**
	 * 无参构造
	 */
	public BaseContractPactReviewBfd() {
	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReviewBfd(String reviewBfdId) {
		this.reviewBfdId = reviewBfdId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReviewBfd(String reviewBfdId, String reviewId, String fileTypeId, String fileTypeName) {
		this.reviewBfdId = reviewBfdId;
		this.reviewId = reviewId;
		this.fileTypeId = fileTypeId;
		this.fileTypeName = fileTypeName;
	}

	@Id
	@Column(name = "bfd_id", nullable = false, length = 32)
	public String getReviewBfdId() {
		return reviewBfdId;
	}

	public void setReviewBfdId(String reviewBfdId) {
		this.reviewBfdId = reviewBfdId;
	}

	@Column(name = "rev_id", nullable = true, length = 32)
	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "file_type_id", nullable = true, length = 32)
	public String getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	@Column(name = "file_type_name", nullable = true, length = 256)
	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

}
