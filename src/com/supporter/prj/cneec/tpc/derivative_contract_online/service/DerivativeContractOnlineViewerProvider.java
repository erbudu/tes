package com.supporter.prj.cneec.tpc.derivative_contract_online.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractOnline;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class DerivativeContractOnlineViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return DerivativeContractOnline.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/derivative_contract_online/derivativeContractOnline_view.html?contractId=" + entityId;
	}

}
