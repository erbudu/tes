package com.supporter.prj.ppm.ecc.base_info.fund_plan.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制资金安排,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccFundPlan implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fpId;
	private String eccId;
	private String fpDescC;
	private String fpDescE;

	/**
	 * 无参构造函数.
	 */
	public BaseEccFundPlan() {
	}

	/**
	 * 构造函数.
	 *
	 * @param fpId
	 */
	public BaseEccFundPlan(String fpId) {
		this.fpId = fpId;
	}

	/**
	 * 方法: 取得FP_ID.
	 * @return: java.lang.String FP_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "FP_ID", nullable = false, length = 32)
	public String getFpId() {
		return this.fpId;
	}

	/**
	 * 方法: 设置FP_ID.
	 * @param: java.lang.String FP_ID
	 */
	public void setFpId(String fpId) {
		this.fpId = fpId;
	}

	/**
	 * 方法: 取得出口管制单id.
	 * @return: java.lang.String 出口管制单id
	 */
	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return this.eccId;
	}

	/**
	 * 方法: 设置出口管制单id.
	 * @param: java.lang.String 出口管制单id
	 */
	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 方法: 取得资金来源以及汇款路径安排-中文.
	 * @return: java.lang.String 资金来源以及汇款路径安排-中文
	 */
	@Column(name = "FP_DESC_C", nullable = true, length = 256)
	public String getFpDescC() {
		return this.fpDescC;
	}

	/**
	 * 方法: 设置资金来源以及汇款路径安排-中文.
	 * @param: java.lang.String 资金来源以及汇款路径安排-中文
	 */
	public void setFpDescC(String fpDescC) {
		this.fpDescC = fpDescC;
	}

	/**
	 * 方法: 取得资金来源以及汇款路径安排-英文.
	 * @return: java.lang.String 资金来源以及汇款路径安排-英文
	 */
	@Column(name = "FP_DESC_E", nullable = true, length = 256)
	public String getFpDescE() {
		return this.fpDescE;
	}

	/**
	 * 方法: 设置资金来源以及汇款路径安排-英文.
	 * @param: java.lang.String 资金来源以及汇款路径安排-英文
	 */
	public void setFpDescE(String fpDescE) {
		this.fpDescE = fpDescE;
	}

}
