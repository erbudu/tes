package com.supporter.prj.ppm.ecc.base_info.partner.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制项目合作伙伴,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccPartner implements Serializable {

	private static final long serialVersionUID = 1L;
	private String partnerId;
	private String partnerCName;
	private String partnerEName;
	private String partnerTypeId;
	private String partnerTypeName;
	private String partnerCCofId;
	private String partnerCCofName;
	private String partnerECofId;
	private String partnerECofName;
	private String partnerCMbuc;
	private String partnerEMbuc;
	private String partnerCAdd;
	private String partnerEAdd;
	private int partnerAddE;
	private String eccId;

	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return eccId;
	}

	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseEccPartner() {
	}

	/**
	 * 构造函数.
	 *
	 * @param partnerId
	 */
	public BaseEccPartner(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * 方法: 取得PARTNER_ID.
	 * @return: java.lang.String PARTNER_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "PARTNER_ID", nullable = false, length = 32)
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
	 * 方法: 取得合作伙伴中文名.
	 * @return: java.lang.String 合作伙伴中文名
	 */
	@Column(name = "PARTNER_C_NAME", nullable = true, length = 256)
	public String getPartnerCName() {
		return this.partnerCName;
	}

	/**
	 * 方法: 设置合作伙伴中文名.
	 * @param: java.lang.String 合作伙伴中文名
	 */
	public void setPartnerCName(String partnerCName) {
		this.partnerCName = partnerCName;
	}

	/**
	 * 方法: 取得合作伙伴英文名.
	 * @return: java.lang.String 合作伙伴英文名
	 */
	@Column(name = "PARTNER_E_NAME", nullable = true, length = 256)
	public String getPartnerEName() {
		return this.partnerEName;
	}

	/**
	 * 方法: 设置合作伙伴英文名.
	 * @param: java.lang.String 合作伙伴英文名
	 */
	public void setPartnerEName(String partnerEName) {
		this.partnerEName = partnerEName;
	}

	/**
	 * 方法: 取得合作伙伴类型id.
	 * @return: java.lang.String 合作伙伴类型id
	 */
	@Column(name = "PARTNER_TYPE_ID", nullable = true, length = 32)
	public String getPartnerTypeId() {
		return this.partnerTypeId;
	}

	/**
	 * 方法: 设置合作伙伴类型id.
	 * @param: java.lang.String 合作伙伴类型id
	 */
	public void setPartnerTypeId(String partnerTypeId) {
		this.partnerTypeId = partnerTypeId;
	}

	/**
	 * 方法: 取得合作伙伴类型名称.
	 * @return: java.lang.String 合作伙伴类型名称
	 */
	@Column(name = "PARTNER_TYPE_NAME", nullable = true, length = 128)
	public String getPartnerTypeName() {
		return this.partnerTypeName;
	}

	/**
	 * 方法: 设置合作伙伴类型名称.
	 * @param: java.lang.String 合作伙伴类型名称
	 */
	public void setPartnerTypeName(String partnerTypeName) {
		this.partnerTypeName = partnerTypeName;
	}

	/**
	 * 方法: 取得合作伙伴组织形式 中文.
	 * @return: java.lang.String 合作伙伴组织形式 中文
	 */
	@Column(name = "PARTNER_C_COF_ID", nullable = true, length = 32)
	public String getPartnerCCofId() {
		return this.partnerCCofId;
	}

	/**
	 * 方法: 设置合作伙伴组织形式 中文.
	 * @param: java.lang.String 合作伙伴组织形式 中文
	 */
	public void setPartnerCCofId(String partnerCCofId) {
		this.partnerCCofId = partnerCCofId;
	}

	/**
	 * 方法: 取得合作伙伴组织形式.
	 * @return: java.lang.String 合作伙伴组织形式
	 */
	@Column(name = "PARTNER_C_COF_NAME", nullable = true, length = 256)
	public String getPartnerCCofName() {
		return this.partnerCCofName;
	}

	/**
	 * 方法: 设置合作伙伴组织形式.
	 * @param: java.lang.String 合作伙伴组织形式
	 */
	public void setPartnerCCofName(String partnerCCofName) {
		this.partnerCCofName = partnerCCofName;
	}

	/**
	 * 方法: 取得合作伙伴组织形式 英文.
	 * @return: java.lang.String 合作伙伴组织形式 英文
	 */
	@Column(name = "PARTNER_E_COF_ID", nullable = true, length = 32)
	public String getPartnerECofId() {
		return this.partnerECofId;
	}

	/**
	 * 方法: 设置合作伙伴组织形式 英文.
	 * @param: java.lang.String 合作伙伴组织形式 英文
	 */
	public void setPartnerECofId(String partnerECofId) {
		this.partnerECofId = partnerECofId;
	}

	/**
	 * 方法: 取得合作伙伴组织形式 英文.
	 * @return: java.lang.String 合作伙伴组织形式 英文
	 */
	@Column(name = "PARTNER_E_COF_NAME", nullable = true, length = 256)
	public String getPartnerECofName() {
		return this.partnerECofName;
	}

	/**
	 * 方法: 设置合作伙伴组织形式 英文.
	 * @param: java.lang.String 合作伙伴组织形式 英文
	 */
	public void setPartnerECofName(String partnerECofName) {
		this.partnerECofName = partnerECofName;
	}

	/**
	 * 方法: 取得合作伙伴主营业务 中文.
	 * @return: java.lang.String 合作伙伴主营业务 中文
	 */
	@Column(name = "PARTNER_C_MBUC", nullable = true, length = 256)
	public String getPartnerCMbuc() {
		return this.partnerCMbuc;
	}

	/**
	 * 方法: 设置合作伙伴主营业务 中文.
	 * @param: java.lang.String 合作伙伴主营业务 中文
	 */
	public void setPartnerCMbuc(String partnerCMbuc) {
		this.partnerCMbuc = partnerCMbuc;
	}

	/**
	 * 方法: 取得合作伙伴主营业务 英文.
	 * @return: java.lang.String 合作伙伴主营业务 英文
	 */
	@Column(name = "PARTNER_E_MBUC", nullable = true, length = 256)
	public String getPartnerEMbuc() {
		return this.partnerEMbuc;
	}

	/**
	 * 方法: 设置合作伙伴主营业务 英文.
	 * @param: java.lang.String 合作伙伴主营业务 英文
	 */
	public void setPartnerEMbuc(String partnerEMbuc) {
		this.partnerEMbuc = partnerEMbuc;
	}

	/**
	 * 方法: 取得合作伙伴注册地 中文.
	 * @return: java.lang.String 合作伙伴注册地 中文
	 */
	@Column(name = "PARTNER_C_ADD", nullable = true, length = 512)
	public String getPartnerCAdd() {
		return this.partnerCAdd;
	}

	/**
	 * 方法: 设置合作伙伴注册地 中文.
	 * @param: java.lang.String 合作伙伴注册地 中文
	 */
	public void setPartnerCAdd(String partnerCAdd) {
		this.partnerCAdd = partnerCAdd;
	}

	/**
	 * 方法: 取得合作伙伴注册地 英文.
	 * @return: java.lang.String 合作伙伴注册地 英文
	 */
	@Column(name = "PARTNER_E_ADD", nullable = true, length = 512)
	public String getPartnerEAdd() {
		return this.partnerEAdd;
	}

	/**
	 * 方法: 设置合作伙伴注册地 英文.
	 * @param: java.lang.String 合作伙伴注册地 英文
	 */
	public void setPartnerEAdd(String partnerEAdd) {
		this.partnerEAdd = partnerEAdd;
	}

	/**
	 * 方法: 取得合作伙伴地址是否敏感.
	 * @return: int 合作伙伴地址是否敏感
	 */
	@Column(name = "PARTNER_ADD_E", nullable = true, precision = 10)
	public int getPartnerAddE() {
		return this.partnerAddE;
	}

	/**
	 * 方法: 设置合作伙伴地址是否敏感.
	 * @param: int 合作伙伴地址是否敏感
	 */
	public void setPartnerAddE(int partnerAddE) {
		this.partnerAddE = partnerAddE;
	}

}
