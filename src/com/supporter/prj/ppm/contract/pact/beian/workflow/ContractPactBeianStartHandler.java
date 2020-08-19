package com.supporter.prj.ppm.contract.pact.beian.workflow;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeian;
import com.supporter.prj.ppm.contract.pact.beian.service.ContractPactBeianService;

public class ContractPactBeianStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractPactBeianService service = (ContractPactBeianService) SpringContextHolder.getBean(ContractPactBeianService.class);
		String id = (String) execContext.getProcVar("recordId");
		ContractPactBeian entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(ContractPactBeian.StatusCodeTable.EXAM);
		// 判断是否已存在备案编号，没有编号则生成
		String recordNo = entity.getRecordNo();
		if (StringUtils.isBlank(recordNo)) {
			entity.setRecordNo(service.generateBeianNo());
		}
		service.update(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
