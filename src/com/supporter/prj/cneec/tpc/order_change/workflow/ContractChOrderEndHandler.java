package com.supporter.prj.cneec.tpc.order_change.workflow;

import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.order_change.service.OrderChangeService;
import com.supporter.prj.cneec.tpc.project_goods_bill.service.ProjectGoodsBillService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class ContractChOrderEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OrderChangeService orderChangeService = (OrderChangeService) SpringContextHolder.getBean(OrderChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		ContractChOrder contractChOrder = orderChangeService.getContractOrderByChId(chId);
		contractChOrder.setSwfStatus(ContractChOrder.COMPLETED);
		contractChOrder.setProcId(execContext.getProcId());
		orderChangeService.update(contractChOrder);
		orderChangeService.change(null,contractChOrder);
//		orderChangeService.completeExam(contractChOrder);
		// 生成项目货品清单
		ProjectGoodsBillService billService = (ProjectGoodsBillService) SpringContextHolder.getBean(ProjectGoodsBillService.class);
		billService.saveProjectGoodsBillByOrderChange(contractChOrder);
		
		//改变变更单状态
		String changeId = contractChOrder.getChangeId();
		OrderChange orderChange = orderChangeService.get(changeId);
		orderChange.setSwfStatus(OrderChange.CONTRACTOK);
		orderChangeService.update(orderChange);
		return null;
	}

}