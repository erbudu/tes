package com.supporter.prj.cneec.tpc.derivative_contract_change.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChangeAmount;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractChangeAmountDao
 * @Description: DAO类
 * @version: V1.0
 */
@Repository
public class DerivativeContractChangeAmountDao extends MainDaoSupport<DerivativeContractChangeAmount, String> {

	/**
	 * 分页查询
	 */
	public List<DerivativeContractChangeAmount> findPage(JqGrid jqGrid, String chId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" chId = ? ", CommonUtil.trim(chId));
		jqGrid.addSortPropertyAsc("amountId");
		return this.retrievePage(jqGrid);
	}

}
