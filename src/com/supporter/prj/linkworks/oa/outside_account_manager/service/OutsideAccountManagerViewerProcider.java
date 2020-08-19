package com.supporter.prj.linkworks.oa.outside_account_manager.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManager;

@Service
public class OutsideAccountManagerViewerProcider implements
		IBusiEntityViewerProvider {

	public String getId() {
		return OutsideAccountManager.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/outside_account_manager/outside_account_view.html?managerId=" + entityId;
	}

}
