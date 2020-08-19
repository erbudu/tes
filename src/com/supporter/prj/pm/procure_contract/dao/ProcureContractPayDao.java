package com.supporter.prj.pm.procure_contract.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractPay;
import com.supporter.util.CommonUtil;

/**
 * @Title: 付款条款
 * @Description: DAO类
 * @author liyinfeng
 * @date 2018-6-14
 * @version: V1.0
 */
@Repository
public class ProcureContractPayDao extends MainDaoSupport<ProcureContractPay, String> {

	/**
	 * 分页查询.
	 * @param jqGrid 表格
	 * @param contractId 采购合同ID
	 * @return List<ProcureContractPay>
	 */
	public List<ProcureContractPay> findPage(JqGrid jqGrid, String contractId) {
		jqGrid.addHqlFilter("contractId = ?", CommonUtil.trim(contractId));
		jqGrid.addSortPropertyAsc("payId");
		return this.retrievePage(jqGrid);
	}
	
	public List<ProcureContractPay> getPayGridToChange(JqGrid jqGrid, String contractId) {
		jqGrid.addHqlFilter("contractId = ?", CommonUtil.trim(contractId));
		jqGrid.addHqlFilter("payItem = '预付款'");
		jqGrid.addSortPropertyAsc("payId");
		return this.retrievePage(jqGrid);
	}
	
	public List<ProcureContractPay> findPaymentPage(JqGrid jqGrid,ProcureContractPay contractPay,String contractId) {
		if(contractPay != null){
			List<ProcureContractPay> retList = new ArrayList<ProcureContractPay>();
			StringBuffer sb = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sb.append(" from ").append(ProcureContractPay.class.getName()).append(" z where 1=1 ");
			sb.append("and  contractId = '" + contractId + "'");	
			sb.append("and exists (select contract.contractId from "
					+ "ProcureContract contract where z.contractId = contract.contractId "
					+ "and contract.status=" + ProcureContract.Status.COMPLETE + ")"); //1-生效
			this.retrievePage(jqGrid, sb.toString(), params.toArray());		
			return jqGrid.getRows();
		}
		return null;
	}

	/**
	 * 查询付款金额
	 * @param parameters
	 * @return
	 */
	public Map<String, Double> getPayPlanAmounts(String contractId) {
		Map<String, Double> payplanActAmounts = new HashMap<String, Double>();
		String hql = "select substr(t.receiveDate, 0, 7), sum(t.receiveRmbAmount) from " + ProcureContractPay.class.getName() + " t where t.contractId = ?"
				+ " group by substr(t.receiveDate, 0, 7)";
		List<Object[]> list = this.find(hql, contractId);
		for (Object[] obj : list) {
			payplanActAmounts.put(obj[0].toString(), Double.parseDouble(obj[1].toString()));
		}
		return payplanActAmounts;
	}
	
	/**
	 * 根据合同id查询付款
	 */
	public List<ProcureContractPay> getByContractId(String contractId) {
		String hql = "from ProcureContractPay where contractId = '"+contractId+"'";
		List<ProcureContractPay> payList = this.find(hql);
		if(payList != null && payList.size() > 0){
			return payList;
		}else{
			return null;
		}		 
	}

	public ProcureContractPay getByPayItemId(String contractId,String payItemId){
		String hql = "from ProcureContractPay where contractId = '"+contractId+"' and payItemId = '"+ payItemId +"'";
		List<ProcureContractPay> payList = this.find(hql);
		if(payList != null && payList.size() > 0){
			return payList.get(0);
		}else{
			return null;
		}
	}
	
	public List<ProcureContractPay> getListByPayItemId(String contractId,String payItemId){
		String hql = "from ProcureContractPay where contractId = '"+contractId+"' and payItemId = '"+ payItemId +"'";
		List<ProcureContractPay> payList = this.find(hql);
		if(payList != null && payList.size() > 0){
			return payList;
		}else{
			return null;
		}
	}
	
}
