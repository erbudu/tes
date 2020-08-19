package com.supporter.prj.ppm.contract.pact.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;

@Service
public class ContractPactReportPropRetriever extends AbstractPropRetriever {
  @Autowired
	private ContractPactReportService service;
  
	public String getId() {
		return ContractPactReport.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}