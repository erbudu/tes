package com.supporter.prj.ppm.contract.sign.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.contract.sign.review.entity.PpmContractSignReview;

@Service
public class ContractSignRevPropRetriever extends AbstractPropRetriever {
  @Autowired
	private PpmContractSignReviewService service;
  
	public String getId() {
		return PpmContractSignReview.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}