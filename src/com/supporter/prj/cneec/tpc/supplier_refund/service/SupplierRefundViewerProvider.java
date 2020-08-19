package com.supporter.prj.cneec.tpc.supplier_refund.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefund;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class SupplierRefundViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return SupplierRefund.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/supplier_refund/supplier_refund_view.html?refundId=" + entityId;
	}

}
