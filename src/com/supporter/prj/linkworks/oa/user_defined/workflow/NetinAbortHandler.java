package com.supporter.prj.linkworks.oa.user_defined.workflow;

import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;
import com.supporter.prj.linkworks.oa.user_defined.service.UNetinService;

public class NetinAbortHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		UNetinService service = (UNetinService) SpringContextHolder
				.getBean(UNetinService.class);
		String id = (String) execContext.getProcVar("id");
		UNetin entity = service.get(id);
		entity.setStatus(UNetin.DRAFT);
		service.update(entity);
		Message message = new Todo();//EIPService.getBMSService().newMessage();
		message.setPersonId(entity.getCreatedById());
		String title = "入网申请：不同意";
		// 待办标题
		message.setEventTitle(title);
		message.setNotifyTime(new Date());
		// 待办地址
		message
				.setWebPageURL("/oa/app_defined/netin/netin_audit_view_swf.html?isCcPage=true&netinId="
						+ entity.getId());
		message.setModuleId("NETIN");
		// 默认地指定为“待办”类型
		message.setMessageType(ITodo.MsgType.CC);
		// 加入待办事宜（BMS）
		message.setRelatedRecordTable("NETIN");
		message.setWfProcId(entity.getProcId());
		EIPService.getBMSService().addMessage(message);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
