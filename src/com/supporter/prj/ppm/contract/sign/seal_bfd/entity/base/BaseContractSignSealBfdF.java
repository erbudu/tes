package com.supporter.prj.ppm.contract.sign.seal_bfd.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 资料清单文件,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-10
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractSignSealBfdF implements Serializable {

	private static final long serialVersionUID = 1L;
	private String recordId;
	private String bfdId;
	private String contractSignId;
	private String fuSealFileId;
	private int displayOrder;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignSealBfdF() {
	}

	/**
	 * 构造函数.
	 *
	 * @param recordId
	 */
	public BaseContractSignSealBfdF(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * 方法: 取得RECORD_ID.
	 * @return: java.lang.String RECORD_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "RECORD_ID", nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	/**
	 * 方法: 设置RECORD_ID.
	 * @param: java.lang.String RECORD_ID
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * 方法: 取得BFD_ID.
	 * @return: java.lang.String BFD_ID
	 */
	@Column(name = "BFD_ID", nullable = true, length = 32)
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
	 * 方法: 取得文件id.
	 * @return: java.lang.String 文件id
	 */
	@Column(name = "FU_SEAL_FILE_ID", nullable = true, length = 32)
	public String getFuSealFileId() {
		return this.fuSealFileId;
	}

	/**
	 * 方法: 设置文件id.
	 * @param: java.lang.String 文件id
	 */
	public void setFuSealFileId(String fuSealFileId) {
		this.fuSealFileId = fuSealFileId;
	}

	/**
	 * 方法: 取得排序.
	 * @return: int 排序
	 */
	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * 方法: 设置排序.
	 * @param: int 排序
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
