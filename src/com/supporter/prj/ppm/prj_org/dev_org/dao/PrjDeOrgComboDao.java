package com.supporter.prj.ppm.prj_org.dev_org.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgCombo;

@Repository
public class PrjDeOrgComboDao extends MainDaoSupport < PrjDeOrgCombo, String >{
	
	/**
	 * <pre>分页查询,Grid列表显示数据</pre>
	 */
	public List<PrjDeOrgCombo> findPage(UserProfile user, JqGrid jqGrid, String prjId) {
		jqGrid.addHqlFilter("prjId = ? ",prjId);
		return this.retrievePage(jqGrid);
	}

	public PrjDeOrgCombo getByPrjId(String prjId) {
		String hql = " from "+ PrjDeOrgCombo.class.getName() 
				+" where prjId = ? ";
			List<PrjDeOrgCombo> editers = find(hql,prjId);
			if (editers == null || editers.size() == 0) {
				return null;
			}	
			return editers.get(0);	
	}	
	

}
