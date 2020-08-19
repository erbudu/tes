package com.supporter.prj.ppm.contract.sign.filing.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD_F,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractFilingBfdF implements Serializable {

	private static final long serialVersionUID = 1L;
	private String recordId;
	private String bfdId;
	private String filingId;
	private String fileId;
	private int displayOrder;

	/**
	 * 无参构造函数.
	 */
	public BaseContractFilingBfdF() {
	}

	/**
	 * 构造函数.
	 *
	 * @param recordId
	 */
	public BaseContractFilingBfdF(String recordId) {
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
	 * 方法: 取得资料类型id.
	 * @return: java.lang.String 资料类型id
	 */
	@Column(name = "BFD_ID", nullable = true, length = 32)
	public String getBfdId() {
		return this.bfdId;
	}

	/**
	 * 方法: 设置资料类型id.
	 * @param: java.lang.String 资料类型id
	 */
	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 方法: 取得合同备案id.
	 * @return: java.lang.String 合同备案id
	 */
	@Column(name = "FILING_ID", nullable = true, length = 32)
	public String getFilingId() {
		return this.filingId;
	}

	/**
	 * 方法: 设置合同备案id.
	 * @param: java.lang.String 合同备案id
	 */
	public void setFilingId(String filingId) {
		this.filingId = filingId;
	}

	/**
	 * 方法: 取得文件id.
	 * @return: java.lang.String 文件id
	 */
	@Column(name = "FILE_ID", nullable = true, length = 32)
	public String getFileId() {
		return this.fileId;
	}

	/**
	 * 方法: 设置文件id.
	 * @param: java.lang.String 文件id
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
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
