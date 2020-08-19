package com.supporter.prj.ppm.poa.bor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.poa.bor.entity.BidCurrency;
import com.supporter.prj.ppm.poa.bor.entity.BidInf;
import com.supporter.util.CommonUtil;


@Repository
public class BidCurrencyDao extends MainDaoSupport<BidCurrency, String> {
	
	public List<BidCurrency> checkCurrency(String borId) {
		String hql = " from " + BidInf.class.getName() + 
			      " where BOR_ID=?";
			    List listCurrency = find(hql, new Object[] { CommonUtil.trim(borId) });

			    if ((listCurrency == null) || (listCurrency.size() == 0)) {
			    	return null;
			    } 
			    //BidInf supplier = (BidInf)listBidINf.get(0);
			    return listCurrency;
	}		
	public boolean  checkCurrencyName(String currenyId) {
		String hql = " from " + BidInf.class.getName() + 
			      " where CURRENCY_ID=?";
			    List listCurrency = find(hql, new Object[] { CommonUtil.trim(currenyId) });

			    if ((listCurrency == null) || (listCurrency.size() == 0)) {
			    	return false;
			    } 
			    //BidInf supplier = (BidInf)listBidINf.get(0);
			    return true;
	}		
		
	
	
}
