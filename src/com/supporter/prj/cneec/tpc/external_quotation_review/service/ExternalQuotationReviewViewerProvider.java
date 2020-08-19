package com.supporter.prj.cneec.tpc.external_quotation_review.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class ExternalQuotationReviewViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ExternalQuotationReview.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/external_quotation_review/external_quotation_review_view.html?externalId=" + entityId;
	}

}
