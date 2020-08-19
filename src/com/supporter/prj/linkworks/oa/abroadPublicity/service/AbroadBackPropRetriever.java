package com.supporter.prj.linkworks.oa.abroadPublicity.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbroadBackPropRetriever extends AbstractPropRetriever
{

  @Autowired
  private AbroadBackService service;

  public String getId()
  {
    return AbroadBack.class.getName();
  }

  protected Object getEntity(int budgetYear, Object backId)
  {
    if (backId == null) {
      return null;
    }

    return this.service.get(backId.toString());
  }

	public AbroadBackPropRetriever() {
		
	}
}