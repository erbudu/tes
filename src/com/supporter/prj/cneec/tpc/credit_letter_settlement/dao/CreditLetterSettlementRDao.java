package com.supporter.prj.cneec.tpc.credit_letter_settlement.dao;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlementR;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadPerson;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class CreditLetterSettlementRDao extends MainDaoSupport < CreditLetterSettlementR, String > {
	
	/**
	 * 根据信用证付款id获取明细信息
	 * @param jqGrid
	 * @param creditLetterSettlementId
	 * @return
	 */
	public List<CreditLetterSettlementR> getRecGrid(JqGrid jqGrid,String creditLetterSettlementId){
		if (StringUtils.isNotBlank(creditLetterSettlementId)) {
			jqGrid.addHqlFilter("creditLetterSettlementId = ?  ", creditLetterSettlementId);
			return this.retrievePage(jqGrid);
		}else{
			return null;
		}
	}
	/**
	 * 根据信用证付款id获取明细信息
	 * @param jqGrid
	 * @param creditLetterSettlementId
	 * @return
	 */
	public List<CreditLetterSettlementR> getRecList(String creditLetterSettlementId){
		String hql = "from " + CreditLetterSettlementR.class.getName() + " where creditLetterSettlementId = ?  ";
		return this.find(hql, creditLetterSettlementId);
	}
	/**
	 * 修改时删除原有明细数据
	 * @param recordId
	 * @return
	 */
	public void deleteBySettlementId(String creditLetterSettlementId){	
		String hql = "from " + CreditLetterSettlementR.class.getName() + " where creditLetterSettlementId = ?  ";
		List<CreditLetterSettlementR> lists = this.find(hql, creditLetterSettlementId);
		this.delete(lists);
	}
}
