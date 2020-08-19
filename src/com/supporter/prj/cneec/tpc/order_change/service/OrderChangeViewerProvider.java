package com.supporter.prj.cneec.tpc.order_change.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class OrderChangeViewerProvider implements IBusiEntityViewerProvider {
	@Autowired
	private OrderChangeService service;
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return OrderChange.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		if(service.get(entityId.toString()).getChType()==1) {
			return "/tpc/order_change/contract_changeOrderViewAdd.html?changeId=" + entityId;
		}else if(service.get(entityId.toString()).getChType()==2) {
			return "/tpc/order_change/contract_adjustmentOrderViewAdd.html?changeId=" + entityId;
		}
		return null;
	}

}
