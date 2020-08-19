package com.supporter.prj.linkworks.oa.bank_account.service;


import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;
@Service
public class BankAccountRevokeApplyViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return BankAccountRevokeApply.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/bank_account/bank_account_change_undo_view.html?revokeId=" + entityId+"&isProcPage=true";
	}

}
