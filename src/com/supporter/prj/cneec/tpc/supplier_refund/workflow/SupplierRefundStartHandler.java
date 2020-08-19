package com.supporter.prj.cneec.tpc.supplier_refund.workflow;

import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefund;
import com.supporter.prj.cneec.tpc.supplier_refund.service.SupplierRefundService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: SupplierRefundStartHandler
 * @Description: 流程开始操作类
 * @author: yanweichao
 * @date: 2017-11-22
 * @version: V1.0
 */
public class SupplierRefundStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		SupplierRefundService supplierRefundService = SpringContextHolder.getBean(SupplierRefundService.class);
		String refundId = (String) execContext.getProcVar("refundId");
		SupplierRefund supplierRefund = supplierRefundService.get(refundId);
		if (supplierRefund != null) {
			supplierRefund.setSwfStatus(SupplierRefund.PROCESSING);
			supplierRefund.setProcId(execContext.getProcId());
			supplierRefundService.update(supplierRefund);
			supplierRefundService.startProc(supplierRefund);
		} else {
			EIPService.getLogService().error("无效的refundId:" + refundId);
		}
		return null;
	}

}