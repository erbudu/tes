package com.supporter.prj.cneec.tpc.invoice.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.invoice.entity.Invoice;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class InvoiceViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return Invoice.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/invoice/invoice_view.html?invoiceId=" + entityId;
	}

}
