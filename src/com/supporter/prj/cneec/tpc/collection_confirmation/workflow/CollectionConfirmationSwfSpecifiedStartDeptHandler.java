package com.supporter.prj.cneec.tpc.collection_confirmation.workflow;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionConfirmation;
import com.supporter.prj.cneec.tpc.collection_confirmation.service.CollectionConfirmationService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: CollectionConfirmationSwfSpecifiedStartDeptHandler
 * @Description: 工作流组织树起始部门程序指定类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class CollectionConfirmationSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		CollectionConfirmationService collectionConfirmationService = SpringContextHolder.getBean(CollectionConfirmationService.class);
		String collectionId = (String) execContext.getProcVar("collectionId");
		CollectionConfirmation collectionConfirmation = collectionConfirmationService.get(collectionId);
		return collectionConfirmation.getDeptId();
	}

}
