package com.supporter.prj.ppm.contract.pact.beian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeian;

@Service
public class ContractPactBeianPropRetriever extends AbstractPropRetriever {
  @Autowired
	private ContractPactBeianService service;
  
	public String getId() {
		return ContractPactBeian.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}