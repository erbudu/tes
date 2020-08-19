package com.supporter.prj.eip.code_share.dao;

import java.util.List;

import com.supporter.prj.eip.code_share.entity.CsSerialNumberRecord;

// ~ File Information
// ====================================================================================================================

/**
 * 规则明细dao接口类.
 * 
 * @author 康博
 * @createDate 2017-5-5
 * @since 6.0
 *
 */
public interface ISerialNumberRecordDao {

	// ~ Static Fields
	// ================================================================================================================

	// ~ Methods
	// ================================================================================================================

	/**
	 * 保存规则明细集合实例.
	 * 
	 * @param listReocrd 规则明细实例
	 */
	void save(List < CsSerialNumberRecord > listReocrd);
	
	/**
	 * 根据条件删除明细记录.
	 * 
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @return
	 */
	int deleteByProperty(String propertyName, Object propertyValue);
	
	/**
	 * 根据编号规则ID获得所有明细.
	 * 
	 * @param serialNumberId 编号规则id
	 * @return
	 */
	List < CsSerialNumberRecord > getListRecordBySerialNumberId(String serialNumberId);
	
	/**
	 * 批量删除.
	 * 
	 * @param listSerialNumberId 编号规则id集合
	 * @return
	 */
	void batchDelete(List < String > listSerialNumberId);
}
