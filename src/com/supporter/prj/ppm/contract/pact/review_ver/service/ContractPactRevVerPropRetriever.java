package com.supporter.prj.ppm.contract.pact.review_ver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;

@Service
public class ContractPactRevVerPropRetriever extends AbstractPropRetriever {
  @Autowired
	private ContractPactRevVerService service;
  
	public String getId() {
		return ContractPactRevVer.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}