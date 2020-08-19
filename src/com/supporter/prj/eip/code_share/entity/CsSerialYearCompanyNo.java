package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialYearCompanyNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialYearCompanyNoKey;

// ~ File Information
// ====================================================================================================================

/**
 * [年度单位顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_YEAR_COMPANY_NO")
public class CsSerialYearCompanyNo extends BaseCsSerialYearCompanyNo {

	// ~ Static Fields
	// ================================================================================================================
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 201705180946L;

	// ~ Fields
	// ================================================================================================================

	// ~ Constructors
	// ================================================================================================================
	
	public CsSerialYearCompanyNo(CsSerialYearCompanyNoKey key) {
		super(key);
	}
	
	public CsSerialYearCompanyNo() {
		super();
	}

	// ~ Methods
	// ================================================================================================================

}
