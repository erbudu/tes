package com.supporter.prj.linkworks.oa.use_seal_apply.service;


import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
@Service
public class UseSealApplyViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return UseSealApply.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/use_seal_apply/useSealApply_overall_view.html?applyId=" + entityId;
	}

}
