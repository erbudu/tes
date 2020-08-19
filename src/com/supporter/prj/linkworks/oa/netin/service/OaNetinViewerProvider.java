package com.supporter.prj.linkworks.oa.netin.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;

@Service
public class OaNetinViewerProvider implements IBusiEntityViewerProvider {

	public String getId() {
		return OaNetin.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/netin/netin_view.html?netinId=" + entityId;
	}

}
