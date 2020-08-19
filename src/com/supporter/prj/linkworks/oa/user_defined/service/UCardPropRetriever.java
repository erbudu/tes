package com.supporter.prj.linkworks.oa.user_defined.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.user_defined.entity.UCard;

@Service
public class UCardPropRetriever
  extends AbstractPropRetriever
{
  @Autowired
  private UCardService service;
  
  public String getId()
  {
    return UCard.class.getName();
  }
  
  protected Object getEntity(int budgetYear, Object entityId)
  {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}