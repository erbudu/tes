package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialYearUniformNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialYearUniformNoKey;

// ~ File Information
// ====================================================================================================================

/**
 * [年度统一编号]对应的实体类.
 * 
 * @author 康博 
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_YEAR_UNIFORM_NO")
public class CsSerialYearUniformNo extends BaseCsSerialYearUniformNo {

    // ~ Static Fields
    // ================================================================================================================

    /**
     * UID.
     */
    private static final long serialVersionUID = 201705180940L;

    // ~ Fields
    // ================================================================================================================

    // ~ Constructors
    // ================================================================================================================

    public CsSerialYearUniformNo(CsSerialYearUniformNoKey key) {
        super(key);
    }

    public CsSerialYearUniformNo() {
        super();
    }

    // ~ Methods
    // ================================================================================================================

}
