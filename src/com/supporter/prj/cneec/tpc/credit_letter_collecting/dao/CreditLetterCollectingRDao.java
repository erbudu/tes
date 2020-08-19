package com.supporter.prj.cneec.tpc.credit_letter_collecting.dao;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollectingR;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class CreditLetterCollectingRDao extends MainDaoSupport < CreditLetterCollectingR, String > {
	
	/**
	 * 根据信用证付款id获取明细信息
	 * @param jqGrid
	 * @param creditLetterCollectingId
	 * @return
	 */
	public List<CreditLetterCollectingR> getRecGrid(JqGrid jqGrid,String creditLetterCollectingId){
		if (StringUtils.isNotBlank(creditLetterCollectingId)) {
			jqGrid.addHqlFilter("creditLetterCollectingId = ?  ", creditLetterCollectingId);
			return this.retrievePage(jqGrid);
		}else{
			return null;
		}
	}
	/**
	 * 根据信用证付款id获取明细信息
	 * @param jqGrid
	 * @param creditLetterCollectingId
	 * @return
	 */
	public List<CreditLetterCollectingR> getRecList(String creditLetterCollectingId){
		String hql = "from " + CreditLetterCollectingR.class.getName() + " where creditLetterCollectingId = ?  ";
		return this.find(hql, creditLetterCollectingId);
	}
	/**
	 * 修改时删除原有明细数据
	 * @param recordId
	 * @return
	 */
	public void deleteBySettlementId(String creditLetterCollectingId){	
		String hql = "from " + CreditLetterCollectingR.class.getName() + " where creditLetterCollectingId = ?  ";
		List<CreditLetterCollectingR> lists = this.find(hql, creditLetterCollectingId);
		this.delete(lists);
	}
}
