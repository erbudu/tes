package com.supporter.prj.cneec.tpc.benefit_note.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNoteBudget;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: BenefitNoteBudgetDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Repository
public class BenefitNoteBudgetDao extends MainDaoSupport<BenefitNoteBudget, String> {

	/**
	 * 分页查询
	 */
	public List<BenefitNoteBudget> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
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
	public List<BenefitNoteBudget> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitNoteBudget.class, params, likeSearhNames, orders);
		return (List<BenefitNoteBudget>) getHibernateTemplate().findByCriteria(dc);
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
	public List<BenefitNoteBudget> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitNoteBudget.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<BenefitNoteBudget>) getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 根据主表ID预算ID获取预算项
	 * @param noteId
	 * @param budgetId
	 * @return
	 */
	public BenefitNoteBudget queryUnique(String noteId, String budgetId) {
		String hql = "from " + BenefitNoteBudget.class.getName() + " t where t.noteId = ? and t.budgetId = ?";
		return this.findUniqueResult(hql, noteId, budgetId);
	}

	/**
	 * 根据主表ID获取币别集
	 * @param noteId
	 * @return
	 */
	public List<BenefitNoteBudget> queryByNoteId(String noteId) {
		return this.queryBy("noteId", noteId, false, "orderDisplay", true);
	}

}
