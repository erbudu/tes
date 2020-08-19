package com.supporter.prj.cneec.tpc.protocol_review.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class ProtocolReviewViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ProtocolReview.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/protocol_review/protocol_review_view.html?reviewId=" + entityId;
	}

}
