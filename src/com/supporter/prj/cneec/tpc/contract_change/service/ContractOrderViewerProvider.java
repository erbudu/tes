package com.supporter.prj.cneec.tpc.contract_change.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class ContractOrderViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ContractOrder.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/contract_change/contract_changeInformationView.html?chId=" + entityId;
	}

}
