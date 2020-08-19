package com.supporter.prj.ppm.prj_org.base_info.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjPfi;

@Repository
public class PrjPfiDao extends MainDaoSupport < PrjPfi, String >{	
	/**
	 * 分页查询
	 */
	public List<PrjPfi> findPage(UserProfile user, JqGrid jqGrid, String prjId) {
		jqGrid.addHqlFilter("prjId = ? ",prjId);
		//权限过滤
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user,PrjConstant.MODULE_ID,AUTH_OPER_NAME_PAGESHOW);
		//jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}
	
	public PrjPfi getByPrjId(String prjId) {
		String hql = " from "+ PrjPfi.class.getName() 
				+" where prjId = ? ";
			List<PrjPfi> editers = find(hql,prjId);
			if (editers == null || editers.size() == 0) {
				return null;
			}	
			return editers.get(0);	
	}	

	
	

}
