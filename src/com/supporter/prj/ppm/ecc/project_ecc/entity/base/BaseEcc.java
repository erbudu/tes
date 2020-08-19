package com.supporter.prj.ppm.ecc.project_ecc.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEcc implements Serializable {

	private static final long serialVersionUID = 1L;
	private String eccId;
	private String prjId;
	private int isEcc;
	private int status;//管制状态 0草稿 1管制中 2管制完成
	private String result;
	@Column(name = "RESULT", nullable = true, length = 512)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseEcc() {
	}

	/**
	 * 构造函数.
	 *
	 * @param eccId
	 */
	public BaseEcc(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 方法: 取得ECC_ID.
	 * @return: java.lang.String ECC_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "ECC_ID", nullable = false, length = 32)
	public String getEccId() {
		return this.eccId;
	}

	/**
	 * 方法: 设置ECC_ID.
	 * @param: java.lang.String ECC_ID
	 */
	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 方法: 取得PRJ_ID.
	 * @return: java.lang.String PRJ_ID
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	/**
	 * 方法: 设置PRJ_ID.
	 * @param: java.lang.String PRJ_ID
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * 方法: 取得0 需要 1不需要.
	 * @return: int 0 需要 1不需要
	 */
	@Column(name = "IS_ECC", nullable = true, precision = 10)
	public int getIsEcc() {
		return this.isEcc;
	}

	/**
	 * 方法: 设置0 需要 1不需要.
	 * @param: int 0 需要 1不需要
	 */
	public void setIsEcc(int isEcc) {
		this.isEcc = isEcc;
	}
	@Column(name = "status", nullable = true, precision = 10)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
