package com.supporter.prj.eip.code_share.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip.code_share.entity.CsSerialNumberRecord;

// ~ File Information
// ====================================================================================================================

/**
 * 规则明细dao类.
 * 
 * @author 康博
 * @createDate 2017-5-5
 * @since 6.0
 *
 */
@Repository
public class CsSerialNumberRecordDao extends MainDaoSupport < CsSerialNumberRecord, String > {

	// ~ Static Fields
	// ================================================================================================================

	// ~ Fields
	// ================================================================================================================

	// ~ Constructors
	// ================================================================================================================

	// ~ Methods
	// ================================================================================================================

	/**
	 * 根据编号规则id获得所有明细记录.
	 * 
	 * @see com.supporter.prj.eip.code_share.dao.ISerialNumberRecordDao#
	 * getListRecordBySerialNumberId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List < CsSerialNumberRecord > getListRecordBySerialNumberId(String serialNumberId) {
		String hql = "from CsSerialNumberRecord where serialNumberId = :serialNumberId order by displayOrder";
		Map < String, Object > param = new HashMap < String, Object > ();
		param.put("serialNumberId", serialNumberId);
		
		return this.retrieve(hql, param);
	}

	/**
	 * @see com.supporter.prj.eip.code_share.dao.ISerialNumberRecordDao#batchDelete(java.util.List)
	 */
	public void batchDelete(List < String > listSerialNumberId) {
		String hql = "delete from CsSerialNumberRecord where serialNumberId in (:listSerialNumberId)";
		Map < String, Object > param = new HashMap < String, Object > ();
		param.put("listSerialNumberId", listSerialNumberId);
		
		this.execUpdate(hql, param);
	}
}
