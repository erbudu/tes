package com.supporter.prj.cneec.tpc.supplier_refund.workflow;

import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefund;
import com.supporter.prj.cneec.tpc.supplier_refund.service.SupplierRefundService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: SupplierRefundAbortHandler
 * @Description: 流程中止操作类
 * @author: yanweichao
 * @date: 2017-11-22
 * @version: V1.0
 */
public class SupplierRefundAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		SupplierRefundService supplierRefundService = SpringContextHolder.getBean(SupplierRefundService.class);
		String refundId = (String) execContext.getProcVar("refundId");
		SupplierRefund supplierRefund = supplierRefundService.get(refundId);
		supplierRefund.setSwfStatus(SupplierRefund.DRAFT);
		supplierRefundService.update(supplierRefund);
		supplierRefundService.abortProc(supplierRefund);
		return null;
	}

}