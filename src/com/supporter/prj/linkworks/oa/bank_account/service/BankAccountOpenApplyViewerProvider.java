package com.supporter.prj.linkworks.oa.bank_account.service;


import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
@Service
public class BankAccountOpenApplyViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return BankAccountOpenApply.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/bank_account/bank_account_open_apply_view.html?applyId=" + entityId+"&isProcPage=true";
	}

}
