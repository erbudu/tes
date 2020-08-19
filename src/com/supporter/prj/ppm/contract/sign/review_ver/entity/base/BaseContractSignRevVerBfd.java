package com.supporter.prj.ppm.contract.sign.review_ver.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 验证标前评审资料清单,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractSignRevVerBfd implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bfdId;
	private String reviewVerId;
	private String contractSignId;
	private String bfdTypeId;
	private String bfdTypeName;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignRevVerBfd() {
	}

	/**
	 * 构造函数.
	 *
	 * @param bfdId
	 */
	public BaseContractSignRevVerBfd(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 方法: 取得BFD_ID.
	 * @return: java.lang.String BFD_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "BFD_ID", nullable = false, length = 32)
	public String getBfdId() {
		return this.bfdId;
	}

	/**
	 * 方法: 设置BFD_ID.
	 * @param: java.lang.String BFD_ID
	 */
	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 方法: 取得REVIEW_VER_ID.
	 * @return: java.lang.String REVIEW_VER_ID
	 */
	@Column(name = "REVIEW_VER_ID", nullable = true, length = 32)
	public String getReviewVerId() {
		return this.reviewVerId;
	}

	/**
	 * 方法: 设置REVIEW_VER_ID.
	 * @param: java.lang.String REVIEW_VER_ID
	 */
	public void setReviewVerId(String reviewVerId) {
		this.reviewVerId = reviewVerId;
	}

	/**
	 * 方法: 取得CONTRACT_SIGN_ID.
	 * @return: java.lang.String CONTRACT_SIGN_ID
	 */
	@Column(name = "CONTRACT_SIGN_ID", nullable = true, length = 32)
	public String getContractSignId() {
		return this.contractSignId;
	}

	/**
	 * 方法: 设置CONTRACT_SIGN_ID.
	 * @param: java.lang.String CONTRACT_SIGN_ID
	 */
	public void setContractSignId(String contractSignId) {
		this.contractSignId = contractSignId;
	}

	/**
	 * 方法: 取得文件类型id.
	 * @return: java.lang.String 文件类型id
	 */
	@Column(name = "BFD_TYPE_ID", nullable = true, length = 32)
	public String getBfdTypeId() {
		return this.bfdTypeId;
	}

	/**
	 * 方法: 设置文件类型id.
	 * @param: java.lang.String 文件类型id
	 */
	public void setBfdTypeId(String bfdTypeId) {
		this.bfdTypeId = bfdTypeId;
	}

	/**
	 * 方法: 取得文件类型名称.
	 * @return: java.lang.String 文件类型名称
	 */
	@Column(name = "BFD_TYPE_NAME", nullable = true, length = 256)
	public String getBfdTypeName() {
		return this.bfdTypeName;
	}

	/**
	 * 方法: 设置文件类型名称.
	 * @param: java.lang.String 文件类型名称
	 */
	public void setBfdTypeName(String bfdTypeName) {
		this.bfdTypeName = bfdTypeName;
	}

}
