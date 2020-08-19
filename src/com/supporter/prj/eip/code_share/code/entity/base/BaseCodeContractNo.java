package com.supporter.prj.eip.code_share.code.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * @Title: Entity
 * @Description: CS_CODE_CONTRACT_NO,字段与数据库字段一一对应.
 * @author Administrator
 * @date 2019-07-17 16:46:50
 * @version V1.0
 *
 */
@MappedSuperclass
public class BaseCodeContractNo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * CODE_ID.
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "CODE_ID", nullable = false, length = 32)
	private java.lang.String codeId;
	/**
	 * PRJ_ID.
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	private java.lang.String prjId;
	/**
	 * CONTRACT_ID.
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	private java.lang.String contractId;
	/**
	 * CONTRACT_NO.
	 */
	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	private java.lang.String contractNo;
	/**
	 * PRJ_LIB.
	 */
	@Column(name = "PRJ_LIB", nullable = true, length = 16)
	private java.lang.String prjLib;
	/**
	 * CODE_TYPE.
	 */
	@Column(name = "CODE_TYPE", nullable = true, precision = 10)
	private int codeType = -1;
	
	@Column(name = "PARENT_CODE", nullable = true, length = 128)
	private java.lang.String parentCode;
	
	/**
	 * SHARE_CODE.
	 */
	@Column(name = "SHARE_CODE", nullable = true, length = 128)
	private java.lang.String shareCode;
	/**
	 * DISPLAY_ORDER.
	 */
	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	private int displayOrder;
	/**
	 * CREATED_DATE.
	 */
	@Column(name = "CREATED_DATE", nullable = true)
	private java.util.Date createdDate;
	/**
	 * IS_CODE_LOCKED.
	 */
	@Column(name = "IS_CODE_LOCKED", nullable = true, length = 1)
	@Type(type = "true_false")
	private Boolean codeLocked;

	/**
	 * 方法: 取得CODE_ID.
	 * 
	 * @return: java.lang.String CODE_ID
	 */
	public java.lang.String getCodeId() {
		return this.codeId;
	}

	/**
	 * 方法: 设置CODE_ID.
	 * 
	 * @param: java.lang.String
	 *             CODE_ID
	 */
	public void setCodeId(java.lang.String codeId) {
		this.codeId = codeId;
	}

	/**
	 * 方法: 取得PRJ_ID.
	 * 
	 * @return: java.lang.String PRJ_ID
	 */
	public java.lang.String getPrjId() {
		return this.prjId;
	}

	/**
	 * 方法: 设置PRJ_ID.
	 * 
	 * @param: java.lang.String
	 *             PRJ_ID
	 */
	public void setPrjId(java.lang.String prjId) {
		this.prjId = prjId;
	}

	/**
	 * 方法: 取得CONTRACT_ID.
	 * 
	 * @return: java.lang.String CONTRACT_ID
	 */
	public java.lang.String getContractId() {
		return this.contractId;
	}

	/**
	 * 方法: 设置CONTRACT_ID.
	 * 
	 * @param: java.lang.String
	 *             CONTRACT_ID
	 */
	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	
	/**
	 * 方法: 取得CONTRACT_NO.
	 * 
	 * @return: java.lang.String CONTRACT_NO
	 */
	public java.lang.String getContractNo() {
		return this.contractNo;
	}

	/**
	 * 方法: 设置CONTRACT_NO.
	 * 
	 * @param: java.lang.String
	 *             CONTRACT_NO
	 */
	public void setContractNo(java.lang.String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 方法: 取得PRJ_LIB.
	 * 
	 * @return: java.lang.String PRJ_LIB
	 */
	public java.lang.String getPrjLib() {
		return this.prjLib;
	}

	/**
	 * 方法: 设置PRJ_LIB.
	 * 
	 * @param: java.lang.String
	 *             PRJ_LIB
	 */
	public void setPrjLib(java.lang.String prjLib) {
		this.prjLib = prjLib;
	}

	/**
	 * 方法: 取得CODE_TYPE.
	 * 
	 * @return: int CODE_TYPE
	 */
	public int getCodeType() {
		return this.codeType;
	}

	/**
	 * 方法: 设置CODE_TYPE.
	 * 
	 * @param: int
	 *             CODE_TYPE
	 */
	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	/**
	 * 方法: 取得SHARE_CODE.
	 * 
	 * @return: java.lang.String SHARE_CODE
	 */
	public java.lang.String getShareCode() {
		return this.shareCode;
	}
	public void setShareCode(java.lang.String shareCode) {
		this.shareCode = shareCode;
	}
	
	public java.lang.String getParentCode() {
		return this.parentCode;
	}
	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * 方法: 取得DISPLAY_ORDER.
	 * 
	 * @return: int DISPLAY_ORDER
	 */
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * 方法: 设置DISPLAY_ORDER.
	 * 
	 * @param: int
	 *             DISPLAY_ORDER
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * 方法: 取得CREATED_DATE.
	 * 
	 * @return: java.util.Date CREATED_DATE
	 */
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * 方法: 设置CREATED_DATE.
	 * 
	 * @param: java.util.Date
	 *             CREATED_DATE
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 方法: 取得IS_CODE_LOCKED.
	 * 
	 * @return: java.lang.String IS_CODE_LOCKED
	 */
	public Boolean isCodeLocked() {
		return this.codeLocked;
	}

	/**
	 * 方法: 设置IS_CODE_LOCKED.
	 * 
	 * @param: java.lang.String
	 *             IS_CODE_LOCKED
	 */
	public void setCodeLocked(Boolean isCodeLocked) {
		this.codeLocked = isCodeLocked;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseCodeContractNo() {

	}

	/**
	 * 构造函数.
	 * 
	 * @param codeId
	 */
	public BaseCodeContractNo(String codeId) {
		this.codeId = codeId;
	}
}
