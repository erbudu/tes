package com.supporter.prj.ppm.ecc.group_review.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 集团评审结论,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccGroupRevieCon implements Serializable {

	private static final long serialVersionUID = 1L;
	private String groupEccRvConId;
	private String groupEccId;
	private java.util.Date realStartDate;
	private java.util.Date realEndDate;
	private String eccId;
	private int rvConStatus;

	/**
	 * 无参构造函数.
	 */
	public BaseEccGroupRevieCon() {
	}

	/**
	 * 构造函数.
	 *
	 * @param groupEccRvConId
	 */
	public BaseEccGroupRevieCon(String groupEccRvConId) {
		this.groupEccRvConId = groupEccRvConId;
	}

	/**
	 * 方法: 取得GROUP_ECC_RV_CON_ID.
	 * @return: java.lang.String GROUP_ECC_RV_CON_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "GROUP_ECC_RV_CON_ID", nullable = false, length = 32)
	public String getGroupEccRvConId() {
		return this.groupEccRvConId;
	}

	/**
	 * 方法: 设置GROUP_ECC_RV_CON_ID.
	 * @param: java.lang.String GROUP_ECC_RV_CON_ID
	 */
	public void setGroupEccRvConId(String groupEccRvConId) {
		this.groupEccRvConId = groupEccRvConId;
	}

	/**
	 * 方法: 取得管制审核主键.
	 * @return: java.lang.String 管制审核主键
	 */
	@Column(name = "GROUP_ECC_ID", nullable = true, length = 32)
	public String getGroupEccId() {
		return this.groupEccId;
	}

	/**
	 * 方法: 设置管制审核主键.
	 * @param: java.lang.String 管制审核主键
	 */
	public void setGroupEccId(String groupEccId) {
		this.groupEccId = groupEccId;
	}

	/**
	 * 方法: 取得实际报审开始日期.
	 * @return: java.util.Date 实际报审开始日期
	 */
	@Column(name = "REAL_START_DATE", nullable = true)
	public java.util.Date getRealStartDate() {
		return this.realStartDate;
	}

	/**
	 * 方法: 设置实际报审开始日期.
	 * @param: java.util.Date 实际报审开始日期
	 */
	public void setRealStartDate(java.util.Date realStartDate) {
		this.realStartDate = realStartDate;
	}

	/**
	 * 方法: 取得实际报审完成日期.
	 * @return: java.util.Date 实际报审完成日期
	 */
	@Column(name = "REAL_END_DATE", nullable = true)
	public java.util.Date getRealEndDate() {
		return this.realEndDate;
	}

	/**
	 * 方法: 设置实际报审完成日期.
	 * @param: java.util.Date 实际报审完成日期
	 */
	public void setRealEndDate(java.util.Date realEndDate) {
		this.realEndDate = realEndDate;
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
	 * 方法: 取得评审结论状态 1通过 2未通过.
	 * @return: int 评审结论状态 1通过 2未通过
	 */
	@Column(name = "RV_CON_STATUS", nullable = true, precision = 10)
	public int getRvConStatus() {
		return this.rvConStatus;
	}

	/**
	 * 方法: 设置评审结论状态 1通过 2未通过.
	 * @param: int 评审结论状态 1通过 2未通过
	 */
	public void setRvConStatus(int rvConStatus) {
		this.rvConStatus = rvConStatus;
	}

}
