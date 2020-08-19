package com.supporter.prj.pm.contract_balance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceVisa;

@Repository
public class ContractBalanceVisaDao extends MainDaoSupport<ContractBalanceVisa, String> {
	public List<ContractBalanceVisa> getListContractBalanceVisa(String balanceId) {
		String hql = " from " + ContractBalanceVisa.class.getName()
				+ " where balanceId = '" + balanceId + "'";
		List<ContractBalanceVisa> listContractBalanceVisa = this.find(hql);
		return listContractBalanceVisa;
	}
	
}
