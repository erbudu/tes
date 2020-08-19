package com.supporter.prj.linkworks.oa.bank_account.service;


import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
@Service
public class BankAccountChangeApplyViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return BankAccountChangeApply.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/bank_account/bank_account_change_apply_view.html?changeApplyId=" + entityId+"&isProcPage=true";
	}

}
