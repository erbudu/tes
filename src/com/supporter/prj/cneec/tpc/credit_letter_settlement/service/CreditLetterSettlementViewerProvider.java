package com.supporter.prj.cneec.tpc.credit_letter_settlement.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class CreditLetterSettlementViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return CreditLetterSettlement.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/credit_letter_settlement/credit_letter_settlement_view.html?creditLetterSettlementId=" + entityId;
	}

}
