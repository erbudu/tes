package com.supporter.prj.cneec.tpc.trade_homepage.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.trade_homepage.entity.TradeHomepage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

@Repository
public class TradeHomepageDao extends MainDaoSupport<TradeHomepage, String> {

	/**
	 * 
	 * @param params
	 * @param orders
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TradeHomepage> queryByParam(Map<String, Object> params, Map<String, Boolean> orders) {
		DetachedCriteria dc = DetachedCriteria.forClass(TradeHomepage.class);
		Set<Map.Entry<String, Object>> setParams = params.entrySet();
		for (Map.Entry<String, Object> entry : setParams) {
			if (entry.getValue() == null) {
				dc.add(Restrictions.isNull((String) entry.getKey()));
			} else {
				if (entry.getValue().getClass().isArray()) {
					dc.add(Restrictions.in((String) entry.getKey(), (Object[]) entry.getValue()));
				} else {
					dc.add(Restrictions.eq((String) entry.getKey(), entry.getValue()));
				}
			}
		}
		if (orders != null) {
			Set<Map.Entry<String, Boolean>> setOrders = orders.entrySet();
			for (Map.Entry<String, Boolean> entry : setOrders) {
				dc.addOrder(((Boolean) entry.getValue()).booleanValue() ? Order.asc((String) entry.getKey()) : Order.desc((String) entry.getKey()));
			}
		}
		return (List<TradeHomepage>) getHibernateTemplate().findByCriteria(dc);
	}

}
