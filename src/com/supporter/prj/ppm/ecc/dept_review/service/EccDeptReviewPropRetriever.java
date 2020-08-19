package com.supporter.prj.ppm.ecc.dept_review.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EccDeptReviewPropRetriever
  extends AbstractPropRetriever
{
  @Autowired
  private EccDeptReviewService service;
  @Autowired
  private BaseInfoService baseInfoService;
  public String getId()
  {
    return EccDeptReview.class.getName();
  }
  
  protected Object getEntity(int budgetYear, Object entityId)
  {
    if (entityId == null) {
      return null;
    }
    EccDeptReview entity = this.service.get(entityId.toString());
    Prj prj = baseInfoService.initBaseInfoView(entity.getProjectId());
    if (prj!=null){
      entity.setPrjNameC(prj.getPrjCName());
    }
    return entity;
  }
}