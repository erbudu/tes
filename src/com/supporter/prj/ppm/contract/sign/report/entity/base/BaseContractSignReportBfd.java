package com.supporter.prj.ppm.contract.sign.report.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 主合同签约评审资料单,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractSignReportBfd implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bfdId;
	private String contractSignId;
	private String bfdTypeId;
	private String bfdTypeName;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignReportBfd() {
	}

	/**
	 * 构造函数.
	 *
	 * @param bfdId
	 */
	public BaseContractSignReportBfd(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 方法: 取得主键.
	 * @return: java.lang.String 主键
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "BFD_ID", nullable = false, length = 32)
	public String getBfdId() {
		return this.bfdId;
	}

	/**
	 * 方法: 设置主键.
	 * @param: java.lang.String 主键
	 */
	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 方法: 取得外键.
	 * @return: java.lang.String 外键
	 */
	@Column(name = "CONTRACT_SIGN_ID", nullable = true, length = 32)
	public String getContractSignId() {
		return this.contractSignId;
	}

	/**
	 * 方法: 设置外键.
	 * @param: java.lang.String 外键
	 */
	public void setContractSignId(String contractSignId) {
		this.contractSignId = contractSignId;
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
