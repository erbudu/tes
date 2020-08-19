package com.supporter.prj.cneec.tpc.order_change.workflow;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderSeal;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class OrderSealAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OrderChangeService orderChangeService = (OrderChangeService) SpringContextHolder.getBean(OrderChangeService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		OrderSeal orderSeal = orderChangeService.getContractSealBySealId(sealId);
		orderSeal.setSwfStatus(OrderSeal.DRAFT);
		orderChangeService.update(orderSeal);
		return null;
	}

}