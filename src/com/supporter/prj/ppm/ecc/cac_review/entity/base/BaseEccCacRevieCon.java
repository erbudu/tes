package com.supporter.prj.ppm.ecc.cac_review.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制委员会评审结论,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccCacRevieCon implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cacEccRvConId;
	private String cacEccId;
	private String eccId;
	private int rvConStatus;
	private int rvConBussinesStatus;
	private int rvConNexWf;

	/**
	 * 无参构造函数.
	 */
	public BaseEccCacRevieCon() {
	}

	/**
	 * 构造函数.
	 *
	 * @param cacEccRvConId
	 */
	public BaseEccCacRevieCon(String cacEccRvConId) {
		this.cacEccRvConId = cacEccRvConId;
	}

	/**
	 * 方法: 取得CAC_ECC_RV_CON_ID.
	 * @return: java.lang.String CAC_ECC_RV_CON_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "CAC_ECC_RV_CON_ID", nullable = false, length = 32)
	public String getCacEccRvConId() {
		return this.cacEccRvConId;
	}

	/**
	 * 方法: 设置CAC_ECC_RV_CON_ID.
	 * @param: java.lang.String CAC_ECC_RV_CON_ID
	 */
	public void setCacEccRvConId(String cacEccRvConId) {
		this.cacEccRvConId = cacEccRvConId;
	}

	/**
	 * 方法: 取得管制审核主键.
	 * @return: java.lang.String 管制审核主键
	 */
	@Column(name = "CAC_ECC_ID", nullable = true, length = 32)
	public String getCacEccId() {
		return this.cacEccId;
	}

	/**
	 * 方法: 设置管制审核主键.
	 * @param: java.lang.String 管制审核主键
	 */
	public void setCacEccId(String cacEccId) {
		this.cacEccId = cacEccId;
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
	 * 方法: 取得评审后续执行 1进入 cmec评审 2不进入.
	 * @return: int 评审后续执行 1进入 cmec评审 2不进入
	 */
	@Column(name = "RV_CON_STATUS", nullable = true, precision = 10)
	public int getRvConStatus() {
		return this.rvConStatus;
	}

	/**
	 * 方法: 设置评审后续执行 1进入 cmec评审 2不进入.
	 * @param: int 评审后续执行 1进入 cmec评审 2不进入
	 */
	public void setRvConStatus(int rvConStatus) {
		this.rvConStatus = rvConStatus;
	}

	/**
	 * 方法: 取得评审结论业务状态 1: 情况清晰的有限制裁2: 情况复杂的有限制裁/全面制裁.
	 * @return: int 评审结论业务状态 1: 情况清晰的有限制裁2: 情况复杂的有限制裁/全面制裁
	 */
	@Column(name = "RV_CON_BUSSINES_STATUS", nullable = true, precision = 10)
	public int getRvConBussinesStatus() {
		return this.rvConBussinesStatus;
	}

	/**
	 * 方法: 设置评审结论业务状态 1: 情况清晰的有限制裁2: 情况复杂的有限制裁/全面制裁.
	 * @param: int 评审结论业务状态 1: 情况清晰的有限制裁2: 情况复杂的有限制裁/全面制裁
	 */
	public void setRvConBussinesStatus(int rvConBussinesStatus) {
		this.rvConBussinesStatus = rvConBussinesStatus;
	}

	/**
	 * 方法: 取得RV_CON_NEX_WF.
	 * @return: int RV_CON_NEX_WF
	 */
	@Column(name = "RV_CON_NEX_WF", nullable = true, precision = 10)
	public int getRvConNexWf() {
		return this.rvConNexWf;
	}

	/**
	 * 方法: 设置RV_CON_NEX_WF.
	 * @param: int RV_CON_NEX_WF
	 */
	public void setRvConNexWf(int rvConNexWf) {
		this.rvConNexWf = rvConNexWf;
	}

}
