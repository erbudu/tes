package com.supporter.prj.pm.external_drawings.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawings;
import com.supporter.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalDrawingsPropRetriever extends AbstractPropRetriever {
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private ExternalDrawingsService service;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return ExternalDrawings.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		 entityId = CommonUtil.trim(entityId.toString());
		ExternalDrawings externalDrawings = service.getExternalDrawingsById(entityId.toString());
		return externalDrawings;
	}
	   
}
