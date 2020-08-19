package com.supporter.prj.cneec.tpc.credit_letter_collecting.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollecting;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class CreditLetterCollectingViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return CreditLetterCollecting.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/credit_letter_collecting/credit_letter_collecting_view.html?creditLetterCollectingId=" + entityId;
	}

}
