package com.supporter.prj.cneec.tpc.prj_settlement.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlement;
import com.supporter.prj.cneec.tpc.prj_settlement.util.CommonUtils;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class PrjSettlementDao extends MainDaoSupport < PrjSettlement, String >{

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<PrjSettlement> findPage(JqGrid jqGrid,String attr,String prjId,String settlementStatus) {
		
		if(StringUtils.isNotBlank(prjId)){
			jqGrid.addHqlFilter(" prjId = ? ", prjId);
			
		}
		if(StringUtils.isNotBlank(settlementStatus)){
			jqGrid.addHqlFilter(" settlementStatus = ? ", Integer.parseInt(settlementStatus));
		}
		if(StringUtils.isNotBlank(attr)){
			if(CommonUtils.isNumeric(attr)){
				jqGrid.addHqlFilter(
						" contractName like ? or contractNo like ? or contractorName like ? or settlementAmount = ?  or confirmDate like ? or settlementApplyDate like ?", 
						"%" + attr + "%" , "%" + attr + "%" , "%" + attr + "%" , Double.valueOf(attr) , "%" + attr + "%" , "%" + attr + "%" );
			}else{
				jqGrid.addHqlFilter(
						" contractName like ? or contractNo like ? or contractorName like ? or confirmDate like ? or settlementApplyDate like ?", 
						"%" + attr + "%" , "%" + attr + "%" , "%" + attr + "%" , "%" + attr + "%" , "%" + attr + "%" );
			}
		}
		
		List<PrjSettlement> list = this.retrievePage(jqGrid);
		return list;
	}
	
	/**
	 * 根据费用支付实例获取它的所有结成功的结算单金额总和.
	 * 
	 * @author ts
	 * @param PrjContract
	 *            合同实例.
	 * @return 本合同的结算总金额.
	 */
	public List<PrjSettlement> getTotalSettlementAmountAct(String prjId){
		String hql = " from " + PrjSettlement.class.getName() + " where prjId = ? and settlementStatus = "
			+ PrjSettlement.COMPLETE;
		List<PrjSettlement> list = this.find(hql, prjId);
		return list;
	}
	
	/**
	 * 从付款单中取得本年度的最大付款序列号.
	 * 
	 * @return
	 */
	public int getLatestIndexInDB() {
		String hql = "select max(settlementIndex) from "
			+ PrjSettlement.class.getName() +" where settlementYear = ?";
		List<Integer> list = this.find(hql, Calendar.getInstance().get(Calendar.YEAR));
		if (CollectionUtils.isNotEmpty(list)) {
			if(list.get(0) != null){
				return list.get(0);				
			}
		}
		return 0;
	}

	/**
	 * 获取汇款用途
	 * @return
	 */
	public List<String> getRemittancePurpose() {
		String hql = "select t.remittancePurpose from " + PrjSettlement.class.getName() + " t where t.remittancePurpose is not null group by t.remittancePurpose";
		List<String> list = this.retrieve(hql);
		return list;
	}

	/**
	 * 获取非合同付款单信息
	 * @param prjId
	 * @param settlementIds
	 * @return
	 */
	public List<PrjSettlement> getComplete(String prjId, List<String> settlementIds) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("from " + PrjSettlement.class.getName() + " where settlementNo != Null and prjId = :prjId");
//		StringBuffer hql = new StringBuffer("from " + PrjSettlement.class.getName() + 
//				" where settlementStatus = :settlementStatus and prjId = :prjId");
//		param.put("settlementStatus", PrjSettlement.COMPLETE);
		param.put("prjId", prjId);
		if(settlementIds.size() > 0) {
			hql.append(" and settlementId not in (:settlementIds)");
			param.put("settlementIds", settlementIds.toArray());
		}
		hql.append(" order by settlementNo desc");
		return this.find(hql.toString(), param);
	}

}
