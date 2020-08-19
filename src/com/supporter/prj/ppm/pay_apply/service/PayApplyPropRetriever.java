package com.supporter.prj.ppm.pay_apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.pay_apply.entity.PayApply;

@Service
public class PayApplyPropRetriever extends AbstractPropRetriever{
	
  @Autowired
  private PayApplyService payApplyService;
/*  @Autowired
 private PublicProcDao procDao; */
  
  @Override
  public String getId(){
    return PayApply.class.getName();
  }
  
  @Override
  protected Object getEntity(int budgetYear, Object entityId){
    if (entityId == null) {
      return null;
    }
    
//    String id = CommonUtil.trim(entityId.toString());
//    PayApply payApply = payApplyService.get(id);
	// 将流程表set进来
	/*PublicProc publicProc = procDao.getPublicProcByEntityId(id);
	payApply.setPublicProc(publicProc);*/
    
    
    return this.payApplyService.get(entityId.toString());
  }
}