package com.supporter.prj.ppm.poa.icc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.prj.ppm.poa.icc.entity.CoordinationResult;
import com.supporter.util.CommonUtil;



@Repository
public class CoordinationDao extends MainDaoSupport<Coordination, String>{
	public List<Coordination> findPage(UserProfile user, JqGrid jqGrid, Coordination Coordination,String prjId) {
		if(user==null)
		{
			return null;
		}
		if(user.getPersonId()!= null) {
			String personId =user.getPersonId();
			jqGrid.addHqlFilter(" createdById =? and prjId=?", personId,prjId);
		}
		return this.retrievePage(jqGrid);
	}
	public List<Coordination> getByPrjId(String prjId , UserProfile user)
	  {
	    String hql = "from " + Coordination.class.getName() + " where prjId=? ";
	    List<Coordination> list = find(hql, new Object[] { CommonUtil.trim(prjId)});
	    if ((list != null) && (list.size() > 0)) {
	      return list;
	    }
	    return null;
	   
	  }
}
