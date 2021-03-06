package com.supporter.prj.ppm.ecc.base_info.partner.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制合伙人警告,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccPartnerWarn implements Serializable {

	private static final long serialVersionUID = 1L;
	private String warnId;
	private String partnerId;
	private int isIdNd;
	private int isAvoidBti;
	private int isPayMOk;
	private int isUnProductBy;

	/**
	 * 无参构造函数.
	 */
	public BaseEccPartnerWarn() {
	}

	/**
	 * 构造函数.
	 *
	 * @param warnId
	 */
	public BaseEccPartnerWarn(String warnId) {
		this.warnId = warnId;
	}

	/**
	 * 方法: 取得WARN_ID.
	 * @return: java.lang.String WARN_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "WARN_ID", nullable = false, length = 32)
	public String getWarnId() {
		return this.warnId;
	}

	/**
	 * 方法: 设置WARN_ID.
	 * @param: java.lang.String WARN_ID
	 */
	public void setWarnId(String warnId) {
		this.warnId = warnId;
	}

	/**
	 * 方法: 取得合作伙伴id.
	 * @return: java.lang.String 合作伙伴id
	 */
	@Column(name = "PARTNER_ID", nullable = true, length = 32)
	public String getPartnerId() {
		return this.partnerId;
	}

	/**
	 * 方法: 设置合作伙伴id.
	 * @param: java.lang.String 合作伙伴id
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * 方法: 取得是否明确身份.
	 * @return: int 是否明确身份
	 */
	@Column(name = "IS_ID_ND", nullable = true, precision = 10)
	public int getIsIdNd() {
		return this.isIdNd;
	}

	/**
	 * 方法: 设置是否明确身份.
	 * @param: int 是否明确身份
	 */
	public void setIsIdNd(int isIdNd) {
		this.isIdNd = isIdNd;
	}

	/**
	 * 方法: 取得是否回避某些商务或技术问题.
	 * @return: int 是否回避某些商务或技术问题
	 */
	@Column(name = "IS_AVOID_BTI", nullable = true, precision = 10)
	public int getIsAvoidBti() {
		return this.isAvoidBti;
	}

	/**
	 * 方法: 设置是否回避某些商务或技术问题.
	 * @param: int 是否回避某些商务或技术问题
	 */
	public void setIsAvoidBti(int isAvoidBti) {
		this.isAvoidBti = isAvoidBti;
	}

	/**
	 * 方法: 取得支付方式是否符合一般商业习惯？.
	 * @return: int 支付方式是否符合一般商业习惯？
	 */
	@Column(name = "IS_PAY_M_OK", nullable = true, precision = 10)
	public int getIsPayMOk() {
		return this.isPayMOk;
	}

	/**
	 * 方法: 设置支付方式是否符合一般商业习惯？.
	 * @param: int 支付方式是否符合一般商业习惯？
	 */
	public void setIsPayMOk(int isPayMOk) {
		this.isPayMOk = isPayMOk;
	}

	/**
	 * 方法: 取得是否在不了解产品性能的情况下执意购买产品？.
	 * @return: int 是否在不了解产品性能的情况下执意购买产品？
	 */
	@Column(name = "IS_UN_PRODUCT_BY", nullable = true, precision = 10)
	public int getIsUnProductBy() {
		return this.isUnProductBy;
	}

	/**
	 * 方法: 设置是否在不了解产品性能的情况下执意购买产品？.
	 * @param: int 是否在不了解产品性能的情况下执意购买产品？
	 */
	public void setIsUnProductBy(int isUnProductBy) {
		this.isUnProductBy = isUnProductBy;
	}

}
