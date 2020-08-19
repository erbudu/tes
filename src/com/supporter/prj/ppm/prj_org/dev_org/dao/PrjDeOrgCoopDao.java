package com.supporter.prj.ppm.prj_org.dev_org.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgCoop;

@Repository
public class PrjDeOrgCoopDao extends MainDaoSupport < PrjDeOrgCoop, String >{
	
	/**
	 * 分页查询
	 */
	public List<PrjDeOrgCoop> findPage(UserProfile user, JqGrid jqGrid, String prjId) {
		jqGrid.addHqlFilter("prjId = ? ",prjId);
		return this.retrievePage(jqGrid);
	}

	public PrjDeOrgCoop getByPrjId(String prjId) {
		String hql = " from "+ PrjDeOrgCoop.class.getName() 
				+" where prjId = ? ";
			List<PrjDeOrgCoop> editers = find(hql,prjId);
			if (editers == null || editers.size() == 0) {
				return null;
			}	
			return editers.get(0);	
	}	
	

}
