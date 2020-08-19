package com.supporter.prj.ppm.prj_op.pact_close.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.ppm.prj_op.pact_close.entity.PactClose;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class PactCloseViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return PactClose.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/ppm/index.html?docId=" + entityId;
	}
	   
}
