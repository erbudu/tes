package com.supporter.prj.eip.code_share.constant;

// ~ File Information
// ====================================================================================================================

/**
 * 流水号类型枚举类.
 * 
 * @author 康博
 * @createDate 2017-5-9
 * @since 6.0
 *
 */
public enum NumberingTypeEnum {

    // ~ Enums
    // ================================================================================================================

    /**
     * 顺序号.
     */
    //GLOBAL("GLOBAL", "顺序号", 0),

    /**
     * 单位顺序号.
     */
    COMPANY("COMPANY", "按单位编号", 9),

    /**
     * 部门顺序号.
     */
    DEPT("DEPT", "按部门编号", 10),
    /**
     * 统一编号.
     */
    UNIFORM_NUMBER("UNIFORM_NUMBER","统一编号", 11),
    /**
     * 随机数
     */
    ROUND_NUMBER("ROUND_NUMBER","随机数", 0),
	
	/**
     * 业务对象顺序号.
     */
    ENTITY("ENTITY", "按业务对象编号", 3);
    
    /**
     * 年度顺序号.
     */
    //YEAR("YEAR", "年度顺序号", 0),

    /**
     * 年度部门顺序号.
     */
    //YEAR_DEPT("YEAR_DEPT", "年度部门顺序号", 10),

    /**
     * 年度单位顺序号.
     */
    //YEAR_COMPANY("YEAR_COMPANY", "年度单位顺序号", 9),

    /**
     * 月度部门顺序号.
     */
    //MONTH_DEPT("MONTH_DEPT", "月度部门顺序号", 10),

    /**
     * 月度单位顺序号.
     */
    //MONTH_COMPANY("MONTH_COMPANY", "月度单位顺序号", 9);


    // ~ Static Fields
    // ================================================================================================================

    // ~ Fields
    // ================================================================================================================

    /**
     * 内部名.
     */
    private String innerName;

    /**
     * 显示名.
     */
    private String displayName;

    /**
     * 类型，单位为9，部门为10，业务对象为3, 不限制为0.
     */
    private int dataType;

    // ~ Constructors
    // ================================================================================================================

    /**
     * 构造方法
     *
     * @param innerName
     * @param displayName
     */
    private NumberingTypeEnum(String innerName, String displayName, int dataType) {
        this.innerName = innerName;
        this.displayName = displayName;
        this.dataType = dataType;
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
     * @return 返回  {@link #dataType}。
     */
    public int getDataType() {
        return dataType;
    }

    /**
     * @param dataType 要设置的 {@link #dataType}。
     */
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
