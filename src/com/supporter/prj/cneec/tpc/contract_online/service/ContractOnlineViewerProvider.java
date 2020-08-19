package com.supporter.prj.cneec.tpc.contract_online.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.contract_online.entity.ContractOnline;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class ContractOnlineViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ContractOnline.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/contract_online/contract_online_view.html?contractId=" + entityId;
	}

}
