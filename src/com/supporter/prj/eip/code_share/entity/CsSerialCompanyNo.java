package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialCompanyNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialCompanyNoKey;

// ~ File Information
// ====================================================================================================================

/**
 * [单位顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2015-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_COMPANY_NO")
public class CsSerialCompanyNo extends BaseCsSerialCompanyNo {

	// ~ Static Fields
	// ================================================================================================================
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 201705180858L;

	// ~ Fields
	// ================================================================================================================

	// ~ Constructors
	// ================================================================================================================
	
	public CsSerialCompanyNo(CsSerialCompanyNoKey key) {
		super(key);
	}
	
	public CsSerialCompanyNo() {
		super();
	}
	
	// ~ Methods
	// ================================================================================================================

}
