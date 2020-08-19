package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;

// ~ File Information
// ====================================================================================================================

/**
 * [部门顺序号]对应的实体类的联合主键.
 * 
 * @author 康博
 * @createDate 2017-5-19
 * @since 6.0
 *
 */
public class CsSerialDeptNoKey implements Serializable {

	// ~ Static Fields
	// ================================================================================================================
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 201705191148L;

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

	// ~ Constructors
	// ================================================================================================================
	
	public CsSerialDeptNoKey(String serialNumberId, String deptId) {
		this.serialNumberId = serialNumberId;
		this.deptId = deptId;
	}
	
	public CsSerialDeptNoKey() {
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
}
