package com.supporter.prj.cneec.tpc.supplier_refund.workflow;

import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefund;
import com.supporter.prj.cneec.tpc.supplier_refund.service.SupplierRefundService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: SupplierRefundSwfTaskSpecifiedHandler
 * @Description: 工作流普通任务人员程序指定类
 * @author: yanweichao
 * @date: 2017-11-22
 * @version: V1.0
 */
public class SupplierRefundSwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		SupplierRefundService supplierRefundService = SpringContextHolder.getBean(SupplierRefundService.class);
		String refundId = (String) execContext.getProcVar("refundId");
		SupplierRefund supplierRefund = supplierRefundService.get(refundId);		
		return supplierRefund.getCreatedById();
	}

}
