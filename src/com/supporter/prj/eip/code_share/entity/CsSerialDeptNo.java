package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialDeptNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialDeptNoKey;

// ~ File Information
// ====================================================================================================================

/**
 * [部门顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_DEPT_NO")
public class CsSerialDeptNo extends BaseCsSerialDeptNo {

	// ~ Static Fields
	// ================================================================================================================
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 201705180908L;

	// ~ Fields
	// ================================================================================================================

	// ~ Constructors
	// ================================================================================================================
	
	public CsSerialDeptNo(CsSerialDeptNoKey key) {
		super(key);
	}
	
	public CsSerialDeptNo() {
		super();
	}

	// ~ Methods
	// ================================================================================================================

}
