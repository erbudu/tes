package com.supporter.prj.ppm.ecc.base_info.owner.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制客户的合伙人,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccOwnerPartner implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ownerPartnerId;
	private String ownerId;
	private String ownerPartnerCName;
	private String ownerPartnerEName;
	private int isEcc;

	/**
	 * 无参构造函数.
	 */
	public BaseEccOwnerPartner() {
	}

	/**
	 * 构造函数.
	 *
	 * @param ownerPartnerId
	 */
	public BaseEccOwnerPartner(String ownerPartnerId) {
		this.ownerPartnerId = ownerPartnerId;
	}

	/**
	 * 方法: 取得OWNER_PARTNER_ID.
	 * @return: java.lang.String OWNER_PARTNER_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "OWNER_PARTNER_ID", nullable = false, length = 32)
	public String getOwnerPartnerId() {
		return this.ownerPartnerId;
	}

	/**
	 * 方法: 设置OWNER_PARTNER_ID.
	 * @param: java.lang.String OWNER_PARTNER_ID
	 */
	public void setOwnerPartnerId(String ownerPartnerId) {
		this.ownerPartnerId = ownerPartnerId;
	}

	/**
	 * 方法: 取得是否为被制裁或受限制实体.
	 * @return: java.lang.String 是否为被制裁或受限制实体
	 */
	@Column(name = "OWNER_ID", nullable = true, length = 32)
	public String getOwnerId() {
		return this.ownerId;
	}

	/**
	 * 方法: 设置是否为被制裁或受限制实体.
	 * @param: java.lang.String 是否为被制裁或受限制实体
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * 方法: 取得OWNER_PARTNER_C_NAME.
	 * @return: java.lang.String OWNER_PARTNER_C_NAME
	 */
	@Column(name = "OWNER_PARTNER_C_NAME", nullable = true, length = 256)
	public String getOwnerPartnerCName() {
		return this.ownerPartnerCName;
	}

	/**
	 * 方法: 设置OWNER_PARTNER_C_NAME.
	 * @param: java.lang.String OWNER_PARTNER_C_NAME
	 */
	public void setOwnerPartnerCName(String ownerPartnerCName) {
		this.ownerPartnerCName = ownerPartnerCName;
	}

	/**
	 * 方法: 取得OWNER_PARTNER_E_NAME.
	 * @return: java.lang.String OWNER_PARTNER_E_NAME
	 */
	@Column(name = "OWNER_PARTNER_E_NAME", nullable = true, length = 256)
	public String getOwnerPartnerEName() {
		return this.ownerPartnerEName;
	}

	/**
	 * 方法: 设置OWNER_PARTNER_E_NAME.
	 * @param: java.lang.String OWNER_PARTNER_E_NAME
	 */
	public void setOwnerPartnerEName(String ownerPartnerEName) {
		this.ownerPartnerEName = ownerPartnerEName;
	}

	/**
	 * 方法: 取得IS_ECC.
	 * @return: int IS_ECC
	 */
	@Column(name = "IS_ECC", nullable = true, precision = 10)
	public int getIsEcc() {
		return this.isEcc;
	}

	/**
	 * 方法: 设置IS_ECC.
	 * @param: int IS_ECC
	 */
	public void setIsEcc(int isEcc) {
		this.isEcc = isEcc;
	}

}
