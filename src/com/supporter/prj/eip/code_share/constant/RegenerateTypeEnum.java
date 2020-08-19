package com.supporter.prj.eip.code_share.constant;

// ~ File Information
// ====================================================================================================================

/**
 * 重新生成枚举类.
 * 
 * @author 康博
 * @createDate 2017-5-3
 * @since 6.0
 *
 */
public enum RegenerateTypeEnum {

    // ~ Enums
    // ================================================================================================================

    /**
     * 从不.
     */
    NEVER_VALUE("NEVER_VALUE", "从不"),

    /**
     * 每年.
     */
    YEAR_VALUE("YEAR_VALUE", "每年"),
    /**
     * 日期类型。
     */
    MONTH_VALUE("MONTH_VALUE", "每月");

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
    private RegenerateTypeEnum(String innerName, String displayName) {
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
