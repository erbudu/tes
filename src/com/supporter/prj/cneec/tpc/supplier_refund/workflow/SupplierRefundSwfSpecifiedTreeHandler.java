package com.supporter.prj.cneec.tpc.supplier_refund.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: SupplierRefundSwfSpecifiedTreeHandler
 * @Description: 工作流组织树程序指定类
 * @author: yanweichao
 * @date: 2017-11-22
 * @version: V1.0
 */
public class SupplierRefundSwfSpecifiedTreeHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树程序指定
	@Override
	public Object execute(ExecContext execContext) {
		return "ORG_TREE";
	}

}
