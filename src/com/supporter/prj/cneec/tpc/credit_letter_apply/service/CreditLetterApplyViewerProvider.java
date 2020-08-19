package com.supporter.prj.cneec.tpc.credit_letter_apply.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 */
@Service
public class CreditLetterApplyViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return CreditLetterApply.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/credit_letter_apply/creditLetterApply_view.html?creditLetterId=" + entityId;
	}

}
