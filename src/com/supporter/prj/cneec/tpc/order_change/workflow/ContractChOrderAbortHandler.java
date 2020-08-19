package com.supporter.prj.cneec.tpc.order_change.workflow;

import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeAbortHandler
 * @Description: 流程中止操作类
 */
public class ContractChOrderAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OrderChangeService orderChangeService = (OrderChangeService) SpringContextHolder.getBean(OrderChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		ContractChOrder contractChOrder = orderChangeService.getContractOrderByChId(chId);
		contractChOrder.setSwfStatus(ContractChOrder.DRAFT);
		orderChangeService.update(contractChOrder);
		return null;
	}

}