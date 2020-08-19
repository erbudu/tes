package com.supporter.prj.cneec.tpc.contract_online.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_online.entity.ContractCollectionTerms;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractCollectionTermsDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-06
 * @version: V1.0
 */
@Repository
public class ContractCollectionTermsDao extends MainDaoSupport<ContractCollectionTerms, String> {

	/**
	 * 分页查询
	 */
	public List<ContractCollectionTerms> findPage(JqGrid jqGrid, String contractId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" contractId = ? ", CommonUtil.trim(contractId));
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
		String hql = "select substr(t.receiveDate, 0, 7), sum(t.receiveRmbAmount) from " + ContractCollectionTerms.class.getName() + " t where t.contractId = ?"
				+ " group by substr(t.receiveDate, 0, 7)";
		List<Object[]> list = this.find(hql, contractId);
		for (Object[] obj : list) {
			payplanActAmounts.put(obj[0].toString(), Double.parseDouble(obj[1].toString()));
		}
		return payplanActAmounts;
	}
	
	/**
	 * 根据合同ID获取付款条款
	 * @param contractId
	 * @return
	 */
	public List<ContractCollectionTerms> getTermsByContractId(String contractId){
		String hql = " from " + ContractCollectionTerms.class.getName() + " where contractId = '" + contractId + "' ";
		List<ContractCollectionTerms> list = this.find(hql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

}
