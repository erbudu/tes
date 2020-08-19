package com.supporter.prj.eip.code_share.constant;

// ~ File Information
// ====================================================================================================================

/**
 * 部门类型对应的值的枚举类。
 * 
 * @author 康博
 * @createDate 2017-5-3
 * @since 6.0
 *
 */
public enum DeptTypeValueEnum {

	// ~ Enums
	// ================================================================================================================
	
	/**
	 * 系统编号.
	 */
	SYSTEM_NO("SYSTEM_NO", "系统编号"),
	
	/**
	 * 单位名称.
	 */
	DEPT_NAME("DEPT_NAME", "单位名称"),
	
	/**
	 * 英文单位名称.
	 */
	DEPT_NAME_EN("DEPT_NAME_EN", "英文单位名称"),
	
	/**
	 * 单位名称拼音.
	 */
	DEPT_NAME_PY("DEPT_NAME_PY", "单位名称拼音"),
	
	/**
	 * 显示名称.
	 */
	DISPLAY_NAME("DISPLAY_NAME", "显示名称"),
	
	/**
	 * 英文显示名称.
	 */
	DISPLAY_NAME_EN("DISPLAY_NAME_EN", "英文显示名称"),
	
	/**
	 * 显示名称拼音.
	 */
	DISPLAY_NAME_PY("DISPLAY_NAME_PY", "显示名称拼音");

	// ~ Static Fields
	// ================================================================================================================

	// ~ Fields
	// ================================================================================================================
	
	/**
	 * 内部名.
	 */
	private String innerName;
	
	/**
	 * 显示名称.
	 */
	private String displayName;

	// ~ Constructors
	// ================================================================================================================
	
	/**
	 * 构造函数
	 *
	 * @param innerName
	 * @param displayName
	 */
	private DeptTypeValueEnum(String innerName, String displayName) {
		this.innerName = innerName;
		this.displayName = displayName;
	}

	// ~ Methods
	// ================================================================================================================

	/**
	 * @return 返回  {@link #innerName}。
	 */
	public String getInnerName() {
		return innerName;
	}

	/**
	 * @param innerName 要设置的 {@link #innerName}。
	 */
	public void setInnerName(String innerName) {
		this.innerName = innerName;
	}

	/**
	 * @return 返回  {@link #displayName}。
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName 要设置的 {@link #displayName}。
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
