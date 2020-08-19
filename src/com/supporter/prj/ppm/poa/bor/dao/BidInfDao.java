package com.supporter.prj.ppm.poa.bor.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.bor.entity.BidInf;
import com.supporter.util.CommonUtil;


@Repository
public class BidInfDao extends MainDaoSupport<BidInf, String> {
	
	
	public List<BidInf> getByBorId(String borId){
		String hql = " from " + BidInf.class.getName() + 
			      " where BOR_ID=? order by orderNo";
			    List listBidINf = find(hql, new Object[] { CommonUtil.trim(borId) });

			    if ((listBidINf == null) || (listBidINf.size() == 0)) {
			    	return null;
			    } 
			    //BidInf supplier = (BidInf)listBidINf.get(0);
			    return listBidINf;
	}		
	public List<BidInf> getByOrder(String borId){
		String hql = " from " + BidInf.class.getName() + 
			      " where BOR_ID=? order by  BID_VALIDITY_ID desc, EQUIVALENT_DOLLARS asc";
			    List listBidINf = find(hql,new Object[] { CommonUtil.trim(borId) });

			    if ((listBidINf == null) || (listBidINf.size() == 0)) {
			    	return null;
			    } 
			    return listBidINf;
	}
public List<BidInf> findPageInf(UserProfile user, JqGrid jqGrid, String  borId) {
		
		if (user == null) {
	    	return null;
	    } 

	    //Map params = new HashMap();

	    String hql = "from " + BidInf.class.getName()+" where BOR_ID=?" ;
       
		return this.retrieve(hql,new Object[] { CommonUtil.trim(borId)});
	}
	
	
}
