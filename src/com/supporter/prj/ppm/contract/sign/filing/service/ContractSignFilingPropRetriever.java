package com.supporter.prj.ppm.contract.sign.filing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFiling;

@Service
public class ContractSignFilingPropRetriever extends AbstractPropRetriever {
  @Autowired
	private ContractFilingService service;
  
	public String getId() {
		return ContractFiling.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}