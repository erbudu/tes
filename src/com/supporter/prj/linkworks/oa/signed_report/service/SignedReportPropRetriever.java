package com.supporter.prj.linkworks.oa.signed_report.service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignedReportPropRetriever extends AbstractPropRetriever
{

  @Autowired
  private SignedReportService service;

  public String getId()
  {
    return SignedReport.class.getName();
  }

  protected Object getEntity(int budgetYear, Object signedReportId)
  {
    if (signedReportId == null) {
      return null;
    }
//    System.out.println();
//    System.out.println(recordId+"111");
//    System.out.println();
//    System.out.println(this.service.get(recordId.toString()));
    return this.service.get(signedReportId.toString());
  }

	public SignedReportPropRetriever() {
		
	}
}