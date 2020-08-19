package com.supporter.prj.cneec.tpc.tariff_vat_payment.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: TariffVatPaymentSwfSpecifiedTreeHandler
 * @Description: 工作流组织树程序指定类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
public class TariffVatPaymentSwfSpecifiedTreeHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树程序指定
	@Override
	public Object execute(ExecContext execContext) {
		return "ORG_TREE";
	}

}
