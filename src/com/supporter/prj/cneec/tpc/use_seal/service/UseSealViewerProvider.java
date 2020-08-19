package com.supporter.prj.cneec.tpc.use_seal.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.use_seal.entity.UseSeal;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class UseSealViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return UseSeal.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/use_seal/use_seal_view.html?sealId=" + entityId;
	}

}
