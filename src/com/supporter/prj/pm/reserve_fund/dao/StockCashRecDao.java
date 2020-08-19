package com.supporter.prj.pm.reserve_fund.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.bank_manage.entity.BankAccount;
import com.supporter.prj.pm.reserve_fund.entity.StockCashRec;

@Repository
public class StockCashRecDao extends MainDaoSupport < StockCashRec, String >{
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List <StockCashRec> findPage(JqGrid jqGrid, StockCashRec cashRec,UserProfile user,String stockId){
		if(cashRec != null){
			if(StringUtils.isNotBlank(stockId) ){
				jqGrid.addHqlFilter("stockId = ? ",stockId);
			}
		}
    	return this.retrievePage(jqGrid);
    }
	
	public List<StockCashRec> getStockCashRecByDate(Date startDate,Date endDate){
		Map <String, Object> params = new HashMap <String, Object>();
		String hql = "from " + StockCashRec.class.getName()+ " where 1 = 1";
		if (startDate != null) {
			params.put("startDate", startDate);
			hql += " and reserveDate >= :startDate";
		}
		if (endDate != null) {
			params.put("endDate", endDate);
			hql += " and reserveDate <= :endDate";
		}	
		return this.find(hql, params);
	}
}
