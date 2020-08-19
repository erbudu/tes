package com.supporter.prj.linkworks.oa.user_defined.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.user_defined.entity.Empchange;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class EmpchangeViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return Empchange.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/app_defined/empChange/empChange_view.html?empChangeId=" + entityId;
	}
	   
}
