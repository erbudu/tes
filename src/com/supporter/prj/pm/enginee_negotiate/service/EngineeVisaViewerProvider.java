package com.supporter.prj.pm.enginee_negotiate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisa;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf;
import com.supporter.util.CommonUtil;

@Service
public class EngineeVisaViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private EngineeVisaService service;
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return EngineeVisaSwf.class.getName();
	}
	
	public String getViewerUrl(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		String visaId = CommonUtil.trim(entityId.toString());
		EngineeVisa engineeVisa = service.get(visaId);
		if(engineeVisa != null){
			return "/pm/enginee_negotiate/enginee_visa_view.html?visaId=" + entityId;
		}
		return null;
	}
}
