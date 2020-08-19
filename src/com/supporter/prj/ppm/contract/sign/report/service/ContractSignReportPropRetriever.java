package com.supporter.prj.ppm.contract.sign.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;

@Service
public class ContractSignReportPropRetriever extends AbstractPropRetriever {
  @Autowired
	private ContractSignReportService service;
  
	public String getId() {
		return ContractSignReport.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}