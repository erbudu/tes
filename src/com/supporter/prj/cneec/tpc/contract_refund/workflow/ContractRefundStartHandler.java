package com.supporter.prj.cneec.tpc.contract_refund.workflow;

import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefund;
import com.supporter.prj.cneec.tpc.contract_refund.service.ContractRefundService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractRefundStartHandler
 * @Description: 流程开始操作类
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
public class ContractRefundStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractRefundService contractRefundService = SpringContextHolder.getBean(ContractRefundService.class);
		String refundId = (String) execContext.getProcVar("refundId");
		ContractRefund contractRefund = contractRefundService.get(refundId);
		if (contractRefund != null) {
			contractRefund.setSwfStatus(ContractRefund.PROCESSING);
			contractRefund.setProcId(execContext.getProcId());
			contractRefundService.update(contractRefund);
			contractRefundService.startProc(contractRefund);
		} else {
			EIPService.getLogService().error("无效的refundId:" + refundId);
		}
		return null;
	}

}