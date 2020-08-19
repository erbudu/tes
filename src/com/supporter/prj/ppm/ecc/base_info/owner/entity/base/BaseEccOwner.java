package com.supporter.prj.ppm.ecc.base_info.owner.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制客户,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccOwner implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ownerId;
	private String ownerCName;
	private String ownerEName;
	private String ownerCCofId;
	private String ownerCCofName;
	private String ownerECofId;
	private String ownerECofName;
	private String ownerCMbuc;
	private String ownerEMbuc;
	private String ownerCAdd;
	private String ownerEAdd;
	private int ownerAddE;
	private int isOwnerSpecialE;
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
	public BaseEccOwner() {
	}

	/**
	 * 构造函数.
	 *
	 * @param ownerId
	 */
	public BaseEccOwner(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * 方法: 取得OWNER_ID.
	 * @return: java.lang.String OWNER_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "OWNER_ID", nullable = false, length = 32)
	public String getOwnerId() {
		return this.ownerId;
	}

	/**
	 * 方法: 设置OWNER_ID.
	 * @param: java.lang.String OWNER_ID
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * 方法: 取得业主中文名.
	 * @return: java.lang.String 业主中文名
	 */
	@Column(name = "OWNER_C_NAME", nullable = true, length = 256)
	public String getOwnerCName() {
		return this.ownerCName;
	}

	/**
	 * 方法: 设置业主中文名.
	 * @param: java.lang.String 业主中文名
	 */
	public void setOwnerCName(String ownerCName) {
		this.ownerCName = ownerCName;
	}

	/**
	 * 方法: 取得业主英名称.
	 * @return: java.lang.String 业主英名称
	 */
	@Column(name = "OWNER_E_NAME", nullable = true, length = 256)
	public String getOwnerEName() {
		return this.ownerEName;
	}

	/**
	 * 方法: 设置业主英名称.
	 * @param: java.lang.String 业主英名称
	 */
	public void setOwnerEName(String ownerEName) {
		this.ownerEName = ownerEName;
	}

	/**
	 * 方法: 取得业主组织形式(中).
	 * @return: java.lang.String 业主组织形式(中)
	 */
	@Column(name = "OWNER_C_COF_ID", nullable = true, length = 32)
	public String getOwnerCCofId() {
		return this.ownerCCofId;
	}

	/**
	 * 方法: 设置业主组织形式(中).
	 * @param: java.lang.String 业主组织形式(中)
	 */
	public void setOwnerCCofId(String ownerCCofId) {
		this.ownerCCofId = ownerCCofId;
	}

	/**
	 * 方法: 取得业主组织形式（中）.
	 * @return: java.lang.String 业主组织形式（中）
	 */
	@Column(name = "OWNER_C_COF_NAME", nullable = true, length = 256)
	public String getOwnerCCofName() {
		return this.ownerCCofName;
	}

	/**
	 * 方法: 设置业主组织形式（中）.
	 * @param: java.lang.String 业主组织形式（中）
	 */
	public void setOwnerCCofName(String ownerCCofName) {
		this.ownerCCofName = ownerCCofName;
	}

	/**
	 * 方法: 取得业主组织形式(英).
	 * @return: java.lang.String 业主组织形式(英)
	 */
	@Column(name = "OWNER_E_COF_ID", nullable = true, length = 32)
	public String getOwnerECofId() {
		return this.ownerECofId;
	}

	/**
	 * 方法: 设置业主组织形式(英).
	 * @param: java.lang.String 业主组织形式(英)
	 */
	public void setOwnerECofId(String ownerECofId) {
		this.ownerECofId = ownerECofId;
	}

	/**
	 * 方法: 取得业主组织形式(英).
	 * @return: java.lang.String 业主组织形式(英)
	 */
	@Column(name = "OWNER_E_COF_NAME", nullable = true, length = 256)
	public String getOwnerECofName() {
		return this.ownerECofName;
	}

	/**
	 * 方法: 设置业主组织形式(英).
	 * @param: java.lang.String 业主组织形式(英)
	 */
	public void setOwnerECofName(String ownerECofName) {
		this.ownerECofName = ownerECofName;
	}

	/**
	 * 方法: 取得业主中文主营业务.
	 * @return: java.lang.String 业主中文主营业务
	 */
	@Column(name = "OWNER_C_MBUC", nullable = true, length = 256)
	public String getOwnerCMbuc() {
		return this.ownerCMbuc;
	}

	/**
	 * 方法: 设置业主中文主营业务.
	 * @param: java.lang.String 业主中文主营业务
	 */
	public void setOwnerCMbuc(String ownerCMbuc) {
		this.ownerCMbuc = ownerCMbuc;
	}

	/**
	 * 方法: 取得业主英文主营业务.
	 * @return: java.lang.String 业主英文主营业务
	 */
	@Column(name = "OWNER_E_MBUC", nullable = true, length = 256)
	public String getOwnerEMbuc() {
		return this.ownerEMbuc;
	}

	/**
	 * 方法: 设置业主英文主营业务.
	 * @param: java.lang.String 业主英文主营业务
	 */
	public void setOwnerEMbuc(String ownerEMbuc) {
		this.ownerEMbuc = ownerEMbuc;
	}

	/**
	 * 方法: 取得业主中文注册地.
	 * @return: java.lang.String 业主中文注册地
	 */
	@Column(name = "OWNER_C_ADD", nullable = true, length = 512)
	public String getOwnerCAdd() {
		return this.ownerCAdd;
	}

	/**
	 * 方法: 设置业主中文注册地.
	 * @param: java.lang.String 业主中文注册地
	 */
	public void setOwnerCAdd(String ownerCAdd) {
		this.ownerCAdd = ownerCAdd;
	}

	/**
	 * 方法: 取得业主英文注册地.
	 * @return: java.lang.String 业主英文注册地
	 */
	@Column(name = "OWNER_E_ADD", nullable = true, length = 512)
	public String getOwnerEAdd() {
		return this.ownerEAdd;
	}

	/**
	 * 方法: 设置业主英文注册地.
	 * @param: java.lang.String 业主英文注册地
	 */
	public void setOwnerEAdd(String ownerEAdd) {
		this.ownerEAdd = ownerEAdd;
	}

	/**
	 * 方法: 取得业主地址是否敏感.
	 * @return: int 业主地址是否敏感
	 */
	@Column(name = "OWNER_ADD_E", nullable = true, precision = 10)
	public int getOwnerAddE() {
		return this.ownerAddE;
	}

	/**
	 * 方法: 设置业主地址是否敏感.
	 * @param: int 业主地址是否敏感
	 */
	public void setOwnerAddE(int ownerAddE) {
		this.ownerAddE = ownerAddE;
	}

	/**
	 * 方法: 取得是否与拥有大规模杀伤性武器或大规模杀伤性武器发展计划的国家和地区有密切的贸易往来.
	 * @return: int 是否与拥有大规模杀伤性武器或大规模杀伤性武器发展计划的国家和地区有密切的贸易往来
	 */
	@Column(name = "IS_OWNER_SPECIAL_E", nullable = true, precision = 10)
	public int getIsOwnerSpecialE() {
		return this.isOwnerSpecialE;
	}

	/**
	 * 方法: 设置是否与拥有大规模杀伤性武器或大规模杀伤性武器发展计划的国家和地区有密切的贸易往来.
	 * @param: int 是否与拥有大规模杀伤性武器或大规模杀伤性武器发展计划的国家和地区有密切的贸易往来
	 */
	public void setIsOwnerSpecialE(int isOwnerSpecialE) {
		this.isOwnerSpecialE = isOwnerSpecialE;
	}

}
