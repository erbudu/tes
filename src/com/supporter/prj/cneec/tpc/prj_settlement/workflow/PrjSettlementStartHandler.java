package com.supporter.prj.cneec.tpc.prj_settlement.workflow;


import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlement;
import com.supporter.prj.cneec.tpc.prj_settlement.service.PrjSettlementService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class PrjSettlementStartHandler extends AbstractExecHandler
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PrjSettlementService prjSettlementService = SpringContextHolder.getBean(PrjSettlementService.class);
		String settlementId = (String) execContext.getProcVar("settlementId");
		PrjSettlement prjSettlement = prjSettlementService.get(settlementId);
		prjSettlement.setProcId(execContext.getProcId());
		prjSettlementService.startProc(prjSettlement);
		return null;
	}
 
}