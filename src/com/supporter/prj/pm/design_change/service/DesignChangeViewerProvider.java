package com.supporter.prj.pm.design_change.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.pm.design_change.entity.DesignChange;
import com.supporter.util.CommonUtil;
@Service
public class DesignChangeViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private DesignChangeService service;
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return DesignChange.class.getName();
	}
	
	public String getViewerUrl(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		String applyId = CommonUtil.trim(entityId.toString());
		DesignChange designChange = service.getDesignChangeById(applyId);
		if(designChange != null){
			return "/pm/design_change/designChange_view.html?applyId=" + entityId;
		}
		return null;
	}
}
