package com.supporter.prj.cneec.tpc.order_online.workflow;

import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.cneec.tpc.order_online.service.OrderOnlineService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: OrderOnlineStartHandler
 * @Description: 工作流普通任务人员程序指定类
 * @author: yanweichao
 * @date: 2017-10-30
 * @version: V1.0
 */
public class OrderOnlineSwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		OrderOnlineService orderOnlineService = SpringContextHolder.getBean(OrderOnlineService.class);
		String orderId = (String) execContext.getProcVar("orderId");
		OrderOnline orderOnline = orderOnlineService.get(orderId);		
		return orderOnline.getCreatedById();
	}

}
