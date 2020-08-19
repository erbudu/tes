package com.supporter.prj.cneec.tpc.prj_settlement.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlement;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class PrjSettlementViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return PrjSettlement.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/prj_settlement/prj_settlement_view.html?settlementId=" + entityId;
	}

}
