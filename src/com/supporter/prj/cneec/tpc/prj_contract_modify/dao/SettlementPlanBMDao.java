package com.supporter.prj.cneec.tpc.prj_contract_modify.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.SettlementPlanBM;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

@Repository
public class SettlementPlanBMDao extends MainDaoSupport<SettlementPlanBM, String> {

	/**
	 * 根据contractId获取对象
	 * @param contractId
	 * @return
	 */
	public List<SettlementPlanBM> getByContractId(String contractId) {
		String hql = " from " + SettlementPlanBM.class.getName() + " where contractId = ?";
		List<SettlementPlanBM> list = this.find(hql, contractId);
		return list;
	}

}
