package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;

// ~ File Information
// ====================================================================================================================

/**
 * [年度部门顺序号]对应的实体类的联合主键.
 * 
 * @author 康博
 * @createDate 2017-5-19
 * @since 6.0
 *
 */
public class CsSerialYearDeptNoKey implements Serializable {

	// ~ Static Fields
	// ================================================================================================================
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 201705191458L;

	// ~ Fields
	// ================================================================================================================
	
	/**
	 * 编号规则ID .
	 */
	private String serialNumberId;
		
	/**
	 * 单位ID .
	 */
	private String deptId;
	
	/**
	 * 年份.
	 */
	private int year;
	
	// ~ Constructors
	// ================================================================================================================
	
	public CsSerialYearDeptNoKey(int year, String serialNumberId, String deptId) {
		this.year = year;
		this.serialNumberId = serialNumberId;
		this.deptId = deptId;
	}
	
	public CsSerialYearDeptNoKey() {
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
}
