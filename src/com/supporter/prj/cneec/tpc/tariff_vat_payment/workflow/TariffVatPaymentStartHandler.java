package com.supporter.prj.cneec.tpc.tariff_vat_payment.workflow;

import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.service.TariffVatPaymentService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: TariffVatPaymentStartHandler
 * @Description: 流程开始操作类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
public class TariffVatPaymentStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		TariffVatPaymentService tariffVatPaymentService = (TariffVatPaymentService) SpringContextHolder.getBean(TariffVatPaymentService.class);
		String paymentId = (String) execContext.getProcVar("paymentId");
		TariffVatPayment tariffVatPayment = tariffVatPaymentService.get(paymentId);
		if (tariffVatPayment != null) {
			tariffVatPayment.setProcId(execContext.getProcId());
			tariffVatPaymentService.startProc(tariffVatPayment);
		} else {
			EIPService.getLogService().error("无效的paymentId:" + paymentId);
		}
		return null;
	}

}