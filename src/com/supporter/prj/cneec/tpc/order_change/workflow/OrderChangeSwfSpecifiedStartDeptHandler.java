package com.supporter.prj.cneec.tpc.order_change.workflow;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: OrderChangeSwfSpecifiedStartDeptHandler
 * @Description: 工作流组织树起始部门程序指定类
 */
public class OrderChangeSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		OrderChangeService orderChangeService = SpringContextHolder.getBean(OrderChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		OrderChange orderChange = orderChangeService.get(changeId);
		return orderChange.getDeptId();
	}

}
