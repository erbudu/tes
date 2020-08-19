package com.supporter.prj.eip.code_share.constant;

/**
 * 明细类型枚举类.
 * 
 * @author 康博
 * @createDate 2017-5-3
 * @since 6.0
 *
 */
public enum RecordTypeEnum {

    /**
     * 固定值.
     */
    FIXED_VALUE("FIXED_VALUE", "固定值"),

    /**
     * 系统级值类型.
     * 过时类型
     */
    //SYSTEM_VALUE("SYSTEM_VALUE", "系统级值类型"),

    /**
     * 业务对象元数据.
     */
    BUSINESS_VALUE("BUSINESS_VALUE", "元数据"),
	
	/**
     * 日期类型。
     */
    DATE_VALUE("DATE_VALUE", "日期"),
    
    /**
     * 顺序号类型。
     */
    NUMBER_VALUE("NUMBER_VALUE", "顺序号");


    /**
     * 内部名.
     */
    private String innerName;

    /**
     * 显示名称.
     */
    private String displayName;

    /**
     * 构造函数
     *
     * @param innerName
     * @param displayName
     */
    private RecordTypeEnum(String innerName, String displayName) {
        this.innerName = innerName;
        this.displayName = displayName;
    }

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
