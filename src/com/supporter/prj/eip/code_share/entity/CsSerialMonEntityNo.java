package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialMonEntityNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialMonEntityNoKey;

/**
 * [月度业务实体顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_MON_ENTITY_NO")
public class CsSerialMonEntityNo extends BaseCsSerialMonEntityNo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CsSerialMonEntityNo(CsSerialMonEntityNoKey key) {
		super(key);
	}
	
	public CsSerialMonEntityNo() {
		super();
	}

}
