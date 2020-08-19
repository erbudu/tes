package com.supporter.prj.cneec.tpc.tariff_vat_payment.workflow;

import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.service.TariffVatPaymentService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: TariffVatPaymentAbortHandler
 * @Description: 流程中止操作类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
public class TariffVatPaymentAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		TariffVatPaymentService tariffVatPaymentService = (TariffVatPaymentService) SpringContextHolder.getBean(TariffVatPaymentService.class);
		String paymentId = (String) execContext.getProcVar("paymentId");
		TariffVatPayment tariffVatPayment = tariffVatPaymentService.get(paymentId);
		tariffVatPaymentService.abortProc(tariffVatPayment);
		return null;
	}

}