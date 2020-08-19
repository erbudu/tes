package com.supporter.prj.ppm.prj_org.base_info.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjAddr;

@Repository
public class PrjAddrDao extends MainDaoSupport < PrjAddr, String >{	
	/**
	 * 分页查询
	 */
	public List<PrjAddr> findPage(UserProfile user, JqGrid jqGrid, String prjId) {
		jqGrid.addHqlFilter("prjId = ? ",prjId);
		//权限过滤
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user,PrjConstant.MODULE_ID,AUTH_OPER_NAME_PAGESHOW);
		//jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}

	
	

}
