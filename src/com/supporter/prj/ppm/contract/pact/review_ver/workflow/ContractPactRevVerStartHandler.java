package com.supporter.prj.ppm.contract.pact.review_ver.workflow;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerService;

public class ContractPactRevVerStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractPactRevVerService service = (ContractPactRevVerService) SpringContextHolder.getBean(ContractPactRevVerService.class);
		String id = (String) execContext.getProcVar("revVerId");
		ContractPactRevVer entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(ContractPactRevVer.StatusCodeTable.EXAM);
		// 判断是否已存在评审验证编号，没有编号则生成
		String revVerNo = entity.getRevVerNo();
		if (StringUtils.isBlank(revVerNo)) {
			entity.setRevVerNo(service.generateRevVerNo());
		}
		service.update(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
