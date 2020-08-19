package com.supporter.prj.ppm.poa.icc.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinationPropRetriever extends AbstractPropRetriever
{

  @Autowired
  private CoordinationService service;

  public String getId()
  {
    return Coordination.class.getName();
  }

  protected Object getEntity(int budgetYear, Object iccId)
  {
    if (iccId == null) {
      return null;
    }
    return this.service.get(iccId.toString());
  }

	public CoordinationPropRetriever() {
		
	}
}