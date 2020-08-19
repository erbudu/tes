package com.supporter.prj.ppm.contract.effect.review_ver.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 验证标前评审资料清单单文件,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectRevVerBF implements Serializable {

	private static final long serialVersionUID = 1L;
	private String recordId;
	private String bfdId;
	private String contractEffectId;
	private String fuFileId;
	private int isUseSeal;
	private String ufSaleFileId;
	private int displayOrder;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectRevVerBF() {
	}

	/**
	 * 构造函数.
	 *
	 * @param recordId
	 */
	public BaseContractEffectRevVerBF(String recordId) {
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
	 * 方法: 取得评审id.
	 * @return: java.lang.String 评审id
	 */
	@Column(name = "FU_FILE_ID", nullable = true, length = 32)
	public String getFuFileId() {
		return this.fuFileId;
	}

	/**
	 * 方法: 设置评审id.
	 * @param: java.lang.String 评审id
	 */
	public void setFuFileId(String fuFileId) {
		this.fuFileId = fuFileId;
	}

	/**
	 * 方法: 取得文件id.
	 * @return: int 文件id
	 */
	@Column(name = "IS_USE_SEAL", nullable = true, precision = 10)
	public int getIsUseSeal() {
		return this.isUseSeal;
	}

	/**
	 * 方法: 设置文件id.
	 * @param: int 文件id
	 */
	public void setIsUseSeal(int isUseSeal) {
		this.isUseSeal = isUseSeal;
	}

	/**
	 * 方法: 取得实际附件上传组件的文件id（盖完章的）.
	 * @return: java.lang.String 实际附件上传组件的文件id（盖完章的）
	 */
	@Column(name = "UF_SALE_FILE_ID", nullable = true, length = 32)
	public String getUfSaleFileId() {
		return this.ufSaleFileId;
	}

	/**
	 * 方法: 设置实际附件上传组件的文件id（盖完章的）.
	 * @param: java.lang.String 实际附件上传组件的文件id（盖完章的）
	 */
	public void setUfSaleFileId(String ufSaleFileId) {
		this.ufSaleFileId = ufSaleFileId;
	}

	/**
	 * 方法: 取得同一文件类型的排序号.
	 * @return: int 同一文件类型的排序号
	 */
	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * 方法: 设置同一文件类型的排序号.
	 * @param: int 同一文件类型的排序号
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
