package com.supporter.prj.pm.payment_onsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsite;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsiteSwf;
import com.supporter.util.CommonUtil;

@Service
public class PaymentOnsiteViewerProvider implements IBusiEntityViewerProvider {
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private PaymentOnsiteService service;
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return PaymentOnsiteSwf.class.getName();
	}
	
	public String getViewerUrl(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String id = CommonUtil.trim(entityId.toString());
		PaymentOnsite paymentOnsite = service.get(id);
		if (paymentOnsite != null) {
			return "/pm/payment_onsite/paymentOnsite_view.html?id=" + entityId;
		}
		return null;
	}
}
