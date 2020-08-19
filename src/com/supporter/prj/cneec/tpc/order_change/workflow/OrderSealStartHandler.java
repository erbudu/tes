package com.supporter.prj.cneec.tpc.order_change.workflow;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderSeal;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class OrderSealStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OrderChangeService orderChangeService = (OrderChangeService) SpringContextHolder.getBean(OrderChangeService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		OrderSeal orderSeal = orderChangeService.getContractSealBySealId(sealId);
		if (orderSeal != null) {
			orderSeal.setSwfStatus(OrderSeal.PROCESSING);
			orderSeal.setProcId(execContext.getProcId());
			orderChangeService.update(orderSeal);
		} else {
			EIPService.getLogService().error("无效的sealId:" + sealId);
		}
		return null;
	}

}