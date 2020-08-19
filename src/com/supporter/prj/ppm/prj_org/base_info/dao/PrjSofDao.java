package com.supporter.prj.ppm.prj_org.base_info.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjSof;

@Repository
public class PrjSofDao extends MainDaoSupport < PrjSof, String >{	
	/**
	 * 分页查询
	 */
	public List<PrjSof> findPage(UserProfile user, JqGrid jqGrid, String prjId) {
		jqGrid.addHqlFilter("projectId = ? ",prjId);
		//权限过滤
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user,PrjConstant.MODULE_ID,AUTH_OPER_NAME_PAGESHOW);
		//jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}

	
	

}
