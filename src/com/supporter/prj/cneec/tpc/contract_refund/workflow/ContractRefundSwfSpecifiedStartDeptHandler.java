package com.supporter.prj.cneec.tpc.contract_refund.workflow;

import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefund;
import com.supporter.prj.cneec.tpc.contract_refund.service.ContractRefundService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractRefundSwfSpecifiedStartDeptHandler
 * @Description: 工作流组织树起始部门程序指定类
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
public class ContractRefundSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		ContractRefundService contractRefundService = SpringContextHolder.getBean(ContractRefundService.class);
		String refundId = (String) execContext.getProcVar("refundId");
		ContractRefund contractRefund = contractRefundService.get(refundId);
		return contractRefund.getDeptId();
	}

}
