package com.supporter.prj.cneec.tpc.order_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeStartHandler
 * @Description: 流程开始操作类
 */
public class OrderChangeStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OrderChangeService contractChangeService = (OrderChangeService) SpringContextHolder.getBean(OrderChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		OrderChange orderChange = contractChangeService.get(changeId);
		if (orderChange != null) {
			orderChange.setSwfStatus(ContractChange.PROCESSING);
			orderChange.setProcId(execContext.getProcId());
			contractChangeService.update(orderChange);
		} else {
			EIPService.getLogService().error("无效的changeId:" + changeId);
		}
		return null;
	}

}