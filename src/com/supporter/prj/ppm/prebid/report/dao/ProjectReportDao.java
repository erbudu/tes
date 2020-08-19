package com.supporter.prj.ppm.prebid.report.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;

@Repository
public class ProjectReportDao extends MainDaoSupport<PpmPrebidReport, String> {
	public List<PpmPrebidReport> getPrebidReportListByPrjId(String prjId) {
		List<PpmPrebidReport> ppmPrebidReportList = this.findBy("prjId",prjId);
		if(ppmPrebidReportList.size()<1) {
			return null;
		}
		return ppmPrebidReportList;
	}
}
