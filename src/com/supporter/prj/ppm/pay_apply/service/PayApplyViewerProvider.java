package com.supporter.prj.ppm.pay_apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.ppm.pay_apply.entity.PayApply;
import com.supporter.util.CommonUtil;

@Service
public class PayApplyViewerProvider implements IBusiEntityViewerProvider {
	
	 @Autowired
	  private PayApplyService payApplyService;
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return PayApply.class.getName();
	}

//	public String getViewerUrl(int budgetYear, Object entityId) {
//		return "/ppm/index.html?docId=" + entityId;
//	}
	
	
	public String getViewerUrl(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String id = CommonUtil.trim(entityId.toString());
		PayApply payApply = payApplyService.get(id);
		if (payApply != null) {
			return "/ppm/pay_apply/pay_apply_view.html?id=" + id;
		}
		return null;
	}
	
	
	
	
	
	
	   
}
