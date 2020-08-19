package com.supporter.prj.ppm.ecc.cac_review.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacReview;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EccCacReviewPropRetriever
  extends AbstractPropRetriever
{
  @Autowired
  private EccCacReviewService service;
  @Autowired
  private BaseInfoService baseInfoService;
  
  public String getId()
  {
    return EccCacReview.class.getName();
  }
  
  protected Object getEntity(int budgetYear, Object entityId)
  {
    if (entityId == null) {
      return null;
    }
    EccCacReview entity = this.service.get(entityId.toString());
    Prj prj = baseInfoService.initBaseInfoView(entity.getPrjId());
    if (prj!=null){
      entity.setPrjCName(prj.getPrjCName());
    }
    return entity;
  }
}