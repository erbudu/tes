package com.supporter.prj.eip.code_share.constant;

// ~ File Information
// ====================================================================================================================

/**
 * 人员类型对应的值的枚举类.
 * 
 * @author 康博
 * @createDate 2017-5-3
 * @since 6.0
 *
 */
public enum PersonTypeValueEnum {

	// ~ Enums
	// ================================================================================================================
	
	/**
	 * 姓名.
	 */
	NAME("NAME", "姓名"),
	
	/**
	 * 工号.
	 */
	NO("NO", "工号");

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
	private PersonTypeValueEnum(String innerName, String displayName) {
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
