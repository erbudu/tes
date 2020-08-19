package com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.constant;

public enum BiddingPermissionStatusEnum {
    DRAFT(0, "草稿"),
    PROCESSING(1, "办理中"),
    COMPLETED(2, "备案完成"),
    INVALID(3, "作废");

    /**
     * 内部名.
     */
    private int innerName;

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
    private BiddingPermissionStatusEnum(int innerName, String displayName) {
        this.innerName = innerName;
        this.displayName = displayName;
    }

    public int getInnerName() {
        return innerName;
    }

    public void setInnerName(int innerName) {
        this.innerName = innerName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
