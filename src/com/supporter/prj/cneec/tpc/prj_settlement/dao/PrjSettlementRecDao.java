package com.supporter.prj.cneec.tpc.prj_settlement.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlementRec;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class PrjSettlementRecDao extends MainDaoSupport < PrjSettlementRec, String >{

	
	
	/**
	 * 根据费用支付id获取明细.
	 * 
	 * @author ts
	 * @param settlementId
	 * @return 本合同的结算总金额.
	 */
	public List<PrjSettlementRec> getBySettlementId(String settlementId){
		String hql = " from " + PrjSettlementRec.class.getName() + " where settlementId = ?";
		List<PrjSettlementRec> list = this.find(hql, settlementId);
		return list;
	}
	
	/**
	 * 通过采购合同付款主键获取明细表
	 * @param jqGrid
	 * @param ap
	 * @param recordId
	 * @return
	 */
	public List<PrjSettlementRec> getRecGrid(JqGrid jqGrid,String settlementId){
		if (StringUtils.isNotBlank(settlementId)) {
			jqGrid.addHqlFilter("settlementId ='" +settlementId +"'");
			List<PrjSettlementRec> list = this.retrievePage(jqGrid);
			return list;
		}else{
			return null;
		}
	}

}
