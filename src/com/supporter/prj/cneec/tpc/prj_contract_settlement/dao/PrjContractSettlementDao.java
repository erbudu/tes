package com.supporter.prj.cneec.tpc.prj_contract_settlement.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlement;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class PrjContractSettlementDao extends MainDaoSupport < PrjContractSettlement, String >{

	/**
	 * 分页查询
	 */
	public List<PrjContractSettlement> findPage(JqGrid jqGrid, String attr,String prjId,String settlementStatus,String controlStatus) {
		if (StringUtils.isNotBlank(attr)) {
			jqGrid.addHqlFilter(
				" contractName like ? or contractNo like ? or confirmDate like ? or settlementApplyDate like ? or settlementNo like ? ",
				"%" + attr + "%", "%" + attr + "%", "%"+ attr + "%", "%"+ attr + "%", "%"+ attr + "%");
		}
		
		if (StringUtils.isNotBlank(prjId)) {
			jqGrid.addHqlFilter(" prjId= ? ",prjId);
		}
		
		if (StringUtils.isNotBlank(settlementStatus)) {
			jqGrid.addHqlFilter(" settlementStatus= ? ",settlementStatus);
		}
		
		if (StringUtils.isNotBlank(controlStatus)) {
			jqGrid.addHqlFilter(" controlStatus= ? ",controlStatus);
		}
		return this.retrievePage(jqGrid);
	}
	
	
	/**
	 * 从付款单中取得本年度的最大付款序列号.
	 * @return
	 */
	public int getMaxIndex(){		
		String hql = "select max(settlementIndex) from "
			+ PrjContractSettlement.class.getName() +" where settlementYear = ?";
		List<Integer> list = this.find(hql, Calendar.getInstance().get(Calendar.YEAR));
		if (CollectionUtils.isNotEmpty(list)) {
			if(list.get(0) != null){
				return list.get(0);				
			}
		}
		return 0;
	}
	
    /**
     * 根据执行中项目获得这个项目的所有结算单金额总和.
     * @author ts
     * @param prjId 执行项目实例实例id.
     * @return 本项目的结算总金额.
     */
    public double getTotalSettlementAmountAct(String prjId){
    	
    	double ld_TotalSettlementAmountAct = 0;
    	String hql = "from " + PrjContractSettlement.class.getName()+ " where settlementStatus = '"+PrjContractSettlement.SettlementStatus.COMPLETE+"' and " +
    			" controlStatusCode = '"+ PrjContractSettlement.ControlStatus.EFFECTIV +"' and prjId = ? ";
    	List<PrjContractSettlement> managers = this.find(hql, prjId);
        for (PrjContractSettlement settlement : managers) {
        		ld_TotalSettlementAmountAct += settlement.getOnWayAmount() + settlement.getRealSettlementAmount();
        	}
    	return ld_TotalSettlementAmountAct;
    }
    
    /**
     * 根据合同获取所有付款
     * @return
     */
    public List<PrjContractSettlement> getAllContractSettlement(String contractId){
    	String hql = " from " + PrjContractSettlement.class.getName()+ " where contractId = ?";
    	List<PrjContractSettlement> list = this.find(hql,contractId);
    	return list;
    }
    
    /**
     * 根据采购合同实例获取它的所有在途金额总和(审核中和审批完成的).
     * @author ts
     * @param PrjContract 合同实例.
     * @return 本合同的在途金额总和.
     */
	public List<PrjContractSettlement> getTotalOnWayAmountF(String contractId, String currencyCode, boolean containDraft, boolean containProcessing, String settlementDate) {

		String ls_SQL = " from " + PrjContractSettlement.class.getName() + " where contractId = ?" + " and controlStatus='" + PrjContractSettlement.ControlStatus.EFFECTIV
				+ "'";
		if (!containDraft) {
			ls_SQL += " and settlement_status <> " + PrjContractSettlement.SettlementStatus.DRAFT;
		}
		if (!containProcessing) {
			ls_SQL += " and settlement_status <> " + PrjContractSettlement.SettlementStatus.PROCESSING;
		}
		if (StringUtils.isNotBlank(settlementDate)) {
			ls_SQL += " and created_date < '" + settlementDate + "' ";
		}
		List<PrjContractSettlement> list = this.find(ls_SQL, contractId);

		return list;
	}

	/**
	 * 获取汇款用途
	 * @return
	 */
	public List<String> getRemittancePurpose() {
		String hql = "select t.remittancePurpose from " + PrjContractSettlement.class.getName() + " t where t.remittancePurpose is not null group by t.remittancePurpose";
		List<String> list = this.retrieve(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PrjContractSettlement> queryByParam(Map<String, Object> params, List<String> likeSearhNames, Map<String, Boolean> orders) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(PrjContractSettlement.class, params, likeSearhNames, orders);
		return (List<PrjContractSettlement>) getHibernateTemplate().findByCriteria(dc);
	}

}
