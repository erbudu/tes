package com.supporter.prj.ppm.ecc.base_info.customer.entity.base;

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
public class BaseEccCustomerPartner implements Serializable {

	private static final long serialVersionUID = 1L;
	private String customerId;
	private String partnerCName;
	private String partnerEName;
	private int isEcc;

	/**
	 * 无参构造函数.
	 */
	public BaseEccCustomerPartner() {
	}

	/**
	 * 构造函数.
	 *
	 * @param customerId
	 */
	public BaseEccCustomerPartner(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * 方法: 取得CUSTOMER_ID.
	 * @return: java.lang.String CUSTOMER_ID
	 */
	@Id
	/*@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")*/
	@Column(name = "CUSTOMER_ID", nullable = false, length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	/**
	 * 方法: 设置CUSTOMER_ID.
	 * @param: java.lang.String CUSTOMER_ID
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * 方法: 取得合伙人名称 中文.
	 * @return: java.lang.String 合伙人名称 中文
	 */
	@Column(name = "PARTNER_C_NAME", nullable = true, length = 256)
	public String getPartnerCName() {
		return this.partnerCName;
	}

	/**
	 * 方法: 设置合伙人名称 中文.
	 * @param: java.lang.String 合伙人名称 中文
	 */
	public void setPartnerCName(String partnerCName) {
		this.partnerCName = partnerCName;
	}

	/**
	 * 方法: 取得合伙人名称 英文.
	 * @return: java.lang.String 合伙人名称 英文
	 */
	@Column(name = "PARTNER_E_NAME", nullable = true, length = 256)
	public String getPartnerEName() {
		return this.partnerEName;
	}

	/**
	 * 方法: 设置合伙人名称 英文.
	 * @param: java.lang.String 合伙人名称 英文
	 */
	public void setPartnerEName(String partnerEName) {
		this.partnerEName = partnerEName;
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
