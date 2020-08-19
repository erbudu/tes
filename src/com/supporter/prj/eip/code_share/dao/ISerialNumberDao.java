package com.supporter.prj.eip.code_share.dao;

import java.util.List;
import java.util.Map;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip_service.security.entity.UserProfile;

// ~ File Information
// ====================================================================================================================

/**
 * 编号规则dao接口类.
 * 
 * @author 康博
 * @createDate 2017-5-5
 * @since 6.0
 *
 */
public interface ISerialNumberDao {

	// ~ Static Fields
	// ================================================================================================================

	// ~ Methods
	// ================================================================================================================

	/**
	 * 保存或更新编号规则实例.
	 * 
	 * @param serialNumber
	 */
	void saveOrUpdate(CsSerialNumber serialNumber);
	
	/**
	 * 获得编号规则列表数据.
	 * 
	 * @param listPage 列表数据
	 * @param searchParam 查询参数
	 * @return
	 */
	ListPage < CsSerialNumber > getSerialNumberListByPage(UserProfile user, ListPage < CsSerialNumber > listPage,
			Map < String, Object > searchParam);
	
	/**
	 * 获得编号规则实例.
	 * 
	 * @param serialNumberId 编号规则实例
	 * @return
	 */
	CsSerialNumber get(String serialNumberId);
	
	/**
	 * 根据参数执行查询操作.
	 * 
	 * @param params
	 * @return
	 */
	List < CsSerialNumber > queryByParam(Map < String, Object > params, Map < String, Boolean > orders);
	
	/**
	 * 批量删除.
	 * 
	 * @param ids 编号规则id集合
	 * @return
	 */
	void batchDelete(List < String > listId);
	
	/**
	 * 将数据从持久态变为游离态.
	 * 
	 */
	void clearData();
}
