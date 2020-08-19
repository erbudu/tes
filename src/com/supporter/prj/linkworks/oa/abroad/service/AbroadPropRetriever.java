package com.supporter.prj.linkworks.oa.abroad.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbroadPropRetriever extends AbstractPropRetriever
{

  @Autowired
  private AbroadService service;

  public String getId()
  {
    return Abroad.class.getName();
  }

  protected Object getEntity(int budgetYear, Object recordId)
  {
    if (recordId == null) {
      return null;
    }
//    System.out.println();
//    System.out.println(recordId+"111");
//    System.out.println();
//    System.out.println(this.service.get(recordId.toString()));
    return this.service.get(recordId.toString());
  }

	public AbroadPropRetriever() {
		
	}
}