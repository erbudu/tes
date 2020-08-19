package com.supporter.prj.cneec.tpc.record_filing_manager.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class RecordFilingManagerViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return RecordFilingManager.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/record_filing_manager/record_filing_manager_view.html?recordFilingId=" + entityId;
	}

}
