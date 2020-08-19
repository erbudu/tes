package com.supporter.prj.linkworks.oa.use_seal_apply.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.VUseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.util.AuthConstant;
import com.supporter.prj.linkworks.oa.use_seal_apply.util.UseSealApplyConstant;
@Repository
public class VUseSealApplyDao extends MainDaoSupport < VUseSealApply , String >{
	
	public List<VUseSealApply> getVUseSealApply(UserProfile user,JqGrid jqGrid,VUseSealApply vUseSealApply) {
		if (vUseSealApply != null) {
			String applyDept = vUseSealApply.getApplyDept();
			if (StringUtils.isNotBlank(applyDept)) {
				jqGrid
						.addHqlFilter(
								" applyDept like ? or signNo like ? or sealType like ? or createdDate like ? or useSealReason like ? or createdBy like ? ",
								"%" + applyDept + "%", "%" + applyDept + "%", "%"
										+ applyDept + "%", "%"+ applyDept + "%","%" + applyDept + "%","%" + applyDept + "%");
			}
			// 状态过滤
			if (vUseSealApply.getApplyStatus() != null) {
				jqGrid.addHqlFilter(" applyStatus= ? ",vUseSealApply.getApplyStatus());
			}
			jqGrid.addSortPropertyDesc("createdDate");
		}
		
		//权限过滤（判断获取数据范围的权限）
		String authHql="";
		authHql = EIPService.getAuthorityService().getHqlFilter(user,UseSealApplyConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME_AUTHUSESEALOFLIST );
		//System.out.println("authHql==="+authHql);
		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}
}
