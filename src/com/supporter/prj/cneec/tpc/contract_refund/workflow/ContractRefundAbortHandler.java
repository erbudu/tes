package com.supporter.prj.cneec.tpc.contract_refund.workflow;

import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefund;
import com.supporter.prj.cneec.tpc.contract_refund.service.ContractRefundService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractRefundAbortHandler
 * @Description: 流程中止操作类
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
public class ContractRefundAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractRefundService contractRefundService = SpringContextHolder.getBean(ContractRefundService.class);
		String refundId = (String) execContext.getProcVar("refundId");
		ContractRefund contractRefund = contractRefundService.get(refundId);
		contractRefund.setSwfStatus(ContractRefund.DRAFT);
		contractRefundService.update(contractRefund);
		contractRefundService.abortProc(contractRefund);
		return null;
	}

}