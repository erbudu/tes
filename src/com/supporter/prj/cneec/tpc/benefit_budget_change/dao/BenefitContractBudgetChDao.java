package com.supporter.prj.cneec.tpc.benefit_budget_change.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractBudgetCh;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class BenefitContractBudgetChDao extends MainDaoSupport<BenefitContractBudgetCh, String> {
	public List<BenefitContractBudgetCh> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if ((parameters != null) && (!(parameters.isEmpty()))) {
			String changeId = (String) parameters.get("changeId");
			jqGrid.addHqlFilter(" changeId = ? ", new Object[] { changeId });
		} else {
			jqGrid.addHqlFilter(" changeId = ? ", new Object[] { Integer.valueOf(0) });
		}

		jqGrid.addSortPropertyAsc("orderDisplay");
		return retrievePage(jqGrid);
	}

	@SuppressWarnings("unchecked")
	public List<BenefitContractBudgetCh> queryByParam(Map<String, Object> params, List<String> likeSearhNames,
			Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractBudgetCh.class, params,
				likeSearhNames, orders);
		return (List<BenefitContractBudgetCh>) getHibernateTemplate().findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	public List<BenefitContractBudgetCh> queryBy(String properName, Object propValue, Boolean likeSearch,
			String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractBudgetCh.class, properName, propValue,
				likeSearch, orderByName, sort);
		return (List<BenefitContractBudgetCh>) getHibernateTemplate().findByCriteria(dc);
	}

	public BenefitContractBudgetCh queryUnique(String changeId, String budgetId) {
		String hql = "from " + BenefitContractBudgetCh.class.getName() + " t where t.changeId = ? and t.budgetId = ?";
		return ((BenefitContractBudgetCh) findUniqueResult(hql, new Object[] { changeId, budgetId }));
	}

	public List<BenefitContractBudgetCh> queryByProcessId(String changeId) {
		return queryBy("changeId", changeId, Boolean.valueOf(false), "orderDisplay", Boolean.valueOf(true));
	}
	
	public BenefitContractBudgetCh getBenefitBudgetMaoliByChangeId(String changeId) {
		String hql = " from " + BenefitContractBudgetCh.class.getName() + " where changeId = ? and budgetId = 'TPC_BENEFIT_SUMMARY_GROSS_PROFIT' ";
		List<BenefitContractBudgetCh> budget = this.find(hql, changeId);
		if (budget != null) {
			return budget.get(0);
		}
		return null;
	}
	
}