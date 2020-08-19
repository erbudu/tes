package com.supporter.prj.cneec.tpc.derivative_contract_change.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeConChange;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class DerivativeConChangeViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return DerivativeConChange.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/derivative_contract_change/contract_changeInformationView.html?chId=" + entityId;
	}

}
