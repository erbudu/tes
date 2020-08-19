package com.supporter.prj.linkworks.oa.user_defined.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.user_defined.entity.BusinessRequirement;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class BusinessRequirementViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return BusinessRequirement.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/app_defined/business_requirement/business_requirement_view.html?businessRequirementId=" + entityId;
	}
	   
}
