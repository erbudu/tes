package com.supporter.prj.ppm.ecc.wg_review.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制工作组评审结论,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccWgRevieCon implements Serializable {

	private static final long serialVersionUID = 1L;
	private String wgEccRvConId;
	private String wgEccId;
	private String eccId;
	private int rvConStatus;
	private int rvConBussinesStatus;

	/**
	 * 无参构造函数.
	 */
	public BaseEccWgRevieCon() {
	}

	/**
	 * 构造函数.
	 *
	 * @param wgEccRvConId
	 */
	public BaseEccWgRevieCon(String wgEccRvConId) {
		this.wgEccRvConId = wgEccRvConId;
	}

	/**
	 * 方法: 取得WG_ECC_RV_CON_ID.
	 * @return: java.lang.String WG_ECC_RV_CON_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "WG_ECC_RV_CON_ID", nullable = false, length = 32)
	public String getWgEccRvConId() {
		return this.wgEccRvConId;
	}

	/**
	 * 方法: 设置WG_ECC_RV_CON_ID.
	 * @param: java.lang.String WG_ECC_RV_CON_ID
	 */
	public void setWgEccRvConId(String wgEccRvConId) {
		this.wgEccRvConId = wgEccRvConId;
	}

	/**
	 * 方法: 取得管制审核主键.
	 * @return: java.lang.String 管制审核主键
	 */
	@Column(name = "WG_ECC_ID", nullable = true, length = 32)
	public String getWgEccId() {
		return this.wgEccId;
	}

	/**
	 * 方法: 设置管制审核主键.
	 * @param: java.lang.String 管制审核主键
	 */
	public void setWgEccId(String wgEccId) {
		this.wgEccId = wgEccId;
	}

	/**
	 * 方法: 取得出口管制id.
	 * @return: java.lang.String 出口管制id
	 */
	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return this.eccId;
	}

	/**
	 * 方法: 设置出口管制id.
	 * @param: java.lang.String 出口管制id
	 */
	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 方法: 取得评审结论状态.
	 * @return: int 评审结论状态
	 */
	@Column(name = "RV_CON_STATUS", nullable = true, precision = 10)
	public int getRvConStatus() {
		return this.rvConStatus;
	}

	/**
	 * 方法: 设置评审结论状态.
	 * @param: int 评审结论状态
	 */
	public void setRvConStatus(int rvConStatus) {
		this.rvConStatus = rvConStatus;
	}

	/**
	 * 方法: 取得评审结论业务状态 1 情况清晰有限制制裁；2 情况复杂的有限制制裁\全面制裁.
	 * @return: int 评审结论业务状态 1 情况清晰有限制制裁；2 情况复杂的有限制制裁\全面制裁
	 */
	@Column(name = "RV_CON_BUSSINES_STATUS", nullable = true, precision = 10)
	public int getRvConBussinesStatus() {
		return this.rvConBussinesStatus;
	}

	/**
	 * 方法: 设置评审结论业务状态 1 情况清晰有限制制裁；2 情况复杂的有限制制裁\全面制裁.
	 * @param: int 评审结论业务状态 1 情况清晰有限制制裁；2 情况复杂的有限制制裁\全面制裁
	 */
	public void setRvConBussinesStatus(int rvConBussinesStatus) {
		this.rvConBussinesStatus = rvConBussinesStatus;
	}

}
