package com.supporter.prj.ppm.contract.pact.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;

@Service
public class ContractPactReviewPropRetriever extends AbstractPropRetriever {
  @Autowired
	private ContractPactReviewService service;
  
	public String getId() {
		return ContractPactReview.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}