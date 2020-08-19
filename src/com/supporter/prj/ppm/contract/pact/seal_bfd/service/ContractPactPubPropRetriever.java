package com.supporter.prj.ppm.contract.pact.seal_bfd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublish;

@Service
public class ContractPactPubPropRetriever extends AbstractPropRetriever {
  @Autowired
	private ContractPactPublishService service;
  
	public String getId() {
		return ContractPactPublish.class.getName();
	}
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}