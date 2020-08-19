package com.supporter.prj.cneec.tpc.use_seal.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: UseSealDetailDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-10-23
 * @version: V1.0
 */
@Repository
public class UseSealDetailDao extends MainDaoSupport<UseSealDetail, String> {

	public List<UseSealDetail> findPage(JqGrid jqGrid, String sealId) {
		// 客户明细须通过主表ID获取
		if (StringUtils.isNotBlank(sealId)) {
			jqGrid.addHqlFilter(" sealId = ? ", sealId);
		} else {
			jqGrid.addHqlFilter(" sealId = ? ", "");
		}
		jqGrid.addSortPropertyAsc("detailId");
		return this.retrievePage(jqGrid);
	}

	/**
	 * 参数多值过滤
	 * @param params
	 * @param likeSearhNames
	 * @param orders
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UseSealDetail> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = DetachedCriteria.forClass(UseSealDetail.class);
		if (params != null) {
			for (Map.Entry<String, Object> e : params.entrySet()) {
				if ((likeSearhNames != null) && (likeSearhNames.contains(e.getKey()))) {
					// 需要模糊查询的字段
					dc.add(Restrictions.like((String) e.getKey(), e.getValue()));
				} else if (e.getValue() == null || (e.getValue() instanceof String && StringUtils.isBlank((String) e.getValue()))) {
					// 属性值是空的字段(null或"")
					dc.add(Restrictions.isNull((String) e.getKey()));
				} else if (e.getValue().getClass().isArray()) {
					// 属性值可以是多个的字段
					dc.add(Restrictions.in((String) e.getKey(), (Object[]) e.getValue()));
				} else {
					dc.add(Restrictions.eq((String) e.getKey(), e.getValue()));
				}
			}
		}
		if (orders != null) {
			for (Map.Entry<String, Boolean> e : orders.entrySet()) {
				dc.addOrder(((Boolean) e.getValue()).booleanValue() ? Order.asc((String) e.getKey()) : Order.desc((String) e.getKey()));
			}
		}
		return (List<UseSealDetail>) getHibernateTemplate().findByCriteria(dc);
	}

}
