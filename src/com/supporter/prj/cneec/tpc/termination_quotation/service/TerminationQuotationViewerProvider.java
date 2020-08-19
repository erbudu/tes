package com.supporter.prj.cneec.tpc.termination_quotation.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.termination_quotation.entity.TerminationQuotation;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class TerminationQuotationViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return TerminationQuotation.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/termination_quotation/termination_quotation_view.html?quotationId=" + entityId;
	}

}
