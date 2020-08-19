package com.supporter.prj.ppm.poa.use_seal.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealStartEntity;
import com.supporter.prj.ppm.poa.use_seal.service.UseSealStartService;
import com.supporter.prj.ppm.service.PPMService;

/**
 * 审批驳回
 * @author CHENHAO
 *
 */

public class UseSealWFAbortHandler extends AbstractExecHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.supporter.prj.eip_service.workflow.AbstractExecHandler#execute(com.supporter.prj.eip_service.workflow.ExecContext)
	 */
	@Override
	public Object execute(ExecContext execContext) {
		
		UseSealStartService useSealStartService =  (UseSealStartService)SpringContextHolder.getBean(UseSealStartService.class);//获取模块的service
		
		String useSealId = (String)execContext.getProcVar("useSealId");//--------------------------------------------------------获取流程变量中的业务单主键
		
		UseSealStartEntity useSealStartEntity = useSealStartService.get(useSealId);//--------------------------------------------根据业务单主键获取业务实体类信息
		
		 if (useSealStartEntity != null) {
			useSealStartEntity.setProcId(execContext.getProcId());//--------------------------------------------------------------设 流程ID，改变流程状态
			useSealStartEntity.setStatus(UseSealConstant.WF_ABORT);//-------------------------------------------------------------流程审批终止(驳回状态),状态代码3
			useSealStartService.update(useSealStartEntity);//---------------------------------------------------------------------更新业务表单实体类信息
			PPMService.getScheduleStatusService().saveScheduleStatus(useSealStartEntity.getPrjId(), "preparation07", null);
		 }else {
			 EIPService.getLogService().error("无效的业务单主键useSealId："+useSealId);
		 }
		return null;
	}

	/* (non-Javadoc)
	 * @see com.supporter.prj.eip_service.workflow.AbstractExecHandler#getDesc()
	 */
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
