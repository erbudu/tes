package com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptAmount;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ContractSignDeptAmountDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Repository
public class ContractSignDeptAmountDao extends MainDaoSupport<ContractSignDeptAmount, String> {

	/**
	 * 分页查询
	 */
	public List<ContractSignDeptAmount> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			if (parameters.containsKey("signId")) {
				jqGrid.addHqlFilter("signId = ?", parameters.get("signId").toString());
			}
			if (parameters.containsKey("inforId")) {
				jqGrid.addHqlFilter("inforId = ?", parameters.get("inforId").toString());
			}
			jqGrid.addSortPropertyAsc("amountId");
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 条件过滤
	 * @param properName
	 * @param propValue
	 * @param likeSearch
	 * @param orderByName
	 * @param sort
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ContractSignDeptAmount> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(ContractSignDeptAmount.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<ContractSignDeptAmount>) getHibernateTemplate().findByCriteria(dc);
	}

}
