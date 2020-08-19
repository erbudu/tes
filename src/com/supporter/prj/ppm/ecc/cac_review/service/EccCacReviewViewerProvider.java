package com.supporter.prj.ppm.ecc.cac_review.service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacReview;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import org.springframework.stereotype.Service;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class EccCacReviewViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return EccCacReview.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/ppm/index.html?docId=" + entityId;
	}
	   
}
