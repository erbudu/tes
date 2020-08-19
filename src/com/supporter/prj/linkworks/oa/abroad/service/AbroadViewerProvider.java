package com.supporter.prj.linkworks.oa.abroad.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class AbroadViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return Abroad.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/abroad/abroad_overall_view.html?recordId=" + entityId;
	}
	   
}
