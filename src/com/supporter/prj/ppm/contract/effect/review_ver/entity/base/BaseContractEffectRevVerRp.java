package com.supporter.prj.ppm.contract.effect.review_ver.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 验证评审要点表,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectRevVerRp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String rpId;
	private String reviewVerId;
	private String contractEffectReviewId;
	private String prjId;
	private String reviewPointsId;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectRevVerRp() {
	}

	/**
	 * 构造函数.
	 *
	 * @param rpId
	 */
	public BaseContractEffectRevVerRp(String rpId) {
		this.rpId = rpId;
	}

	/**
	 * 方法: 取得RP_ID.
	 * @return: java.lang.String RP_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "RP_ID", nullable = false, length = 32)
	public String getRpId() {
		return this.rpId;
	}

	/**
	 * 方法: 设置RP_ID.
	 * @param: java.lang.String RP_ID
	 */
	public void setRpId(String rpId) {
		this.rpId = rpId;
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
	 * 方法: 取得CONTRACT_EFFECT_REVIEW_ID.
	 * @return: java.lang.String CONTRACT_EFFECT_REVIEW_ID
	 */
	@Column(name = "CONTRACT_EFFECT_REVIEW_ID", nullable = true, length = 32)
	public String getContractEffectReviewId() {
		return this.contractEffectReviewId;
	}

	/**
	 * 方法: 设置CONTRACT_EFFECT_REVIEW_ID.
	 * @param: java.lang.String CONTRACT_EFFECT_REVIEW_ID
	 */
	public void setContractEffectReviewId(String contractEffectReviewId) {
		this.contractEffectReviewId = contractEffectReviewId;
	}

	/**
	 * 方法: 取得拟评估项目id.
	 * @return: java.lang.String 拟评估项目id
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	/**
	 * 方法: 设置拟评估项目id.
	 * @param: java.lang.String 拟评估项目id
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * 方法: 取得评审要点id.
	 * @return: java.lang.String 评审要点id
	 */
	@Column(name = "REVIEW_POINTS_ID", nullable = true, length = 32)
	public String getReviewPointsId() {
		return this.reviewPointsId;
	}

	/**
	 * 方法: 设置评审要点id.
	 * @param: java.lang.String 评审要点id
	 */
	public void setReviewPointsId(String reviewPointsId) {
		this.reviewPointsId = reviewPointsId;
	}

}
