package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

// ~ File Information
// ====================================================================================================================

/**
 * [随机数]对应的实体类的父类.
 * 
 * @author 康博
 * @createDate 2017-05-17
 * @since 6.0
 * @modifier 康博
 * @modifiedDate 2017-05-17
 */
@MappedSuperclass
public class BaseCsSerialRandomNo implements ISerialNoEntity, Serializable {

    // ~ Static Fields
    // ================================================================================================================

    /**
     * UID.
     */
    private static final long serialVersionUID = 20170517175131L;

    /**
     * 属性 serialNumberId.
     */
    public static final String PROP_SERIAL_NUMBER_ID = "serialNumberId";

    /**
     * 属性 indexNo.
     */
    public static final String PROP_INDEX_NO = "indexNo";

    // ~ Fields
    // ================================================================================================================

    /**
     * 编号规则ID .
     */
    @Id
    @Column(name = "SERIAL_NUMBER_ID", nullable = false, length = 32)
    private String serialNumberId;

    /**
     * 序号 .
     */
    @Column(name = "INDEX_NO")
    private int indexNo;

    // ~ Constructors
    // ================================================================================================================

    // ~ Methods
    // ================================================================================================================

    /**
     * @return 返回  {@link #serialNumberId}。
     */
    public String getSerialNumberId() {
        return this.serialNumberId;
    }

    /**
     * @param serialNumberId 要设置的 {@link #serialNumberId}。
     */
    public void setSerialNumberId(String serialNumberId) {
        this.serialNumberId = serialNumberId;
    }

    /**
     * @return 返回  {@link #indexNo}。
     */
    @Override
    public int getIndexNo() {
        return this.indexNo;
    }

    /**
     * @param indexNo 要设置的 {@link #indexNo}。
     */
    @Override
    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }

}
