package com.supporter.prj.ppm.contract.sign.review_ver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.sign.review_ver.entity.ContractSignRevVer;

@Service
public class ContractSignRevVerPropRetriever extends AbstractPropRetriever {
  @Autowired
	private ContractSignRevVerService service;
  
	public String getId() {
		return ContractSignRevVer.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}