package com.supporter.prj.cneec.tpc.order_change.workflow;

import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeStartHandler
 * @Description: 流程开始操作类
 */
public class ContractChOrderStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OrderChangeService orderChangeService = (OrderChangeService) SpringContextHolder.getBean(OrderChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		ContractChOrder contractChOrder = orderChangeService.getContractOrderByChId(chId);
		if (contractChOrder != null) {
			contractChOrder.setSwfStatus(ContractChOrder.PROCESSING);
			contractChOrder.setProcId(execContext.getProcId());
			orderChangeService.update(contractChOrder);
		} else {
			EIPService.getLogService().error("无效的chId:" + chId);
		}
		return null;
	}

}