package com.supporter.prj.ppm.prj_op.prj_active.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.prj_op.prj_active.entity.PrjActive;

@Service
public class PrjActivePropRetriever extends AbstractPropRetriever {
  @Autowired
	private PrjActiveService service;
  
	public String getId() {
		return PrjActive.class.getName();
  }
  
	protected Object getEntity(int budgetYear, Object entityId) {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}