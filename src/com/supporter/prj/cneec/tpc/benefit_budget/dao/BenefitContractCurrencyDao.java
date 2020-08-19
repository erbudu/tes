package com.supporter.prj.cneec.tpc.benefit_budget.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitContractCurrency;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: BenefitContractCurrencyDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Repository
public class BenefitContractCurrencyDao extends MainDaoSupport<BenefitContractCurrency, String> {

	/**
	 * 分页查询
	 */
	public List<BenefitContractCurrency> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
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
	public List<BenefitContractCurrency> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractCurrency.class, params, likeSearhNames, orders);
		return (List<BenefitContractCurrency>) getHibernateTemplate().findByCriteria(dc);
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
	public List<BenefitContractCurrency> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(BenefitContractCurrency.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<BenefitContractCurrency>) getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 根据主表（过程合同）ID,币别ID获取对象
	 * 
	 * @param processId
	 * @return
	 */
	public BenefitContractCurrency queryUnique(String processId, String currencyId) {
		String hql = "from " + BenefitContractCurrency.class.getName() + " t where t.processId = ? and t.currencyId = ?";
		return this.findUniqueResult(hql, processId, currencyId);
	}

	/**
	 * 根据主表（过程合同）ID获取币别集
	 * 
	 * @param processId
	 * @return
	 */
	public List<BenefitContractCurrency> queryByProcessId(String processId) {
		return this.queryBy("processId", processId, false, "orderDisplay", true);
	}

}
