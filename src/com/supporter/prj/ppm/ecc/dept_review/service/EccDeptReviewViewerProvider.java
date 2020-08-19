package com.supporter.prj.ppm.ecc.dept_review.service;

import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class EccDeptReviewViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return EccDeptReview.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/ppm/index.html?docId=" + entityId;
	}
	   
}
