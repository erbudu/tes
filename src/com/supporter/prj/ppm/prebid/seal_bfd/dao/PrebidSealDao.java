package com.supporter.prj.ppm.prebid.seal_bfd.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSeal;

@Repository
public class PrebidSealDao extends MainDaoSupport<PpmPrebidSeal, String> {

	public List<PpmPrebidSeal> getSealIdByPrjId(String prjId) {
		List<PpmPrebidSeal> ppmPrebidReportList = this.findBy("prjId",prjId);
		if(ppmPrebidReportList.size()<1) {
			return null;
		}
		return ppmPrebidReportList;
	}

}
