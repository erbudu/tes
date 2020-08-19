package com.supporter.prj.ppm.contract.pact.report.workflow;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportService;

public class ContractPactReportStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractPactReportService service = (ContractPactReportService) SpringContextHolder.getBean(ContractPactReportService.class);
		String id = (String) execContext.getProcVar("reportId");
		ContractPactReport entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(ContractPactReport.StatusCodeTable.EXAM);
		entity.setSubmissionDate(new Date());
		// 判断是否已存在报审编号，没有编号则生成
		String reportNo = entity.getReportNo();
		if (StringUtils.isBlank(reportNo)) {
			entity.setReportNo(service.generateReportNo());
		}
		service.update(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
