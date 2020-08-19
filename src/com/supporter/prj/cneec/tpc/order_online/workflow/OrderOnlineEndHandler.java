package com.supporter.prj.cneec.tpc.order_online.workflow;

import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.cneec.tpc.order_online.service.OrderOnlineService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: OrderOnlineEndHandler
 * @Description: 流程结束操作类
 * @author: yanweichao
 * @date: 2017-10-30
 * @version: V1.0
 */
public class OrderOnlineEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OrderOnlineService orderOnlineService = (OrderOnlineService) SpringContextHolder.getBean(OrderOnlineService.class);
		String orderId = (String) execContext.getProcVar("orderId");
		OrderOnline orderOnline = orderOnlineService.get(orderId);
		orderOnline.setSwfStatus(OrderOnline.COMPLETED);
		orderOnline.setProcId(execContext.getProcId());
		orderOnlineService.update(orderOnline);
		orderOnlineService.completeExam(orderOnline);
		return null;
	}

}