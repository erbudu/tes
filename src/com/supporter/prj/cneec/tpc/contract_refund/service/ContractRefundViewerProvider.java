package com.supporter.prj.cneec.tpc.contract_refund.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefund;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class ContractRefundViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ContractRefund.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/contract_refund/contract_refund_view.html?refundId=" + entityId;
	}

}
