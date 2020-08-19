package com.supporter.prj.linkworks.oa.user_defined.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;

@Service
public class NetinPropRetriever
  extends AbstractPropRetriever
{
  @Autowired
  private UNetinService service;
  
  public String getId()
  {
    return UNetin.class.getName();
  }
  
  protected Object getEntity(int budgetYear, Object entityId)
  {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}