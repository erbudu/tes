package com.supporter.prj.cneec.tpc.derivative_contract_change.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChangeTerms;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractCollectionTerms;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractCollectionTermsDao
 * @Description: DAO类
 * @version: V1.0
 */
@Repository
public class DerivativeContractChangeTermsDao extends MainDaoSupport<DerivativeContractChangeTerms, String> {

	/**
	 * 分页查询
	 */
	public List<DerivativeContractChangeTerms> findPage(JqGrid jqGrid, String chId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" chId = ? ", CommonUtil.trim(chId));
		jqGrid.addSortPropertyAsc("termsId");
		return this.retrievePage(jqGrid);
	}

	/**
	 * 查询付款金额
	 * @param parameters
	 * @return
	 */
	public Map<String, Double> getPayPlanAmounts(String contractId) {
		Map<String, Double> payplanActAmounts = new HashMap<String, Double>();
		String hql = "select substr(t.receiveDate, 0, 7), sum(t.receiveRmbAmount) from " + DerivativeContractCollectionTerms.class.getName() + " t where t.contractId = ?"
				+ " group by substr(t.receiveDate, 0, 7)";
		List<Object[]> list = this.find(hql, contractId);
		for (Object[] obj : list) {
			payplanActAmounts.put(obj[0].toString(), Double.parseDouble(obj[1].toString()));
		}
		return payplanActAmounts;
	}

}
