package com.supporter.prj.pm.fund_appropriation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriation;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriationSwf;
import com.supporter.util.CommonUtil;

@Service
public class FundApproViewerProvider implements IBusiEntityViewerProvider {
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private FundAppropriationService service;
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return FundAppropriationSwf.class.getName();
	}
	
	public String getViewerUrl(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String fundId = CommonUtil.trim(entityId.toString());
		FundAppropriation fund = service.get(fundId);
		if (fund != null) {
			return "/pm/fund_appropriation/fundAppropriation_view.html?fundId=" + entityId;
		}
		return null;
	}
}
