package com.supporter.prj.pm.reserve_fund.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.reserve_fund.entity.StockCash;


@Repository
public class StockCashDao extends MainDaoSupport < StockCash, String >{
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param qualityProblemIds 模块ids
	 * @return
	 */
	public List <StockCash> findPage(JqGrid jqGrid, StockCash stockCash,UserProfile user){
		if(stockCash != null){
			// 只获取某项目下的数据
			String prjId = stockCash.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
			return this.retrievePage(jqGrid);
		}else{
			return null;
		} 	
    }
	
	public StockCash getStockCashByCurrencyId(String currencyId){
		String hql = "from StockCash where currencyId ='" + currencyId + "'";
		return this.findUniqueResult(hql);
	}
	
	/**
	 * 数据库中是否存在记录.
	 * @param stockId 库存现金ID
	 * @return boolean
	 */
	public boolean existInDB(String stockId) {
		String hql = "select count(stockId) as recCount from "
				+ StockCash.class.getName() + " where stockId=?";
		Object obj = this.retrieveFirst(hql, stockId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
	
}
