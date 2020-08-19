package com.supporter.prj.ppm.contract.effect.seal_bfd.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 主合同签约出版资料清单,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-10
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectSealBfd implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bfdId;
	private String contractEffectId;
	private String effectSealId;
	private String bfdTypeId;
	private String bfdTypeName;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectSealBfd() {
	}

	/**
	 * 构造函数.
	 *
	 * @param bfdId
	 */
	public BaseContractEffectSealBfd(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 方法: 取得BFD_ID.
	 * @return: java.lang.String BFD_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "BFD_ID", nullable = false, length = 32)
	public String getBfdId() {
		return this.bfdId;
	}

	/**
	 * 方法: 设置BFD_ID.
	 * @param: java.lang.String BFD_ID
	 */
	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 方法: 取得CONTRACT_EFFECT_ID.
	 * @return: java.lang.String CONTRACT_EFFECT_ID
	 */
	@Column(name = "CONTRACT_EFFECT_ID", nullable = true, length = 32)
	public String getContractEffectId() {
		return this.contractEffectId;
	}

	/**
	 * 方法: 设置CONTRACT_EFFECT_ID.
	 * @param: java.lang.String CONTRACT_EFFECT_ID
	 */
	public void setContractEffectId(String contractEffectId) {
		this.contractEffectId = contractEffectId;
	}

	/**
	 * 方法: 取得EFFECT_SEAL_ID.
	 * @return: java.lang.String EFFECT_SEAL_ID
	 */
	@Column(name = "EFFECT_SEAL_ID", nullable = true, length = 32)
	public String getEffectSealId() {
		return this.effectSealId;
	}

	/**
	 * 方法: 设置EFFECT_SEAL_ID.
	 * @param: java.lang.String EFFECT_SEAL_ID
	 */
	public void setEffectSealId(String effectSealId) {
		this.effectSealId = effectSealId;
	}

	/**
	 * 方法: 取得文件类型id.
	 * @return: java.lang.String 文件类型id
	 */
	@Column(name = "BFD_TYPE_ID", nullable = true, length = 32)
	public String getBfdTypeId() {
		return this.bfdTypeId;
	}

	/**
	 * 方法: 设置文件类型id.
	 * @param: java.lang.String 文件类型id
	 */
	public void setBfdTypeId(String bfdTypeId) {
		this.bfdTypeId = bfdTypeId;
	}

	/**
	 * 方法: 取得文件类型名称.
	 * @return: java.lang.String 文件类型名称
	 */
	@Column(name = "BFD_TYPE_NAME", nullable = true, length = 256)
	public String getBfdTypeName() {
		return this.bfdTypeName;
	}

	/**
	 * 方法: 设置文件类型名称.
	 * @param: java.lang.String 文件类型名称
	 */
	public void setBfdTypeName(String bfdTypeName) {
		this.bfdTypeName = bfdTypeName;
	}

}
