package com.supporter.prj.eip.code_share.constant;

// ~ File Information
// ====================================================================================================================

/**
 * 系统值类型对应的label枚举类.
 * 
 * @author 康博
 * @createDate 2017-5-3
 * @since 6.0
 *
 */
public enum SystemLabelEnum {

	// ~ Enums
	// ================================================================================================================
	
	/**
	 * 当前所在公司.
	 */
	CURRENT_COMPANY("CURRENT_COMPANY", "当前所在公司", "DEPT"),
	
	/**
	 * 当前时间.
	 */
	CURRENT_DATE("CURRENT_DATE", "当前时间", "DATE");

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
	
	/**
	 * 值类型.
	 */
	private String valueType;

	// ~ Constructors
	// ================================================================================================================
	
	/**
	 * 构造函数
	 *
	 * @param innerName
	 * @param displayName
	 * @param valueType
	 */
	private SystemLabelEnum(String innerName, String displayName, String valueType) {
		this.innerName = innerName;
		this.displayName = displayName;
		this.valueType = valueType;
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

	/**
	 * @return 返回  {@link #valueType}。
	 */
	public String getValueType() {
		return valueType;
	}

	/**
	 * @param valueType 要设置的 {@link #valueType}。
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
}
