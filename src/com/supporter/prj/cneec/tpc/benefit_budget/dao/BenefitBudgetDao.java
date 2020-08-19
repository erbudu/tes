package com.supporter.prj.cneec.tpc.benefit_budget.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitBudget;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: BenefitBudgetDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
@Repository
public class BenefitBudgetDao extends MainDaoSupport<BenefitBudget, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<BenefitBudget> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && parameters.containsKey("projectId")) {
			String projectId = (String) parameters.get("projectId");
			jqGrid.addHqlFilter("projectId = ?", projectId);
			// 列表页面搜索输入框可查询字段
			String keyword = (String) parameters.get("keyword");
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or budgetId like ? or budgetName like ?",
						"%" + keyword + "%", "%" + keyword	+ "%", "%" + keyword + "%");
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyAsc("createdDate");
		} else {
			jqGrid.addHqlFilter(" 1 <> 1");
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 多条件过滤
	 * 
	 * @param params
	 * @param likeSearhNames
	 * @param orders
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BenefitBudget> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitBudget.class, params, likeSearhNames, orders);
		return (List<BenefitBudget>) getHibernateTemplate().findByCriteria(dc);
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
	public List<BenefitBudget> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitBudget.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<BenefitBudget>) getHibernateTemplate().findByCriteria(dc);
	}
	
	/**
	 * 根据项目ID获取项目预算的毛利
	 * @param ProjectId
	 * @return
	 */
	public BenefitBudget getBenefitBudgetMaoliByProjectId(String projectId) {
		String hql = " from " + BenefitBudget.class.getName() + " where projectId = ? and budgetId = 'TPC_BENEFIT_SUMMARY_GROSS_PROFIT' ";
		List<BenefitBudget> budget = this.find(hql, projectId);
		if (budget != null) {
			return budget.get(0);
		}
		return null;
	}

}
