package com.supporter.prj.cneec.tpc.tariff_vat_payment.workflow;

import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.service.TariffVatPaymentService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: TariffVatPaymentSwfSpecifiedStartDeptHandler
 * @Description: 工作流组织树起始部门程序指定类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
public class TariffVatPaymentSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		TariffVatPaymentService tariffVatPaymentService = SpringContextHolder.getBean(TariffVatPaymentService.class);
		String paymentId = (String) execContext.getProcVar("paymentId");
		TariffVatPayment tariffVatPayment = tariffVatPaymentService.get(paymentId);
		return tariffVatPayment.getDeptId();
	}

}
