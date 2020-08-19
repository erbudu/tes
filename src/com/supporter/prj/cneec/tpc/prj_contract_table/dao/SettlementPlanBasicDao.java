package com.supporter.prj.cneec.tpc.prj_contract_table.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.SettlementPlanBasic;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

@Repository
public class SettlementPlanBasicDao extends MainDaoSupport<SettlementPlanBasic, String> {

	/**
	 * 根据contractId获取对象
	 * @param contractId
	 * @return
	 */
	public List<SettlementPlanBasic> getByContractId(String contractId) {
		String hql = " from " + SettlementPlanBasic.class.getName() + " where contractId = ?";
		List<SettlementPlanBasic> list = this.find(hql, contractId);
		return list;
	}

}
