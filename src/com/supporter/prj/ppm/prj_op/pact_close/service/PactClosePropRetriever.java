package com.supporter.prj.ppm.prj_op.pact_close.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.prj_op.pact_close.entity.PactClose;

@Service
public class PactClosePropRetriever extends AbstractPropRetriever {
  @Autowired
	private PactCloseService service;
  
	public String getId() {
		return PactClose.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}