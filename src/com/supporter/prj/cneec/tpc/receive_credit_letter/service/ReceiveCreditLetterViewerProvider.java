package com.supporter.prj.cneec.tpc.receive_credit_letter.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.receive_credit_letter.entity.ReceiveCreditLetter;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class ReceiveCreditLetterViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ReceiveCreditLetter.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/receive_credit_letter/receive_credit_letter_view.html?receiveCreditLetterId=" + entityId;
	}

}
