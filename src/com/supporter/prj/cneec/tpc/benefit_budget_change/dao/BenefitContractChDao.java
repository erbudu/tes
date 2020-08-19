package com.supporter.prj.cneec.tpc.benefit_budget_change.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCh;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCurrencyCh;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class BenefitContractChDao extends MainDaoSupport<BenefitContractCh, String> {
	public List<BenefitContractCh> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if ((parameters != null) && (parameters.containsKey("projectId"))) {
			String projectId = (String) parameters.get("projectId");
			jqGrid.addHqlFilter("projectId = ?", new Object[] { projectId });

			String keyword = (String) parameters.get("keyword");
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(
						" benefitNo like ? or projectName like ? or contractNo like ? or contractName like ?",
						new Object[] { "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%",
								"%" + keyword + "%" });
			}

			jqGrid.addSortPropertyAsc("createdDate");
		} else {
			jqGrid.addHqlFilter(" 1 <> 1", new Object[0]);
		}
		return retrievePage(jqGrid);
	}

	@SuppressWarnings("unchecked")
	public List<BenefitContractCh> queryByParam(Map<String, Object> params, List<String> likeSearhNames,
			Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractCh.class, params, likeSearhNames,
				orders);
		return (List<BenefitContractCh>) getHibernateTemplate().findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	public List<BenefitContractCh> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName,
			Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractCh.class, properName, propValue,
				likeSearch, orderByName, sort);
		return (List<BenefitContractCh>) getHibernateTemplate().findByCriteria(dc);
	}

	public List<BenefitContractCh> queryByProjectId(String projectId) {
		return queryBy("projectId", projectId, Boolean.valueOf(false), "createdDate", Boolean.valueOf(true));
	}

	public String checkBenefitChange(String contractId) {
		String hql = "from BenefitContractCh where contractId = ? order by createdDate desc";
		List orderList = find(hql, new Object[] { contractId });
		if ((orderList != null) && (orderList.size() > 0)) {
			return ((BenefitContractCh) orderList.get(0)).getChangeId();
		}
		return "";
	}

	public boolean checkBenefitChOk(String changeId) {
		String hql = "from BenefitContractCh where changeId = ? and swfStatus=20";
		List sealList = find(hql, new Object[] { changeId });

		return ((sealList != null) && (sealList.size() > 0));
	}

}