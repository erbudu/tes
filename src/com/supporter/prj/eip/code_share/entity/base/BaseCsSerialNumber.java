package com.supporter.prj.eip.code_share.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

// ~ File Information
// ====================================================================================================================

/**
 * [应用库.编号规则表]对应的实体抽象类.
 * 
 * @author 康博
 * @createDate 2017-05-02
 * @since 6.0
 * @modifier 康博
 * @modifiedDate 2017-05-02
 */
@MappedSuperclass
public class BaseCsSerialNumber implements Serializable {

    // ~ Static Fields
    // ================================================================================================================

    /**
     * UID.
     */
    private static final long serialVersionUID = 20170502095009L;

    /**
     * 属性 serialNumberId.
     */
    public static final String PROP_SERIAL_NUMBER_ID = "serialNumberId";

    /**
     * 属性 serialNumberNameZh.
     */
    public static final String PROP_SERIAL_NUMBER_NAME_ZH = "serialNumberNameZh";

    /**
     * 属性 serialNumberNameEn.
     */
    public static final String PROP_SERIAL_NUMBER_NAME_EN = "serialNumberNameEn";

    /**
     * 属性 serialNumberName3.
     */
    public static final String PROP_SERIAL_NUMBER_NAME_3 = "serialNumberName3";

    /**
     * 属性 serialNumberName4.
     */
    public static final String PROP_SERIAL_NUMBER_NAME_4 = "serialNumberName4";

    /**
     * 属性 moduleNo.
     */
    public static final String PROP_MODULE_NO = "moduleNo";

    /**
     * 属性 category2No.
     */
    public static final String PROP_CATEGORY2_NO = "category2No";

    /**
     * 属性 category2Name.
     */
    public static final String PROP_CATEGORY2_NAME = "category2Name";

    /**
     * 属性 serialNumberMode.
     */
    public static final String PROP_SERIAL_NUMBER_MODE = "serialNumberMode";

    /**
     * 属性 notes.
     */
    public static final String PROP_NOTES = "notes";

    /**
     * 属性 numberingType.
     */
    public static final String PROP_NUMBERING_TYPE = "numberingType";

    /**
     * 属性 numberingLength.
     */
    public static final String PROP_NUMBERING_LENGTH = "numberingLength";

    /**
     * 属性 numberingStart.
     */
    public static final String PROP_NUMBERING_START = "numberingStart";

    /**
     * 属性 numberingBaseDeptId.
     */
    public static final String PROP_NUMBERING_BASE_DEPT_ID = "numberingBaseDeptId";
    /**
     * 业务对象Id
     */
    public static final String PROP_DOMAIN_OBJECT_DI = "domainObjectId";
    /**
     * 重新生成类型.
     */
    public static final String PROP_NUMBERING_REGENERATE_TYPE = "regenerateType";

    // ~ Fields
    // ================================================================================================================

    /**
     * 编号规则ID .
     */
    /**
     * 主键 .
     */
    @Id
    @Column(name = "SERIAL_NUMBER_ID", nullable = false, length = 32)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String serialNumberId;

    /**
     * 编号规则中文名称 .
     */
    @Column(name = "SERIAL_NUMBER_NAME_ZH")
    private String serialNumberNameZh;

    /**
     * 编号规则英文名称 .
     */
    @Column(name = "SERIAL_NUMBER_NAME_EN")
    private String serialNumberNameEn;

    /**
     * 编号规则名称3 .
     */
    @Column(name = "SERIAL_NUMBER_NAME_3")
    private String serialNumberName3;

    /**
     * 编号规则名称4 .
     */
    @Column(name = "SERIAL_NUMBER_NAME_4")
    private String serialNumberName4;

    /**
     * 所属应用编号 .
     */
    @Column(name = "MODULE_NO")
    private String moduleNo;

    /**
     * 二级分类编号 .
     */
    @Column(name = "CATEGORY_2_NO")
    private String category2No;

    /**
     * 二级分类名称 .
     */
    @Column(name = "CATEGORY_2_NAME")
    private String category2Name;

    /**
     * 编号方式 .
     */
    @Column(name = "SERIAL_NUMBER_MODE")
    private int serialNumberMode;

    /**
     * 备注 .
     */
    @Column(name = "NOTES")
    private String notes;

    /**
     * 流水号类型 .
     */
    @Column(name = "NUMBERING_TYPE")
    private String numberingType;

    /**
     * 流水号长度 .
     */
    @Column(name = "NUMBERING_LENGTH")
    private int numberingLength;

    /**
     * 流水号起始值 .
     */
    @Column(name = "NUMBERING_START")
    private int numberingStart;

    /**
     * 流水号类型依据的部门/单位ID.
     */
    @Column(name = "NUMBERING_BASE_DEPT_ID")
    private String numberingBaseDeptId;

    /**
     * 业务对象ID.
     *
     */
    @Column(name = "DOMAIN_OBJECT_ID")
    private String domainObjectId;
    /**
     * 重新生成类型.
     */
    @Column(name = "NUMBERING_REGENERATE_TYPE")
    private String regenerateType;
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
     * @return 返回  {@link #serialNumberNameZh}。
     */
    public String getSerialNumberNameZh() {
        return this.serialNumberNameZh;
    }

    /**
     * @param serialNumberNameZh 要设置的 {@link #serialNumberNameZh}。
     */
    public void setSerialNumberNameZh(String serialNumberNameZh) {
        this.serialNumberNameZh = serialNumberNameZh;
    }

    /**
     * @return 返回  {@link #serialNumberNameEn}。
     */
    public String getSerialNumberNameEn() {
        return this.serialNumberNameEn;
    }

    /**
     * @param serialNumberNameEn 要设置的 {@link #serialNumberNameEn}。
     */
    public void setSerialNumberNameEn(String serialNumberNameEn) {
        this.serialNumberNameEn = serialNumberNameEn;
    }

    /**
     * @return 返回  {@link #serialNumberName3}。
     */
    public String getSerialNumberName3() {
        return this.serialNumberName3;
    }

    /**
     * @param serialNumberName3 要设置的 {@link #serialNumberName3}。
     */
    public void setSerialNumberName3(String serialNumberName3) {
        this.serialNumberName3 = serialNumberName3;
    }

    /**
     * @return 返回  {@link #serialNumberName4}。
     */
    public String getSerialNumberName4() {
        return this.serialNumberName4;
    }

    /**
     * @param serialNumberName4 要设置的 {@link #serialNumberName4}。
     */
    public void setSerialNumberName4(String serialNumberName4) {
        this.serialNumberName4 = serialNumberName4;
    }

    /**
     * @return 返回  {@link #moduleNo}。
     */
    public String getModuleNo() {
        return this.moduleNo;
    }

    /**
     * @param moduleNo 要设置的 {@link #moduleNo}。
     */
    public void setModuleNo(String moduleNo) {
        this.moduleNo = moduleNo;
    }

    /**
     * @return 返回  {@link #category2No}。
     */
    public String getCategory2No() {
        return this.category2No;
    }

    /**
     * @param category2No 要设置的 {@link #category2No}。
     */
    public void setCategory2No(String category2No) {
        this.category2No = category2No;
    }

    /**
     * @return 返回  {@link #category2Name}。
     */
    public String getCategory2Name() {
        return this.category2Name;
    }

    /**
     * @param category2Name 要设置的 {@link #category2Name}。
     */
    public void setCategory2Name(String category2Name) {
        this.category2Name = category2Name;
    }

    /**
     * @return 返回  {@link #serialNumberMode}。
     */
    public int getSerialNumberMode() {
        return this.serialNumberMode;
    }

    /**
     * @param serialNumberMode 要设置的 {@link #serialNumberMode}。
     */
    public void setSerialNumberMode(int serialNumberMode) {
        this.serialNumberMode = serialNumberMode;
    }

    /**
     * @return 返回  {@link #notes}。
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * @param notes 要设置的 {@link #notes}。
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return 返回  {@link #numberingType}。
     */
    public String getNumberingType() {
        return this.numberingType;
    }

    /**
     * @param numberingType 要设置的 {@link #numberingType}。
     */
    public void setNumberingType(String numberingType) {
        this.numberingType = numberingType;
    }

    /**
     * @return 返回  {@link #numberingLength}。
     */
    public int getNumberingLength() {
        return this.numberingLength;
    }

    /**
     * @param numberingLength 要设置的 {@link #numberingLength}。
     */
    public void setNumberingLength(int numberingLength) {
        this.numberingLength = numberingLength;
    }

    /**
     * @return 返回  {@link #numberingStart}。
     */
    public int getNumberingStart() {
        return this.numberingStart;
    }

    /**
     * @param numberingStart 要设置的 {@link #numberingStart}。
     */
    public void setNumberingStart(int numberingStart) {
        this.numberingStart = numberingStart;
    }

    /**
     * @return 返回  {@link #numberingBaseDeptId}。
     */
    public String getNumberingBaseDeptId() {
        return numberingBaseDeptId;
    }

    /**
     * @param numberingBaseDeptId 要设置的 {@link #numberingBaseDeptId}。
     */
    public void setNumberingBaseDeptId(String numberingBaseDeptId) {
        this.numberingBaseDeptId = numberingBaseDeptId;
    }

    public String getDomainObjectId() {
        return domainObjectId;
    }

    public void setDomainObjectId(String domainObjectId) {
        this.domainObjectId = domainObjectId;
    }

    public String getRegenerateType() {
        return regenerateType;
    }

    public void setRegenerateType(String regenerateType) {
        this.regenerateType = regenerateType;
    }

}
