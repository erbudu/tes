package com.supporter.prj.ppm.poa.bor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.bor.entity.BidPrice;
import com.supporter.util.CommonUtil;


@Repository
public class BidPriceDao extends MainDaoSupport<BidPrice, String> {
	
		public BidPrice getByThreeId(String borCurrencyId,String borId,String bidInfId){
			String hql = "from " + BidPrice.class.getName()+" where BID_CURRENCY_ID=? and BOR_ID=? and BID_INF_ID=?" ;
			 
				    List ListBidPrice = find(hql, new Object[] { CommonUtil.trim(borCurrencyId),CommonUtil.trim(borId),CommonUtil.trim(bidInfId) });

				    if ((ListBidPrice == null) || (ListBidPrice.size() == 0)) {
				    	return null;
				    } 
				    BidPrice bidPrices = (BidPrice)ListBidPrice.get(0);
				    return bidPrices;   
			
		}
		public List<BidPrice> getByBidInfId(String borId){
			String hql = " from " + BidPrice.class.getName() + 
				      " where Bid_Inf_Id=?";
				    List listBidPrice = find(hql, new Object[] { CommonUtil.trim(borId) });

				    if ((listBidPrice == null) || (listBidPrice.size() == 0)) {
				    	return null;
				    } 
				    //BidInf supplier = (BidInf)listBidINf.get(0);
				    return listBidPrice;
		}	
		public List<BidPrice> findPagePrice(UserProfile user, JqGrid jqGrid, String  bidInfId) {
			
			if (user == null) {
		    	return null;
		    } 

		    //Map params = new HashMap();
		    //System.out.println(params);

		    String hql = "from " + BidPrice.class.getName()+" where BID_INF_ID=?" ;
	       
			return this.retrieve(hql,new Object[] { CommonUtil.trim(bidInfId)});
		}

		public List<BidPrice> getByBorId(String borId){
			String hql = " from " + BidPrice.class.getName() + 
				      " where BOR_ID=?";
				    List listBidPrice = find(hql, new Object[] { CommonUtil.trim(borId) });

				    if ((listBidPrice == null) || (listBidPrice.size() == 0)) {
				    	return null;
				    } 
				    //BidInf supplier = (BidInf)listBidINf.get(0);
				    return listBidPrice;
		}	
	
}
