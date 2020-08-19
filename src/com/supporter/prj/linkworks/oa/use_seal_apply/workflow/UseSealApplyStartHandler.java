package com.supporter.prj.linkworks.oa.use_seal_apply.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.service.UseSealApplyService;

/**
 * 新闻动态流程启动时的Handler.
 * @author Arnold
 *
 */
public class UseSealApplyStartHandler extends AbstractExecHandler {

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
			useSealApply.setApplyStatus(Long.valueOf(UseSealApply.PROCESSING));
			
			useSealApply.setProcId(execContext.getProcId());
			
//			report.setApplyTime(new Date());
			useSealApplyService.update(useSealApply);		
		} else {
			EIPService.getLogService().error("无效的applyId:" + applyId);
		}
		
		return null;
	}


}
