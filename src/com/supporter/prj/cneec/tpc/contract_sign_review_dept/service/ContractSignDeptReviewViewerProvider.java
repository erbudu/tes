package com.supporter.prj.cneec.tpc.contract_sign_review_dept.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class ContractSignDeptReviewViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ContractSignDeptReview.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/contract_sign_review_dept/contract_sign_dept_review_view.html?signId=" + entityId;
	}

}
