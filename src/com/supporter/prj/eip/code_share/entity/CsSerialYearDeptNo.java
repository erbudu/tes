package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialYearDeptNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialYearDeptNoKey;

// ~ File Information
// ====================================================================================================================

/**
 * [年度部门顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_YEAR_DEPT_NO")
public class CsSerialYearDeptNo extends BaseCsSerialYearDeptNo {

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
	
	public CsSerialYearDeptNo(CsSerialYearDeptNoKey key) {
		super(key);
	}
	
	public CsSerialYearDeptNo() {
		super();
	}

	// ~ Methods
	// ================================================================================================================

}
