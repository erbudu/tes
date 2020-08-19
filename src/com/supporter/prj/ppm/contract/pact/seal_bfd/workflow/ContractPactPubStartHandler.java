package com.supporter.prj.ppm.contract.pact.seal_bfd.workflow;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublish;
import com.supporter.prj.ppm.contract.pact.seal_bfd.service.ContractPactPublishService;

public class ContractPactPubStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractPactPublishService service = (ContractPactPublishService) SpringContextHolder.getBean(ContractPactPublishService.class);
		String id = (String) execContext.getProcVar("publishId");
		ContractPactPublish entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(ContractPactPublish.StatusCodeTable.EXAM);
		// 判断是否已存在出版编号，没有编号则生成
		String publishNo = entity.getPublishNo();
		if (StringUtils.isBlank(publishNo)) {
			entity.setPublishNo(service.generatePublicNo());
		}
		service.update(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
