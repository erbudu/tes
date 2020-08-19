package com.supporter.prj.cneec.tpc.drawback.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.drawback.entity.Drawback;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class DrawbackViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return Drawback.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/drawback/drawback_view.html?drawbackId=" + entityId;
	}

}
