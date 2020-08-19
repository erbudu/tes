package com.supporter.prj.cneec.tpc.order_online.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class OrderOnlineViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return OrderOnline.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/order_online/order_online_view.html?orderId=" + entityId;
	}

}
