package com.supporter.prj.ppm.poa.icc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.icc.entity.CoordinationResult;
import com.supporter.util.CommonUtil;

@Repository
public class CoordinationResultDao extends MainDaoSupport<CoordinationResult, String> {
	public CoordinationResult getByIccId(String iccId , UserProfile user)
	  {
	    String hql = "from " + CoordinationResult.class.getName() + " where ICC_ID=? ";
	    List list = find(hql, new Object[] { CommonUtil.trim(iccId)});
	    if ((list != null) && (list.size() > 0)) {
	      return (CoordinationResult)list.get(0);
	    }
	    return null;
	   
	  }
}
