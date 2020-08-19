package com.supporter.prj.pm.public_proc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.pm.public_proc.entity.PublicProc;

@Repository
public class PublicProcDao extends MainDaoSupport < PublicProc, String >{
	public PublicProc getPublicProcByEntityId(String entityId) {
		String hql = " from " + PublicProc.class.getName() + " where entityId='" + entityId + "'";
		List<PublicProc> publicProcList = find(hql);
		if (publicProcList == null || publicProcList.size() == 0)
			return null;
		return publicProcList.get(0);
	}
	
}
