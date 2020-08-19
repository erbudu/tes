package com.supporter.prj.ppm.poa.inr.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.bor.entity.BidInf;
import com.supporter.prj.ppm.poa.inr.entity.NegotiationRecord;
import com.supporter.util.CommonUtil;


@Repository
public class NegotiationRecordDao extends MainDaoSupport<NegotiationRecord, String> {
	public List<NegotiationRecord> findPage(UserProfile user, JqGrid jqGrid, NegotiationRecord negotiationRecord,String prjId) {
		
		if (prjId == null) {
			return this.retrievePage(jqGrid);
	    } 

	    //Map params = new HashMap();
	    //System.out.println(params);
		else {
	    String hql = "from " + NegotiationRecord.class.getName()+" where prjId=?" ;
       
		return this.retrieve(hql,new Object[] { CommonUtil.trim(prjId)});}
		
	}
			
	
}
