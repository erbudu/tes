package com.supporter.prj.eip.code_share.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.eip.code_share.entity.base.BaseCsSerialYearEntityNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialYearEntityNoKey;

/**
 * [年度业务对象顺序号]对应的实体类.
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Entity
@Table(name = "CS_SERIAL_YEAR_ENTITY_NO")
public class CsSerialYearEntityNo extends BaseCsSerialYearEntityNo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CsSerialYearEntityNo(CsSerialYearEntityNoKey key) {
		super(key);
	}
	
	public CsSerialYearEntityNo() {
		super();
	}

}
