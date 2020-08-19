package com.supporter.prj.eip.code_share.code.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: CS_ENITY_PROJECT,字段与数据库字段一一对应.
 * @author Administrator
 * @date 2019-07-17 16:46:49
 * @version V1.0
 *
 */
@MappedSuperclass
public class BaseEntityProject implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * PRJ_REC_ID.
	 */
	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "PRJ_REC_ID", nullable = false, length = 64)
	private java.lang.String prjRecId;
	/**
	 * PRJ_ID.
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	private java.lang.String prjId;
	/**
	 * PRJ_NAME.
	 */
	@Column(name = "PRJ_NAME", nullable = true, length = 512)
	private java.lang.String prjName;
	/**
	 * PRJ_NO.
	 */
	@Column(name = "PRJ_NO", nullable = true, length = 64)
	private java.lang.String prjNo;
	/**
	 * PRJ_LIB.
	 */
	@Column(name = "PRJ_LIB", nullable = true, length = 16)
	private java.lang.String prjLib;
	/**
	 * SOURCE_FROM.
	 */
	@Column(name = "SOURCE_FROM", nullable = true, length = 128)
	private java.lang.String sourceFrom;
	/**
	 * CREATED_DATE.
	 */
	@Column(name = "CREATED_DATE", nullable = true)
	private java.util.Date createdDate;

	/**
	 * 方法: 取得PRJ_REC_ID.
	 * 
	 * @return: java.lang.String PRJ_REC_ID
	 */
	public java.lang.String getPrjRecId() {
		return this.prjRecId;
	}

	/**
	 * 方法: 设置PRJ_REC_ID.
	 * 
	 * @param: java.lang.String
	 *             PRJ_REC_ID
	 */
	public void setPrjRecId(java.lang.String prjRecId) {
		this.prjRecId = prjRecId;
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
	 * 方法: 取得PRJ_NAME.
	 * 
	 * @return: java.lang.String PRJ_NAME
	 */
	public java.lang.String getPrjName() {
		return this.prjName;
	}

	/**
	 * 方法: 设置PRJ_NAME.
	 * 
	 * @param: java.lang.String
	 *             PRJ_NAME
	 */
	public void setPrjName(java.lang.String prjName) {
		this.prjName = prjName;
	}

	/**
	 * 方法: 取得PRJ_NO.
	 * 
	 * @return: java.lang.String PRJ_NO
	 */
	public java.lang.String getPrjNo() {
		return this.prjNo;
	}

	/**
	 * 方法: 设置PRJ_NO.
	 * 
	 * @param: java.lang.String
	 *             PRJ_NO
	 */
	public void setPrjNo(java.lang.String prjNo) {
		this.prjNo = prjNo;
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
	 * 方法: 取得SOURCE_FROM.
	 * 
	 * @return: java.lang.String SOURCE_FROM
	 */
	public java.lang.String getSourceFrom() {
		return this.sourceFrom;
	}

	/**
	 * 方法: 设置SOURCE_FROM.
	 * 
	 * @param: java.lang.String
	 *             SOURCE_FROM
	 */
	public void setSourceFrom(java.lang.String sourceFrom) {
		this.sourceFrom = sourceFrom;
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
	 * 无参构造函数.
	 */
	public BaseEntityProject() {

	}

	/**
	 * 构造函数.
	 * 
	 * @param prjRecId
	 */
	public BaseEntityProject(String prjRecId) {
		this.prjRecId = prjRecId;
	}
}
