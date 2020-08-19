package com.supporter.prj.pm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.ITransition;
import com.supporter.prj.eip_service.workflow.IWfBusiEntity;
import com.supporter.prj.pm.constant.PmSwfBusiEntity;
import com.supporter.prj.pm.constant.PmSwfConstant;

@Service
public class PmSwfUtil {

	/**
	 * 启动流程
	 * @param swf
	 * @param creatorId
	 * @param innerName
	 */
	public static void startProc(PmSwfBusiEntity swf, String creatorId) {
		String procInnerName = PmSwfConstant.PM_SWF_PROC_INNER_NAME;
		startProc(swf, creatorId, procInnerName);
	}

	/**
	 * 启动流程
	 * @param swf
	 * @param creatorId
	 * @param innerName
	 */
	public static void startProc(PmSwfBusiEntity swf, String creatorId, String innerName) {
		String procInnerName = innerName != null ? innerName : PmSwfConstant.PM_SWF_PROC_INNER_NAME;
		String procTitle = swf.getProcTitle();
		Map<String, Object> extraProcVars = null;
		Map<String, ?> pageParams = null;
		String nextActorId = "";
		List<IWfBusiEntity> entitys = new ArrayList<IWfBusiEntity>();
		entitys.add(swf);
		// 调用系统启动流程接口
		String procId = EIPService.getWfService().startProc(procInnerName, procTitle, swf, extraProcVars, creatorId);
		// 跳至经办人下一节点
		List<String> taskInstanceIds = EIPService.getWfService().getActiveTaskInstanceIds(procId);
		for (int i = 0; i < taskInstanceIds.size(); i++) {
			String taskInstanceId = taskInstanceIds.get(i);
			List<ITransition> trans = EIPService.getWfService().getLeavingTransitions(taskInstanceId);
			for (int j = 0; j < trans.size(); j++) {
				EIPService.getWfService().completeTask(taskInstanceId, creatorId, trans.get(j).getName(), entitys,
						pageParams, extraProcVars, nextActorId);
			}
		}
	}

}
