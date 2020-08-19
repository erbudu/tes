package com.supporter.prj.cneec.tpc.prj_contract_settlement.workflow;

import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlement;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.service.PrjContractSettlementService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class PrjContractSettlementEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PrjContractSettlementService service = SpringContextHolder.getBean(PrjContractSettlementService.class);
		String settlementId = (String) execContext.getProcVar("settlementId");
		PrjContractSettlement contractSettlement = service.get(settlementId);
		service.completeExam(contractSettlement);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}

}