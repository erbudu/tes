package com.supporter.prj.linkworks.oa.seal_manage.engrave.workflow;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.entity.SealEngrave;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.service.SealEngraveService;
import com.supporter.util.CommonUtil;

public class SealEngraveEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;
	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		
		SealEngraveService sealEngraveService = SpringContextHolder.getBean(SealEngraveService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		SealEngrave sealEngrave =  sealEngraveService.get(applyId);
		sealEngrave.setStatus(SealEngrave.COMPLETED);
		sealEngrave.setProcId(execContext.getProcId());
		sealEngraveService.update(sealEngrave);
		sendNotify(sealEngrave);
		return null;
	} 
	
	/**
	 * 发送知会
	 * @param entity
	 */
	private void sendNotify(SealEngrave entity) {
		String title = "知会 印章刻制：" + entity.getSealName() + "审批完成！（"+entity.getDeptName()+"）";
		//获取知会人员
		String notifyPersonIds = "";
		SealEngraveService service = (SealEngraveService) SpringContextHolder.getBean(SealEngraveService.class);
		notifyPersonIds = service.getNotifyPersonIds(entity);
		
		if (StringUtils.isNotBlank(notifyPersonIds)) {
			for (String personId : notifyPersonIds.split(",")) {
				Message messageCreat =new Todo();
				messageCreat.setPersonId(personId);
				messageCreat.setEventTitle(title);
				messageCreat.setNotifyTime(new Date());
				messageCreat.setWebPageURL("/oa/seal_manage/engrave/sealEngrave_audit_notify.html?applyId="
						+ CommonUtil.trim(entity.getApplyId()));
				messageCreat.setMessageType(ITodo.MsgType.CC);
				messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
				EIPService.getBMSService().addMessage(messageCreat);
			}
		}
	}
}
