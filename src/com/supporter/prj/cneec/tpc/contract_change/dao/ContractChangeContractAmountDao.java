package com.supporter.prj.cneec.tpc.contract_change.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeContractAmount;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractChangeContractAmountDao
 * @Description: 合同额DAO
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
@Repository
public class ContractChangeContractAmountDao extends MainDaoSupport<ContractChangeContractAmount, String> {

	/**
	 * 分页查询
	 */
	public List<ContractChangeContractAmount> findPage(JqGrid jqGrid, String chId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" chId = ? ", CommonUtil.trim(chId));
		jqGrid.addSortPropertyAsc("amountId");
		return this.retrievePage(jqGrid);
	}

}
