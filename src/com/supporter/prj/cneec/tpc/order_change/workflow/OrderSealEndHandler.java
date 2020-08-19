package com.supporter.prj.cneec.tpc.order_change.workflow;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderSeal;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class OrderSealEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OrderChangeService orderChangeService = (OrderChangeService) SpringContextHolder.getBean(OrderChangeService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		OrderSeal orderSeal = orderChangeService.getContractSealBySealId(sealId);
		orderSeal.setSwfStatus(OrderSeal.COMPLETED);
		orderSeal.setProcId(execContext.getProcId());
		orderChangeService.update(orderSeal);
		//设置变更单状态
		String changeId = orderSeal.getChangeId();
		OrderChange orderChange = orderChangeService.get(changeId);
		orderChange.setSwfStatus(OrderChange.SEALOK);
		//用印审批完成给变更单赋值协议编号
		orderChange.setBusinessNo(orderSeal.getBusinessNo());
		orderChangeService.update(orderChange);
		return null;
	}

}