package com.supporter.prj.eip.code_share.constant;

// ~ File Information
// ====================================================================================================================

/**
 * 编号规则方式枚举类.
 * 
 * @author 康博
 * @createDate 2017-5-12
 * @since 6.0
 *
 */
public enum SerialNumberModeEnum {

	// ~ Enums
	// ================================================================================================================
	
	/**
	 * 手动输入.
	 */
	MANUAL_INPUT(0, "手动输入"),
	
	/**
	 * 规则定义.
	 */
	RULE_DEFINITION(1, "规则定义");

	// ~ Static Fields
	// ================================================================================================================

	// ~ Fields
	// ================================================================================================================
	
	/**
	 * 内部名.
	 */
	private int innerName;
	
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
	private SerialNumberModeEnum(int innerName, String displayName) {
		this.innerName = innerName;
		this.displayName = displayName;
	}

	// ~ Methods
	// ================================================================================================================

	/**
	 * @return 返回  {@link #innerName}。
	 */
	public int getInnerName() {
		return innerName;
	}

	/**
	 * @param innerName 要设置的 {@link #innerName}。
	 */
	public void setInnerName(int innerName) {
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
