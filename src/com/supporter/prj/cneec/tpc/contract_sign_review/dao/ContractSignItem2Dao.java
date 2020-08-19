package com.supporter.prj.cneec.tpc.contract_sign_review.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignItem2;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ContractSignItemDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Repository
public class ContractSignItem2Dao extends MainDaoSupport<ContractSignItem2, String> {

	/**
	 * 分页查询
	 */
	public List<ContractSignItem2> findPage(JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			if (parameters.containsKey("saleContractId")) {// 销售合同一级明细
				jqGrid.addEq("saleContractId", (String) parameters.get("saleContractId"));
				if (parameters.containsKey("parentItemId") && ((String) parameters.get("parentItemId")).length() == 0) {
					jqGrid.addHqlFilter("parentItemId is null");
				}
			} else if (parameters.containsKey("parentItemId")) {// 销售合同二级明细
				jqGrid.addEq("parentItemId", (String) parameters.get("parentItemId"));
			} else if (parameters.containsKey("purchaseContractId")) {// 采购合同明细
				jqGrid.addEq("purchaseContractId", (String) parameters.get("purchaseContractId"));
			} else {
				jqGrid.addHqlFilter(" 1 <> 1 ");
			}
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据条件过滤数据
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ContractSignItem2> findList(Map<String, Object> parameters) {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + ContractSignItem2.class.getName());
		String filter = "";
		for (Map.Entry<String, Object> e : parameters.entrySet()) {
			if (filter.length() > 0) filter = filter + " and ";
			if (e.getValue() != null) {
				filter += e.getKey() + " = :" + e.getKey();
			} else {
				filter += e.getKey() + " is null";
				//parameters.remove(e.getKey());
			}
		}
		hql.append(" where ").append(filter);
		return this.retrieve(hql.toString(), parameters);
	}

	/**
	 * 清除采购合同评审属性
	 */
	public void removePurchaseValue(String inforId) {
		String hql = "update " + ContractSignItem2.class.getName() + " set purchaseReviewId = null, purchaseContractId = null where purchaseContractId = ?";
		this.execUpdate(hql, inforId);
	}

}
