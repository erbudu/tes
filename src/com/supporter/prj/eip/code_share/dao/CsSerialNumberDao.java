package com.supporter.prj.eip.code_share.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip.code_share.constant.SerialNumberConstant;
import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip.code_share.util.SerialNumberCommonUtil;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;

// ~ File Information
// ====================================================================================================================

/**
 * 编号规则dao类.
 * 
 * @author 康博
 * @createDate 2017-5-5
 * @since 6.0
 *
 */
@Repository
public class CsSerialNumberDao extends MainDaoSupport < CsSerialNumber, String > implements ISerialNumberDao {

	// ~ Static Fields
	// ================================================================================================================

	// ~ Fields
	// ================================================================================================================

	// ~ Constructors
	// ================================================================================================================

	// ~ Methods
	// ================================================================================================================

	/**
	 * @see com.supporter.prj.eip.code_share.dao.ISerialNumberDao#
	 * getSerialNumberListByPage(com.supporter.prj.core.orm.hibernate.ListPage, java.util.Map)
	 */
	public ListPage < CsSerialNumber > getSerialNumberListByPage(UserProfile user, ListPage < CsSerialNumber > listPage,
			Map < String, Object > searchParam) {
		
		Map < String, Object > param = new HashMap < String, Object > ();
		String hql = "from CsSerialNumber where 1 = 1 ";
		
		//规则名称模糊查询
		if (searchParam.containsKey("searchName")) {
			String searchName = StringUtils.trim(searchParam.get("searchName").toString());
			
			if (StringUtils.isNotEmpty(searchName)) {
				String currentLanguageProp = SerialNumberCommonUtil.getLocalLanguageProp();
				
				hql += " and " + currentLanguageProp + " like :searchName";
				param.put("searchName", "%" + searchName + "%");
			}
		}
		
		if (searchParam.containsKey("moduleNo")) {
			String moduleNo = StringUtils.trim(searchParam.get("moduleNo").toString());
			
			if (StringUtils.isNotEmpty(moduleNo)) {
				hql += " and moduleNo = :moduleNo";
				param.put("moduleNo", moduleNo);
			}
		}
		
		if (searchParam.containsKey("category2NoOrName")) {
			String category2NoOrName = StringUtils.trim(searchParam.get("category2NoOrName").toString());
			
			if (StringUtils.isNotEmpty(category2NoOrName)) {
				hql += " and (category2No like :category2NoOrName or category2Name like :category2NoOrName)";
				param.put("category2NoOrName", "%" + category2NoOrName + "%");
			}
		}
		
		if (searchParam.containsKey("serialNumberMode")) {
			String serialNumberMode = StringUtils.trim(searchParam.get("serialNumberMode").toString());
			
			if (StringUtils.isNotEmpty(serialNumberMode)) {
				hql += " and serialNumberMode = :serialNumberMode";
				param.put("serialNumberMode", Integer.parseInt(serialNumberMode));
			}
		}
		
		//添加限定条件
        String hqlFilter = EIPService.getAuthorityService().getHqlFilter(user, SerialNumberConstant.MODULE_ID, SerialNumberConstant.AUTH_OPER_NAME);
        hql += " and " + hqlFilter;
        return retrievePage(listPage, hql, param);
	}

	/**
	 * @see com.supporter.prj.eip.code_share.dao.ISerialNumberDao#queryByParam(java.util.Map, java.util.Map)
	 */
	@SuppressWarnings({
		"unchecked", "rawtypes"
	})
	public List queryByParam(Map < String, Object > params, Map < String, Boolean > orders) {
		if (params == null || params.isEmpty()) {
			throw new RuntimeException("为避免存在性能问题，必须拥有查询条件");
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
		
		//查询条件
		Set< Entry< String, Object > > setParams = params.entrySet();
		
		for (Entry< String, Object > entry : setParams) {
			dc.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		
		//排序
		Set< Entry< String, Boolean > > setOrders = orders.entrySet();
		
		for (Entry< String, Boolean > entry : setOrders) {
			dc.addOrder(entry.getValue() ? Order.asc(entry.getKey()) : Order.desc(entry.getKey()));
		}
		
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * @see com.supporter.prj.eip.code_share.dao.ISerialNumberDao#batchDelete(java.util.List)
	 */
	public void batchDelete(List < String > listId) {
		String hql = "delete from CsSerialNumber where serialNumberId in (:listId)";
		
		Map < String, Object > param = new HashMap < String, Object > ();
		param.put("listId", listId);
		this.execUpdate(hql, param);
	}

	/**
	 * @see com.supporter.prj.eip.code_share.dao.ISerialNumberDao#clearData()
	 */
	public void clearData() {
		this.getHibernateTemplate().clear();
	}
}
