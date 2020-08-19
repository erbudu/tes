package com.supporter.prj.linkworks.oa.invitation_f.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationForeignerApply;
import com.supporter.prj.meip_service.news.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationPropRetriever
  extends AbstractPropRetriever
{
  @Autowired
  private InvitationForeignerApplyService service;
  
  public String getId()
  {
    return InvitationForeignerApply.class.getName();
  }
  
  protected Object getEntity(int budgetYear, Object entityId)
  {
    if (entityId == null) {
      return null;
    }
    return this.service.get(entityId.toString());
  }
}