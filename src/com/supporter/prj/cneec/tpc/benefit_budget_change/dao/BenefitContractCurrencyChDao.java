package com.supporter.prj.cneec.tpc.benefit_budget_change.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCh;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCurrencyCh;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class BenefitContractCurrencyChDao extends MainDaoSupport<BenefitContractCurrencyCh, String> {
	public List<BenefitContractCurrencyCh> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if ((parameters != null) && (!(parameters.isEmpty()))) {
			String noteId = (String) parameters.get("changeId");
			jqGrid.addHqlFilter(" changeId = ? ", new Object[] { noteId });
		} else {
			jqGrid.addHqlFilter(" changeId = ? ", new Object[] { Integer.valueOf(0) });
		}

		jqGrid.addSortPropertyAsc("orderDisplay");
		return retrievePage(jqGrid);
	}

	@SuppressWarnings("unchecked")
	public List<BenefitContractCurrencyCh> queryByParam(Map<String, Object> params, List<String> likeSearhNames,
			Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractCurrencyCh.class, params,
				likeSearhNames, orders);
		return (List<BenefitContractCurrencyCh>) getHibernateTemplate().findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	public List<BenefitContractCurrencyCh> queryBy(String properName, Object propValue, Boolean likeSearch,
			String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractCurrencyCh.class, properName,
				propValue, likeSearch, orderByName, sort);
		return (List<BenefitContractCurrencyCh>) getHibernateTemplate().findByCriteria(dc);
	}

	public BenefitContractCurrencyCh queryUnique(String changeId, String currencyId) {
		String hql = "from " + BenefitContractCurrencyCh.class.getName()
				+ " t where t.changeId = ? and t.currencyId = ?";
		return ((BenefitContractCurrencyCh) findUniqueResult(hql, new Object[] { changeId, currencyId }));
	}
	/**
	 * 根据销售合同预算变更ID，获取币别。
	 * @param changeId
	 * @return
	 */
	public List<BenefitContractCurrencyCh> queryByProcessId(String changeId) {
		String hql = "from " + BenefitContractCurrencyCh.class.getName()
				+ " where changeId = ? order by orderDisplay asc ";
		List<BenefitContractCurrencyCh> sealList = find(hql, changeId);
		if ((sealList != null) && (sealList.size() > 0)) {
			return sealList;
		}
		return null;
	}
	
	/**
	 * 根据销售合同预算变更ID，获取币别列表。
	 * @param changeId
	 * @return sealList
	 */
	public  List<BenefitContractCurrencyCh> getBenefitContractCurrencyChList(BenefitContractCh benefitContractCh) {
		String hql = "from " + BenefitContractCurrencyCh.class.getName()
				+ " where changeId = ? order by orderDisplay desc ";
		List <BenefitContractCurrencyCh> benefitContractCurrencyChList = find(hql, new Object[] { benefitContractCh.getChangeId() });
		if ((benefitContractCurrencyChList != null) && (benefitContractCurrencyChList.size() > 0)) {
			return benefitContractCurrencyChList;
		}
		return null;
	}
}