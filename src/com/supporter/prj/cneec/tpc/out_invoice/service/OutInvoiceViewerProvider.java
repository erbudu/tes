package com.supporter.prj.cneec.tpc.out_invoice.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.out_invoice.entity.OutInvoice;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class OutInvoiceViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return OutInvoice.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/out_invoice/out_invoice_view.html?invoiceOutId=" + entityId;
	}

}
