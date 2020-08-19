package com.supporter.prj.linkworks.oa.use_seal_apply.workflow;


import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.service.UseSealApplyService;
import com.supporter.util.CommonUtil;

/**
 * 新闻动态流程启动时的Handler.
 * @author Arnold
 *
 */
public class UseSealApplySendNoticeHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		UseSealApplyService useSealApplyService = SpringContextHolder.getBean(UseSealApplyService.class);
		
		String applyId = (String) execContext.getProcVar("applyId");
		UseSealApply useSealApply = useSealApplyService.get(applyId);
		if (useSealApply != null) {
			if(useSealApply.getCreatedById()!=null&&!useSealApply.getCreatedById().equals("")){
				Message messageCreat =new Todo();
			    messageCreat.setPersonId(useSealApply.getCreatedById());
			    String title = "知会（用印审批完成,请与综合管理部秘书处杨芳联系，电话：8556。）：部门："+useSealApply.getApplyDept()+" 申请人："+ useSealApply.getCreatedBy();
			    if(useSealApply.getStorageAndTransPretrial()!=null){
			    	 if(useSealApply.getStorageAndTransPretrial().equals("2")){
				    	 title = "知会（用印审批完成,请与储运部王箬溪联系，电话：8362。）：部门："+useSealApply.getApplyDept()+" 申请人："+ useSealApply.getCreatedBy();
				    }
			    }
			    if (UseSealApply.JWZS.equals(useSealApply.getSealTypeId())) {
			    	title = "知会（用印审批完成,请与审计部晁国喻联系，电话：8381。）：部门："+useSealApply.getApplyDept()+" 申请人："+ useSealApply.getCreatedBy();
			    }
				messageCreat.setEventTitle(title);
				messageCreat.setNotifyTime(new Date());
				messageCreat.setWebPageURL("/oa/use_seal_apply/useSealApply_overall_view.html?isSwf=zh&applyId="
						+ CommonUtil.trim(useSealApply.getApplyId()));
				messageCreat.setMessageType(ITodo.MsgType.CC);	
				messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
				EIPService.getBMSService().addMessage(messageCreat);
			}
		} else {
			EIPService.getLogService().error("无效的applyId:" + applyId);
		}
		
		return null;
	}


}
