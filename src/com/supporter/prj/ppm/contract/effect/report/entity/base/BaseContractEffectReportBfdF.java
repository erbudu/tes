package com.supporter.prj.ppm.contract.effect.report.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 主合同签约评审资料清单单文件,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectReportBfdF implements Serializable {

	private static final long serialVersionUID = 1L;
	private String recordId;
	private String bfdId;
	private String contractEffectId;
	private String fuFileId;
	private int isUseSeal;
	private int displayOrder;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectReportBfdF() {
	}

	/**
	 * 构造函数.
	 *
	 * @param recordId
	 */
	public BaseContractEffectReportBfdF(String recordId) {
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
	 * 方法: 取得主合同签约id.
	 * @return: java.lang.String 主合同签约id
	 */
	@Column(name = "CONTRACT_Effect_ID", nullable = true, length = 32)
	public String getContractEffectId() {
		return this.contractEffectId;
	}

	/**
	 * 方法: 设置主合同签约id.
	 * @param: java.lang.String 主合同签约id
	 */
	public void setContractEffectId(String contractEffectId) {
		this.contractEffectId = contractEffectId;
	}

	/**
	 * 方法: 取得实际附件上传组件的文件的id.
	 * @return: java.lang.String 实际附件上传组件的文件的id
	 */
	@Column(name = "FU_FILE_ID", nullable = true, length = 32)
	public String getFuFileId() {
		return this.fuFileId;
	}

	/**
	 * 方法: 设置实际附件上传组件的文件的id.
	 * @param: java.lang.String 实际附件上传组件的文件的id
	 */
	public void setFuFileId(String fuFileId) {
		this.fuFileId = fuFileId;
	}

	/**
	 * 方法: 取得是否需要用印.
	 * @return: int 是否需要用印
	 */
	@Column(name = "IS_USE_SEAL", nullable = true, precision = 10)
	public int getIsUseSeal() {
		return this.isUseSeal;
	}

	/**
	 * 方法: 设置是否需要用印.
	 * @param: int 是否需要用印
	 */
	public void setIsUseSeal(int isUseSeal) {
		this.isUseSeal = isUseSeal;
	}

	/**
	 * 方法: 取得同类型文件序号.
	 * @return: int 同类型文件序号
	 */
	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * 方法: 设置同类型文件序号.
	 * @param: int 同类型文件序号
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
