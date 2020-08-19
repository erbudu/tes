package com.supporter.prj.ppm.poa.bor.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.poa.bor.entity.BidInf;
import com.supporter.prj.ppm.poa.bor.entity.BidNotify;
import com.supporter.util.CommonUtil;


@Repository
public class BidNotifyDao extends MainDaoSupport<BidNotify, String> {
	/*public List<BidOpenResult> findPage(UserProfile user, JqGrid jqGrid, BidOpenResult bidOpenResult) {
		
			
		return this.retrievePage(jqGrid);
	}*/
	public  BidNotify getByPrjId( String prjId) {
		String hql = " from " + BidNotify.class.getName() + 
			      " where PRJ_ID=?";
		List listBidNotify = find(hql, new Object[] { CommonUtil.trim(prjId) });

	    if ((listBidNotify == null) || (listBidNotify.size() == 0)) {
	    	return null;
	    } 
	    else {
	    	BidNotify bidNotify=(BidNotify)listBidNotify.get(0);
	    	return bidNotify;
	    }
	}
	public List<BidInf> getByBorId(String borId){
		String hql = " from " + BidInf.class.getName() + 
			      " where BOR_ID=?";
			    List listBidINf = find(hql, new Object[] { CommonUtil.trim(borId) });

			    if ((listBidINf == null) || (listBidINf.size() == 0)) {
			    	return null;
			    } 
			    //BidInf supplier = (BidInf)listBidINf.get(0);
			    return listBidINf;
	}		
	
	
}
