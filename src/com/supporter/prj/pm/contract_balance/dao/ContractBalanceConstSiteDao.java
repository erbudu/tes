package com.supporter.prj.pm.contract_balance.dao;

import org.springframework.stereotype.Repository;
import com.supporter.util.CommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSite;
import com.supporter.prj.pm.contract_balance.entity.SpecialBalanceItem;

/**   
 * @Title: 合同结算.施工合同.涉及工程部位
 * @Description: PM_CONTRACT_BALANCE_CONST_SITE.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
@Repository
public class ContractBalanceConstSiteDao extends MainDaoSupport< ContractBalanceConstSite, String > {
	/**
	 * 分页查询
	 * @param jqGrid 表格参数
	 * @param balanceId 结算ID
	 * @return List<ContractBalanceConstSite>
	 */
	public List<ContractBalanceConstSite> findPage(JqGrid jqGrid, String balanceId) {
		jqGrid.addHqlFilter(" balanceId = ? ", CommonUtil.trim(balanceId));
		jqGrid.addSortPropertyAsc("siteId");
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取预付款总额（不包含指定的结算）.
	 * @param contractId 合同ID
	 * @param balanceId 结算ID
	 * @return double
	 */
	public double getAdvancePayment(String contractId, String balanceId) {
		String hql = "select sum(currentPay) as totalAmount from " + ContractBalanceConstSite.class.getName()
				+ " where wbsId=? and contractId=? and balanceId<>?";
		Object obj = this.retrieveFirst(hql, SpecialBalanceItem.ItemId.KJX_ADVANCE, contractId, balanceId);
		if (obj == null) {
			return 0D;
		}
		
		return (Double) obj;
	}
	
	/**
	 * 获取累计工程量（不包含指定的结算）.
	 * @param wbsId wbsId
	 * @param contractId 合同ID
	 * @param balanceId 结算ID
	 * @return double
	 */
	private double getWbsAccumulativeCount(String wbsId, String contractId, String balanceId) {
		String hql = "select sum(currentCount) as sumCount from " + ContractBalanceConstSite.class.getName()
				+ " where wbsId=? and contractId=? and balanceId<>?";
		Object obj = this.retrieveFirst(hql, wbsId, contractId, balanceId);
		if (obj == null) {
			return 0D;
		}
		
		return (Double) obj;
	}
	
	/**
	 * 获取累计付款额（不包含指定的结算）.
	 * @param wbsId wbsId
	 * @param contractId 合同ID
	 * @param balanceId 结算ID
	 * @return double
	 */
	private double getWbsAccumulativePay(String wbsId, String contractId, String balanceId) {
		String hql = "select sum(currentPay) as sumPay from " + ContractBalanceConstSite.class.getName()
				+ " where wbsId=? and contractId=? and balanceId<>?";
		Object obj = this.retrieveFirst(hql, wbsId, contractId, balanceId);
		if (obj == null) {
			return 0D;
		}
		
		return (Double) obj;
	}
	
	/**
	 * 获取累计结算SITE对象.
	 * @param contractId 合同ID
	 * @param balanceId 结算ID
	 * @return List<ContractBalanceConstSite>
	 */
	public List<ContractBalanceConstSite> getAccumulativeSites(String contractId, String balanceId) {
		String hql = "from " + ContractBalanceConstSite.class.getName()
				+ " where contractId=? and balanceId<>?";
		return this.find(hql, contractId, balanceId);
	}
	
	//根据siteId和recordId获取
	public List<ContractBalanceConstSite> getEngBySiteIdAndContractId(String siteId, String contractId) {
		String hql = "from ContractBalanceConstSite where siteId = '" + siteId + "' and contractId = '" + contractId + "'";
		List<ContractBalanceConstSite> list = find(hql);
		if(list != null && list.size() > 0){
			return list;
		}else{
			return null;	
		} 	
	}
}

