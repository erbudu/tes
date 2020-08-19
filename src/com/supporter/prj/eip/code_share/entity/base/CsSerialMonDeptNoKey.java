package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;

// ~ File Information
// ====================================================================================================================

/**
 * [月度部门顺序号]对应的实体类的联合主键.
 * 
 * @author 康博
 * @createDate 2017-5-19
 * @since 6.0
 *
 */
public class CsSerialMonDeptNoKey implements Serializable {

	// ~ Static Fields
	// ================================================================================================================
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 201705191406L;

	// ~ Fields
	// ================================================================================================================
	
	/**
	 * 编号规则ID .
	 */
	private String serialNumberId;
		
	/**
	 * 部门ID .
	 */
	private String deptId;
	
	/**
	 * 年份.
	 */
	private int year;
	
	/**
	 * 月份.
	 */
	private int month;

	// ~ Constructors
	// ================================================================================================================
	
	public CsSerialMonDeptNoKey(int year, int month, String serialNumberId, String deptId) {
		this.year = year;
		this.month = month;
		this.serialNumberId = serialNumberId;
		this.deptId = deptId;
	}
	
	public CsSerialMonDeptNoKey() {
		super();
	}

	// ~ Methods
	// ================================================================================================================

	/**
	 * @return 返回  {@link #serialNumberId}。
	 */
	public String getSerialNumberId() {
		return serialNumberId;
	}

	/**
	 * @param serialNumberId 要设置的 {@link #serialNumberId}。
	 */
	public void setSerialNumberId(String serialNumberId) {
		this.serialNumberId = serialNumberId;
	}

	/**
	 * @return 返回  {@link #deptId}。
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId 要设置的 {@link #deptId}。
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return 返回  {@link #year}。
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year 要设置的 {@link #year}。
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return 返回  {@link #month}。
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month 要设置的 {@link #month}。
	 */
	public void setMonth(int month) {
		this.month = month;
	}
}
