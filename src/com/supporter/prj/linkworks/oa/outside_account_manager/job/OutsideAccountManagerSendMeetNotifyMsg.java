package com.supporter.prj.linkworks.oa.outside_account_manager.job;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.role.entity.RoleMember;
import com.supporter.prj.eip.role.service.RoleMemberService;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.job_schedule.IJob;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;

public class OutsideAccountManagerSendMeetNotifyMsg implements IJob {

	private RoleMemberService getRoleMemberService(){
		return SpringContextHolder.getBean(RoleMemberService.class);
	}
	
	@Override
	public void execute(JobExecutionContext context) {
		// TODO Auto-generated method stub
		//获取外聘人员账号管理员
		List<RoleMember> roleMembers = getRoleMemberService().getList("OUTSIDE_ACCOUNT_MANAGER");
		if (roleMembers.size() > 0){
			for (RoleMember member : roleMembers){
				Message message = new Todo();
				message.setPersonId(member.getMemberId());
				message.setEventTitle(CommonUtil.format(new Date(), "yyyy-MM-dd")+"：请及时发布外聘人员账号维护！");
				message.setNotifyTime(new Date());
				message.setWebPageURL("oa/outside_account_manager/outside_account_remid_notify.html?isCcPage=true");
				message.setMessageType(ITodo.MsgType.CC);
				message.setRelatedRecordTable("OUTSIDEACCOU");
				EIPService.getBMSService().addMessage(message);
			}
		}
	}

}
