package com.supporter.prj.cneec.tpc.prj_settlement.workflow;

import java.util.Date;

import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlement;
import com.supporter.prj.cneec.tpc.prj_settlement.service.PrjSettlementService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class PrjSettlementEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PrjSettlementService prjSettlementService = SpringContextHolder.getBean(PrjSettlementService.class);
		String settlementId = (String) execContext.getProcVar("settlementId");
		PrjSettlement prjSettlement = prjSettlementService.get(settlementId);
		prjSettlementService.completeExam(prjSettlement);

		// 知会
		String[] personIds = new String[] {prjSettlement.getCreatedById(), prjSettlement.getPrjManagerId()};
		for (String personId : personIds) {
			Message message = new Todo();
			message.setPersonId(personId);
			message.setEventTitle("（知会）费用支付审批完成：" + prjSettlement.getProcTitle());
			message.setNotifyTime(new Date());
			message.setWebPageURL("/tpc/prj_settlement/prj_settlement_swf_notify.html?isCcPage=true&settlementId=" + settlementId);
			message.setMessageType(ITodo.MsgType.CC);
			message.setRelatedRecordTable(PrjSettlement.MODULE_ID);
			EIPService.getBMSService().addMessage(message);
		}
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}