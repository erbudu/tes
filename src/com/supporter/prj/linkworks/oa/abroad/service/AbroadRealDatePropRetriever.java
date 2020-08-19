package com.supporter.prj.linkworks.oa.abroad.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadRealDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbroadRealDatePropRetriever extends AbstractPropRetriever
{

  @Autowired
  private AbroadRealDateService service;
  @Autowired
  private AbroadService dateService;
  public String getId()
  {
    return AbroadRealDate.class.getName();
  }

  protected Object getEntity(int budgetYear, Object realId)
  {
    if (realId == null) {
      return null;
    }
    AbroadRealDate  readDate = this.service.get(realId.toString());
    if(readDate!=null){
        Abroad ab = dateService.get(readDate.getAbroadId());
        readDate.setDeptId(ab.getApplierDeptId());
    }
//    System.out.println();
//    System.out.println(recordId+"111");
//    System.out.println();
//    System.out.println(this.service.get(recordId.toString()));
    return readDate;
  }

	public AbroadRealDatePropRetriever() {
		
	}
}