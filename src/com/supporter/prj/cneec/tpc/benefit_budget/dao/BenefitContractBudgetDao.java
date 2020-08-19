package com.supporter.prj.cneec.tpc.benefit_budget.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitBudget;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitContractBudget;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: BenefitContractBudgetDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-05-08
 * @version: V1.0
 */
@Repository
public class BenefitContractBudgetDao extends MainDaoSupport<BenefitContractBudget, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<BenefitContractBudget> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			String processId = (String) parameters.get("processId");
			jqGrid.addHqlFilter(" processId = ? ", processId);
		} else {
			jqGrid.addHqlFilter(" processId = ? ", 0);
		}
		// 根据排序属性排列
		jqGrid.addSortPropertyAsc("orderDisplay");
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
	public List<BenefitContractBudget> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractBudget.class, params, likeSearhNames, orders);
		return (List<BenefitContractBudget>) getHibernateTemplate().findByCriteria(dc);
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
	public List<BenefitContractBudget> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractBudget.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<BenefitContractBudget>) getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 根据变更合同ID，预算ID获取唯一对象
	 * 
	 * @param processId
	 * @param budgetId
	 * @return
	 */
	public BenefitContractBudget queryUnique(String processId, String budgetId) {
		String hql = "from " + BenefitContractBudget.class.getName() + " t where t.processId = ? and t.budgetId = ?";
		return this.findUniqueResult(hql, processId, budgetId);
	}
	/**
	 * 根据变更合同ID，预算ID获取唯一对象的毛利
	 * 
	 * @param processId
	 * @param budgetId
	 * @return
	 */
	public BenefitContractBudget getBenefitBudgetMaoliByProcessId(String processId) {
		String hql = " from " + BenefitContractBudget.class.getName() + " where processId = ? and budgetId = 'TPC_BENEFIT_SUMMARY_GROSS_PROFIT' ";
		List<BenefitContractBudget> budget = this.find(hql, processId);
		if (budget != null) {
			return budget.get(0);
		}
		return null;
	}
	/**
	 * 根据主表ID获取币别集
	 * @param processId
	 * @return
	 */
	public List<BenefitContractBudget> queryByProcessId(String processId) {
		return this.queryBy("processId", processId, false, "orderDisplay", true);
	}

}
