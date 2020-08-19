package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialMonCompanyNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialMonCompanyNoKey;

// ~ File Information
// ====================================================================================================================

/**
 * [月度单位顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_MON_COMPANY_NO")
public class CsSerialMonCompanyNo extends BaseCsSerialMonCompanyNo {

	// ~ Static Fields
	// ================================================================================================================
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 201705181017L;

	// ~ Fields
	// ================================================================================================================

	// ~ Constructors
	// ================================================================================================================
	
	public CsSerialMonCompanyNo(CsSerialMonCompanyNoKey key) {
		super(key);
	}
	
	public CsSerialMonCompanyNo() {
		super();
	}

	// ~ Methods
	// ================================================================================================================

}
