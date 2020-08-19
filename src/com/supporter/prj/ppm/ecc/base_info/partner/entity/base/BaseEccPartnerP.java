package com.supporter.prj.ppm.ecc.base_info.partner.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制项目合伙人合作伙伴,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccPartnerP implements Serializable {

	private static final long serialVersionUID = 1L;
	private String partnerPId;
	private String partnerId;
	private String partnerPCName;
	private String partnerPEName;
	private int isEcc;

	/**
	 * 无参构造函数.
	 */
	public BaseEccPartnerP() {
	}

	/**
	 * 构造函数.
	 *
	 * @param partnerPId
	 */
	public BaseEccPartnerP(String partnerPId) {
		this.partnerPId = partnerPId;
	}

	/**
	 * 方法: 取得PARTNER_P_ID.
	 * @return: java.lang.String PARTNER_P_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "PARTNER_P_ID", nullable = false, length = 32)
	public String getPartnerPId() {
		return this.partnerPId;
	}

	/**
	 * 方法: 设置PARTNER_P_ID.
	 * @param: java.lang.String PARTNER_P_ID
	 */
	public void setPartnerPId(String partnerPId) {
		this.partnerPId = partnerPId;
	}

	/**
	 * 方法: 取得PARTNER_ID.
	 * @return: java.lang.String PARTNER_ID
	 */
	@Column(name = "PARTNER_ID", nullable = true, length = 32)
	public String getPartnerId() {
		return this.partnerId;
	}

	/**
	 * 方法: 设置PARTNER_ID.
	 * @param: java.lang.String PARTNER_ID
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * 方法: 取得合作人伙伴中文名称.
	 * @return: java.lang.String 合作人伙伴中文名称
	 */
	@Column(name = "PARTNER_P_C_NAME", nullable = true, length = 256)
	public String getPartnerPCName() {
		return this.partnerPCName;
	}

	/**
	 * 方法: 设置合作人伙伴中文名称.
	 * @param: java.lang.String 合作人伙伴中文名称
	 */
	public void setPartnerPCName(String partnerPCName) {
		this.partnerPCName = partnerPCName;
	}

	/**
	 * 方法: 取得合作伙伴英文名称.
	 * @return: java.lang.String 合作伙伴英文名称
	 */
	@Column(name = "PARTNER_P_E_NAME", nullable = true, length = 256)
	public String getPartnerPEName() {
		return this.partnerPEName;
	}

	/**
	 * 方法: 设置合作伙伴英文名称.
	 * @param: java.lang.String 合作伙伴英文名称
	 */
	public void setPartnerPEName(String partnerPEName) {
		this.partnerPEName = partnerPEName;
	}

	/**
	 * 方法: 取得是否为被制裁或受限制实体.
	 * @return: int 是否为被制裁或受限制实体
	 */
	@Column(name = "IS_ECC", nullable = true, precision = 10)
	public int getIsEcc() {
		return this.isEcc;
	}

	/**
	 * 方法: 设置是否为被制裁或受限制实体.
	 * @param: int 是否为被制裁或受限制实体
	 */
	public void setIsEcc(int isEcc) {
		this.isEcc = isEcc;
	}

}
