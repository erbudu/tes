package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialMonUniformNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialMonUniformNoKey;


// ~ File Information
// ====================================================================================================================

/**
 * [月度统一编号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_MON_UNIFORM_NO")
public class CsSerialMonUniformNo extends BaseCsSerialMonUniformNo {

    // ~ Static Fields
    // ================================================================================================================

    /**
     * UID.
     */
    private static final long serialVersionUID = 201705181008L;

    // ~ Fields
    // ================================================================================================================

    // ~ Constructors
    // ================================================================================================================

    public CsSerialMonUniformNo(CsSerialMonUniformNoKey key) {
        super(key);
    }

    public CsSerialMonUniformNo() {
        super();
    }

    // ~ Methods
    // ================================================================================================================

}
