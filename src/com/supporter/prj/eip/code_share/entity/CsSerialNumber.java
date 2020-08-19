package com.supporter.prj.eip.code_share.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialNumber;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.module.entity.IDomainObject;
import com.supporter.util.I18NUtil;

// ~ File Information
// ====================================================================================================================

/**
 * [应用库.编号规则表]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-05-02
 * @since 6.0
 * @modifier 康博
 * @modifiedDate 2017-05-02
 */
@Entity
@Table(name = "CS_SERIAL_NUMBER")
public class CsSerialNumber extends BaseCsSerialNumber {

    // ~ Static Fields
    // ================================================================================================================

    /**
     * UID.
     */
    private static final long serialVersionUID = 20170502095009L;

    // ~ Fields
    // ================================================================================================================

    /**
     * 当前语言对应的编号规则名称.
     */
    @Transient
    private String serialNumberName;

    /**
     * 所属应用名称.
     */
    @Transient
    private String moduleName;

    /**
     * 编号方式描述.
     */
    @Transient
    private String serialNumberModeDesc;

    /**
     * 编号规则明细.
     */
    @Transient
    private List < CsSerialNumberRecord > listRecord;
    /**
     * 业务对象名称.
     */
    @Transient
    private String domainObjectName;
    // ~ Constructors
    // ================================================================================================================

    // ~ Methods
    // ================================================================================================================

    /**
     * @return 返回  {@link #serialNumberName}。
     */
    public String getSerialNumberName() {
        if (StringUtils.isNotEmpty(serialNumberName)) {
            return serialNumberName;
        }

        serialNumberName = I18NUtil.getPropValue(this, "serialNumberName");

        if (StringUtils.isEmpty(serialNumberName)) {
            serialNumberName = this.getSerialNumberNameZh();
        }

        return serialNumberName;
    }

    /**
     * @param serialNumberName 要设置的 {@link #serialNumberName}。
     */
    public void setSerialNumberName(String serialNumberName) {
        this.serialNumberName = serialNumberName;
    }

    /**
     * @return 返回  {@link #moduleName}。
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName 要设置的 {@link #moduleName}。
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return 返回  {@link #serialNumberModeDesc}。
     */
    public String getSerialNumberModeDesc() {
        return serialNumberModeDesc;
    }

    /**
     * @param serialNumberModeDesc 要设置的 {@link #serialNumberModeDesc}。
     */
    public void setSerialNumberModeDesc(String serialNumberModeDesc) {
        this.serialNumberModeDesc = serialNumberModeDesc;
    }

    /**
     * @return 返回  {@link #listRecord}。
     */
    public List<CsSerialNumberRecord> getListRecord() {
        return listRecord;
    }

    /**
     * @param listRecord 要设置的 {@link #listRecord}。
     */
    public void setListRecord(List<CsSerialNumberRecord> listRecord) {
        this.listRecord = listRecord;
    }

    public String getDomainObjectName() {
        if(StringUtils.isNotEmpty(this.getDomainObjectId())){
            IDomainObject object = EIPService.getModuleService().getDomainObject(this.getDomainObjectId());
            if(object != null){
                return object.getDisplayName();
            }
        }
        return "";
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

}
