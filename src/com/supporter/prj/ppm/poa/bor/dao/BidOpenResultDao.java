package com.supporter.prj.ppm.poa.bor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.bor.entity.BidCurrency;
import com.supporter.prj.ppm.poa.bor.entity.BidOpenResult;
import com.supporter.util.CommonUtil;


@Repository
public class BidOpenResultDao extends MainDaoSupport<BidOpenResult, String> {
	public List<BidOpenResult> findPage(UserProfile user, JqGrid jqGrid, BidOpenResult bidOpenResult) {
		
			
		return this.retrievePage(jqGrid);
	}
		
	public List<BidCurrency> findPageCurrency(UserProfile user, JqGrid jqGrid, String  borId) {
		
		if (user == null) {
	    	return null;
	    } 

	    //Map params = new HashMap();
	    //System.out.println(params);

	    String hql = "from " + BidCurrency.class.getName()+" where BOR_ID=?" ;
       
		return this.retrieve(hql,new Object[] { CommonUtil.trim(borId)});
	}
	public BidOpenResult getByPrjId( String prjId) {
		String hql = " from " + BidOpenResult.class.getName() + 
			      " where PRJ_ID=? ";
		List list = find(hql, new Object[] { CommonUtil.trim(prjId) });

	    if ( (list.size() > 0)) {
	    	return (BidOpenResult) list.get(0);
	    } 
	    else {
	    	BidOpenResult bidOpenResult=new BidOpenResult();
	    	bidOpenResult.setBorId("F");
	    	return bidOpenResult;
	    	
	    }
	}

	
}
