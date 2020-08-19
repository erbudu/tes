package com.supporter.prj.cneec.tpc.external_quotation_review_dept.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class ExternalQuotationReviewDeptViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ExternalQuotationReviewDept.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/external_quotation_review_dept/external_quotation_review_dept_view.html?externalId=" + entityId;
	}

}
