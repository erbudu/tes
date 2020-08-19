package com.supporter.prj.cneec.tpc.prj_contract_settlement.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlementRec;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class PrjContractSettlementRecDao extends MainDaoSupport < PrjContractSettlementRec, String >{

	/**
	 * 通过采购合同付款主键获取明细表
	 * @param settlementId
	 * @return
	 */
	public List<PrjContractSettlementRec> getBySettlementId(String settlementId){
		String hql = "from " + PrjContractSettlementRec.class.getName()+ " where settlementId = ? ";
		List<PrjContractSettlementRec> managers = this.find(hql, settlementId);
		if (managers == null || managers.size() == 0){
			return null;			
		}
		return managers;
	}
	
	/**
	 * 通过采购合同付款主键获取明细表
	 * @param jqGrid
	 * @param ap
	 * @param recordId
	 * @return
	 */
	public List<PrjContractSettlementRec> getRecGrid(JqGrid jqGrid,String settlementId){
		if (StringUtils.isNotBlank(settlementId)) {
			jqGrid.addHqlFilter("settlementId ='" +settlementId +"'");
			List<PrjContractSettlementRec> list = this.retrievePage(jqGrid);
			return list;
		}else{
			return null;
		}
	}
	
}
