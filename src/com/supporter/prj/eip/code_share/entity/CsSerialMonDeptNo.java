package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialMonDeptNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialMonDeptNoKey;


// ~ File Information
// ====================================================================================================================

/**
 * [月度部门顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_MON_DEPT_NO")
public class CsSerialMonDeptNo extends BaseCsSerialMonDeptNo {

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
	
	public CsSerialMonDeptNo(CsSerialMonDeptNoKey key) {
		super(key);
	}
	
	public CsSerialMonDeptNo() {
		super();
	}

	// ~ Methods
	// ================================================================================================================

}
