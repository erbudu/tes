package com.supporter.prj.eip.code_share.constant;

// ~ File Information
// ====================================================================================================================

/**
 * 日期类型对应的值的枚举类.
 * 
 * @author 康博
 * @createDate 2017-5-3
 * @since 6.0
 *
 */
public enum DateTypeValueEnum {

    // ~ Enums
    // ================================================================================================================

    /**
     * 年.
     */
    Y("yyyy", "YYYY"),

    /**
     * 月.
     */
    //M("MM", "月"),

    /**
     * 日.
     */
    //D("dd", "日"),

    /**
     * 年月.
     */
    YM("yyyyMM", "YYYYMM"),

    /**
     * 年月日.
     */
    YMD("yyyyMMdd", "YYYYMMDD"),

    /**
     * 月日.
     */
    //MD("MMdd", "月日"),

    /**
     * 年-月.
     */
    Y_M("yyyy-MM", "YYYY-MM"),

    /**
     * 年-月-日.
     */
    Y_M_D("yyyy-MM-dd", "YYYY-MM-DD");

    /**
     * 月-日.
     */
    //M_D("MM-dd", "月-日");

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
     * @param formatStyle
     */
    private DateTypeValueEnum(String innerName, String displayName) {
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
