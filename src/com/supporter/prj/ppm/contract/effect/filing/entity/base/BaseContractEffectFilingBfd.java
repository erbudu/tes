package com.supporter.prj.ppm.contract.effect.filing.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectFilingBfd implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bfdId;
	private String filingId;
	private String bfdTypeId;
	private String bfdTypeName;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectFilingBfd() {
	}

	/**
	 * 构造函数.
	 *
	 * @param bfdId
	 */
	public BaseContractEffectFilingBfd(String bfdId) {
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
	 * 方法: 取得FILING_ID.
	 * @return: java.lang.String FILING_ID
	 */
	@Column(name = "FILING_ID", nullable = true, length = 32)
	public String getFilingId() {
		return this.filingId;
	}

	/**
	 * 方法: 设置FILING_ID.
	 * @param: java.lang.String FILING_ID
	 */
	public void setFilingId(String filingId) {
		this.filingId = filingId;
	}

	/**
	 * 方法: 取得BFD_TYPE_ID.
	 * @return: java.lang.String BFD_TYPE_ID
	 */
	@Column(name = "BFD_TYPE_ID", nullable = true, length = 32)
	public String getBfdTypeId() {
		return this.bfdTypeId;
	}

	/**
	 * 方法: 设置BFD_TYPE_ID.
	 * @param: java.lang.String BFD_TYPE_ID
	 */
	public void setBfdTypeId(String bfdTypeId) {
		this.bfdTypeId = bfdTypeId;
	}

	/**
	 * 方法: 取得BFD_TYPE_NAME.
	 * @return: java.lang.String BFD_TYPE_NAME
	 */
	@Column(name = "BFD_TYPE_NAME", nullable = true, length = 256)
	public String getBfdTypeName() {
		return this.bfdTypeName;
	}

	/**
	 * 方法: 设置BFD_TYPE_NAME.
	 * @param: java.lang.String BFD_TYPE_NAME
	 */
	public void setBfdTypeName(String bfdTypeName) {
		this.bfdTypeName = bfdTypeName;
	}

}
