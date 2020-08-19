package com.supporter.prj.pm.external_drawings.service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawings;
import com.supporter.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalDrawingsViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private ExternalDrawingsService service;
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ExternalDrawings.class.getName();
	}
	
	public String getViewerUrl(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		String externalId = CommonUtil.trim(entityId.toString());
		ExternalDrawings requirement = service.getExternalDrawingsById(externalId);
		if(requirement != null){
			return "/pm/external_drawings/externalDrawings_view.html?externalId=" + externalId;
		}
		return null;
	}
}
