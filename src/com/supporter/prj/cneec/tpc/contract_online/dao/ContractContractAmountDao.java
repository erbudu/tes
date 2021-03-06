package com.supporter.prj.cneec.tpc.contract_online.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_online.entity.ContractContractAmount;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractContractAmountDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-06
 * @version: V1.0
 */
@Repository
public class ContractContractAmountDao extends MainDaoSupport<ContractContractAmount, String> {

	/**
	 * 分页查询
	 */
	public List<ContractContractAmount> findPage(JqGrid jqGrid, String contractId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" contractId = ? ", CommonUtil.trim(contractId));
		jqGrid.addSortPropertyAsc("amountId");
		return this.retrievePage(jqGrid);
	}

}
