package com.supporter.prj.cneec.tpc.benefit_note.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNoteCurrency;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: BenefitNoteCurrencyDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Repository
public class BenefitNoteCurrencyDao extends MainDaoSupport<BenefitNoteCurrency, String> {

	/**
	 * 分页查询
	 */
	public List<BenefitNoteCurrency> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			String noteId = (String) parameters.get("noteId");
			jqGrid.addHqlFilter(" noteId = ? ", noteId);
		} else {
			jqGrid.addHqlFilter(" noteId = ? ", 0);
		}
		// 根据排序属性排列
		jqGrid.addSortPropertyAsc("orderDisplay");
		return this.retrievePage(jqGrid);
	}

	/**
	 * 多条件过滤
	 * 
	 * @param params
	 *            过滤字段集
	 * @param likeSearhNames
	 *            like字段集
	 * @param orders
	 *            排序字段集
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BenefitNoteCurrency> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitNoteCurrency.class, params, likeSearhNames, orders);
		return (List<BenefitNoteCurrency>) getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 条件过滤
	 * 
	 * @param properName
	 *            过滤字段
	 * @param propValue
	 *            过滤字段值
	 * @param likeSearch
	 *            是否like
	 * @param orderByName
	 *            排序字段
	 * @param sort
	 *            排序方式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BenefitNoteCurrency> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitNoteCurrency.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<BenefitNoteCurrency>) getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 获取唯一对象
	 * @param noteId
	 * @param currencyId
	 * @return
	 */
	public BenefitNoteCurrency queryUnique(String noteId, String currencyId) {
		String hql = "from " + BenefitNoteCurrency.class.getName() + " t where t.noteId = ? and t.currencyId = ?";
		return this.findUniqueResult(hql, noteId, currencyId);
	}

	/**
	 * 根据变更表ID获取币别集
	 * @param noteId
	 * @return
	 */
	public List<BenefitNoteCurrency> queryByNoteId(String noteId) {
		return this.queryBy("noteId", noteId, false, "orderDisplay", true);
	}

}
