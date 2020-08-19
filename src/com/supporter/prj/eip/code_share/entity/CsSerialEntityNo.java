package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialEntityNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialEntityNoKey;

/**
 * [业务对象顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2015-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_ENTITY_NO")
public class CsSerialEntityNo extends BaseCsSerialEntityNo {
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 201705180858L;

	
	public CsSerialEntityNo(CsSerialEntityNoKey key) {
		super(key);
	}
	
	public CsSerialEntityNo() {
		super();
	}

}
