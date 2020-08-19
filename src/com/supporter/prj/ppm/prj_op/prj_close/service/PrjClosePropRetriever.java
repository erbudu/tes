package com.supporter.prj.ppm.prj_op.prj_close.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.prj_op.prj_close.entity.PrjClose;

@Service
public class PrjClosePropRetriever extends AbstractPropRetriever {
  @Autowired
	private PrjCloseService service;
  
	public String getId() {
		return PrjClose.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}