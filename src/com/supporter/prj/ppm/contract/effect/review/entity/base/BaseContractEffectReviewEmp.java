package com.supporter.prj.ppm.contract.effect.review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 评审人员,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectReviewEmp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String reviewEmpId;
	private String contractEffectReviewId;
	private String prjId;
	private String reviewRoleId;
	private String reviewRole;
	private String personId;
	private String personName;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectReviewEmp() {
	}

	/**
	 * 构造函数.
	 *
	 * @param reviewEmpId
	 */
	public BaseContractEffectReviewEmp(String reviewEmpId) {
		this.reviewEmpId = reviewEmpId;
	}

	/**
	 * 方法: 取得REVIEW_EMP_ID.
	 * @return: java.lang.String REVIEW_EMP_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "REVIEW_EMP_ID", nullable = false, length = 32)
	public String getReviewEmpId() {
		return this.reviewEmpId;
	}

	/**
	 * 方法: 设置REVIEW_EMP_ID.
	 * @param: java.lang.String REVIEW_EMP_ID
	 */
	public void setReviewEmpId(String reviewEmpId) {
		this.reviewEmpId = reviewEmpId;
	}

	/**
	 * 方法: 取得评审单id.
	 * @return: java.lang.String 评审单id
	 */
	@Column(name = "CONTRACT_EFFECT_REVIEW_ID", nullable = true, length = 32)
	public String getContractEffectReviewId() {
		return this.contractEffectReviewId;
	}

	/**
	 * 方法: 设置评审单id.
	 * @param: java.lang.String 评审单id
	 */
	public void setContractEffectReviewId(String contractEffectReviewId) {
		this.contractEffectReviewId = contractEffectReviewId;
	}

	/**
	 * 方法: 取得项目id.
	 * @return: java.lang.String 项目id
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	/**
	 * 方法: 设置项目id.
	 * @param: java.lang.String 项目id
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * 方法: 取得评审角色id.
	 * @return: java.lang.String 评审角色id
	 */
	@Column(name = "REVIEW_ROLE_ID", nullable = true, length = 32)
	public String getReviewRoleId() {
		return this.reviewRoleId;
	}

	/**
	 * 方法: 设置评审角色id.
	 * @param: java.lang.String 评审角色id
	 */
	public void setReviewRoleId(String reviewRoleId) {
		this.reviewRoleId = reviewRoleId;
	}

	/**
	 * 方法: 取得评审角色.
	 * @return: java.lang.String 评审角色
	 */
	@Column(name = "REVIEW_ROLE", nullable = true, length = 128)
	public String getReviewRole() {
		return this.reviewRole;
	}

	/**
	 * 方法: 设置评审角色.
	 * @param: java.lang.String 评审角色
	 */
	public void setReviewRole(String reviewRole) {
		this.reviewRole = reviewRole;
	}

	/**
	 * 方法: 取得评审人员id.
	 * @return: java.lang.String 评审人员id
	 */
	@Column(name = "PERSON_ID", nullable = true, length = 32)
	public String getPersonId() {
		return this.personId;
	}

	/**
	 * 方法: 设置评审人员id.
	 * @param: java.lang.String 评审人员id
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * 方法: 取得评审人员.
	 * @return: java.lang.String 评审人员
	 */
	@Column(name = "PERSON_NAME", nullable = true, length = 128)
	public String getPersonName() {
		return this.personName;
	}

	/**
	 * 方法: 设置评审人员.
	 * @param: java.lang.String 评审人员
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
