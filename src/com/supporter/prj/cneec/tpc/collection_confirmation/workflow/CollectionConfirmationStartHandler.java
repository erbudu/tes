package com.supporter.prj.cneec.tpc.collection_confirmation.workflow;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionConfirmation;
import com.supporter.prj.cneec.tpc.collection_confirmation.service.CollectionConfirmationService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: CollectionConfirmationStartHandler
 * @Description: 流程开始操作类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class CollectionConfirmationStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		CollectionConfirmationService collectionConfirmationService = SpringContextHolder.getBean(CollectionConfirmationService.class);
		String collectionId = (String) execContext.getProcVar("collectionId");
		CollectionConfirmation collectionConfirmation = collectionConfirmationService.get(collectionId);
		if (collectionConfirmation != null) {
			collectionConfirmation.setSwfStatus(CollectionConfirmation.PROCESSING);
			collectionConfirmation.setProcId(execContext.getProcId());
			collectionConfirmationService.update(collectionConfirmation);
			collectionConfirmationService.startProc(collectionConfirmation);
		} else {
			EIPService.getLogService().error("无效的collectionId:" + collectionId);
		}
		return null;
	}

}