package com.supporter.prj.cneec.tpc.benefit_budget_change.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitBudgetChange;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class BenefitBudgetChangeDao extends MainDaoSupport<BenefitBudgetChange, String> {
	public List<BenefitBudgetChange> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if ((parameters != null) && (parameters.containsKey("projectId"))) {
			String projectId = (String) parameters.get("projectId");
			jqGrid.addHqlFilter("projectId = ?", new Object[] { projectId });

			String keyword = (String) parameters.get("keyword");
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or budgetId like ? or budgetName like ?",
						new Object[] { "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%" });
			}

			jqGrid.addSortPropertyAsc("createdDate");
		} else {
			jqGrid.addHqlFilter(" 1 <> 1", new Object[0]);
		}
		return retrievePage(jqGrid);
	}
	
	@SuppressWarnings("unchecked")
	public List<BenefitBudgetChange> queryByParam(Map<String, Object> params, List<String> likeSearhNames,
			Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitBudgetChange.class, params, likeSearhNames,
				orders);
		return (List<BenefitBudgetChange>) getHibernateTemplate().findByCriteria(dc);
	}
	
	@SuppressWarnings("unchecked")
	public List<BenefitBudgetChange> queryBy(String properName, Object propValue, Boolean likeSearch,
			String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitBudgetChange.class, properName, propValue,
				likeSearch, orderByName, sort);
		return (List<BenefitBudgetChange>) getHibernateTemplate().findByCriteria(dc);
	}
}